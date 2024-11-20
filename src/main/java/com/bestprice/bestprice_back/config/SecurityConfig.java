package com.bestprice.bestprice_back.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bestprice.bestprice_back.jwt.JwtTokenFilter;
import com.bestprice.bestprice_back.user.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {

                    // requests.requestMatchers("/user/login", "/user/register").permitAll() // 로그인
                    // 및 회원가입은 허용
                    // requests.anyRequest().authenticated(); // 나머지 요청은 인증 필요
                    requests.anyRequest().permitAll();
                })
                .addFilterBefore(new JwtTokenFilter(userService), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build(); // HttpSecurity 빌드 및 반환
    }
}
