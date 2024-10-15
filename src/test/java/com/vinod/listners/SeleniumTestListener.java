package com.vinod.listners;

import com.vinod.utility.ExtentReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;


public class SeleniumTestListener implements WebDriverListener {

    @Override
    public void afterGet(WebDriver driver, String url) {
        ExtentReportManager.logInfo("Url of Page "+url);
    }


    @Override
    public void afterGetCurrentUrl(WebDriver driver, String result) {
        ExtentReportManager.logInfo("Url of Page "+result);
    }


    @Override
    public void afterGetTitle(WebDriver driver, String result) {
        ExtentReportManager.logInfo("Title of Page "+result);
    }

    @Override
    public void afterClose(WebDriver driver) {
        ExtentReportManager.logInfo("Driver Closed Successfully");
    }

    @Override
    public void afterQuit(WebDriver driver) {
        ExtentReportManager.logInfo("Driver Quited Successfully");
    }

    @Override
    public void afterResetInputState(WebDriver driver) {
        ExtentReportManager.logInfo("Input field is Reset");
    }



    @Override
    public void afterClick(WebElement element) {
        ExtentReportManager.logInfo("clicked on element : "+element.getText());
    }


    @Override
    public void afterSubmit(WebElement element) {
        ExtentReportManager.logInfo("clicked on submit button : "+element.getText());
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        ExtentReportManager.logInfo("keys are send : "+keysToSend);
    }


    @Override
    public void afterClear(WebElement element) {
        ExtentReportManager.logInfo("clear input field: "+element);
    }




    @Override
    public void afterIsSelected(WebElement element, boolean result) {
        ExtentReportManager.logInfo("element "+element+" is Selected "+result);
    }

    @Override
    public void afterIsEnabled(WebElement element, boolean result) {
        ExtentReportManager.logInfo("element "+element+" is Enabled "+result);
    }


    @Override
    public void afterGetText(WebElement element, String result) {
        ExtentReportManager.logInfo("element "+element+" text "+result);

    }

    @Override
    public void afterIsDisplayed(WebElement element, boolean result) {
        ExtentReportManager.logInfo(element.getText()+" is displayed : "+result);
    }

    @Override
    public void afterGetLocation(WebElement element, Point result) {
        ExtentReportManager.logInfo("element "+element+" Location "+result);

    }

    @Override
    public void afterBack(WebDriver.Navigation navigation) {
        ExtentReportManager.logInfo("navigating back ");
    }

    @Override
    public void afterForward(WebDriver.Navigation navigation) {
        ExtentReportManager.logInfo("navigating forward ");
    }


    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        ExtentReportManager.logInfo("after refresh ");
    }


    @Override
    public void afterAccept(Alert alert) {
        ExtentReportManager.logInfo("alert accepted ");
    }


    @Override
    public void afterDismiss(Alert alert) {
        ExtentReportManager.logInfo("alert dismissed ");
    }


    @Override
    public void afterGetText(Alert alert, String result) {
        ExtentReportManager.logInfo("alert text  "+alert.getText());
    }


    @Override
    public void afterSendKeys(Alert alert, String text) {
        ExtentReportManager.logInfo("alert send keys "+text);
    }


    @Override
    public void afterGetSize(WebDriver.Window window, Dimension result) {
        ExtentReportManager.logInfo("Dimension of size : height "+result.height+" width "+result.width);
    }

    @Override
    public void afterGetPosition(WebDriver.Window window, Point result) {
        ExtentReportManager.logInfo("Position of element : x co-ordinate "+result.x+" y co-ordinate "+result.y);
    }

    @Override
    public void afterFrame(WebDriver.TargetLocator targetLocator, int index, WebDriver driver) {
        ExtentReportManager.logInfo("Switch to frame by index  value"+index);
    }


    @Override
    public void afterFrame(WebDriver.TargetLocator targetLocator, String nameOrId, WebDriver driver) {
        ExtentReportManager.logInfo("Switch to frame by name or Id  value: "+nameOrId);
    }

    @Override
    public void afterFrame(WebDriver.TargetLocator targetLocator, WebElement frameElement, WebDriver driver) {
        ExtentReportManager.logInfo("Switch to frame by webElement  value: "+frameElement);
    }


    @Override
    public void afterParentFrame(WebDriver.TargetLocator targetLocator, WebDriver driver) {
        ExtentReportManager.logInfo("Navigated to Parent Frame "+ targetLocator);
    }


    @Override
    public void afterAlert(WebDriver.TargetLocator targetLocator, Alert alert) {
        ExtentReportManager.logInfo("Alerts  is Completed");
    }
}
