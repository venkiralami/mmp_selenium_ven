package org.iitwf.healthcare.mmp.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class ZephyrJWTGenerator {

    public static String generateJWT(String accessKey, String secretKey, String accountId, String method, String apiPath) {
        long now = System.currentTimeMillis();

        // ⚠️ QSH (Query String Hash) is required for Zephyr Cloud
        // For simple testing, you can use a placeholder "context_hash_placeholder"
        String qsh = "context_hash_placeholder";

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("sub", accountId)
                .claim("qsh", qsh)
                .setIssuer(accessKey)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600_000)) // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }
}
