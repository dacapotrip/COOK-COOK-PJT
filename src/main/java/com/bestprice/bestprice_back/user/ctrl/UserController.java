package com.bestprice.bestprice_back.user.ctrl;

import com.bestprice.bestprice_back.jwt.JwtTokenUtil;
import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.EmailVerificationDTO;
import com.bestprice.bestprice_back.user.dto.LoginRequestDTO;
import com.bestprice.bestprice_back.user.dto.LoginResponseDTO;
import com.bestprice.bestprice_back.user.dto.NicknameChangeDTO;
import com.bestprice.bestprice_back.user.dto.PasswordChangeDTO;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;
import com.bestprice.bestprice_back.user.dto.UserSignOutDTO;
import com.bestprice.bestprice_back.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.io.IOException;

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

    @Operation(summary = "이메일 인증", description = "이메일 활성화 링크를 클릭하면 서버측 /verify 로 이동되고 유저 활성화 후"
            + "'프론트/login?activate=true' 로 리다이렉트 시킴. 프론트에서는 activate param을 보고 활성화가 완료됐다는 알림을 로그인 화면에 표시해야됨")
    @GetMapping("/verify")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "'프론트주소/login?activate=true' 성공 시 리턴," +
                    "<br>만료 시 프론트주소/resend-mail?expired=true 로 리다이렉트 "),
            @ApiResponse(responseCode = "400", description = "유효하지 않거나 만료된 인증 링크입니다.")
    })
    public void verifyEmail(@RequestParam String email, @RequestParam String token, HttpServletResponse response)
            throws IOException {
        EmailVerificationDTO verificationDTO = new EmailVerificationDTO(email, token);

        try {
            // 이메일 인증 시도
            Optional<User> userOptional = userService.verifyEmail(verificationDTO);

            if (userOptional.isPresent()) {
                // 인증이 성공한 경우 로그인 페이지로 리다이렉트
                response.sendRedirect("http://localhost:3000/login?activate=true");
            } else {
                // 유효하지 않거나 만료된 링크인 경우
                response.sendRedirect("http://localhost:3000/resend-mail?expired=true");
            }
        } catch (Exception e) {
            // 예외 발생 시 유효하지 않거나 만료된 링크로 리다이렉트
            response.sendRedirect("http://localhost:3000/resend-mail?expired=true");
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

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // JWT 생성
            String accessToken = JwtTokenUtil.createAccessToken(user.getUserId(), 3600000); // 1시간

            // 응답 DTO 설정
            LoginResponseDTO response = new LoginResponseDTO();
            response.setUserId(user.getUserId());
            response.setName(user.getName());
            response.setNickname(user.getNickname());
            response.setEmail(user.getEmail());
            response.setAccessToken(accessToken);

            return ResponseEntity.ok(response);
        } else {
            System.err.println("Login failed for userId: " + userId);
            return ResponseEntity.status(401).body(null); // Unauthorized
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

    @Operation(summary = "닉네임 변경", description = "사용자의 닉네임을 변경합니다.")
    @PatchMapping("/nickname")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity<String> changeNickname(@Valid @RequestBody NicknameChangeDTO nicknameChangeDTO) {
        try {
            userService.changeNickname(nicknameChangeDTO);
            return ResponseEntity.ok("닉네임이 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("닉네임 변경에 실패하였습니다. " + e.getMessage());
        }
    }

    @Operation(summary = "비밀번호 변경", description = "기존 사용자의 비밀번호를 변경합니다.")
    @PatchMapping("/password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(passwordChangeDTO);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    @Operation(summary = "회원 탈퇴 요청", description = "회원 탈퇴를 요청합니다.")
    @DeleteMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserSignOutDTO userSignOutDTO) {
        userService.deleteUser(userSignOutDTO.getUserId());
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}
