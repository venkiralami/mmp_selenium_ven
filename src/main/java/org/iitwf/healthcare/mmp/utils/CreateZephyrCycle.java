
		package org.iitwf.healthcare.mmp.utils;

		import java.util.Base64;
		import java.util.Date;
		import javax.crypto.Mac;
		import javax.crypto.spec.SecretKeySpec;

		import okhttp3.*;

		import org.json.JSONObject;

		public class CreateZephyrCycle {

		    private static final String BASE_URL = "https://prod-api.zephyr4jiracloud.com/connect";
		    private static final String API_PATH = "/public/rest/api/1.0/cycle";

		    // Your Zephyr credentials
		    private static final String ACCESS_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL3ZlbmtpcmFsYW1pLmF0bGFzc2lhbi5uZXQiLCJ1c2VyIjp7ImFjY291bnRJZCI6IjYyMDM2MWQ0NTA2MzE3MDA2YjA4MjM2ZCIsInRva2VuSWQiOiJhZTZjY2RmNC00N2E0LTRmNmQtYTM5My0xMmNiNjA3MGQ5YTYifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiNWI2NTc4MWYtMTVkMC0zMmNjLWE0N2YtNTkzZjk1ZjZhODcwIiwiZXhwIjoxNzg3ODkyODExLCJpYXQiOjE3NTYzNTY4MTF9.kP1PgiooCMHKpM84I2QgfYs8r2-G-OfhrE2Vu-qXl9Q";
		    private static final String SECRET_KEY = "ae6ccdf4-47a4-4f6d-a393-12cb6070d9a6";
		    private static final String ACCOUNT_ID = "620361d4506317006b08236d";


		    public static void main(String[] args) throws Exception {

		        long expiration = System.currentTimeMillis() + 1000 * 60; // 1 min expiry
		        String jwt = generateJWT(ACCESS_KEY, SECRET_KEY, ACCOUNT_ID, "POST", API_PATH, expiration);

		        OkHttpClient client = new OkHttpClient();

		        // Correct payload using projectId (not projectKey)
		        JSONObject payload = new JSONObject();
		        payload.put("name", "Automation Cycle - Aug 2025");
		        payload.put("projectId", 10000); // Replace with your actual projectId
		        payload.put("versionId", -1);

		        Request request = new Request.Builder()
		                .url(BASE_URL + API_PATH)
		                .addHeader("Authorization", "JWT " + jwt)
		                .addHeader("zapiAccessKey", ACCESS_KEY)
		                .addHeader("Content-Type", "application/json")
		                .post(RequestBody.create(payload.toString(), MediaType.parse("application/json")))
		                .build();

		        Response response = client.newCall(request).execute();
		        System.out.println("Response Code: " + response.code());
		        System.out.println("Response Body: " + response.body().string());
		    }

		    private static String generateJWT(String accessKey, String secretKey, String accountId,
		                                      String method, String apiPath, long expiration) throws Exception {

		        String header = Base64.getUrlEncoder().withoutPadding()
		                .encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());

		        String payload = String.format("{\"sub\":\"%s\",\"qsh\":\"%s&%s&%s\",\"iss\":\"%s\",\"exp\":%d}",
		                accountId,
		                method.toUpperCase(),
		                apiPath,
		                "", // query string empty
		                accessKey,
		                expiration);

		        String payloadBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes());
		        String token = header + "." + payloadBase64;

		        Mac hmac = Mac.getInstance("HmacSHA256");
		        hmac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));
		        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal(token.getBytes()));

		        return token + "." + signature;
		    }
		}

