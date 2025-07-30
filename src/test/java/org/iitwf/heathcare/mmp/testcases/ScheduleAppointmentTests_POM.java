package org.iitwf.heathcare.mmp.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.iitwf.healthcare.mmp.BaseTest;
import org.iitwf.healthcare.mmp.pages.AddSymptomsPage;
import org.iitwf.healthcare.mmp.pages.CurrentAppointmentsPage;
import org.iitwf.healthcare.mmp.pages.DatePickerPage;
import org.iitwf.healthcare.mmp.pages.HomePage;
import org.iitwf.healthcare.mmp.pages.LoginPage;
import org.iitwf.healthcare.mmp.pages.ProvidersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScheduleAppointmentTests_POM extends BaseTest{ 

	String mmp_URL = "http://85.209.95.122/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";

	//Login Page Object Input Value
	String uName = "ria1";
	String pw = "Ria12345";

	// Appointment Details Page Object Value
	String[] doctorNames = {"Charlie", "Smith", "Alexander", "Becky", "Sophia Rich", "Annabeth", "subramanya"};

	String expectedDoctorName = doctorNames [0];

	List<String> doctorNamesFromExcell= null;

	String expectedAppointmentDate = getExpectedAppointmentDateAndTime(200, "d/MMMMM/yyyy");
	String[] expectedAppointmentTimes = {"10Am", "11Am" , "12Pm"};
	String expectedAppointmentTime = expectedAppointmentTimes[1];

	//Sysmptoms Page Object Value
	String expectedSymptoms ="Fever and Nasuia and Caugh";

	HashMap<String,String> expectedHMap = new HashMap<String, String>();
	HashMap<String,String> actualHMap;
	String excellFileName = "ScheduleAppointmentData.xlsx";
	String excellSheetName = "ExpectedAppointmentDataSheet";

	WebDriver driver;

	//Home Page Object Value
	private String[] moduleNames = {"HOME", "Profile", "Schedule Appointment", "Information" , "Fees", "Search Symptoms", "Messages", "Logout"};
	private String moduleName = moduleNames [2];

	@Test
	public void validateBookAppointment() throws InterruptedException
	{
		driver = launchBrowser();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginValidUser(uName, pw);
		HomePage homePage = new HomePage(driver);
		homePage.navigateToAModule(moduleNames[2]); 
		CurrentAppointmentsPage currentAppointmentsPage = new CurrentAppointmentsPage(driver);
		currentAppointmentsPage.createNewAppointment();
		ProvidersPage providersPage = new ProvidersPage(driver);
		expectedHMap = providersPage.bookAppointment(expectedDoctorName, expectedHMap);
		DatePickerPage datePickerPage = new DatePickerPage(driver);
		expectedHMap = datePickerPage.selectAppointmentDateTime(expectedAppointmentDate, expectedAppointmentTime, expectedHMap);
		AddSymptomsPage addSymptomsPage = new AddSymptomsPage(driver);
		expectedHMap = addSymptomsPage.addSymptoms(expectedSymptoms, expectedHMap);

		try {
			// expectedHMap = expectedData();
			actualHMap = homePage.fetchActualDatafromPatientPortalTable();
			System.out.println("Home Page Actual HMap::" +actualHMap);
			System.out.println("Expected HMap::" +expectedHMap);
			Assert.assertTrue(expectedHMap.equals(actualHMap));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Wrong data in Home page");
		}
		try {
			homePage.navigateToAModule(moduleName);
			actualHMap = currentAppointmentsPage.currentAppointmentDetails();
			System.out.println("Current Appointment Page Actual HMap::" +actualHMap);
			System.out.println("Expected HMap::" +expectedHMap);
			Assert.assertTrue(expectedHMap.equals(actualHMap));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Wrong data in Current Appointment page");
		}

	}
	@AfterTest
	public void closeBrowser(){
		if (driver != null) {
			driver.quit();	
			}
	}

}










