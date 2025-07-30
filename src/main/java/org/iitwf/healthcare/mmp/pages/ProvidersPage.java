/**
 * 
 */
package org.iitwf.healthcare.mmp.pages;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 */
public class ProvidersPage {

	protected WebDriver driver;
	
	
		
	
		
	/**
	 * 
	 */
	public ProvidersPage(WebDriver driver){
		this.driver = driver;
		if (!driver.getTitle().equals("Providers")) {
			throw new IllegalStateException("This is not a Providers page," +
					" current page is: " + driver.getCurrentUrl());
		}
	}

	public   HashMap<String, String> bookAppointment(String doctorName,HashMap<String, String> expectedMap) {

		expectedMap.put("doctor", doctorName);
		String doctorAppointmentXPath = "//h4[text()='Dr."+doctorName+"']/ancestor::ul/following-sibling::button";
		WebElement revealed = driver.findElement(By.xpath(doctorAppointmentXPath));
		/*
		System.out.println("Is revealed b "+revealed.isDisplayed());
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		System.out.println("Is revealed "+revealed.isDisplayed());
		wait.until(d -> revealed.isDisplayed());
		 */
		revealed.click();
		return expectedMap;
	}
}