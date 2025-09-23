package org.iitwf.healthcare.mmp.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.iitwf.healthcare.mmp.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ListenerUtilityForJiraUpdate implements org.testng.ITestListener {

	private ExtentReports extent= ExtentReportUtil.getInstance();
	private ExtentTest test;

    private JiraClient jiraClient = new JiraClient(
            "https://venkiralami.atlassian.net",       // ✅ Correct Jira base
            "venki.ralami@gmail.com",
            "ATATT3xFfGF0MnP_-G_ZUApyzFhQMy0DdzqVvBlVTBEuYO0ZM1Bq_qWtZwTBD-cvsQYJxtfKlZsdEZ4QdfYy_2GB1zS2xUioYoXwicsYiFaI3nZFlIuXxVpcyUw15Cli8NlB5ce5qjpqEB-u_8DrR8_RymK5siHHaT5a-aP-Ra71PQ_AIv4cd9A=9090F396"
    );

   
    
	@Override
	public void onTestStart(org.testng.ITestResult result) {
		System.out.println("Test started: " + result.getName());
		// TODO Auto-generated method stub
		test = extent.createTest(result.getMethod().getMethodName());
		test.assignCategory("Regression");
		test.assignAuthor("Ria");
		test.assignDevice("Windows 10, Chrome");
		test.log(com.aventstack.extentreports.Status.INFO, "Test started");
	}
	

	 @Override
	    public void onTestSuccess(ITestResult result) {
	        handleTestResult(result, "PASS");
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	        handleTestResult(result, "FAIL");
	        //captureScreenshot(result);
	    }

	    private void handleTestResult(ITestResult result, String status) {
	        String methodName = result.getMethod().getMethodName();
	        String issueKey = getIssueKeyFromMethod(methodName);

	        // 1️⃣ Update Jira comment
	        jiraClient.addCommentToIssue(issueKey, "[Automation] Test " + methodName + " → " + status);

	        // 2️⃣ Update Zephyr Scale execution
	      //  zephyrClient.updateTestExecution(issueKey, status);
	    }

	   

	    private String captureScreenshot(ITestResult result) {
	    	Object currentObject = result.getInstance();
			WebDriver driver = ((BaseTest) currentObject).driver; 
			String screenshotPath = null;
			try {
				screenshotPath = ScreenshotUtil.takeScreenshot(result.getName(), driver);
				  test = extent.createTest(result.getName());
				    test.fail("Test Failed - Ven").addScreenCaptureFromPath(screenshotPath);
				    // Optionally attach to Jira/Zephyr
		           // jiraClient.addAttachment(result.getMethod().getMethodName(), screenshotPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return screenshotPath;
	    }
	    
	    private String getIssueKeyFromMethod(String methodName) {
	        // Map your test method names to Jira issue keys
	        // Example: profileMainTest → SCRUM-6
	        if (methodName.equalsIgnoreCase("profileMainTest")) return "SCRUM-6";
	        if (methodName.equalsIgnoreCase("profileMainTestFailScenario")) return "SCRUM-8";
	        return "SCRUM-1"; // Default fallback
	    }


	
	public void onTestSkipped(org.testng.ITestResult result) {
		System.out.println("Test skipped: " + result.getName());
		test.log(com.aventstack.extentreports.Status.SKIP, "Test skipped");
	}
	public void onFinish(ITestContext context) {
		System.out.println("All tests finished.");
		if (extent != null) {
			extent.flush();
		}
		File reportFile = ExtentReportUtil.getReportFile();
	    if (reportFile != null && reportFile.exists()) {
	        try {
	            Desktop.getDesktop().browse(reportFile.toURI());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Report not found: " + ExtentReportUtil.getReportPath());
	    }
		System.out.println("Extent report flushed successfully.");
	}

}
