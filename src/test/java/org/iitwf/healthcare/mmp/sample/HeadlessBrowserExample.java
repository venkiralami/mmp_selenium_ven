package org.iitwf.healthcare.mmp.sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class HeadlessBrowserExample {
    public static void main(String[] args) {
        // Example for Headless Chrome
		HeadlessChromeExample();

		// Example for Headless Firefox
		HeadlessFirefoxExample();
		
		HeadlessEdgeExample();
    }
    
    public static void HeadlessChromeExample() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // or "--headless" for legacy mode
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.com");

        System.out.println("ChromeOptions Title: " + driver.getTitle());
        driver.quit();
    }
    
   

        public static void HeadlessFirefoxExample() {
        	 //System.setProperty("webdriver.gecko.driver", "path/to/geckodriver.exe");

        	FirefoxOptions options = new FirefoxOptions();
           // options.setHeadless(true);
            options.addArguments("-headless");
            options.addArguments("--window-size=1920,1080");
            WebDriver driver = new FirefoxDriver(options);
            driver.get("https://example.com");

            System.out.println("FirefoxOptions Title: " + driver.getTitle());
            driver.quit();
        }
       
        
            public static void HeadlessEdgeExample() {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless");
                options.addArguments("--window-size=1920,1080");

                WebDriver driver = new EdgeDriver(options);
                driver.get("https://www.microsoft.com");

                System.out.println("EdgeOptions Title: " + driver.getTitle());
                driver.quit();
            }
        

}

