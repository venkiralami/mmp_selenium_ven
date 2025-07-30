/**
 * 
 */
package org.iitwf.healthcare.mmp.pages;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 */
public class AddSymptomsPage {

	protected WebDriver driver;
	
	//Symptoms Page xPath
		String xPath_Symptoms = "//textarea[@id='sym']";
		String xPath_SymptomSubmitButton = "//input[@value=\'Submit\']";
		
		
	
			
	/**
	 * 
	 */
	public AddSymptomsPage(WebDriver driver){
		this.driver = driver;
		/*
		 * if (!driver.getTitle().equals("Shedule Appointments")) { throw new
		 * IllegalStateException("This is not a Add Symptoms Page," +
		 * " current page is: " + driver.getCurrentUrl()); }
		 */
	}

	public HashMap<String, String> addSymptoms(String symptoms, HashMap<String, String> expectedMap) {

		driver.findElement(By.xpath(xPath_Symptoms)).sendKeys(symptoms);

		String sym_DOMVal = driver.findElement(By.xpath(xPath_Symptoms)).getDomProperty("value");
		System.out.println("Sym Dom val -- > "+sym_DOMVal);

		if(!sym_DOMVal.isEmpty() || !sym_DOMVal.equalsIgnoreCase("") ||
				!sym_DOMVal.equalsIgnoreCase(null)) { 
			expectedMap.put("sym", sym_DOMVal);
		}else {		 
			expectedMap.put("sym",	symptoms);
		}
		driver.findElement(By.xpath(xPath_SymptomSubmitButton)).click();
		return expectedMap;
	}
}