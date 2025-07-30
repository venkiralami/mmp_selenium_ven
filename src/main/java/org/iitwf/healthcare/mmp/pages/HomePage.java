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
public class HomePage {

	protected WebDriver driver;
	
	public HomePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}

	
	
	
		
	//Home Page xPath
		String xPath_ModuleName = "//span[normalize-space(text())='\"+moduleName+\"'";

		// Home Module xPath
		String xPath_Home_Date = "//table[@class='table']/tbody/tr[1]/td[1]";
		String xPath_Home_Time = "//table[@class='table']/tbody/tr[1]/td[2]";
		String xPath_Home_Symptoms = "//table[@class='table']/tbody/tr[1]/td[3]";
		String xPath_Home_Doctor = "//table[@class='table']/tbody/tr[1]/td[4]";

		
	/**
	 * 
	 */
	/*
	 * public HomePage(WebDriver driver){ super(driver); if
	 * (!driver.getTitle().equals("home")) { throw new
	 * IllegalStateException("This is not a Home Page," + " current page is: " +
	 * driver.getCurrentUrl()); } }
	 */

	public void navigateToAModule(String moduleName)
	{
		System.out.println("Module Name : " + moduleName);
		driver.findElement(By.xpath( "//span[normalize-space(text())='"+moduleName+"']")).click();
	}
	
	public HashMap<String, String> fetchActualDatafromPatientPortalTable()
	{
		HashMap<String,String> actualHMap = new HashMap<String,String>();
		actualHMap.put("date",driver.findElement(By.xpath(xPath_Home_Date)).getText());
		actualHMap.put("time",driver.findElement(By.xpath(xPath_Home_Time)).getText());
		actualHMap.put("sym",driver.findElement(By.xpath(xPath_Home_Symptoms)).getText());
		actualHMap.put("doctor",driver.findElement(By.xpath(xPath_Home_Doctor)).getText());

		return actualHMap;
	}
}