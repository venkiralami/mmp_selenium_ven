package org.iitwf.healthcare.mmp.utils;

import java.io.File;
import java.text.SimpleDateFormat;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil {

	private static ExtentReports extent;
	  private static ExtentSparkReporter spark;
	    private static String reportPath;
	    
		/*
		 * public static ExtentReports getInstance1() {
		 * 
		 * if (extent == null) {
		 * System.out.println("ExtentReportUtil getInstance called"); SimpleDateFormat
		 * sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); // Create a new
		 * ExtentSparkReporter with a unique file name ExtentSparkReporter spark = new
		 * ExtentSparkReporter("target/ExtentReport_"+sdf.format(new
		 * java.util.Date())+".html"); extent = new ExtentReports();
		 * extent.attachReporter(spark); } return extent; }
		 */
	 public static ExtentReports getInstance() {
	        if (extent == null) {
	            System.out.println("ExtentReportUtil getInstance called");
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	            reportPath = "target/ExtentReport_" + sdf.format(new java.util.Date()) + ".html";
	            spark = new ExtentSparkReporter(reportPath);
	            extent = new ExtentReports();
	            extent.attachReporter(spark);
	        }
	        return extent;
	    }
	
	 // ✅ Call this after extent.flush() to get the generated file
    public static File getReportFile() {
        return (spark != null) ? spark.getFile() : null;
    }

    public static String getReportPath() {
        return reportPath;
    }
}
