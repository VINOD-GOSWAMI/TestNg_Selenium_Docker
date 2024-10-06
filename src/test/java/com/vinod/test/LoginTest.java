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
    public void testLoginPageAndLinks() throws InterruptedException {
        HomePage homePage =  new HomePage(driver);
        handleNavBar(homePage);
        log.info("Home page title: {}", driver.getTitle());
        String originalTitle=driver.getTitle();
        homePage.clickLogin();
        switchToPage("login");
        LoginPage loginPage = new LoginPage(driver);
        log.info("Login page title: {}", driver.getTitle());
        verifyLoginPage(loginPage);
        switchToPage("demo");
        Assert.assertEquals(driver.getTitle(), originalTitle, "Failed to switch back to the home page.");
        verifyLinksOnHomePage(homePage);
    }


    @Test(description = "Tests  login page & navigate back.")
    public void testLoginPageAndNavigateBack() throws InterruptedException {
        HomePage homePage =  new HomePage(driver);
        handleNavBar(homePage);
        log.info("Home page title: {}", driver.getTitle());
        String originalTitle=driver.getTitle();
        homePage.clickLogin();
        switchToPage("login");
        LoginPage loginPage = new LoginPage(driver);
        log.info("Login page title: {}", driver.getTitle());
        verifyLoginPage(loginPage);
        switchToPage("demo");
        Assert.assertEquals(driver.getTitle(), originalTitle, "Failed to switch back to the home page.");
    }

    @Test(description = "Verifies all links on the Home Page.")
    public void verifyingLinksOnHomePage() {
        HomePage homePage = new HomePage(driver);
        verifyLinksOnHomePage(homePage);
    }


    @Test(description = "Passing Test")
    public void passingTest() {
        log.info("Passing Test Passed");
        Assert.assertTrue(true,"test Passed");
    }


    @Test(description = "Failing Test")
    public void failingTest() {
        log.info("Test Failed");
        Assert.assertFalse(true,"test failed");
    }


    @Test(description = "Exception Test")
    public void exeptionTest() {
        log.info("Test Exception");
        throw new RuntimeException("Throwing Exception");
    }


    private void switchToPage(String page) {
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
            if (driver.getCurrentUrl().contains(page)) {
                driver.switchTo().window(tab);
                log.info("Clicked on login: current page title {}", driver.getTitle());
                break;
            }
        }
    }

    private static void handleNavBar(HomePage homePage) {
        if (homePage.isNavBarVisible()) {
            homePage.clickOnNavBar();
            ExtentReportManager.logInfo("Clicked on Nav Bar.");
        }
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
