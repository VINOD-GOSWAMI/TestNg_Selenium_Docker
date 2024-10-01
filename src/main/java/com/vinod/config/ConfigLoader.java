package com.vinod.config;

import com.vinod.exception.PropertyNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConfigLoader {
    private static final Properties properties = new Properties();
    private static final ConcurrentHashMap<String, String> propertiesMap = new ConcurrentHashMap<>();
    private static final ConfigLoader instance = new ConfigLoader();

    private ConfigLoader() {}

    public static ConfigLoader getInstance() {
        return instance;
    }

    public void loadProperties() {
        String environment = System.getProperty("env");
        log.info("Application loaded with following env : {}",environment);
        // Load default properties
        loadResource("application.properties");

        // Load environment-specific properties, if provided
        if (environment != null && !environment.isEmpty()) {
            loadResource("application-" + environment + ".properties");
        }

        // Override with system properties
        overrideWithSystemProperties();

        // Populate properties map for case-insensitive access
        populatePropertiesMap();
        log.info("Runtime properties {}",propertiesMap);
    }

    private void loadResource(String resourcePath) {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input != null) {
                properties.load(input);
                log.info("Properties loaded for {} with following values {}",resourcePath,properties);
            } else {
                throw new IOException("Property file '" + resourcePath + "' not found in the classpath.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from " + resourcePath, e);
        }
    }

    private void populatePropertiesMap() {
        properties.forEach((key, value) ->
                propertiesMap.put(key.toString().toLowerCase(), value.toString())
        );
    }

    private void overrideWithSystemProperties() {
        System.getProperties().forEach((key, value) -> {
            String lowerKey = key.toString().toLowerCase();
            properties.setProperty(key.toString(), value.toString());
            propertiesMap.put(lowerKey, value.toString());
        });
    }

    public String getProperty(String key) throws PropertyNotFoundException {
        String value = propertiesMap.get(key.toLowerCase());
        if (value == null) {
            log.error("Property '{}' not found. Returning default value.", key);
            throw new PropertyNotFoundException("Property '" + key + "' not found in the properties.");
        }
        return value;
    }
}
