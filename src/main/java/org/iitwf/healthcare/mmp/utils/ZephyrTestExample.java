package org.iitwf.healthcare.mmp.utils;

public class ZephyrTestExample {
    public static void main(String[] args) throws Exception {
        ZephyrClient client = new ZephyrClient();

        String projectId = "10000";      // from Jira
        String versionId = "-1";         // Unscheduled version
        String cycleName = "Automation Cycle";

        // 1. Create cycle
        String cycleId = client.createTestCycle(projectId, versionId, cycleName);
        System.out.println("Created Cycle ID: " + cycleId);

        // 2. Add test cases
        client.addTestToCycle(projectId, versionId, cycleId, "10003"); // Issue ID of test case
        client.addTestToCycle(projectId, versionId, cycleId, "10004");

        // 3. Update execution status
        client.updateExecutionStatus("10001", 1); // PASS
        client.updateExecutionStatus("10002", 2); // FAIL
    }
}
