package org.iitwf.healthcare.mmp.pages;


import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class UsersPage {
	protected WebDriver driver;
	/**
	 * Constructor for MessagesPage
	 * @param driver WebDriver instance to interact with the browser
	 */
	//xPath
	String xPath_FirstName = "//input[@id='fname']";
	String xPath_LastName = "//input[@id='lname']";
	//String xPath_dob = "//input[@id='datepicker']";
	String xPath_Licence = "//input[@id='licn']";
	String xPath_Ssn = "//input[@id='ssn']";
	String xPath_Address = "//input[@id='addr']";
	String xPath_Age = "//input[@id='age']";
	String xPath_Weight = "//input[@id='weight']";
	String xPath_Height = "//input[@id='height']";
	String xPath_City = "//input[@id='city']";
	String xPath_State = "//input[@id='state']";
	String xPath_ZipCode = "//input[@id='zip']";
	String xPath_saApproval = "//select[@id='sapproval']";
	String xPath_Submit = "//input[@value='Submit']";

	/*
	 * String xPath_Pharmacy = "//input[@id='pharmacy']"; String xPath_Pharma_Adress
	 * = "//input[@id='pharma_adress']"; String xPath_Email =
	 * "//input[@id='email']"; String xPath_Password = "//input[@id='password']";
	 * String xPath_UserName = "//input[@id='username']"; String
	 * xPath_ConfirmPassword = "//input[@id='confirmpassword']"; String xPath_Answer
	 * = "//input[@id='answer']";
	 */


	public UsersPage(WebDriver driver){
		this.driver = driver;
		System.out.println("Page Title - "+driver.getTitle());
		if (!driver.getTitle().equals("Schedule Appointments")) {
			throw new IllegalStateException("This is not Patients Page," +
					" current page is: " + driver.getCurrentUrl());
		}
	}

	public HashMap<String, String> actionOnNewUser(String userName,String ssn) {
		HashMap<String, String> actualHMap = new HashMap<String, String>();
		//td[contains(text(),'207-65-3542')]
		System.out.println("userName : "+userName+" Ssn : "+ssn);
		WebElement userElement = driver.findElement(By.xpath("//a[contains(text(),'"+userName+"')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userElement);
		actualHMap.put("userName", userElement.getDomProperty("value"));
		userElement.click();

		//Getting actual value from User page
		WebElement wFirstName =  driver.findElement(By.xpath(xPath_FirstName));
		WebElement wLastName =  driver.findElement(By.xpath(xPath_LastName));
		//WebElement wDob = driver.findElement(By.xpath(xPath_dob));
		WebElement wLicence =  driver.findElement(By.xpath(xPath_Licence));
		WebElement wSsn =  driver.findElement(By.xpath(xPath_Ssn));
		WebElement wState =  driver.findElement(By.xpath(xPath_State));
		WebElement wCity =  driver.findElement(By.xpath(xPath_City));
		WebElement wAddress =  driver.findElement(By.xpath(xPath_Address));
		WebElement wZipCode =  driver.findElement(By.xpath(xPath_ZipCode));
		WebElement wAge =  driver.findElement(By.xpath(xPath_Age));
		WebElement wHeight =  driver.findElement(By.xpath(xPath_Height));
		WebElement wWeight =  driver.findElement(By.xpath(xPath_Weight));


		System.out.println("Page F Name actual :"+wFirstName.getDomProperty("value"));
		actualHMap = new HashMap<String, String>();

		actualHMap.put("fname", wFirstName.getDomProperty("value"));
		actualHMap.put("lname", wLastName.getDomProperty("value"));
		actualHMap.put("licence", wLicence.getDomProperty("value"));
		actualHMap.put("ssn", wSsn.getDomProperty("value"));
		actualHMap.put("adress", wAddress.getDomProperty("value"));
		//actualHMap.put("username", wUserName.getDomProperty("value"));
		//actualHMap.put("password", "Pass@123");

		WebElement approvalSel = driver.findElement(By.xpath("//select[@id='sapproval']"));
		new Select(approvalSel).selectByVisibleText("Accepted");

		driver.findElement(By.xpath("//input[@value='Submit']")).click();
		try {
			Thread.sleep(5000);
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert Text: " + alert.getText());
			alert.accept();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actualHMap;
	}
}
