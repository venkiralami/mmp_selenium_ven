/**
 * 
 */
package org.iitwf.heathcare.mmp.testcases;

import java.util.HashMap;

import org.iitwf.healthcare.mmp.BaseTest;
import org.iitwf.healthcare.mmp.pages.HomePage;
import org.iitwf.healthcare.mmp.pages.LoginPage;
import org.iitwf.healthcare.mmp.pages.MessagesPage;
import org.iitwf.healthcare.mmp.pages.ProfilePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * 
 */
public class MessagesPageTest extends BaseTest{

	//Login Page Object Input Value
	String uName = "ria1";
	String pw = "Ria12345";

	private String contactReason = "AAA";
	private String messages = "BBB";
	private String expectedAlertMessage = "Messages Successfully sent.";

	HashMap<String, String> expectedHMap = null;
	HashMap<String, String> actualHMap = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Test
	public void sendMessagesTest() throws InterruptedException {
		// TODO Auto-generated method stub
		driver = launchBrowser();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginValidUser(uName, pw);
		HomePage homePage = new HomePage(driver);
		homePage.navigateToAModule("Messages"); 
		MessagesPage messagesPage = new MessagesPage(driver);
		String alertMessage = messagesPage.sendMessages(contactReason,messages); 

		Assert.assertEquals(expectedAlertMessage, alertMessage);

	}

	@AfterTest
	public void closeBrowser(){
		// Close the browser
		driver.quit();	
	}
}
