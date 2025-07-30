/**
 * 
 */
package org.iitwf.heathcare.mmp;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

/*
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
public class FirstAppiumTest {
	AppiumDriver<MobileElement> driver;
	@Test
	public void testFirstMobileApp() throws MalformedURLException {
		//Setting desired capabilities for the android device with details like device name, version, etc 
		//Since we are using the TestGrid Real device cloud we have copied the capabilities from the device
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Naruto uzumakip");
		capabilities.setCapability("udid", "ecdde7b");
		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("platformVersion", "10");
		
		//setting capability for application we want to test
		capabilities.setCapability("appPackage", "com.miui.calculator");
		capabilities.setCapability("appActivity", "com.miui.calculator.cal.CalculatorActivity");
		//Instantiating Android Driver and using Appium server host and port
		URL url = new URL("http://192.168.1.110:4723/wd/hub");
		System.out.println("Before Appium call");
		driver = new AppiumDriver(url, capabilities);
		System.out.println("After Appium call");
		
		 //http://192.168.1.110:4723/
		   //     http://127.0.0.1:4723/ (only accessible from the same host)
		        	
	}*/
	import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;


	public class CalculatorMobileTest {
	    public static void main1(String[] args) throws Exception {
	        DesiredCapabilities caps = new DesiredCapabilities();
	        caps.setCapability("automationName", "UiAutomator2");
	        caps.setCapability("platformName", "Android");
	        caps.setCapability("platformVersion", "10 QKQ1.190915.002");
	        caps.setCapability("deviceName", "pixel_9_pro_xl");
	        caps.setCapability("appPackage", "com.miui.calculator");
	        caps.setCapability("udid", "ecdde7b");
	        
	       
	        caps.setCapability("appPackage", "com.miui.calculator");
	        caps.setCapability("appActivity", "com.miui.calculator.cal.CalculatorActivity");
	       // ;

	        
	        AppiumDriver driver  = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);

	        // Example action
	       // driver.findElement("com.miui.calculator:id/btn_1_s").click();

	        driver.quit();
	    }
	    
	    public static void main(String[] args) throws MalformedURLException {
	        UiAutomator2Options options = new UiAutomator2Options();
	        options.setPlatformName("Android");
	        options.setDeviceName("ecdde7b"); // use actual device/emulator name
	        options.setAutomationName("UiAutomator2");
	        //options.setAppPackage("com.android.settings");
	        //options.setAppActivity("com.android.settings.Settings");
//	        options.setApp("com.miui.calculator"); // full valid .apk path
//	        options.setAppPackage("com.miui.calculator");
//	        options.setAppActivity("com.miui.calculator.cal.CalculatorActivity");
	        options.setCapability("ignoreHiddenApiPolicyError", true);
	        AndroidDriver driver = new AndroidDriver(
	            new URL("http://127.0.0.1:4723/"), options
	        );

	        // Do some actions
	        driver.quit();
	    }
	}
	
	/*
	 * @Test public void multiply() { //Locating numbers, mathematical operator and
	 * equals button on the calculator app
	 * driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_4")).
	 * click();
	 * driver.findElement(AppiumBy.id("com.google.android.calculator:id/op_mul")).
	 * click();
	 * driver.findElement(AppiumBy.id("com.google.android.calculator:id/digit_9")).
	 * click();
	 * driver.findElement(AppiumBy.id("com.google.android.calculator:id/eq")).click(
	 * );
	 * 
	 * //Storing the result in a string variable String result =
	 * driver.findElement(AppiumBy.id(
	 * "com.google.android.calculator:id/result_final")).getText();
	 * System.out.println("The result is - "+result); //Asserting the result as
	 * expected Assert.assertEquals(result, "36"); }
	 * 
	 * @AfterMethod public void tearDown() { //Quitting the driver object
	 * driver.quit(); }
	 use:

java
Copy
Edit
caps.setCapability("appPackage", "com.android.calculator2");
caps.setCapability("appActivity", "com.android.calculator2.Calculator")
	
}
 */
