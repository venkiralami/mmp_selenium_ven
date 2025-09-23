package org.iitwf.healthcare.mmp.utils;

import java.util.List;

public class JiraTestExampleIIT {

    public static void main(String[] args) {
        String baseUrl = "https://iitworkforceelearning.atlassian.net";
        String email = "venki.ralami@gmail.com";
        String apiToken = "ATATT3xFfGF0MnP_-G_ZUApyzFhQMy0DdzqVvBlVTBEuYO0ZM1Bq_qWtZwTBD-cvsQYJxtfKlZsdEZ4QdfYy_2GB1zS2xUioYoXwicsYiFaI3nZFlIuXxVpcyUw15Cli8NlB5ce5qjpqEB-u_8DrR8_RymK5siHHaT5a-aP-Ra71PQ_AIv4cd9A=9090F396";

        String projectKey = "SCRUM";
        String issueKey = "SCRUM-13";

     
        JiraClient jira = new JiraClient(baseUrl, email, apiToken);

     // Add a comment
     jira.addCommentToIssue(issueKey, "[Automation] Test executed successfully! comments added by Venkat : "+email);

     // Get comments
     jira.getComments(issueKey);

     // Get all issue keys in the project
     List<String> keys = jira.getAllIssueKeys(projectKey);
     System.out.println("All issue keys in project: " + keys);
    }
}
