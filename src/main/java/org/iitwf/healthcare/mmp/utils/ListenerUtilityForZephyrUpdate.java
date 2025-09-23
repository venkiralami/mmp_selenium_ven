package org.iitwf.healthcare.mmp.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ListenerUtilityForZephyrUpdate implements ITestListener {

    private JiraClient jiraClient;
    private ZephyrClient zephyrClient;
    private WebDriver driver;

    private String projectKey = "SCRUM";
    private String testCycleId;

    // Map test method names → Jira/Zephyr test cases
    private Map<String, String> testMethodToIssueKey = new HashMap<>();

    public ListenerUtilityForZephyrUpdate() {
        
        String jiraBaseUrl = "https://venkiralami.atlassian.net";
        String email = "venki.ralami@gmail.com";
        String apiToken = "ATATT3xFfGF0MnP_-G_ZUApyzFhQMy0DdzqVvBlVTBEuYO0ZM1Bq_qWtZwTBD-cvsQYJxtfKlZsdEZ4QdfYy_2GB1zS2xUioYoXwicsYiFaI3nZFlIuXxVpcyUw15Cli8NlB5ce5qjpqEB-u_8DrR8_RymK5siHHaT5a-aP-Ra71PQ_AIv4cd9A=9090F396";

     
        jiraClient = new JiraClient(jiraBaseUrl, email, apiToken);
       // zephyrClient = new ZephyrClient(jiraBaseUrl, email, apiToken);

        // Configure test method → Jira issue mapping
        testMethodToIssueKey.put("JiraTest1", "SCRUM-1");
        testMethodToIssueKey.put("JiraTest2", "SCRUM-2");
        testMethodToIssueKey.put("profileMainTest", "SCRUM-3");
        testMethodToIssueKey.put("profileMainTestFailScenario", "SCRUM-4");

        
        // Create or use a test cycle
        try {
			testCycleId = zephyrClient.createTestCycle("Automation Cycle", projectKey, "-1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Add test cases to cycle
        for (String issueKey : testMethodToIssueKey.values()) {
      //      zephyrClient.addTestCaseToCycle(testCycleId, issueKey);
        }
    }

    public void setWebDriver(WebDriver driver) { this.driver = driver; }

    @Override
    public void onTestSuccess(ITestResult result) { handleResult(result, "PASS"); }

    @Override
    public void onTestFailure(ITestResult result) {
        handleResult(result, "FAIL");
        captureScreenshot(result);
    }

    private void handleResult(ITestResult result, String status) {
        String methodName = result.getMethod().getMethodName();
        String issueKey = testMethodToIssueKey.get(methodName);
        if (issueKey == null) return;

        // Update Jira comment
        jiraClient.addCommentToIssue(issueKey, "[Automation] Test " + methodName + " → " + status);

        // Update Zephyr execution
     //   zephyrClient.updateTestExecution(testCycleId, issueKey, status);
    }

    private void captureScreenshot(ITestResult result) {
        if (driver == null) return;
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            String fileName = "screenshots/" + result.getMethod().getMethodName() + ".png";
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(file)) { fos.write(screenshot); }
            System.out.println("Screenshot saved: " + fileName);

            //jiraClient.addAttachment(result.getMethod().getMethodName(), fileName);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
