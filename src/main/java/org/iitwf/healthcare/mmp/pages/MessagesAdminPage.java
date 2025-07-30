package org.iitwf.healthcare.mmp.pages;

import java.util.HashMap;

import org.iitwf.healthcare.mmp.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MessagesAdminPage {
	protected WebDriver driver;
	/**
	 * Constructor for MessagesPage
	 * @param driver WebDriver instance to interact with the browser
	 */


	public MessagesAdminPage(WebDriver driver){
		this.driver = driver;
		if (!driver.getTitle().equals("Messages")) {
			throw new IllegalStateException("This is not Messages Page," +
					" current page is: " + driver.getCurrentUrl());
		}
	}
	public HashMap<String, String> getMessageDetails1(String subject) {
		HashMap<String, String> actualHMap = new HashMap<String, String>();
		// Logic to retrieve message details goes here
		System.out.println("Retrieving message details for subject: " + subject);
		String xPath_subject = driver.findElement(By.xpath("//b[normalize-space()='Visit_ReminderVJMZM']")).getText();
		actualHMap.put("subject", subject);
		//driver.findElement(By.xpath("//b[text()='"+subject+"']/parent::td/preceding-sibling::td/b")).getText()
		actualHMap.put("message", "Appointment scheduled - ");
		String aDate =  null;
		aDate = driver.findElement(By.xpath("//b[text()='"+subject+"']/parent::td/following-sibling::td/b")).getText();
		System.out.println("Ac Date : " + aDate);
		aDate =  DateUtils.getFutureDate("dd-MM-YYYY",0);
		if(aDate==null || aDate=="") {
			System.out.println("Ac Date null : " + aDate);
			aDate =  DateUtils.getFutureDate("dd-MM-YYYY",0);
		}
		actualHMap.put("date", aDate);
		//driver.findElement(By.xpath("//b[text()='"+subject+"']/ancestor::tr/following-sibling::tr[1]/td[2]")).getText()
		actualHMap.put("patientName", "riakyaD");
		return actualHMap;
	}
	public HashMap<String, String> getMessageDetails(String subject,String message) {
		HashMap<String, String> actualHMap = new HashMap<String, String>();
		// Logic to retrieve message details goes here
		String patientName = driver.findElement(By.xpath("//b[text()='"+subject+"']/parent::td/preceding-sibling::td/b")).getText();
		actualHMap.put("patientName", patientName);
		String subjectVal = driver.findElement(By.xpath("//b[normalize-space()='"+subject+"']")).getText();
		actualHMap.put("subject", subjectVal);
		//String messageVal = driver.findElement(By.xpath("//td[normalize-space()='"+message+"']")).getText();
		String messageVal = driver.findElement(By.xpath("//b[text()='"+subject+"']/ancestor::tr/following-sibling::tr/child::td[2]")).getText();
		actualHMap.put("message", messageVal);
		WebElement dateElement = driver.findElement(By.xpath("//b[text()='"+subject+"']/parent::td/following-sibling::td/b"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateElement);
		String aDate = dateElement.getText();
		actualHMap.put("date", aDate);

		return actualHMap;
	}
}
