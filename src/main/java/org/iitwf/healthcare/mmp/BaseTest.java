/**
 * 
 */
package org.iitwf.healthcare.mmp;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * 
 */
public class BaseTest {
	
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



	//Module Name Object Value
	String[] moduleNames = {"HOME", "Profile", "Schedule Appointment", "Information" , "Fees", "Search Symptoms", "Messages", "Logout"};
	String moduleName = moduleNames [1];
	
	//Module Name xPath
	String xPath_ModuleName = "//span[normalize-space(text())='\"+moduleName+\"'";


	
	public static WebDriver driver;
	/**
	 * @param args
	 */
	
	public void BaseMainTest() {
		launchBrowser();
		login(uName,pw);
		navigateToAModule(moduleName);
	}
	public WebDriver launchBrowser() {
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(mmp_URL);
		return driver;
	}

	public String getExpectedAppointmentDateAndTime(int noOfDays, String dateFormat) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, noOfDays);
		Date today = c.getTime();;
		return sdf.format(today);
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
	
	public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rand.nextInt(characters.length())));
        }
        return sb.toString();
    }
	
	public static String generateRandomStringLength(int length) {
        
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            sb.append(generateRandomStringUsingAscii(length));
        }
        System.out.println("Randon String : "+ sb.toString());
        return sb.toString();
    }
	
	@Test
	public static String generateRandomStringUsingAscii(int length) {
       //Lower chars as per Ascii
		Random rand = new Random();
		int minL = 97;
		int maxL = 122;
		char randomCharLower = (char) ( minL+rand.nextInt(maxL-minL+1));
		System.out.println(" Lower Char Random : "+randomCharLower);
		
		//Upper chars as per Ascii
		int minU = 65;
		int maxU = 90;
		char randomCharUpper = (char) (minU+rand.nextInt(maxU-minU+1));
		System.out.println(" Upper Char Random : "+randomCharUpper);
		String randonSting = String.valueOf(randomCharUpper) + String.valueOf(randomCharLower);
		
        return randonSting;
    }
	
	@Test
	public static String generateRandomAddress(int minLen, int maxLen) {
       //Lower chars as per Ascii
		Random rand = new Random();
		int minL = 97;
		int maxL = 122;
		char randomCharLower = (char) ( minL+rand.nextInt(maxL-minL+1));
		System.out.println(" Lower Char Random : "+randomCharLower);
		
		//Upper chars as per Ascii
		int minU = 65;
		int maxU = 90;
		char randomCharUpper = (char) (minU+rand.nextInt(maxU-minU+1));
		System.out.println(" Upper Char Random : "+randomCharUpper);
		
		String randonSting = generateRandomNumber(minLen, maxLen)+" "+String.valueOf(randomCharUpper)+" MacArthur Blvd " + String.valueOf(randomCharLower);
		System.out.println("Randon Address :: "+randonSting);
        return randonSting;
    }

	@Test
	public static int generateRandomNumber(int min, int max) {
		// Between min (inclusive) and max (exclusive)
		// min = 10000000;
		// max = 39999999;
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min) + min;
		System.out.println(" Random number between :  "+min+" and "+max+" :::: " + randomNum);
		return randomNum;
	}	
	
	public static String generateRandomSsn() {
		//302-38-2370
		String randomSsn = null;
		int ssn_p1 = generateRandomNumber(100,999);
		int ssn_p2 = generateRandomNumber(10,99);
		int ssn_p3 = generateRandomNumber(1000,9999);
		randomSsn = String.valueOf(ssn_p1)+"-"+String.valueOf(ssn_p2)+"-"+String.valueOf(ssn_p3);
		System.out.println(randomSsn);
		return randomSsn;
	}
	
	@Test
	public static void main(String[] args) {
		//generateRandomLicenceNumber();
		generateRandomStringLength(5);
		generateRandomSsn();
        Random rand = new Random();
        int randomNum = 10000000 + rand.nextInt(90000000); // 8-digit number
        System.out.println("8-digit random number: " + randomNum);
    }
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
	
}
