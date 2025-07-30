package org.iitwf.healthcare.mmp;

import org.testng.Assert;
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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ScheduleAppointmentTests_Organized extends ScheduleAppointmentExcelUtils{ 

	String mmp_URL = "http://85.209.95.122/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";

	//Login Page Object Input Value
	String uName = "ria1";
	String pw = "Ria12345";
	//Login Page xPath	
	String xPath_uName = "//input[@id='username']";
	String xPath_pw = "//input[@id='password']";
	String xPath_forgotUnPw = "//a[text()='forgot Username / Password']";
	String xPath_signIn = "//input[@value='Sign In']";
	String xPath_register = "//input[@value='Register']";



	//Home Page Object Value
	String[] moduleNames = {"HOME", "Profile", "Schedule Appointment", "Information" , "Fees", "Search Symptoms", "Messages", "Logout"};
	String moduleName = moduleNames [2];
	
	//Home Page xPath
	String xPath_ModuleName = "//span[normalize-space(text())='\"+moduleName+\"'";

	String xPath_CreateNewAppointment ="//input[@value='Create new appointment']";

	// Appointment Details Page Object Value
	String[] doctorNames = {"Charlie", "Smith", "Alexander", "Becky", "Sophia Rich", "Annabeth", "subramanya"};

	String expectedDoctorName = doctorNames [3];

	List<String> doctorNamesFromExcell= null;

	String expectedAppointmentDate = getExpectedAppointmentDateAndTime(200, "dd/MMMMM/yyyy");
	String[] expectedAppointmentTimes = {"10Am", "11Am" , "12Pm"};
	String expectedAppointmentTime = expectedAppointmentTimes[2];

	//Sysmptoms Page Object Value
	String expectedSymptoms ="Fever and Nasuia";

	// Appointment Details xPath
	String xPath_Date = "//input[@id='datepicker']";
	String xPath_Year = "//span[@class='ui-datepicker-year']";
	String xPath_Month = "//span[@class='ui-datepicker-month']";
	String xPath_Next = "//span[text()='Next']";
	String xPath_prev = "//span[text()='Prev']";
	String xPath_AppointmentTime = "//option[@value='"+expectedAppointmentTime+"']";
	String xPath_ContinueButton =  "//button[@id='ChangeHeatName']";



	//Symptoms Page xPath
	String xPath_Symptoms = "//textarea[@id='sym']";
	String xPath_SymptomSubmitButton = "//input[@value=\'Submit\']";
	// Home Module xPath
	String xPath_Home_Date = "//table[@class='table']/tbody/tr[1]/td[1]";
	String xPath_Home_Time = "//table[@class='table']/tbody/tr[1]/td[2]";
	String xPath_Home_Symptoms = "//table[@class='table']/tbody/tr[1]/td[3]";
	String xPath_Home_Doctor = "//table[@class='table']/tbody/tr[1]/td[4]";

	//Current Appointment Page xPath
	String xPath_ActualDate = "//h3[@class='panel-title']//following::h3";
	String xPath_ActualTime = "//div[@class='list-group list-statistics']//child::a";

	HashMap<String,String> expectedHMap = new HashMap<String, String>();
	HashMap<String,String> actualHMap;
	String excellFileName = "ScheduleAppointmentData.xlsx";
	String excellSheetName = "ExpectedAppointmentDataSheet";


	WebDriver driver;

	@Test
	public void validateBookAppointmentFromExcellData() throws InterruptedException
	{
		launchBrowser(mmp_URL);
		login(uName,pw);
		try {
			HashMap<String, String> excelHashMap = getExpectedAppointmentDataFromXLSX(excellFileName, excellSheetName);
			Set<String> expectedKeys = excelHashMap.keySet();
			System.out.println("excelHashMap  :: "+excelHashMap);
			System.out.println("expectedKeys Size :: "+expectedKeys.size());
			//for(String expectedKey : expectedKeys)
			//{
			//System.out.println("Key Name:" + expectedKey);
			//	System.out.println("Key Value:" + excelHashMap.get(expectedKey));

			//if(expectedKey.equalsIgnoreCase("doctor")) {
			expectedDoctorName = excelHashMap.get("doctor");
			System.out.println("D Name "+expectedDoctorName);
			//}
			// if(expectedKey.equalsIgnoreCase("date")) {
			expectedAppointmentDate = excelHashMap.get("date");
			System.out.println(" Ex Date "+expectedAppointmentDate);
			//} if(expectedKey.equalsIgnoreCase("time")) {
			expectedAppointmentTime = excelHashMap.get("time");
			System.out.println(" Ex Time "+expectedAppointmentTime);
			//} if(expectedKey.equalsIgnoreCase("sym")) {
			expectedSymptoms = excelHashMap.get("sym");
			System.out.println(" Ex expectedSymptoms "+expectedSymptoms);
			//}

			expectedHMap.put("doctor",expectedDoctorName);
			expectedHMap.put("date",expectedAppointmentDate);
			expectedHMap.put("time",expectedAppointmentTime);
			expectedHMap.put("sym",expectedSymptoms);

			navigateToAModule(moduleName); 
			createNewAppointment();
			expectedHMap = bookAppointment(expectedDoctorName, expectedHMap);
			expectedHMap = selectAppointmentDateTime(expectedAppointmentDate, expectedAppointmentTime, expectedHMap);
			expectedHMap = addSymptoms(expectedSymptoms, expectedHMap);
			try {
				// expectedHMap = expectedData();
				actualHMap = fetchActualDatafromPatientPortalTable();
				System.out.println("Home Page Actual HMap::" +actualHMap);
				System.out.println("Expected HMap::" +expectedHMap);
				Assert.assertTrue(expectedHMap.equals(actualHMap));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Wrong data in Home page");
			}
			try {
				navigateToAModule(moduleName);
				actualHMap = currentAppointmentDetails();
				System.out.println("Current Appointment Page Actual HMap::" +actualHMap);
				System.out.println("Expected HMap::" +expectedHMap);
				Assert.assertTrue(expectedHMap.equals(actualHMap));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Wrong data in Current Appointment page");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 bookAppointment(expectedDoctorName);
		 selectAppointmentDateTime(expectedAppointmentDate, expectedAppointmentTime);
		 addSymptoms(expectedSymptoms);

		navigateToAModule(moduleName);
		currentAppointmentDetails();
		 */
		// Close the browser
		// driver.quit();
	}

	@Test
	public void validateBookAppointmentFromExcellDoctorListOnly() throws InterruptedException
	{

		launchBrowser(mmp_URL);
		login(uName,pw);
		try {
			doctorNamesFromExcell = getDataFromXLSX("ScheduleAppointmentData.xlsx", 0);
			if(doctorNamesFromExcell.size()!=0) {
				for(int rowIndex=0;rowIndex<doctorNamesFromExcell.size();rowIndex++) {
					expectedDoctorName = doctorNamesFromExcell.get(rowIndex);
					System.out.println("D Name "+expectedDoctorName);

					navigateToAModule(moduleName); 
					createNewAppointment();
					expectedHMap = bookAppointment(expectedDoctorName, expectedHMap);
					expectedHMap = selectAppointmentDateTime(expectedAppointmentDate, expectedAppointmentTime, expectedHMap);
					expectedHMap = addSymptoms(expectedSymptoms, expectedHMap);

					try {
						// expectedHMap = expectedData();
						actualHMap = fetchActualDatafromPatientPortalTable();
						System.out.println("Home Page Actual HMap::" +actualHMap);
						System.out.println("Expected HMap::" +expectedHMap);
						Assert.assertTrue(expectedHMap.equals(actualHMap));
					}catch (Exception e) {
						// TODO: handle exception
						System.out.println("Wrong data in Home page");
					}
					try {
						navigateToAModule(moduleName);
						actualHMap = currentAppointmentDetails();
						System.out.println("Current Appointment Page Actual HMap::" +actualHMap);
						System.out.println("Expected HMap::" +expectedHMap);
						Assert.assertTrue(expectedHMap.equals(actualHMap));
					}catch (Exception e) {
						// TODO: handle exception
						System.out.println("Wrong data in Current Appointment page");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 bookAppointment(expectedDoctorName);
		 selectAppointmentDateTime(expectedAppointmentDate, expectedAppointmentTime);
		 addSymptoms(expectedSymptoms);

		navigateToAModule(moduleName);
		currentAppointmentDetails();
		 */
		// Close the browser
		// driver.quit();
	}

	@Test
	public void validateBookAppointment() throws InterruptedException
	{
		launchBrowser(mmp_URL);
		login(uName,pw);

		navigateToAModule(moduleName); 
		createNewAppointment();

		expectedHMap = bookAppointment(expectedDoctorName, expectedHMap);
		expectedHMap = selectAppointmentDateTime(expectedAppointmentDate, expectedAppointmentTime, expectedHMap);

		expectedHMap = addSymptoms(expectedSymptoms, expectedHMap);


		try {
			// expectedHMap = expectedData();
			actualHMap = fetchActualDatafromPatientPortalTable();
			System.out.println("Home Page Actual HMap::" +actualHMap);
			System.out.println("Expected HMap::" +expectedHMap);
			Assert.assertTrue(expectedHMap.equals(actualHMap));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Wrong data in Home page");
		}
		try {
			navigateToAModule(moduleName);
			actualHMap = currentAppointmentDetails();
			System.out.println("Current Appointment Page Actual HMap::" +actualHMap);
			System.out.println("Expected HMap::" +expectedHMap);
			Assert.assertTrue(expectedHMap.equals(actualHMap));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Wrong data in Current Appointment page");
		}
		// Close the browser
		// driver.quit();
	}



	private String getExpectedAppointmentDateAndTime(int noOfDays, String dateFormat) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, noOfDays);
		Date today = c.getTime();;
		return sdf.format(today);
	}



	public void launchBrowser(String url) {
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(url);
	}


	public   void login(String uname , String pwd) {
		driver.findElement(By.xpath(xPath_uName)).sendKeys(uname);
		driver.findElement(By.xpath(xPath_pw)).sendKeys(pwd);
		driver.findElement(By.xpath(xPath_signIn)).click();
	}

	public void navigateToAModule(String moduleName)
	{
		System.out.println("Module Name : " + moduleName);
		driver.findElement(By.xpath( "//span[normalize-space(text())='"+moduleName+"']")).click();
	}

	public void createNewAppointment()
	{
		System.out.println("Create new Appointment started--");
		driver.findElement(By.xpath(xPath_CreateNewAppointment)).click();
	}

	public   HashMap<String, String> bookAppointment(String doctorName,HashMap<String, String> expectedMap) {

		expectedMap.put("doctor", doctorName);
		String doctorAppointmentXPath = "//h4[text()='Dr."+doctorName+"']/ancestor::ul/following-sibling::button";
		WebElement revealed = driver.findElement(By.xpath(doctorAppointmentXPath));
		/*
		System.out.println("Is revealed b "+revealed.isDisplayed());
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		System.out.println("Is revealed "+revealed.isDisplayed());
		wait.until(d -> revealed.isDisplayed());
		 */
		revealed.click();
		return expectedMap;
	}

	private HashMap<String, String> selectAppointmentDateTime( String expectedAppointmentDate, String appointmentTime,HashMap<String, String> expectedMap) {

		System.out.println(" Appoinment Expected Date : "+expectedAppointmentDate+" Expected Time : "+appointmentTime);

		driver.switchTo().frame("myframe");

		//System.out.println("Inside iFrame --");

		String expectedDate[] = expectedAppointmentDate.split("/");
		String expectedDay   = expectedDate[0];
		String expectedMonth = expectedDate[1];
		String expectedYear =  expectedDate[2];
		System.out.println("Expected Year:::" + expectedYear);

		driver.findElement(By.xpath(xPath_Date)).click();

		String actualYear =   driver.findElement(By.xpath(xPath_Year)).getText(); 

		System.out.println("actual Year:::: " + actualYear);	

		while(!(expectedYear.equals(actualYear)))
		{
			driver.findElement(By.xpath(xPath_Next)).click();
			actualYear =   driver.findElement(By.xpath(xPath_Year)).getText(); 
			//	System.out.println("actual Year:inside loop::: " + actualYear);
			if(expectedYear.equalsIgnoreCase(actualYear)) {
				System.out.println(" == Year loop Break ==");
				break;
			}
		}
		String actualMonth = driver.findElement(By.xpath(xPath_Month)).getText();
		//System.out.println(" == Pic before loop Break ==expectedMonth "+expectedMonth);
		//System.out.println(" == Pic After loop Break == actualMonth "+actualMonth);
		while(!(expectedMonth.equals(actualMonth)))
		{
			//	System.out.println("in while loop checking the Ac Month"+actualMonth);
			//	System.out.println("in while loop checking the Ex Month"+expectedMonth);
			driver.findElement(By.xpath("//span[text()='Next']")).click();
			actualMonth =   driver.findElement(By.xpath(xPath_Month)).getText(); 
			if(expectedMonth.equalsIgnoreCase(actualMonth)) {
				System.out.println(" == Month loop Break ==");
				break;
			}
		}
		driver.findElement(By.linkText(expectedDay)).click();

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMMMM/yyyy");

		Date varDate1 = null;
		try {
			varDate1 = dateFormat1.parse(expectedAppointmentDate);

			dateFormat1=new SimpleDateFormat("MM/dd/yyyy");
			expectedAppointmentDate=dateFormat1.format(varDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//expectedMap.put("date",driver.findElement(By.id("datepicker")).getDomAttribute("value"));
		expectedMap.put("date",expectedAppointmentDate);

		//driver.findElement(By.xpath(xPath_AppointmentTime)).click();
		new Select( driver.findElement(By.id("time"))).selectByVisibleText(expectedAppointmentTime);
		expectedMap.put("time",new Select( driver.findElement(By.id("time"))).getFirstSelectedOption().getText());

		//wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("status")),"OK"));
		driver.findElement(By.xpath(xPath_ContinueButton)).click();
		driver.switchTo().defaultContent();
		return expectedMap;
	}

	private HashMap<String, String> addSymptoms(String symptoms, HashMap<String, String> expectedMap) {

		driver.findElement(By.xpath(xPath_Symptoms)).sendKeys(symptoms);

		String sym_DOMVal = driver.findElement(By.xpath(xPath_Symptoms)).getDomProperty("value");
		System.out.println("Sym Dom val -- > "+sym_DOMVal);

		if(!sym_DOMVal.isEmpty() || !sym_DOMVal.equalsIgnoreCase("") ||
				!sym_DOMVal.equalsIgnoreCase(null)) { 
			expectedMap.put("sym", sym_DOMVal);
		}else {		 
			expectedMap.put("sym",	symptoms);
		}
		driver.findElement(By.xpath(xPath_SymptomSubmitButton)).click();
		return expectedMap;
	}


	private HashMap<String, String> currentAppointmentDetails() {
		String actualDate =  driver.findElement(By.xpath(xPath_ActualDate)).getText(); 
		String actualTime =  driver.findElement(By.xpath(xPath_ActualTime+"[1]")).getText(); 
		String actualTimeA[] = actualTime.split(" ");
		actualTime = actualTimeA[2];
		String actualDoctor =  driver.findElement(By.xpath(xPath_ActualTime+"[2]")).getText(); 
		actualDoctor = actualDoctor.split(":")[1];
		String actualSymptom =  driver.findElement(By.xpath(xPath_ActualTime+"[3]")).getText(); 
		actualSymptom = actualSymptom.split(":")[1];
		actualHMap.put("doctor", actualDoctor);
		actualHMap.put("date", actualDate);
		actualHMap.put("time", actualTime);
		actualHMap.put("sym", actualSymptom);
		return actualHMap;
	}

	public HashMap<String, String> fetchActualDatafromPatientPortalTable()
	{
		HashMap<String,String> actualHMap = new HashMap<String,String>();
		actualHMap.put("date",driver.findElement(By.xpath(xPath_Home_Date)).getText());
		actualHMap.put("time",driver.findElement(By.xpath(xPath_Home_Time)).getText());
		actualHMap.put("sym",driver.findElement(By.xpath(xPath_Home_Symptoms)).getText());
		actualHMap.put("doctor",driver.findElement(By.xpath(xPath_Home_Doctor)).getText());

		return actualHMap;
	}

	public HashMap<String, String> expectedData()
	{
		HashMap<String,String> expectedHMap = new HashMap<String,String>();
		expectedHMap.put("doctor",expectedDoctorName);
		expectedHMap.put("date",expectedAppointmentDate);
		expectedHMap.put("time",expectedAppointmentTime);
		expectedHMap.put("sym",expectedSymptoms);

		return expectedHMap;
	}



	//	public static void main(String[] args) {
	//		
	//		
	//        // Get the current local date
	//        LocalDate currentDate = LocalDate.now();
	//
	//        // Get the current local date and time
	//        LocalDateTime currentDateTime = LocalDateTime.now();
	//
	//        // Define the desired date and time format
	//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
	//
	//        // Format the date and time objects into Strings
	//        String formattedDate = currentDate.format(dateFormatter);
	//        String formattedDateTime = currentDateTime.format(dateTimeFormatter);
	//
	//        // Print the formatted date and time
	//        System.out.println("Current Date: " + formattedDate);
	//        System.out.println("Current Date and Time: " + formattedDateTime);
	//    }

}


//int a =2;
//int b =3;
//while(a!=b)
//{
//	System.out.println("Hello");
//	a=3;
//	
//}








