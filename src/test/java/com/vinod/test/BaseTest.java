package com.vinod.test;

import com.vinod.config.ConfigKeys;
import com.vinod.config.ConfigLoader;
import com.vinod.driver_manager.BrowserMode;
import com.vinod.driver_manager.DriverManager;
import com.vinod.exception.PropertyNotFoundException;
import com.vinod.listners.TestListener;
import com.vinod.utility.Context;
import com.vinod.utility.ContextKeys;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

@Listeners(TestListener.class)
@Slf4j
public class BaseTest{
    protected WebDriver driver;
    ConfigLoader configLoader=ConfigLoader.getInstance();
    @BeforeSuite
    public void globalSetUp() {
        ConfigLoader.getInstance().loadProperties();
    }

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException, PropertyNotFoundException {
        Context.set(ContextKeys.VIDEO_FILE_NAME,method.getName()+System.currentTimeMillis());
        log.info("Initializing driver for method: {}", method.getName());
        driver = new DriverManager().initializeDriver(BrowserMode.valueOf(configLoader.getProperty(ConfigKeys.BROWSER)), Boolean.parseBoolean(ConfigLoader.getInstance().getProperty(ConfigKeys.ISREMOTE)));
        navigateToBaseUrl();
    }

    private void navigateToBaseUrl() throws PropertyNotFoundException {
        driver.get(ConfigLoader.getInstance().getProperty(ConfigKeys.BASEURL));
        log.info("Navigated to base URL: {}", configLoader.getProperty(ConfigKeys.BASEURL));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
