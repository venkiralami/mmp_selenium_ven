/**
 * 
 */
package org.iitwf.heathcare.mmp.testcases;

import java.io.IOException;
import java.time.Duration;

import org.iitwf.healthcare.mmp.BaseTest;
import org.iitwf.healthcare.mmp.data_provider.ExcelUtilsForDataProvider;
import org.iitwf.healthcare.mmp.pages.BasePage;
import org.iitwf.healthcare.mmp.pages.HomePage;
import org.iitwf.healthcare.mmp.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * 
 */
public class LoginPageTest extends BaseTest{

	private String mmp_URL = "http://85.209.95.122/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";
	WebDriver driver;
	//Login Page Object Input Value
	private String uName = "ria1";
	private String pw = "Ria12345";
	
	@Test
	public void loginTest() throws InterruptedException
	{
		driver = launchBrowser();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginValidUser(uName, pw);
	}
	
	 @DataProvider(name = "excelData")
	 public Object[][] getExcelData() throws IOException {
	    return ExcelUtilsForDataProvider.readExcelData("src/test/resources/TestDataLogin.xlsx", "Sheet1");
	 }

    @Test(dataProvider = "excelData")
    public void loginTest(String username, String password, String status) {
        System.out.println("Username: " + username + " | Password: " + password+ " | Status: " + status);
        // Add your Selenium/Playwright code here
        driver = launchBrowser();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginValidUser(uName, pw);
    }
	
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][]{
            // username, password, expectedResult
            {"ria1", "Ria12345", "success", "home"},   // valid login
            {"invalidUser", "Ria12345", "failure","Wrong username and password. " }, // invalid username
            {"ria1", "wrongPass", "failure","Wrong username and password. "},      // invalid password
            {"", "Ria12345", "failure",""},            // empty username
            {"ria1", "", "failure",""},               // empty password
            {"", "", "failure",""}                         // both empty
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest1(String uName, String pw, String loginDataType, String expectedMsg) throws IOException {
        driver = launchBrowser();
		LoginPage loginPage = new LoginPage(driver);
		if(loginDataType.equalsIgnoreCase("failure")) {
			String invalidMsg = loginPage.loginEmptyUser(uName, pw);
			if(uName.isEmpty() && pw.isEmpty()) {
				Assert.assertEquals(invalidMsg, expectedMsg, "Login was not successful, isEmpty() && pw.isEmpty Field empty");
			} else if(uName.isEmpty()) {
				Assert.assertEquals(invalidMsg, expectedMsg, "Login was not successful, uName Field empty");
			} else if(pw.isEmpty()) {
				Assert.assertEquals(invalidMsg, expectedMsg, "Login was not successful, pw.isEmpty() Field empty");
			} else {
				String invalidCreds = loginPage.loginInValidUser(uName, pw);
				Assert.assertEquals(invalidCreds, expectedMsg, "Login was not successful, Wrong credentials");
			}
			} else if(loginDataType.equalsIgnoreCase("success")) {
			loginPage.loginValidUser(uName, pw);
			Assert.assertEquals(driver.getTitle(), "home", "Login was not successful, Home Page title mismatch.");
		}
		driver.quit();	
    }
    
    @AfterTest
	public void closeBrowser(){
		// Close the browser
		driver.quit();	
	}

}
