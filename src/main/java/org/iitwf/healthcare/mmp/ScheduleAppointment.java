/**
 * 
 */
package org.iitwf.healthcare.mmp;



/**
 * 
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ScheduleAppointment {
	static WebDriver driver;
	
	public static void main(String[] args) throws IOException {
		
		/*
		 Access to the url -> http://85.209.95.122/MMP-Release2-Integrated-Build.6.8.000/portal/login.php
ria1/Ria12345
Click on Signin

Click on Schedule Appointment
Click on Create New Appintment
Click on Book Appointment -> Alexander
Switch to A Frame
Click on DatePicker
Select the date 90 days from Now
	call the method get the date-getFutureDate("dd/MM/YYYY",90)- 16/September/2025

Hint: while the month matching with the month which is displayed 
		click on next


		 */
		
		
	    driver = new ChromeDriver();
		driver.get("http://85.209.95.122/MMP-Release2-Integrated-Build.6.8.000/portal/login.php");
		//HashMap<String,Double> expectedData = ExcelUtils. getDataFromXLSX("stockgainers.xlsx");
		driver.manage().window().maximize();
		System.out.println("Login Page ==> ");
		WebElement userName = driver.findElement(By.id("username"));
		userName.sendKeys("ria1");
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Ria12345");
		
		WebElement signIn = driver.findElement(By.name("submit"));
		signIn.click();
		
		//driver.findElement(By.xpath("//a[contains(@href,'Home')]")).click();
		driver.findElement(By.linkText("Schedule Appointment")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[3]/a/input")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/table/tbody/tr/td[2]/button")).click();
	
		//switch To IFrame using name or id
       // WebElement iframe1=driver.findElement(By.name("iframe1-name"));
        //Switch to the frame
        driver.switchTo().frame(0);
       System.out.println("iFrame ==> "+driver.getPageSource().contains("Book Appointment"));
       driver.findElement(By.xpath("/html/body/p[1]/input")).sendKeys("06/26/2025");
        //Now we can type text into email field
       driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[4]/td[5]/a")).click();
       driver.findElement(By.xpath("/html/body/p[2]/select")).sendKeys("10Am");
       
       driver.findElement(By.xpath("/html/body/button")).click();
       driver.switchTo().defaultContent();
       driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[2]/div/form/textarea")).sendKeys("3 days Fever for Venkat");
       
       driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[3]/input")).click();
       
        driver.quit();
		
	}
	public static HashMap<String,Double> fetchStockPriceDetailsfromUI(Set<String> stockNames)
	{
		
		HashMap<String,Double> h = new HashMap<String,Double>();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String key:stockNames)
		{
			System.out.println("UI Key ===> "+key);
			//key="Fabino Enterprises";

			System.out.println("UI Key ===> "+key);
		//	h.put(key,Double.parseDouble(driver.findElement(By.xpath("//a[text()='"+key+"']/parent::td/following-sibling::td[3]")).getText()));
			h.put(key,Double.parseDouble(driver.findElement(By.xpath("//a[text()='"+key+"']/parent::td/following-sibling::td[3]")).getText()));
			
		}
		
		 
		 
		return h;
	}
	

}
