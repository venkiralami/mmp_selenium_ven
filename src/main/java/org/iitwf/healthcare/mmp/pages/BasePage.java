/**
 * 
 */
package org.iitwf.healthcare.mmp.pages;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * 
 */
public class BasePage {
	
	protected WebDriver driver; 
	private String mmp_URL = "http://85.209.95.122/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";

	public BasePage(WebDriver driver){
	    this.driver = driver;
	     if (!driver.getTitle().equals("Login")) {
	      throw new IllegalStateException("This is Login Page," +
	            " current page is: " + driver.getCurrentUrl());
	    }
	  }
	public WebDriver launchBrowser(String url) {
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(url);
		return driver;
	}


	
		
}
