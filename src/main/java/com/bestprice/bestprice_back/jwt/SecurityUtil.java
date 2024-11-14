package com.bestprice.bestprice_back.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



public class SecurityUtil {
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            throw new IllegalStateException("유저 인증 정보가 없습니다. 다시 로그인 해주세요.");
        }
        return Long.parseLong(authentication.getName()); // String을 Long으로 변환
    }
    
}