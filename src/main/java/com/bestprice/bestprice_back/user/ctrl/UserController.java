package com.bestprice.bestprice_back.user.ctrl;

import com.bestprice.bestprice_back.jwt.JwtTokenUtil;
import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.EmailVerificationDTO;
import com.bestprice.bestprice_back.user.dto.LoginRequestDTO;
import com.bestprice.bestprice_back.user.dto.LoginResponseDTO;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;
import com.bestprice.bestprice_back.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil; 

    @Operation(summary = "회원가입", description = "사용자를 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return ResponseEntity.ok("회원가입이 완료되었습니다. 인증 메일을 확인하세요.");
    }

    @Operation(summary = "이메일 인증", description = "이메일 인증을 수행합니다.")
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String token) {
        EmailVerificationDTO verificationDTO = new EmailVerificationDTO(email, token);
        Optional<User> userOptional = userService.verifyEmail(verificationDTO);
        
        if (userOptional.isPresent()) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.status(400).body("유효하지 않거나 만료된 인증 링크입니다.");
        }
    }


    @Operation(summary = "로그인", description = "사용자를 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 실패")
    })
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
