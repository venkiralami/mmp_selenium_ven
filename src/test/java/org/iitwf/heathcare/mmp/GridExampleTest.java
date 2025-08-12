package org.iitwf.heathcare.mmp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.URL;

public class GridExampleTest {

    WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {
            caps.setBrowserName("chrome");
        } else if (browser.equalsIgnoreCase("firefox")) {
            caps.setBrowserName("firefox");
        }
        System.out.println("Running tests on: " + browser);
        driver = new RemoteWebDriver(new URL("http://localhost:4444"), caps);
    }

    @Test
    public void googleTest() {
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

