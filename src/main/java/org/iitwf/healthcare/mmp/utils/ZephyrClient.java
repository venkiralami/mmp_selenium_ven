package org.iitwf.healthcare.mmp.utils;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ZephyrClient {

    private String baseUrl = "https://venkiralami.atlassian.net";
    private String username = "venki.ralami@gmail.com";
    private String password = "ATATT3xFfGF0MnP_-G_ZUApyzFhQMy0DdzqVvBlVTBEuYO0ZM1Bq_qWtZwTBD-cvsQYJxtfKlZsdEZ4QdfYy_2GB1zS2xUioYoXwicsYiFaI3nZFlIuXxVpcyUw15Cli8NlB5ce5qjpqEB-u_8DrR8_RymK5siHHaT5a-aP-Ra71PQ_AIv4cd9A=9090F396"; // or password for server
    private String authHeader;

    public ZephyrClient() {
        String auth = username + ":" + password;
        authHeader = "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }

    // Create Test Cycle
    public String createTestCycle(String projectId, String versionId, String cycleName) throws Exception {
        String url = baseUrl + "/rest/zephyr/latest/cycle";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", authHeader);
        post.setHeader("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        body.put("name", cycleName);
        body.put("projectId", projectId);
        body.put("versionId", versionId); // -1 for Unscheduled

        post.setEntity(new StringEntity(body.toString()));

        CloseableHttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        client.close();

        if (response.getStatusLine().getStatusCode() != 201) {
            throw new RuntimeException("Failed to create cycle: " + result);
        }

        JSONObject jsonResponse = new JSONObject(result);
        return jsonResponse.getString("id"); // cycle ID
    }

    // Add Test Case to Cycle
    public void addTestToCycle(String projectId, String versionId, String cycleId, String issueId) throws Exception {
        String url = baseUrl + "/rest/zephyr/latest/execution";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", authHeader);
        post.setHeader("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        body.put("issueId", issueId);
        body.put("cycleId", cycleId);
        body.put("projectId", projectId);
        body.put("versionId", versionId);

        post.setEntity(new StringEntity(body.toString()));

        CloseableHttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        client.close();

        if (response.getStatusLine().getStatusCode() != 201) {
            throw new RuntimeException("Failed to add test to cycle: " + result);
        }
    }

    // Update Execution Status
    public void updateExecutionStatus(String executionId, int status) throws Exception {
        // Status codes: 1=PASS, 2=FAIL, 3=WIP, 4=BLOCKED
        String url = baseUrl + "/rest/zephyr/latest/execution/" + executionId + "/execute";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(url);
        put.setHeader("Authorization", authHeader);
        put.setHeader("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        body.put("status", status);

        put.setEntity(new StringEntity(body.toString()));

        CloseableHttpResponse response = client.execute(put);
        String result = EntityUtils.toString(response.getEntity());
        client.close();

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed to update execution: " + result);
        }
    }
}
