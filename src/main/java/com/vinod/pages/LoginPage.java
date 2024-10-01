package com.vinod.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Slf4j
public class LoginPage extends BasePage {
    private final By emailField = By.xpath("//input[@placeholder='Email']");
    private final By passwordField = By.id("password");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginPageOpened() {
        return driver.getTitle().contains("Login");
    }

    public boolean isEmailFieldPresent() {
        return driver.findElement(emailField).isDisplayed();
    }

    public boolean isPasswordFieldPresent() {
        return driver.findElement(passwordField).isDisplayed();
    }
}
