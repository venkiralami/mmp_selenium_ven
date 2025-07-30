package org.iitwf.healthcare.mmp.pages;


import java.util.HashMap;

import org.iitwf.healthcare.mmp.DateUtils;
import org.iitwf.healthcare.mmp.utils.CustomRandomUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 */
public class RegistrationPage {

	HashMap<String, String> expectedHMap = null;
	HashMap<String, String> actualHMap = null;

	//xPath
	String xPath_FirstName = "//input[@id='firstname']";
	String xPath_LastName = "//input[@id='lastname']";
	String xPath_dob = "//input[@id='datepicker']";
	String xPath_Licence = "//input[@id='license']";
	String xPath__Ssn = "//input[@id='ssn']";
	String xPath_State = "//input[@id='state']";
	String xPath_City = "//input[@id='city']";
	String xPath_Address = "//input[@id='address']";
	String xPath_ZipCode = "//input[@id='zipcode']";
	String xPath_Age = "//input[@id='age']";
	String xPath_Height = "//input[@id='height']";
	String xPath_Weight = "//input[@id='weight']";
	String xPath_Pharmacy = "//input[@id='pharmacy']";
	String xPath_Pharma_Adress = "//input[@id='pharma_adress']";
	String xPath_Email = "//input[@id='email']";
	String xPath_Password = "//input[@id='password']";
	String xPath_UserName = "//input[@id='username']";
	String xPath_ConfirmPassword = "//input[@id='confirmpassword']";
	String xPath_Security = "//select[@id='security']";
	String xPath_Answer = "//input[@id='answer']";
	String xPath_Register = "//input[@name='register']";


	protected WebDriver driver;
	public RegistrationPage(WebDriver driver){
		this.driver = driver;
		if (!driver.getTitle().equals("Registration")) {
			throw new IllegalStateException("This is not Registration Page," +
					" current page is: " + driver.getCurrentUrl());
		}
	}

	public HashMap<String, String> registerNewUser(){
		expectedHMap = new HashMap<String, String>();

		WebElement wFirstName =  driver.findElement(By.xpath(xPath_FirstName));
		WebElement wLastName =  driver.findElement(By.xpath(xPath_LastName));
		WebElement wDob = driver.findElement(By.xpath(xPath_dob));
		WebElement wLicence =  driver.findElement(By.xpath(xPath_Licence));
		WebElement wSsn =  driver.findElement(By.xpath(xPath__Ssn));
		WebElement wState =  driver.findElement(By.xpath(xPath_State));
		WebElement wCity =  driver.findElement(By.xpath(xPath_City));
		WebElement wAddress =  driver.findElement(By.xpath(xPath_Address));
		WebElement wZipCode =  driver.findElement(By.xpath(xPath_ZipCode));
		WebElement wAge =  driver.findElement(By.xpath(xPath_Age));
		WebElement wHeight =  driver.findElement(By.xpath(xPath_Height));
		WebElement wWeight =  driver.findElement(By.xpath(xPath_Weight));
		WebElement wPharmacy =  driver.findElement(By.xpath(xPath_Pharmacy));
		WebElement wPharma_Adress =  driver.findElement(By.xpath(xPath_Pharma_Adress));
		WebElement wEmail =  driver.findElement(By.xpath(xPath_Email));
		WebElement wPassword =  driver.findElement(By.xpath(xPath_Password));
		WebElement wUserName =  driver.findElement(By.xpath(xPath_UserName));
		WebElement wConfirmPassword =  driver.findElement(By.xpath(xPath_ConfirmPassword));
		WebElement wSecurity =  driver.findElement(By.xpath(xPath_Security));
		WebElement wAnswer =  driver.findElement(By.xpath(xPath_Answer));
		WebElement wRegister =  driver.findElement(By.xpath(xPath_Register));


		String randomString = CustomRandomUtils.generateRandomString(5);


		String firstName = "Venkat"+randomString;
		String lastName = "Ram"+randomString;
		String dob = DateUtils.getFutureDate("d/MMMMM/yyyy", -35);
		String licenceNo = String.valueOf(CustomRandomUtils.generateRandomNumber(10000000,39999999));
		String ssn = CustomRandomUtils.generateRandomSsn();
		String state = "Texas "+randomString;
		String city = "Dallas "+randomString;
		String address = CustomRandomUtils.generateRandomAddress(100, 500);
		String zipCode = String.valueOf(CustomRandomUtils.generateRandomNumber(10000,99999));
		String age = String.valueOf(CustomRandomUtils.generateRandomNumber(1,99));
		String height = String.valueOf(CustomRandomUtils.generateRandomNumber(10,200));
		String weight = String.valueOf(CustomRandomUtils.generateRandomNumber(5,100));
		String pharmacy = "CVS "+randomString;
		String pharma_Adress = "8680 N MacArthur Blvd  "+randomString;
		String email = "ven"+randomString+"@gmail.com";
		String password = "Pass@123";
		String userName = "ven"+randomString;
		String confirmPassword = "Pass@123";

		String security = "What is your mother maiden name";
		String answer = "Answer "+randomString;

		wFirstName.sendKeys(firstName);
		wLastName.sendKeys(lastName);
		wDob.sendKeys(dob);
		wLicence.sendKeys(licenceNo);
		wSsn.sendKeys(ssn);
		wState.sendKeys(state);
		wCity.sendKeys(city);
		wAddress.sendKeys(address);
		wZipCode.sendKeys(zipCode);
		wAge.sendKeys(age);
		wHeight.sendKeys(height);
		wWeight.sendKeys(weight);
		wPharmacy.sendKeys(pharmacy);
		wPharma_Adress.sendKeys(pharma_Adress);
		wEmail.sendKeys(email);
		wPassword.sendKeys(password);
		wUserName.sendKeys(userName);
		wConfirmPassword.sendKeys(confirmPassword);
		new Select(wSecurity).selectByVisibleText(security);
		wAnswer.sendKeys(answer);


		expectedHMap.put("security",new Select(wSecurity).getFirstSelectedOption().getText());



		System.out.println("User name : "+wUserName.getDomProperty("value"));
		System.out.println("Password : "+wPassword.getDomProperty("value"));
		expectedHMap.put("fname", wFirstName.getDomProperty("value"));
		expectedHMap.put("lname", wLastName.getDomProperty("value"));
		expectedHMap.put("licence", wLicence.getDomProperty("value"));
		expectedHMap.put("ssn", wSsn.getDomProperty("value"));
		expectedHMap.put("adress", wAddress.getDomProperty("value"));
		expectedHMap.put("username", wUserName.getDomProperty("value"));
		expectedHMap.put("password", wPassword.getDomProperty("value"));
		wRegister.click();

		Alert alert = driver.switchTo().alert();

		// Optional: Read the alert message
		System.out.println("Alert Text: " + alert.getText());

		// Click OK
		alert.accept();


		return expectedHMap;

	}
	public HashMap<String, String> saveProfileInfo(){
		driver.findElement(By.xpath("//input[@id='Sbtn']")).click();
		//driver.findElement(By.xpath("//input[@id='fname']")).getDomAttribute("value")
		//driver.findElement(By.xpath("//input[@id='fname']")).getDomProperty("value")

		Alert alert = driver.switchTo().alert();

		// Optional: Read the alert message
		System.out.println("Alert Text: " + alert.getText());

		// Click OK
		alert.accept();
		WebElement wFirstName =  driver.findElement(By.xpath(xPath_FirstName));
		WebElement wLastName =  driver.findElement(By.xpath(xPath_LastName));
		WebElement wLicence =  driver.findElement(By.xpath(xPath_Licence));
		WebElement wSsn =  driver.findElement(By.xpath(xPath__Ssn));
		WebElement wAddress =  driver.findElement(By.xpath(xPath_Address));

		System.out.println("Page F Name actual :"+wFirstName.getDomProperty("value"));
		actualHMap = new HashMap<String, String>();
		actualHMap.put("fname", wFirstName.getDomProperty("value"));
		actualHMap.put("lname", wLastName.getDomProperty("value"));
		actualHMap.put("licence", wLicence.getDomProperty("value"));
		actualHMap.put("ssn", wSsn.getDomProperty("value"));
		actualHMap.put("adress", wAddress.getDomProperty("value"));

		return actualHMap;
	}




}