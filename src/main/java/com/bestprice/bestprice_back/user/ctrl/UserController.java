package com.bestprice.bestprice_back.user.ctrl;

import com.bestprice.bestprice_back.jwt.JwtTokenUtil;
import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.EmailVerificationDTO;
import com.bestprice.bestprice_back.user.dto.LoginRequestDTO;
import com.bestprice.bestprice_back.user.dto.LoginResponseDTO;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;
import com.bestprice.bestprice_back.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponse(responseCode = "201", description = "회원가입 성공")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return ResponseEntity.status(201).body("회원가입이 완료되었습니다. 인증 메일을 확인하세요.");
    }

    @Operation(summary = "이메일 인증", description = "이메일 인증을 수행합니다.")
    @GetMapping("/verify")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 인증 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않거나 만료된 인증 링크입니다.")
    })
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String token) {
        EmailVerificationDTO verificationDTO = new EmailVerificationDTO(email, token);
        Optional<User> userOptional = userService.verifyEmail(verificationDTO);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("유효하지 않거나 만료된 인증 링크입니다.");
        }
    }

    @Operation(summary = "로그인", description = "사용자를 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String userId = loginRequestDTO.getUserId();
        String password = loginRequestDTO.getPassword();

        // 로그 출력
        System.out.println("Received userId: '" + userId + "'");
        System.out.println("Received password: '" + password + "'");

        // 유효성 검사
        if (userId == null || userId.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Bad Request
        }

        // 사용자 인증
        Optional<User> userOptional = userService.login(userId, password);

        if (userOptional.isPresent() && userOptional.get().getUserId() != null) { // 추가 검증
            String userIdToUse = userOptional.get().getUserId();

            // JWT 생성 직전 userId 확인
            System.out.println("Creating JWT for userId: '" + userIdToUse + "' (length: " + userIdToUse.length() + ")");

            // JWT 생성 (정적 메서드 호출)
            String accessToken = JwtTokenUtil.createAccessToken(userIdToUse, 3600000); // 1시간
            String refreshToken = JwtTokenUtil.createRefreshToken(userIdToUse, 86400000); // 24시간

            // 응답 DTO 설정
            LoginResponseDTO response = new LoginResponseDTO();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } else {
            System.err.println("Login failed for userId: " + userId);
            return ResponseEntity.status(401).body(null); // Unauthorized
        }

    }

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃합니다.")
    @PostMapping("/logout")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패")
    })
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("로그아웃 실패: 유효하지 않은 Authorization 헤더입니다.");
        }

        // "Bearer " 접두사를 제거하고 공백 제거
        String token = authorizationHeader.substring(7).trim();

        try {
            // JWT에서 사용자 ID 추출
            String userId = JwtTokenUtil.getUserId(token);

            // userService.logout에서 String 타입 userId 처리
            userService.logout(userId);
            return ResponseEntity.ok("로그아웃이 완료되었습니다.");
        } catch (JwtTokenUtil.TokenValidationException e) {
            return ResponseEntity.badRequest().body("로그아웃 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "비밀번호 초기화 요청", description = "비밀번호 변경 메일을 보냅니다.")
    @PostMapping("/password/reset")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "메일 전송 성공"),
            @ApiResponse(responseCode = "400", description = "가입 이력이 없거나 유효하지 않은 이메일")
    })
    public ResponseEntity<String> passwordReset(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        String email = userRegisterDTO.getEmail();
        userService.passwordResetRequest(email);
        return ResponseEntity.status(201).body(email + " 메일함을 확인하여 비밀번호 초기화 진행해주세요.");
    }

    @Operation(summary = "새 비밀번호로 변경", description = "새 비밀번호를 설정합니다.")
    @PostMapping("/password/change")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity<String> changePassword(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.changePassword(userRegisterDTO);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @Operation(summary = "회원 탈퇴 요청", description = "회원 탈퇴를 요청합니다.")
    @DeleteMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 불일치 시")
    })
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.deleteUser(userRegisterDTO.getUserId(), userRegisterDTO.getPassword());
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}
