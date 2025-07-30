package org.iitwf.heathcare.mmp.testcases;

import java.util.HashMap;

import org.iitwf.healthcare.mmp.pages.HomePage;
import org.iitwf.healthcare.mmp.pages.LoginPage;
import org.iitwf.healthcare.mmp.pages.RegistrationPage;
import org.iitwf.healthcare.mmp.pages.UsersPage;
import org.iitwf.healthcare.mmp.utils.FrameworkLibrary;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class RegistrationE2E extends FrameworkLibrary {

	@Test
	public void registerNewPatient() {

		//Patient login to send a message
		LoginPage lPage = new LoginPage(driver);
		RegistrationPage rPage = lPage.registerNewUser();	 
		HashMap<String, String> expectedHMap =rPage.registerNewUser();
		System.out.println("expectedHMap :"+expectedHMap);
		launchApplication(prop.getProperty("patient_url"));
		SoftAssert sa = new SoftAssert();
		String alertMsg = lPage.loginInValidUser(expectedHMap.get("username"), expectedHMap.get("password"));
		sa.assertEquals(alertMsg, "Admin Approval is pending. "); 

		//Admin login to verify the message
		launchApplication(prop.getProperty("admin_url"));
		HomePage hPage = lPage.loginValidUser(prop.getProperty("admin_username"), prop.getProperty("admin_password"));	 
		hPage.navigateToAModule("Users");
		UsersPage uPage = new UsersPage(driver);
		HashMap<String, String> actualHMap = uPage.actionOnNewUser(expectedHMap.get("fname"), expectedHMap.get("ssn"));

		launchApplication(prop.getProperty("patient_url"));
		lPage.loginValidUser(expectedHMap.get("username"), expectedHMap.get("password"));

		expectedHMap.remove("username");
		expectedHMap.remove("password");
		expectedHMap.remove("security");
		System.out.println("expectedHMap :"+expectedHMap);
		System.out.println("actualHMap :"+actualHMap);
		sa.assertEquals(expectedHMap, actualHMap);
		sa.assertAll();
	}

	@AfterTest
	public void closeBrowser(){
		// Close the browser
				if (driver != null) {
				driver.quit();	
				}
			}
	
}
