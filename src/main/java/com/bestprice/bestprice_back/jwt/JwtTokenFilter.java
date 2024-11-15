package com.bestprice.bestprice_back.jwt;

import com.bestprice.bestprice_back.user.service.UserService;
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

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 공백 제거 후 토큰 추출
        String token = authorizationHeader.substring(7).trim();
        System.out.println("Token after trimming: " + token);

        // 토큰이 Access Token인지 확인
        try {
            if (!JwtTokenUtil.isAccessToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (JwtTokenUtil.TokenValidationException e) {
            errorResponse(response, "Invalid token format: not an access token");
            return;
        }

        // 토큰 만료 여부 확인
        try {
            if (JwtTokenUtil.isExpired(token)) {
                throw new JwtTokenUtil.TokenValidationException("Token expired");
            }
        } catch (JwtTokenUtil.TokenValidationException e) {
            errorResponse(response, e.getMessage());
            return;
        }

        // Access Token인 경우 처리
        String userId;
        try {
            userId = JwtTokenUtil.getUserId(token.trim()); // 공백 제거 후 파싱
            if (userId == null || userId.isEmpty()) {
                throw new JwtTokenUtil.TokenValidationException("Invalid token format: userId is empty");
            }
        } catch (JwtTokenUtil.TokenValidationException e) {
            errorResponse(response, e.getMessage());
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userId, null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    // 토큰 자체에 문제가 있을 때 리턴
    private void errorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }
}
