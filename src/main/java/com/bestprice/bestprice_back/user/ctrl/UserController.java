package com.bestprice.bestprice_back.user.ctrl;

import com.bestprice.bestprice_back.jwt.JwtTokenUtil;
import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.LoginRequestDTO;
import com.bestprice.bestprice_back.user.dto.LoginResponseDTO;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;
import com.bestprice.bestprice_back.user.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil; 

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<User> userOptional = userService.login(loginRequestDTO.getUserId(), loginRequestDTO.getPassword());

        if (userOptional.isPresent()) {
            // JWT 생성
            String accessToken = jwtTokenUtil.createAccessToken(userOptional.get().getUserId(), 3600000); // 1시간
            String refreshToken = jwtTokenUtil.createRefreshToken(userOptional.get().getUserId(), 86400000); // 24시간

            // 로그인 응답 반환
            LoginResponseDTO response = new LoginResponseDTO();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
    }
}