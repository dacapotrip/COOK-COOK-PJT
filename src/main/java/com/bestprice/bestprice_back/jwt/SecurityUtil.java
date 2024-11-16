package com.bestprice.bestprice_back.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



public class SecurityUtil {
    public static String getCurrentUserId() { // String으로 반환
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            throw new IllegalStateException("유저 인증 정보가 없습니다. 다시 로그인 해주세요.");
        }
        return authentication.getName(); // 그대로 반환
    }
}
