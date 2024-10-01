package com.vinod.test;

import com.vinod.pages.HomePage;
import com.vinod.pages.LoginPage;
import com.vinod.utility.CommonUtility;
import com.vinod.utility.ExtentReportManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Slf4j
public class LoginTest extends BaseTest {

    @Test(description = "Tests the login page navigation and related links.")
    public void testLoginPageAndLinks() throws InterruptedException {HomePage homePage =  new HomePage(driver);
        if (homePage.isNavBarVisible()) {
            homePage.clickOnNavBar();
            ExtentReportManager.logInfo("Clicked on Nav Bar.");
        }
        log.info("Home page title: {}", driver.getTitle());

        String originalTab = driver.getWindowHandle();
        String originalTitle=driver.getTitle();
        homePage.clickLogin();
        for (String tab : driver.getWindowHandles()) {
            if (!originalTab.equalsIgnoreCase(tab)) {
                driver.switchTo().window(tab);
            }
        }
        log.info("Clicked on login: current page title {}", driver.getTitle());
        LoginPage loginPage = new LoginPage(driver);
        log.info("Login page title: {}", driver.getTitle());
        verifyLoginPage(loginPage);
        driver.switchTo().window(originalTab);
        Assert.assertEquals(driver.getTitle(), originalTitle, "Failed to switch back to the home page.");

        verifyLinksOnHomePage(homePage);
    }

    @Test(description = "Verifies all links on the Home Page.")
    public void verifyingLinksOnHomePage() {
        HomePage homePage = new HomePage(driver);
        verifyLinksOnHomePage(homePage);
    }

    private void verifyLoginPage(LoginPage loginPage) {
        Assert.assertTrue(loginPage.isLoginPageOpened(), "Login page not opened.");
        Assert.assertTrue(loginPage.isEmailFieldPresent(), "Email field not present.");
        Assert.assertTrue(loginPage.isPasswordFieldPresent(), "Password field not present.");
    }

    private void verifyLinksOnHomePage(HomePage homePage) {
        List<String> links = homePage.getAllLinks();
        CommonUtility.verifyLinks(links);
    }

}
