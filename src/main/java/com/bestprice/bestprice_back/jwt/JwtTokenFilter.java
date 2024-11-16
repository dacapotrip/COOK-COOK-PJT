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

        // Authorization 헤더가 없거나 "Bearer "로 시작하지 않으면 필터를 통과시킵니다.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 접두사를 제거하고 토큰만 추출
        String token = authorizationHeader.substring(7).trim();

        // 토큰에 공백이 포함되어 있는지 확인하고 오류 응답 처리
        if (token.isEmpty() || token.contains(" ")) {
            System.out.println("Error: Token contains unexpected spaces or is empty.");
            errorResponse(response, "Invalid token format");
            return;
        }

        try {
            // Access Token인지 확인
            if (!JwtTokenUtil.isAccessToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 토큰 만료 여부 확인
            if (JwtTokenUtil.isExpired(token)) {
                throw new JwtTokenUtil.TokenValidationException("Token expired");
            }

            // 사용자 ID 추출
            String userId = JwtTokenUtil.getUserId(token);
            if (userId == null || userId.isEmpty()) {
                throw new JwtTokenUtil.TokenValidationException("Invalid token format: userId is empty");
            }

            // 사용자 인증 설정
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userId, null, List.of(new SimpleGrantedAuthority("USER")));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtTokenUtil.TokenValidationException e) {
            errorResponse(response, e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    // 토큰 자체에 문제가 있을 때 리턴
    private void errorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }
}
