/**
 * 
 */
package org.iitwf.heathcare.mmp.testcases;

import java.util.HashMap;

import org.iitwf.healthcare.mmp.BaseTest;
import org.iitwf.healthcare.mmp.pages.HomePage;
import org.iitwf.healthcare.mmp.pages.LoginPage;
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
public class ProfilePageTest extends BaseTest{

	//Login Page Object Input Value
	String uName = "ria1";
	String pw = "Ria12345";


	HashMap<String, String> expectedHMap = null;
	HashMap<String, String> actualHMap = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Test
	public void profileMainTest() throws InterruptedException {
		// TODO Auto-generated method stub
		driver = launchBrowser();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginValidUser(uName, pw);
		HomePage homePage = new HomePage(driver);
		homePage.navigateToAModule("Profile"); 
		ProfilePage profilePage = new ProfilePage(driver);
		expectedHMap = profilePage.editProfileInfo(); 
		actualHMap = profilePage.saveProfileInfo();
		System.out.println("expectedHMap : "+expectedHMap);
		System.out.println("actualHMap : "+actualHMap);
		Assert.assertEquals(expectedHMap, actualHMap);;

	}

	@Test
	public void profileMainTestFailScenario() throws InterruptedException {
		// TODO Auto-generated method stub
		/*
		 * HomePage homePage = new HomePage(driver);
		 * homePage.navigateToAModule("Profile"); ProfilePage profilePage = new
		 * ProfilePage(driver); expectedHMap = profilePage.editProfileInfo();
		 * actualHMap = profilePage.saveProfileInfo();
		 */
		System.out.println("expectedHMap : "+expectedHMap);
		System.out.println("actualHMap : "+actualHMap);
		Assert.assertEquals(expectedHMap, actualHMap, "Both map result not matching - Venkat");;

		//Assert.assertNotEquals(true, true,"Both result not matching - Venkat");
	}

	@AfterTest
	public void closeBrowser(){
		// Close the browser
				if (driver != null) {
				driver.quit();	
				}
			}
	


}
