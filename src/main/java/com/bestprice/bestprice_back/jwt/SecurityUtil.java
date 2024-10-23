package com.bestprice.bestprice_back.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


public class SecurityUtil {
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication.getName() = " + authentication.getName());
        if (authentication.getName().equals("anonymousUser") ||authentication.getName() == null) { // 로그인 토큰이 없을 시
            throw new IllegalStateException("유저 인증 정보가 없습니다. 다시 로그인 해주세요.");
        }
        return Long.parseLong(authentication.getName());
    }
}