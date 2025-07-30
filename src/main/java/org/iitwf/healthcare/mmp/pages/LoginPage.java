/**
 * 
 */
package org.iitwf.healthcare.mmp.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 */
public class LoginPage{

	protected WebDriver driver;

	

	//Login Page xPath	
	private By xPath_uName = By.xpath("//input[@id='username']");
	private By  xPath_pw = By.xpath("//input[@id='password']");
	private By  xPath_forgotUnPw = By.xpath("//a[text()='forgot Username / Password']");
	private By  xPath_signIn = By.xpath("//input[@value='Sign In']");
	private By  xPath_register = By.xpath("//input[@value='Register']");

	/**
	 * 
	 */
	public LoginPage(WebDriver driver){
			        this.driver = driver;
	    
		if (!driver.getTitle().equals("Login")) {
			throw new IllegalStateException("This is not Login Page," +
					" current page is: " + driver.getCurrentUrl());
		}
	}

	/*
	 * public void login(String uname , String pwd) {
	 * driver.findElement(xPath_uName).sendKeys(uname);
	 * driver.findElement(xPath_pw).sendKeys(pwd);
	 * driver.findElement(xPath_signIn).click(); }
	 */
	public HomePage loginValidUser(String userName, String password) {
		driver.findElement(xPath_uName).sendKeys(userName);
		driver.findElement(xPath_pw).sendKeys(password);
		driver.findElement(xPath_signIn).click();
		return new HomePage(driver);
	}

	public String loginInValidUser(String userName, String password) {
		driver.findElement(xPath_uName).sendKeys(userName);
		driver.findElement(xPath_pw).sendKeys(password);
		driver.findElement(xPath_signIn).click();
		
		String alertMsg= handleAlertIfPresent(driver);
		
		return alertMsg;
	}
	
	public String handleAlertIfPresent(WebDriver driver) {
	    String alertText = null;
		try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        alertText =  alert.getText();
	        System.out.println("Alert found: " +alertText);
	        alert.accept();
	    } catch (TimeoutException e) {
	        // No alert appeared
	    }
	    return alertText;
	}

	public RegistrationPage registerNewUser() {

		driver.findElement(xPath_register).click();

		return new RegistrationPage(driver);
	}
}
