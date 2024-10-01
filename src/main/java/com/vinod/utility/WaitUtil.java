package com.vinod.utility;

import com.vinod.driver_manager.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {
    private static WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
    }

    // Wait for an element to be visible
    public static WebElement waitForVisibility(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for an element to be clickable
    public static WebElement waitForClickability(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait for an element to be present in the DOM
    public static WebElement waitForPresence(By locator) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Wait for specific text to be present in an element
    public static void waitForTextToBePresent(By locator, String text) {
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // Wait for an element to be selected
    public static void waitForElementToBeSelected(By locator) {
        getWait().until(ExpectedConditions.elementToBeSelected(locator));
    }


    public static String waitForAttributeValue(WebElement element, String attribute) {
        return getWait().until(driver -> {
            String value = element.getAttribute(attribute);
            return (value != null && !value.isEmpty()) ? value : null;
        });
    }
    // Wait for an element to be enabled
    public static void waitForElementToBeEnabled(By locator) {
        getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait for an element to be disabled
    public static void waitForElementToBeDisabled(By locator) {
        getWait().until(driver -> {
            WebElement element = driver.findElement(locator);
            return !element.isEnabled();
        });
    }

}
