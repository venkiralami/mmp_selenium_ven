/**
 * 
 */
package org.iitwf.healthcare.mmp.pages;

import java.util.HashMap;

import org.iitwf.healthcare.mmp.utils.CustomRandomUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 */
public class ProfilePage {

	protected WebDriver driver;
	HashMap<String, String> expectedHMap = null;
	HashMap<String, String> actualHMap = null;
	
	//xPath
		String xPath_FirstName = "//input[@id='fname']";
		String xPath_LastName = "//input[@id='lname']";
		String xPath_Licence = "//input[@id='licn']";
		String xPath_Ssn = "//input[@id='ssn']";
		String xPath_Address = "//input[@id='addr']";
		String xPath_Age = "//input[@id='age']";
		String xPath_Weight = "//input[@id='weight']";
		String xPath_Height = "//input[@id='height']";
		String xPath_City = "//input[@id='city']";
		String xPath_State = "//input[@id='state']";
		String xPath_ZipCode = "//input[@id='zip']";
		String xPath_ProviderInformation = "//input[@id='proinfo']";
		String xPath_InsuranceInformation = "//input[@id='Insinfo']";
		String xPath_Edit = "//input[@id='Ebtn']";
		String xPath_ViewHistory = "//button[text()='View History']";
		String xPath_ViewReports = "//button[text()='View Reports']";
		
	/**
	 * 
	 */
	public ProfilePage(WebDriver driver){
		this.driver = driver;
		
	}
	
	public HashMap<String, String> editProfileInfo(){
		expectedHMap = new HashMap<String, String>();
		driver.findElement(By.xpath("//input[@id='Ebtn']")).click();
		
		WebElement wFirstName =  driver.findElement(By.xpath(xPath_FirstName));
		WebElement wLastName =  driver.findElement(By.xpath(xPath_LastName));
		WebElement wLicence =  driver.findElement(By.xpath(xPath_Licence));
		WebElement wSsn =  driver.findElement(By.xpath(xPath_Ssn));
		WebElement wAddress =  driver.findElement(By.xpath(xPath_Address));
		
		String firstName = CustomRandomUtils.generateRandomString(10);
		String lastName = CustomRandomUtils.generateRandomString(12);
		String licenceNo = String.valueOf(CustomRandomUtils.generateRandomNumber(10000000,39999999));
		String ssn = CustomRandomUtils.generateRandomSsn();
		String address = CustomRandomUtils.generateRandomAddress(100, 500);
		
		wFirstName.clear();
		wLastName.clear();
		wLicence.clear();
		wSsn.clear();
		wAddress.clear();
		
		wFirstName.sendKeys(firstName);
		wLastName.sendKeys(lastName);
		wLicence.sendKeys(licenceNo);
		wSsn.sendKeys(ssn);
		wAddress.sendKeys(address);
		System.out.println("F name : "+firstName);
		expectedHMap.put("fname", wFirstName.getDomProperty("value"));
		expectedHMap.put("lname", wLastName.getDomProperty("value"));
		expectedHMap.put("licence", wLicence.getDomProperty("value"));
		expectedHMap.put("ssn", wSsn.getDomProperty("value"));
		expectedHMap.put("adress", wAddress.getDomProperty("value"));
		
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
		WebElement wSsn =  driver.findElement(By.xpath(xPath_Ssn));
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

	public String getPatientFName()
	{
		return driver.findElement(By.id("fname")).getDomProperty("value");
	}
}