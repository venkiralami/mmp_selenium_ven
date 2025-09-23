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
    public void loginTest(String uName, String pw, String status) {
        System.out.println("Username: " + uName + " | Password: " + pw+ " | Status: " + status);
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
            {"", "Ria12345", "failure","Username field cannot be empty"},            // empty username
            {"ria1", "", "failure","Password field cannot be empty"},               // empty password
            {"", "", "failure","Username and Password fields cannot be empty"}                         // both empty
        };
    }

   
    
    
    // 🔹 Test method using DataProvider
    @Test(dataProvider = "loginData")
    public void loginTestSameMethod(String uName, String pw, String loginType, String expectedMsg) throws IOException {
        driver = launchBrowser();
        LoginPage loginPage = new LoginPage(driver);

        String actualMessage = null;

        if (loginType.equals("success")) {
        	loginPage.loginValidUser(uName, pw);
        	actualMessage = driver.getTitle();
		}else if (loginType.equals("failure")) {
			// 🔹 Example: handle alert for invalid login
			actualMessage = loginPage.loginInValidUser(uName, pw);
		}
        System.out.println("Username: " + uName + " | Password: " + pw+ " | LoginType: " + loginType+ " | Expected : "+expectedMsg+ " | Actual : "+actualMessage);
        Assert.assertEquals(actualMessage, expectedMsg, 
            "Validation message mismatch for user: " + uName);
    }
    
    @AfterTest
	public void closeBrowser(){
		// Close the browser
		driver.quit();	
	}

}
