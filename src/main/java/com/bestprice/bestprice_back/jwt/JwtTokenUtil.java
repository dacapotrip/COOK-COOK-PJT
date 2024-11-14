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
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    // Access Token 발급
    public static String createAccessToken(String userId, long expireTimeMs) {
        validateUserId(userId); // userId 유효성 검사
        return createToken(userId.trim(), expireTimeMs, ACCESS_TOKEN_TYPE); // 공백 제거
    }

    // Refresh Token 발급
    public static String createRefreshToken(String userId, long expireTimeMs) {
        validateUserId(userId); // userId 유효성 검사
        return createToken(userId.trim(), expireTimeMs, REFRESH_TOKEN_TYPE); // 공백 제거
    }

    // userId 유효성 검사 메서드 추가
    private static void validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }

    // JWT 토큰 생성 시 인코딩 방식 확인
    private static String createToken(String userId, long expireTimeMs, String tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId); // userId 추가
        claims.put("tokenType", tokenType);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 파싱 시 세그먼트 분리 및 확인
    public static String getUserId(String token) {
        System.out.println("Received token for parsing: " + token.trim()); // 공백 제거 후 출력
        // JWT 세그먼트 확인
        String[] parts = token.trim().split("\\."); // 공백 제거 후 분리
        if (parts.length != 3) {
            throw new TokenValidationException("Invalid JWT structure. Expected 3 segments.");
        }
        System.out.println("Header: " + parts[0]);
        System.out.println("Payload: " + parts[1]);
        System.out.println("Signature: " + parts[2]);

        return extractClaims(token.trim()).get("userId", String.class); // 공백 제거 후 파싱
    }

    // 토큰 만료 여부 확인
    public static boolean isExpired(String token) {
        return extractClaims(token.trim()).getExpiration().before(new Date());
    }

    // 토큰이 Access Token인지 확인
    public static boolean isAccessToken(String token) {
        return ACCESS_TOKEN_TYPE.equals(extractClaims(token.trim()).get("tokenType", String.class));
    }

    // Refresh Token 유효성 검증
    public static boolean validateRefreshToken(String token) {
        try {
            Claims claims = extractClaims(token.trim());
            String tokenType = claims.get("tokenType", String.class);

            if (isExpired(token.trim())) {
                return false; // 시간 만료 시 실패 반환
            }
            return REFRESH_TOKEN_TYPE.equals(tokenType); // 만료되지 않고 리프레시 토큰일 경우 true 반환
        } catch (TokenValidationException e) {
            return false;
        }
    }

    // token 파싱
    private static Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.trim()) // 공백 제거 후 파싱
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
