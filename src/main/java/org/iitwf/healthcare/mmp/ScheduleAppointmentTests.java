package org.iitwf.healthcare.mmp;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ScheduleAppointmentTests {

	String xPath_URL = "http://85.209.95.122/MMP-Release2-Integrated-Build.6.8.000/portal/login.php";
	
	//xPath	
	String xPath_uName = "";
	String xPath_pw = "";
	String xPath_forgotUnPw = "";
	String xPath_signIn = "";
	
	WebDriver driver;
	@Test
	public void validateBookAppointment() throws InterruptedException
	{
		launchBrowser();
		getUrl(xPath_URL);
		login("ria1","Ria12345");
		navigateToAModule("Schedule Appointment");
		String expectedAppointmentDate = getExpectedAppointmentDateAndTime(300, "dd/MMMMM/yyyy");
		System.out.println(" Future Date --> "+expectedAppointmentDate);



		String expectedAppointmentTime = "11Am";

		bookAppointment("Becky", expectedAppointmentDate, expectedAppointmentTime);
		String symptoms ="I have high fever past 3 days";
		addSymptoms(symptoms);
		
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



	public void launchBrowser() {
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	public   void getUrl(String url) {
		driver.get(url);
	}
	public   void login(String uname , String pwd) {
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(pwd);
		driver.findElement(By.name("submit")).click();
	}

	public void navigateToAModule(String moduleName)
	{
		driver.findElement(By.xpath( "//span[normalize-space(text())='"+moduleName+"']")).click();
	}

	public   void bookAppointment(String doctorName, String expectedAppointmentDate, String appointmentTime) {

		driver.findElement(By.xpath("//input[@value='Create new appointment']")).click();
		//driver.findElement(By.xpath("//button[text()='Book Appointment']")).click();
		String doctorAppointmentXPath = "//h4[text()='Dr."+doctorName+"']/ancestor::ul/following-sibling::button";
		System.out.println("Before iFrame --doctorAppointmentXPath "+doctorAppointmentXPath);
		//driver.switchTo().frame(0);

		//driver.switchTo().defaultContent();
		WebElement revealed = driver.findElement(By.xpath(doctorAppointmentXPath));

		System.out.println("Is revealed b "+revealed.isDisplayed());
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		System.out.println("Is revealed "+revealed.isDisplayed());
		wait.until(d -> revealed.isDisplayed());

		revealed.click();
		//  Assertions.assertEquals("Displayed", revealed.getDomProperty("value"));

		driver.switchTo().frame("myframe");

		System.out.println("Inside iFrame --");

		String expectedDate[] = expectedAppointmentDate.split("/");
		String expectedDay   = expectedDate[0];
		String expectedMonth = expectedDate[1];
		String expectedYear =  expectedDate[2];
		System.out.println("Expected Year:::" + expectedYear);



		driver.findElement(By.id("datepicker")).click();


		String actualYear =   driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText(); 

		System.out.println("actual Year:::: " + actualYear);	


		while(!(expectedYear.equals(actualYear)))
		{
			System.out.println("Result:::" + actualYear.equals(expectedYear));
			System.out.println("in while loop checking the year"+actualYear);
			driver.findElement(By.xpath("//span[text()='Next']")).click();
			actualYear =   driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText(); 
			System.out.println("actual Year:inside loop::: " + actualYear);
			if(expectedYear.equalsIgnoreCase(actualYear)) {
				System.out.println(" == Year loop Break ==");

				break;
			}

		}
		String actualMonth = driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
		System.out.println(" == Pic before loop Break ==expectedMonth "+expectedMonth);
		//driver.findElement(By.id("datepicker")).click();
		System.out.println(" == Pic After loop Break == actualMonth "+actualMonth);
		while(!(expectedMonth.equals(actualMonth)))
		{
			System.out.println("in while loop checking the Ac Month"+actualMonth);
			System.out.println("in while loop checking the Ex Month"+expectedMonth);
			driver.findElement(By.xpath("//span[text()='Next']")).click();
			actualMonth =   driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText(); 
			if(expectedMonth.equalsIgnoreCase(actualMonth)) {
				System.out.println(" == Month loop Break ==");
				break;
			}
		}



		driver.findElement(By.linkText(expectedDay)).click();
		driver.findElement(By.xpath( "//option[@value='"+appointmentTime+"']")).click();
		driver.findElement(By.xpath( "//button[@id='ChangeHeatName']")).click();
		driver.switchTo().defaultContent();
		//option[@value='10Am']
	}

	private void addSymptoms(String symptoms) {
		driver.findElement(By.xpath( "//textarea[@id=\"sym\"]")).sendKeys(symptoms);

		driver.findElement(By.xpath( "//input[@value=\"Submit\"]")).click();
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








