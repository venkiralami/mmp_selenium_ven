/**
 * 
 */
package org.iitwf.healthcare.mmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 */
public class ExcelUtils {

	/**
	 * @param args
	 */


	public static void main(String[] args) throws IOException {

		HashMap<String,Double> output = getDataFromXLSX("stockgainers.xlsx");
		System.out.println(output);
	}
	//Right click on the column->format cell->Select Number (2 decimal points)
	//Post that, add the data in the second column
	public static HashMap<String,Double> getDataFromXLSX(String fileName) throws IOException  
	{
		HashMap<String,Double> hmap= new HashMap<String,Double>();
		File f = new File(fileName);
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();

		for(int rowIndex=0;rowIndex<rowCount;rowIndex++)
		{

			XSSFRow row = sheet.getRow(rowIndex);//fixing the row index
			XSSFCell keyCell=	  row.getCell(0);//fetch the first column - stockName
			XSSFCell KeyValue =  row.getCell(1);//fetch the second column - stockprice
			String key=      keyCell.getStringCellValue();
			Double value=    KeyValue.getNumericCellValue();
			System.out.println("Key => "+key+" Value => "+value);
			hmap.put(key,value);
		}

		return hmap;

	}

}
