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

    private static final String SECRET_KEY = "66033f8ac5b9e47867e6bbda4c8757ac483106a0741bf9baed903fd39c1e850e"; // 실제 환경에서는 환경 변수 등에서 가져오는 것이 좋습니다.
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    // Access Token 발급
    public static String createAccessToken(String userId, long expireTimeMs) {
        return createToken(userId, expireTimeMs, ACCESS_TOKEN_TYPE);
    }

    // Refresh Token 발급
    public static String createRefreshToken(String userId, long expireTimeMs) {
        return createToken(userId, expireTimeMs, REFRESH_TOKEN_TYPE);
    }

    // Token 발급
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

    // Claims에서 userId 꺼내기
    public static String getUserId(String token) {
        return extractClaims(token).get("userId", String.class);
    }

    // 토큰 만료 여부 확인
    public static boolean isExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // 토큰이 Access Token인지 확인
    public static boolean isAccessToken(String token) {
        return ACCESS_TOKEN_TYPE.equals(extractClaims(token).get("tokenType", String.class));
    }

    // Refresh Token 유효성 검증
    public static boolean validateRefreshToken(String token) {
        try {
            Claims claims = extractClaims(token);
            String tokenType = claims.get("tokenType", String.class);

            if (isExpired(token)) {
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
