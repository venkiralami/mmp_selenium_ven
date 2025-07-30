/**
 * 
 */
package org.iitwf.healthcare.mmp.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 */
public class DatePickerPage {

	protected WebDriver driver;
	
	// Date Picker Page xPath
		String xPath_Date = "//input[@id='datepicker']";
		String xPath_Year = "//span[@class='ui-datepicker-year']";
		String xPath_Month = "//span[@class='ui-datepicker-month']";
		String xPath_Next = "//span[text()='Next']";
		String xPath_prev = "//span[text()='Prev']";
		//String xPath_AppointmentTime = "//option[@value='"+expectedAppointmentTime+"']";
		String xPath_ContinueButton =  "//button[@id='ChangeHeatName']";

		
	
		
	/**
	 * 
	 */
	public DatePickerPage(WebDriver driver){
		this.driver = driver;
		if (!driver.getTitle().equals("Providers")) {
			throw new IllegalStateException("This is not a Date Picker page," +
					" current page is: " + driver.getCurrentUrl());
		}
	}

	public HashMap<String, String> selectAppointmentDateTime( String expectedAppointmentDate, String expectedAppointmentTime,HashMap<String, String> expectedMap) {

		System.out.println(" Appoinment Expected Date : "+expectedAppointmentDate+" Expected Time : "+expectedAppointmentTime);

		driver.switchTo().frame("myframe");

		//System.out.println("Inside iFrame --");

		String expectedDate[] = expectedAppointmentDate.split("/");
		
		String expectedDay   = expectedDate[0];
		String expectedMonth = expectedDate[1];
		String expectedYear =  expectedDate[2];
		System.out.println("Expected Year:::" + expectedYear);
		Duration d = Duration.ofSeconds(30);
		WebDriverWait wait = new WebDriverWait(driver,d);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(xPath_Date))));

		
		driver.findElement(By.xpath(xPath_Date)).click();

		String actualYear =   driver.findElement(By.xpath(xPath_Year)).getText(); 

		System.out.println("actual Year:::: " + actualYear);	

		while(!(expectedYear.equals(actualYear)))
		{
			driver.findElement(By.xpath(xPath_Next)).click();
			actualYear =   driver.findElement(By.xpath(xPath_Year)).getText(); 
			//	System.out.println("actual Year:inside loop::: " + actualYear);
			if(expectedYear.equalsIgnoreCase(actualYear)) {
				System.out.println(" == Year loop Break ==");
				break;
			}
		}
		String actualMonth = driver.findElement(By.xpath(xPath_Month)).getText();
		//System.out.println(" == Pic before loop Break ==expectedMonth "+expectedMonth);
		//System.out.println(" == Pic After loop Break == actualMonth "+actualMonth);
		while(!(expectedMonth.equals(actualMonth)))
		{
			//	System.out.println("in while loop checking the Ac Month"+actualMonth);
			//	System.out.println("in while loop checking the Ex Month"+expectedMonth);
			driver.findElement(By.xpath("//span[text()='Next']")).click();
			actualMonth =   driver.findElement(By.xpath(xPath_Month)).getText(); 
			if(expectedMonth.equalsIgnoreCase(actualMonth)) {
				System.out.println(" == Month loop Break ==");
				break;
			}
		}

System.out.println("expectedDay :: "+expectedDay);
		
		driver.findElement(By.linkText(expectedDay)).click();
		


		expectedAppointmentDate = driver.findElement(By.id("datepicker")).getDomProperty("value");
		System.out.println("expectedAppointmentDate :: "+expectedAppointmentDate);
		/*
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMMMM/yyyy");
		 * 
		 * Date varDate1 = null; try { varDate1 =
		 * dateFormat1.parse(expectedAppointmentDate);
		 * 
		 * dateFormat1=new SimpleDateFormat("MM/dd/yyyy");
		 * expectedAppointmentDate=dateFormat1.format(varDate1); } catch (ParseException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		//expectedMap.put("date",driver.findElement(By.id("datepicker")).getDomAttribute("value"));
		expectedMap.put("date",expectedAppointmentDate);

		//driver.findElement(By.xpath(xPath_AppointmentTime)).click();
		new Select( driver.findElement(By.id("time"))).selectByVisibleText(expectedAppointmentTime);
		expectedMap.put("time",new Select( driver.findElement(By.id("time"))).getFirstSelectedOption().getText());

		wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("status")),"OK"));
		driver.findElement(By.xpath(xPath_ContinueButton)).click();
		driver.switchTo().defaultContent();
		return expectedMap;
		
		
	}
}