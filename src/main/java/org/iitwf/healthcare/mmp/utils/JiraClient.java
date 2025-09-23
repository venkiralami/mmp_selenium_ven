package org.iitwf.healthcare.mmp.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class JiraClient {

    private String baseUrl;
    private String authHeader;

    public JiraClient(String baseUrl, String email, String apiToken) {
        this.baseUrl = baseUrl;
        String auth = email + ":" + apiToken;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    // ------------------- Add comment to issue -------------------
    public void addCommentToIssue(String issueKey, String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            System.out.println("⚠️ Comment is empty. Skipping Jira update for " + issueKey);
            return;
        }

        comment = comment.replace("\"", "\\\"").replace("\n", " ");

        String url = baseUrl + "/rest/api/3/issue/" + issueKey + "/comment";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", authHeader);
            request.setHeader("Content-Type", "application/json");

            // ✅ Rich text JSON for Jira Cloud
            String payload = "{ \"body\": { \"type\": \"doc\", \"version\": 1, \"content\": [ { \"type\": \"paragraph\", \"content\": [ { \"text\": \"" 
                    + comment + "\", \"type\": \"text\" } ] } ] } }";

            request.setEntity(new StringEntity(payload));

            try (CloseableHttpResponse response = client.execute(request)) {
                System.out.println("Add comment response: " + response.getStatusLine());
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------- Get all comments of an issue -------------------
    public void getComments(String issueKey) {
        String url = baseUrl + "/rest/api/3/issue/" + issueKey + "/comment";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", authHeader);
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONObject obj = new JSONObject(jsonResponse);

                if (obj.has("comments")) {
                    JSONArray comments = obj.getJSONArray("comments");
                    System.out.println("Comments for " + issueKey + ":");
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject commentObj = comments.getJSONObject(i);
                        JSONObject body = commentObj.getJSONObject("body");
                        JSONArray content = body.getJSONArray("content");
                        String text = parseContent(content);
                        System.out.println("- " + text);
                    }
                } else {
                    System.out.println("⚠️ No comments found or access denied: " + jsonResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------- Recursive method to parse rich text content -------------------
    private String parseContent(JSONArray contentArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contentArray.length(); i++) {
            JSONObject obj = contentArray.getJSONObject(i);
            String type = obj.getString("type");

            if ("text".equals(type) && obj.has("text")) {
                sb.append(obj.getString("text"));
            } else if (obj.has("content")) {
                sb.append(parseContent(obj.getJSONArray("content")));
            }
        }
        return sb.toString();
    }

    // ------------------- Get all issue keys in a project -------------------
    public List<String> getAllIssueKeys(String projectKey) {
        List<String> issueKeys = new ArrayList<>();
        String url = baseUrl + "/rest/api/3/search?jql=project=" + projectKey + "&maxResults=100&fields=key";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", authHeader);
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONObject obj = new JSONObject(jsonResponse);

                if (obj.has("issues")) {
                    JSONArray issues = obj.getJSONArray("issues");
                    for (int i = 0; i < issues.length(); i++) {
                        JSONObject issue = issues.getJSONObject(i);
                        issueKeys.add(issue.getString("key"));
                    }
                } else {
                    System.out.println("⚠️ No issues found or access denied: " + jsonResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return issueKeys;
    }
}
