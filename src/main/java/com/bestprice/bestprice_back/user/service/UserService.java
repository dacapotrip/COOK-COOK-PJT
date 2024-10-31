package com.bestprice.bestprice_back.user.service;

import com.bestprice.bestprice_back.user.dao.UserMapper;
import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.EmailVerificationDTO;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Builder
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodedPassword);
        
        // UUID 생성 및 설정
        String token = UUID.randomUUID().toString();
        userRegisterDTO.setToken(token);
        
        userMapper.joinUser(userRegisterDTO); // 사용자 등록
        sendVerificationEmail(userRegisterDTO.getEmail(), token); // 이메일 인증 메일 전송
    }

    private void sendVerificationEmail(String email, String token) {
    try {
        // MimeMessage 생성
        MimeMessage message = mailSender.createMimeMessage();
        
        // MimeMessageHelper 사용
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // UTF-8 인코딩 설정
        helper.setFrom(new InternetAddress("bestpriceback@naver.com", "BestPrice_Admin", "UTF-8")); // 보내는 사람 설정
        helper.setTo(email); // 수신자 설정
        helper.setSubject("회원가입 이메일 인증"); // 제목 설정
        
        // HTML 내용
        String body = "<div style='font-family: Arial, sans-serif;'>"
                + "<h1>안녕하세요. BestPrice 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.</p>"
                + "<a href='http://localhost:3000/user/verify?token=" + token + "' style='color: blue; text-decoration: underline;'>인증 링크</a>"
                + "</div>";

        helper.setText(body, true); // true는 HTML 형식 설정
        
        // 이메일 전송
        mailSender.send(message);
    } catch (Exception e) {
        e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
    }
}

    

    public Optional<User> login(String userId, String password) {
        Optional<User> userOptional = userMapper.findByUserId(userId);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional;
        }
        return Optional.empty();
    }

    public Optional<User> verifyEmail(EmailVerificationDTO emailVerificationDTO) {
        String email = emailVerificationDTO.getEmail();
        String token = emailVerificationDTO.getToken();
        
        Optional<User> userOptional = userMapper.findByToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // 이메일과 사용자 이메일이 일치할 경우 상태 업데이트
            if (user.getEmail().equals(email)) {
                userMapper.updateStateByToken(token);
                return Optional.of(user); // 인증된 사용자 반환
            }
        }
        return Optional.empty(); // 인증 실패
    }
}
