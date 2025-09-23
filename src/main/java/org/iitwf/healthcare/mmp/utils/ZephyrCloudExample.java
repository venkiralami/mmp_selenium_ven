package org.iitwf.healthcare.mmp.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class ZephyrCloudExample {

    // Replace with your Jira details
    private static final String JIRA_EMAIL = "venki.ralami@gmail.com";   // your Atlassian account email
    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL3ZlbmtpcmFsYW1pLmF0bGFzc2lhbi5uZXQiLCJ1c2VyIjp7ImFjY291bnRJZCI6IjYyMDM2MWQ0NTA2MzE3MDA2YjA4MjM2ZCIsInRva2VuSWQiOiJhZTZjY2RmNC00N2E0LTRmNmQtYTM5My0xMmNiNjA3MGQ5YTYifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiNWI2NTc4MWYtMTVkMC0zMmNjLWE0N2YtNTkzZjk1ZjZhODcwIiwiZXhwIjoxNzg3ODkyODExLCJpYXQiOjE3NTYzNTY4MTF9.kP1PgiooCMHKpM84I2QgfYs8r2-G-OfhrE2Vu-qXl9Q";           // generate from id.atlassian.com
    private static final String PROJECT_KEY = "SCRUM";                  // Jira project key
    private static final String BASE_URL = "https://prod-api.zephyr4jiracloud.com/connect/public/rest/api/1.0";

    public static void main(String[] args) {
        try {
            createTestCycle("Automation Cycle - Aug 2025");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTestCycle(String cycleName) throws Exception {
        String apiUrl = BASE_URL + "/cycle";

        // Build JSON body
        String jsonBody = "{"
                + "\"name\": \"" + cycleName + "\","
                + "\"projectKey\": \"" + PROJECT_KEY + "\","
                + "\"versionId\": -1"
                + "}";

        // Setup connection
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        // Authentication (Basic Auth: email + token)
        String auth = JIRA_EMAIL + ":" + API_TOKEN;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
            os.flush();
        }

        // Read response
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream()
        ));

        StringBuilder response = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }

        conn.disconnect();
        System.out.println("Response Body: " + response);
    }
}
