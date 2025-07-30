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
import org.openqa.selenium.chrome.ChromeDriver;

public class TabularDataEx {
	static WebDriver driver;
	
	public static void main(String[] args) throws IOException {
		
		
	    driver = new ChromeDriver();
		driver.get("https://money.rediff.com/gainers");
		HashMap<String,Double> expectedData = ExcelUtils. getDataFromXLSX("stockgainers.xlsx");
		
		System.out.println("Expected ==> " + expectedData);
		HashMap<String,Double> actualData = fetchStockPriceDetailsfromUI(expectedData.keySet());
		System.out.println("Actual" + actualData);
	    System.out.println("Result::::" + expectedData.equals(actualData));
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
