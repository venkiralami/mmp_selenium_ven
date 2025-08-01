package org.iitwf.healthcare.mmp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;

public class FrameworkLibrary {

	protected WebDriver driver;
	protected Properties prop = new Properties();
    @BeforeClass
	public void setUp() throws IOException {
		// TODO Auto-generated method stub
		readPropertyFile("mmp_global.properties");

		System.out.println("Browser:: Ven"+ prop.getProperty("browsertype"));
		System.out.println("Environment::"+ prop.getProperty("environment"));

		//Read the browser type and environment from the properties file
		switch (prop.getProperty("browsertype").toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			System.out.println("Firefox browser is not implemented yet.");
			break;
		}
		switch (prop.getProperty("environment").toLowerCase()) {

		case "qa":
			readPropertyFile("mmp_qa.properties");
			break;
		case "dev":
			readPropertyFile("mmp_dev.properties");
			break;

		}
		launchApplication(prop.getProperty("patient_url"));

	}

	public  void readPropertyFile(String fileName) throws IOException {
		// TODO Auto-generated method stub

		String filePath = System.getProperty("user.dir") + "/config/" + fileName;
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		// Load the properties file
		prop.load(fis);
		System.out.println("Properties file loaded successfully: " + filePath);
	}
	public  void launchApplication(String url) {
		// TODO Auto-generated method stub
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	

}
