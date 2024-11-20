package com.bestprice.bestprice_back.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "66033f8ac5b9e47867e6bbda4c8757ac483106a0741bf9baed903fd39c1e850e";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final String ACCESS_TOKEN_TYPE = "access";

    public static String createAccessToken(String userId, long expireTimeMs) {
        validateUserId(userId);
        return createToken(userId.trim(), expireTimeMs, ACCESS_TOKEN_TYPE);
    }

    private static void validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }

    private static String createToken(String userId, long expireTimeMs, String tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("tokenType", tokenType);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getUserId(String token) {
        if (token == null || token.isEmpty() || token.contains(" ")) {
            System.out.println("Invalid JWT format: '" + token + "'");
            throw new TokenValidationException("Invalid JWT format. Unexpected spaces or empty token found.");
        }

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            System.out.println("Invalid JWT structure. Token: " + token);
            throw new TokenValidationException("Invalid JWT structure. Expected 3 segments.");
        }

        // JWT 토큰을 파싱하여 userId를 추출하는 단계
        return extractClaims(token).get("userId", String.class);
    }

    public static boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public static boolean isAccessToken(String token) {
        return ACCESS_TOKEN_TYPE.equals(extractClaims(token).get("tokenType", String.class));
    }


    private static Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenValidationException("Token expired");
        } catch (JwtException e) {
            throw new TokenValidationException("Error in JWT processing: " + e.getMessage());
        }
    }

    public static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String message) {
            super(message);
        }
    }
}
