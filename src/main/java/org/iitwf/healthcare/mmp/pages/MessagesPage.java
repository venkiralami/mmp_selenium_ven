/**
 * 
 */
package org.iitwf.healthcare.mmp.pages;

import java.time.Duration;
import java.util.HashMap;

import org.iitwf.healthcare.mmp.DateUtils;
import org.iitwf.healthcare.mmp.utils.CustomRandomUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 */
public class MessagesPage {

	protected WebDriver driver;
		
	//xPath
		String xPath_ContactReason = "//input[@id='subject']";
		String xPath_Subject = "//textarea[@id='message']";
		String xPath_Send = "//input[@value='Send']";
		
	/**
	 * 
	 */
	public MessagesPage(WebDriver driver){
		this.driver = driver;
		
	}
	
	public String sendMessages(String reason, String messages){
		

		
		WebElement w_ContactReason =  driver.findElement(By.xpath(xPath_ContactReason));
		w_ContactReason.sendKeys(reason);
		WebElement w_Subject =  driver.findElement(By.xpath(xPath_Subject));
		w_Subject.sendKeys(messages);
		
		driver.findElement(By.xpath(xPath_Send)).click();
		
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

	        // Verify the alert text
	        String alertText = alert.getText();
	        System.out.println("Alert says: " + alertText);
	        if (!"Messages Successfully sent.".equals(alertText)) {
	        	throw new AssertionError("Unexpected alert text: " + alertText);
	  	      	        }
	        // Accept (click OK)
	        alert.accept();
		return alertText;
	}
	/**
	 * This method will contain the logic to send a message to a patient
	 * It will use the FrameworkLibrary and other page classes to perform the actions
	 * @return 
	 */
	public HashMap<String, String> sendMessageToAdmin(String patientName, String message) {


		HashMap<String, String> expectedHMap = new HashMap<String, String>();
		// Logic to send message goes here
		System.out.println("Sending message  patient name: " + patientName);
		System.out.println("Message content: " + message);
		expectedHMap.put("patientName", patientName);
		//Generate Subject and Message
		// Assuming the subject is a combination of a static string and a random string
		String ranString = CustomRandomUtils.generateRandomString(5);
		driver.findElement(By.id("subject")).sendKeys("Visit Reminder - "+ranString);
		expectedHMap.put("subject", driver.findElement(By.id("subject")).getDomProperty("value"));
		driver.findElement(By.id("message")).sendKeys(message+ranString);
		expectedHMap.put("message", driver.findElement(By.id("message")).getDomProperty("value"));
		driver.findElement(By.xpath("//input[@value='Send']")).submit();
		expectedHMap.put("date", DateUtils.getFutureDate("dd-MM-YYYY",0));
		System.out.println("Message sent successfully.");
		return expectedHMap;
	}	

	public String getMessageStatus() {
		// This method would typically check the status of the sent message
		// For now, we will return a dummy status
		Alert alert = driver.switchTo().alert();
		String messagesText = alert.getText(); // Get the alert text
		alert.accept(); // Accept the alert to close it
		return messagesText;
	}
	
	
}