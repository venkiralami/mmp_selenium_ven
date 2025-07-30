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
public class CurrentAppointmentsPage {

	protected WebDriver driver;
	//Current Appointment Page xPath
		String xPath_ActualDate = "//h3[@class='panel-title']//following::h3";
		String xPath_ActualTime = "//div[@class='list-group list-statistics']//child::a";
	
		
	//Current Appointments Page xPath
	String xPath_CreateNewAppointment ="//input[@value='Create new appointment']";

		
	/**
	 * 
	 */
	public CurrentAppointmentsPage(WebDriver driver){
		this.driver = driver;
		if (!driver.getTitle().equals("Shedule Appointments")) {
			throw new IllegalStateException("This is not a Schedule Appointments Page," +
					" current page is: " + driver.getCurrentUrl());
		}
	}

	public void createNewAppointment()
	{
		System.out.println("Create new Appointment started--");
		driver.findElement(By.xpath(xPath_CreateNewAppointment)).click();
	}
	
	public HashMap<String, String> currentAppointmentDetails() {
		HashMap<String,String> actualHMap = new HashMap<String,String>();
		String actualDate =  driver.findElement(By.xpath(xPath_ActualDate)).getText(); 
		String actualTime =  driver.findElement(By.xpath(xPath_ActualTime+"[1]")).getText(); 
		String actualTimeA[] = actualTime.split(" ");
		actualTime = actualTimeA[2];
		String actualDoctor =  driver.findElement(By.xpath(xPath_ActualTime+"[2]")).getText(); 
		actualDoctor = actualDoctor.split(":")[1];
		String actualSymptom =  driver.findElement(By.xpath(xPath_ActualTime+"[3]")).getText(); 
		actualSymptom = actualSymptom.split(":")[1];
		actualHMap.put("doctor", actualDoctor);
		actualHMap.put("date", actualDate);
		actualHMap.put("time", actualTime);
		actualHMap.put("sym", actualSymptom);
		return actualHMap;
	}

}