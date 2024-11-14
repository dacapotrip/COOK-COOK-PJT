package com.bestprice.bestprice_back.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bestprice.bestprice_back.user.service.UserService;


import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Authorization Header: " + authorizationHeader); // 헤더 출력
    
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
    
        // "Bearer " 접두사 제거 후 공백 제거
        String token = authorizationHeader.substring(7).trim(); // 공백 제거
        System.out.println("Extracted token = " + token);
    
        try {
            // JWT 검증
            if (!JwtTokenUtil.isAccessToken(token) || JwtTokenUtil.isExpired(token)) {
                filterChain.doFilter(request, response);
                return;
            }
    
            String userId = JwtTokenUtil.getUserId(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userId, null, List.of(new SimpleGrantedAuthority("USER"))
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
    
        } catch (JwtTokenUtil.TokenValidationException e) {
            errorResponse(response);
        }
    }
    
    
    

    //토큰 자체에 문제가 있을때 리턴
    private void errorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + "로그인 정보에 문제가 있습니다. 다시 로그인 해주세요." + "\"}");
    }
}

