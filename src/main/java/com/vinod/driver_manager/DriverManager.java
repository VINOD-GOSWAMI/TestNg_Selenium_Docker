package com.vinod.driver_manager;

import com.vinod.config.ConfigKeys;
import com.vinod.config.ConfigLoader;
import com.vinod.exception.PropertyNotFoundException;
import com.vinod.utility.Context;
import com.vinod.utility.ContextKeys;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public WebDriver initializeDriver(BrowserMode mode, boolean isRemoteExecution) throws MalformedURLException, PropertyNotFoundException {
        log.info("Initializing driver for mode: {}, Remote execution: {}", mode, isRemoteExecution);
        WebDriver driverInstance = createWebDriver(mode, isRemoteExecution);
        configureDriver(driverInstance);
        driver.set(driverInstance);
        log.info("{} driver instantiated successfully.", mode);
        return driverInstance;
    }

    private void configureDriver(WebDriver driverInstance) {
        log.debug("Configuring driver with timeouts and window settings.");
        driverInstance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverInstance.manage().window().maximize();
        log.debug("Driver configuration completed.");
    }

    public static void quitDriver() {
        WebDriver driverInstance = driver.get();
        if (driverInstance != null) {
            log.info("Quitting the driver: {}", driverInstance);
            driverInstance.quit();
            driver.remove();
            log.info("Driver closed successfully.");
        } else {
            log.error("Attempted to quit the driver, but it was not initialized.");
        }
    }

    private Capabilities getDesiredCapabilities(BrowserMode mode) throws PropertyNotFoundException {
        DesiredCapabilities additionalCap=getAdditionalCapabilities();
        Capabilities capabilities;
        switch (mode) {
            case CHROME:
                capabilities =new ChromeOptions().merge(additionalCap);
                break;
            case CHROME_MOBILE:
                capabilities = getChromeMobileCapabilities().merge(additionalCap);
                break;
            default:
                throw new IllegalArgumentException("Browser Name is Not Correct: " + mode);
        }
        return capabilities;

    }

    private DesiredCapabilities getAdditionalCapabilities() throws PropertyNotFoundException {
        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
        desiredCapabilities.setCapability("se:name", (String) Context.get(ContextKeys.VIDEO_FILE_NAME));
        desiredCapabilities.setCapability("se:recordVideo",  ConfigLoader.getInstance().getProperty(ConfigKeys.REMOTEVIDEORECORD));
        return desiredCapabilities;
    }

    private Capabilities getChromeMobileCapabilities() {
        ChromeOptions options = new ChromeOptions();
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Pixel 2");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        return options;
    }


    private WebDriver createWebDriver(BrowserMode mode, boolean isRemote) throws PropertyNotFoundException {
        log.debug("Creating WebDriver instance. Mode: {}, Is Remote: {}", mode, isRemote);
        return isRemote ? createRemoteWebDriver(mode) : createLocalWebDriver(mode);
    }


    private WebDriver createRemoteWebDriver(BrowserMode mode) throws PropertyNotFoundException {
        log.info("Creating RemoteWebDriver for mode: {}", mode);
        Capabilities capabilities = getDesiredCapabilities(mode);
        log.info("Capabilities for Remote WebDriver: {}", capabilities);
        try {
            String hubUrl = "http://" + Optional.ofNullable(System.getenv("HUB_HOST")).filter(h -> !h.isEmpty()).orElse("localhost") + ":4444/wd/hub";
            log.info("hub url : {}",hubUrl);
            URL remoteUrl = new URL(hubUrl);
            log.debug("Connecting to Remote WebDriver at URL: {}", remoteUrl);
            return new RemoteWebDriver(remoteUrl, capabilities);
        } catch (MalformedURLException e) {
            log.error("Invalid Remote WebDriver URL: {}", e.getMessage());
            throw new RuntimeException("Invalid Remote WebDriver URL", e);
        }
    }

    private WebDriver createLocalWebDriver(BrowserMode mode) {
        log.info("Creating local WebDriver for mode: {}", mode);
        switch (mode) {
            case CHROME:
                log.debug("Instantiating ChromeDriver.");
                return new ChromeDriver(getChromeOptions());
            case CHROME_MOBILE:
                log.debug("Instantiating ChromeDriver with mobile emulation.");
                return new ChromeDriver(getMobileChromeOptions());
            case EDGE:
                log.debug("Instantiating EdgeDriver.");
                return new EdgeDriver();
            default:
                log.error("Unsupported mode: {}", mode);
                throw new IllegalArgumentException("Unsupported mode: " + mode);
        }
    }

    private ChromeOptions getChromeOptions() {
        log.debug("Creating default ChromeOptions.");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    private ChromeOptions getMobileChromeOptions() {
        log.debug("Creating ChromeOptions with mobile emulation.");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", getMobileEmulationOptions());
        return options;
    }

    private Map<String, String> getMobileEmulationOptions() {
        log.debug("Setting up mobile emulation options.");
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Pixel 2");
        return mobileEmulation;
    }

}
