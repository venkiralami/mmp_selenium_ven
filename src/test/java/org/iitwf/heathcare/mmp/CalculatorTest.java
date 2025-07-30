package org.iitwf.heathcare.mmp;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class CalculatorTest {

    private AndroidDriver driver;
   

     
	@BeforeTest
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.google.android.calculator");
        caps.setCapability("appActivity", "com.android.calculator2.Calculator");
        caps.setCapability("uiautomator2ServerLaunchTimeout", 60000);
        caps.setCapability("adbExecTimeout", 45000);
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("noReset", true);

        // Use root path for Appium 2.x (no /wd/hub)
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
    }

    @Test
    public void testAddition() {
        // 2 + 3 = 5
       System.out.println("Succss Venkat -----------------------------------------");
		/*
		 * driver.findElement(By.xpath("com.android.calculator2:id/digit_2")).click();
		 * driver.findElement(By.xpath("plus")).click();
		 * driver.findElement(By.xpath("com.android.calculator2:id/digit_3")).click();
		 * driver.findElement(By.linkText("equals")).click();
		 */
        WebElement result = driver.findElement(By.xpath("com.android.calculator2:id/result"));

        System.out.println("Result: " + result.getText());
        assert result.getText().equals("5");
    }

    @AfterTest
	public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}