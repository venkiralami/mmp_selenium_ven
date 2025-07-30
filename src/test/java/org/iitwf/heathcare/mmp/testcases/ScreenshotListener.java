package org.iitwf.heathcare.mmp.testcases;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils; // For FileUtils
import org.iitwf.healthcare.mmp.BaseTest;

import java.io.File;
import java.io.IOException;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Retrieve the WebDriver instance from the BaseClass
        WebDriver driver = BaseTest.driver;

        // Check if the driver is not null and is an instance of TakesScreenshot
        if (driver instanceof TakesScreenshot) {
            // Convert WebDriver object to TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) driver;

            // Capture screenshot as file
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Specify the destination path
            // For example: "screenshots/failed_tests/" + result.getName() + ".png"
            String screenshotPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + result.getName() + ".png";
            File destination = new File(screenshotPath);

            try {
                // Copy the file to the destination
                FileUtils.copyFile(source, destination);
                System.out.println("Screenshot captured for failed test: " + result.getName());
            } catch (IOException e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        // Retrieve the WebDriver instance from the BaseClass
        WebDriver driver = BaseTest.driver;

        // Check if the driver is not null and is an instance of TakesScreenshot
        if (driver instanceof TakesScreenshot) {
            // Convert WebDriver object to TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) driver;

            // Capture screenshot as file
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Specify the destination path
            // For example: "screenshots/failed_tests/" + result.getName() + ".png"
            String screenshotPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + result.getName() + ".png";
            File destination = new File(screenshotPath);

            try {
                // Copy the file to the destination
                FileUtils.copyFile(source, destination);
                System.out.println("Screenshot captured for Pass test: " + result.getName());
            } catch (IOException e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    // Other methods of ITestListener can be left as default or implemented as needed
}
