package com.vinod.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.vinod.config.ConfigKeys;
import com.vinod.config.ConfigLoader;
import com.vinod.exception.PropertyNotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.vinod.config.ConfigKeys.BROWSER;

public class ExtentReportManager {
    private static final String REPORT_FOLDER = "reports/";
    private static final String FILE_NAME = "ExtentReport_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".html";

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static synchronized ExtentReports getReporter() throws PropertyNotFoundException {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_FOLDER + FILE_NAME);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setEncoding("utf-8");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Organization", "Coda Payments");
            extent.setSystemInfo("Browser", ConfigLoader.getInstance().getProperty(BROWSER));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }

    public static synchronized void logTest(String testName) throws PropertyNotFoundException {
        ExtentTest extentTest = getReporter().createTest(testName);
        test.set(extentTest);
    }

    public static synchronized ExtentTest getTest() {
        return test.get();
    }

    public static void captureScreenshot(WebDriver driver,String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        String base64Image = ts.getScreenshotAs(OutputType.BASE64);
        getTest().addScreenCaptureFromBase64String(base64Image, screenshotName);
    }

    public static synchronized void logInfo(String message) {
        getTest().info(message);
    }

    public static synchronized void logError(String message) {
        getTest().log(Status.WARNING,message);
    }

    public static synchronized void logWarn(String message) {
        getTest().log(Status.WARNING,message);
    }
    public static synchronized void logFailure(String message) {
        getTest().fail(message);
    }

    public static synchronized void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}