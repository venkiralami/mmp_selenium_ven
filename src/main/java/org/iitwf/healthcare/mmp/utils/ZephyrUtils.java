package org.iitwf.healthcare.mmp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ZephyrUtils {
    public static String generateQSH(String httpMethod, String apiEndpoint) {
        try {
            String canonicalRequest = httpMethod.toUpperCase() + "&" + apiEndpoint + "&";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(canonicalRequest.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating QSH", e);
        }
    }
}

