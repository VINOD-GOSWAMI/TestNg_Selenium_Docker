package com.vinod.pages;

import com.vinod.utility.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HomePage extends BasePage {
    private final By loginButton = By.linkText("Login");
    private final By navBarButton = By.xpath("//nav//button");
    public HomePage(WebDriver driver) {
        super(driver);
    }
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    public void clickOnNavBar() {
        driver.findElement(navBarButton).click();
    }

    public synchronized List<String> getAllLinks() {
        WaitUtil.waitForPresence(By.tagName("a"));
        List<WebElement> links = driver.findElements(By.tagName("a"));
        List<String> linkUrls = new ArrayList<>();
        for (WebElement link : links) {
            try {
                String url = WaitUtil.waitForAttributeValue(link,"href");
                if (url != null && !url.isEmpty()) {
                    linkUrls.add(url);
                }
            } catch (Exception e) {
                log.info("Failed to fetch 'href' for an element, skipping it. Error: " + e.getMessage());
            }
        }
        return linkUrls;
    }

    public boolean isNavBarVisible() {
         return driver.findElement(navBarButton).isDisplayed();
    }
}
