/**
 * 
 */
package org.iitwf.healthcare.mmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

/**
 * 
 */
public class ScheduleAppointmentExcelUtils {

	/**
	 * @param args
	 */
	String expectedDoctorName = "";
	String expectedAppointmentDate = getExpectedAppointmentDateAndTime(200, "dd/MMMMM/yyyy");
	String[] expectedAppointmentTimes = {"10Am", "11Am" , "12Pm"};
	String expectedAppointmentTime = expectedAppointmentTimes[2];

	//Sysmptoms Page Object Value
	String expectedSymptoms ="Fever and Nasuia";
	
	static String excellFileName = "ScheduleAppointmentData.xlsx";
	static String excellSheetName = "ExpectedAppointmentDataSheet";


	public static void main(String[] args) throws IOException, Exception {
		HashMap<String, String> hp = bookAppointmentTest();
		//List<String> doctorsList = getDataFromXLSX(excellFileName, 0);
		System.out.println(hp);
	}
	//Right click on the column->format cell->Select Number (2 decimal points)
	//Post that, add the data in the second column
	public static List<String> getDataFromXLSX(String fileName, int sheetIndex) throws IOException  
	{
		List<String> doctorList= new ArrayList<String>();
		File f = new File(fileName);
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(sheetIndex);
		int rowCount = sheet.getPhysicalNumberOfRows();
		System.out.println("Doctor Row Count => "+rowCount);
		for(int rowIndex=0;rowIndex<rowCount;rowIndex++)
		{
			System.out.println("Doctor Names index => "+rowIndex);
			XSSFRow row = sheet.getRow(rowIndex);//fixing the row index
			XSSFCell keyCell=	  row.getCell(0);//fetch the first column - stockName
			//XSSFCell KeyValue =  row.getCell(1);//fetch the second column - stockprice
			String key=      keyCell.getStringCellValue();
			// Double value=    KeyValue.getNumericCellValue();
			System.out.println("Doctor Names => "+key);
			doctorList.add(key);
		}

		return doctorList;

	}


	public  static HashMap<String, String> bookAppointmentTest() throws InterruptedException
	{





		try {
			HashMap<String, String> excelHashMap = getExpectedAppointmentDataFromXLSX(excellFileName,excellSheetName);
			Set<String> expectedKeys = excelHashMap.keySet();
			System.out.println("Key Size = "+expectedKeys.size());
			for(String expectedKey : expectedKeys)
			{
				System.out.println("Key Name:" + expectedKey);
				System.out.println("Key Value:" + excelHashMap.get(expectedKey));

				if(expectedKey.equalsIgnoreCase("doctor")) {
					String expectedDoctorName = excelHashMap.get(expectedKey);
					System.out.println("D Name "+expectedDoctorName);
				}else if(expectedKey.equalsIgnoreCase("date")) {
					String expectedAppointmentDate = excelHashMap.get(expectedKey);
					System.out.println(" Ex Date "+expectedAppointmentDate);
				}else if(expectedKey.equalsIgnoreCase("time")) {
					String expectedAppointmentTime = excelHashMap.get(expectedKey);
					System.out.println(" Ex Time "+expectedAppointmentTime);
				}else if(expectedKey.equalsIgnoreCase("sym")) {
					String expectedSymptoms = excelHashMap.get(expectedKey);
					System.out.println(" Ex expectedSymptoms "+expectedSymptoms);
				}
				/*
					navigateToAModule(moduleName); 
					createNewAppointment();
					expectedHMap = bookAppointment(expectedDoctorName, expectedHMap);
					expectedHMap = selectAppointmentDateTime(expectedAppointmentDate, expectedAppointmentTime, expectedHMap);
					expectedHMap = addSymptoms(expectedSymptoms, expectedHMap);


					// expectedHMap = expectedData();
					actualHMap = fetchActualDatafromPatientPortalTable();
					System.out.println("Actual HMap::" +actualHMap);
					System.out.println("Expected HMap::" +expectedHMap);
					Assert.assertTrue(expectedHMap.equals(actualHMap));

					navigateToAModule(moduleName);
					actualHMap = currentAppointmentDetails();
					System.out.println("Actual HMap::" +actualHMap);
					System.out.println("Expected HMap::" +expectedHMap);
					Assert.assertTrue(expectedHMap.equals(actualHMap));
				 */
			}

			return	excelHashMap;
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
		
	return null;	
	}

	public static HashMap<String,String> getExpectedAppointmentDataFromXLSX(String fileName, String sheetName) throws IOException  
	{
		HashMap<String,String> hmap= new HashMap<String,String>();
		File f = new File(fileName);
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(sheetName);
		//int rowCount = sheet.getPhysicalNumberOfRows();
		int rowCount = sheet.getLastRowNum()+1;
		int colCount = sheet.getDefaultColumnWidth();
		System.out.println("Row Count "+rowCount);
		System.out.println("Column Count "+colCount);


		
		for(int rowIndex=0;rowIndex<rowCount;rowIndex++)
		{

			XSSFRow row = sheet.getRow(rowIndex);//fixing the row index
			int colNum = row.getLastCellNum();
		       System.out.println("Total Number of Columns in the excel is : "+colNum);
		       int rowNum1 = sheet.getLastRowNum()+1;
		       System.out.println("Total Number of Rows in the excel is : "+rowNum1);
			XSSFCell doctorCell=	  row.getCell(0);//fetch the first column - Doctor names
			String doctorName=      doctorCell.getStringCellValue();
			System.out.println("doctorName => "+doctorName);
			hmap.put("doctor",doctorName);

			XSSFCell appDateCell =	  row.getCell(1);//fetch the first column - Doctor names
			Number appDate1 =      appDateCell.getNumericCellValue();
			//String appDate = appDate1.toString();
			
			System.out.println("appDate => "+appDate1.intValue());
			String expectedAppointmentDate = getExpectedAppointmentDateAndTime(appDate1.intValue(), "dd/MMMMM/yyyy");
			System.out.println("Exp Date => "+expectedAppointmentDate);
			hmap.put("date",expectedAppointmentDate);

			XSSFCell appTimeCell =	  row.getCell(2);//fetch the first column - Doctor names
			String appTime =      appTimeCell.getStringCellValue();
			System.out.println("appTime => "+appTime);
			hmap.put("time",appTime);

			XSSFCell SymCell =	  row.getCell(3);//fetch the first column - Doctor names
			String sym =      SymCell.getStringCellValue();
			System.out.println("Symp => "+sym);
			hmap.put("sym",sym);
		}

		return hmap;

	}

	private static String getExpectedAppointmentDateAndTime(int noOfDays, String dateFormat) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, noOfDays);
		Date today = c.getTime();;
		return sdf.format(today);
	}
}
