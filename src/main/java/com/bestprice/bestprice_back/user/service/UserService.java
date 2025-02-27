package com.bestprice.bestprice_back.user.service;

import com.bestprice.bestprice_back.user.dao.UserMapper;
import com.bestprice.bestprice_back.user.domain.User;
import com.bestprice.bestprice_back.user.dto.EmailVerificationDTO;
import com.bestprice.bestprice_back.user.dto.NicknameChangeDTO;
import com.bestprice.bestprice_back.user.dto.PasswordChangeDTO;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final JavaMailSender mailSender;
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
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(new InternetAddress("bestpriceback@naver.com", "BestPrice_Admin", "UTF-8"));
            helper.setTo(email);
            helper.setSubject("회원가입 이메일 인증");

            // URL에 email 추가
            String body = "<div style='font-family: Arial, sans-serif;'>"
            + "<h1>안녕하세요. <span style='color: #ff5833;'>CookCook</span> 입니다</h1>"
            + "<br>"
            + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.</p>"
            + "<a href='http://localhost:8001/user/verify?token=" + token + "&email=" + email
            + "' style='color: blue; text-decoration: underline;'>인증 링크</a>"
            + "</div>";


            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<User> login(String userId, String password) {
        Optional<User> userOptional = userMapper.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Found userId in database: " + user.getUserId()); // userId 값 확인

            if (passwordEncoder.matches(password, user.getPassword())) {
                return userOptional;
            } else {
                System.err.println("Password mismatch for userId: " + userId);
            }
        } else {
            System.err.println("User not found for userId: " + userId);
        }
        return Optional.empty();
    }

    public Optional<User> verifyEmail(EmailVerificationDTO emailVerificationDTO) {
        String email = emailVerificationDTO.getEmail();
        String token = emailVerificationDTO.getToken();

        Optional<User> userOptional = userMapper.findByToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getEmail().equals(email)) {
                userMapper.updateStateByToken(token);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public void passwordResetRequest(String email) {
        Optional<User> userOptional = userMapper.findByEmail(email);
        if (userOptional.isPresent()) {
            String token = UUID.randomUUID().toString();
            userMapper.updateResetToken(userOptional.get().getUserId(), token);
            sendPasswordResetEmail(email, token);
        } else {
            throw new IllegalArgumentException("등록된 이메일이 없습니다.");
        }
    }

    private void sendPasswordResetEmail(String email, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(new InternetAddress("bestpriceback@naver.com", "BestPrice_Admin", "UTF-8"));
            helper.setTo(email);
            helper.setSubject("비밀번호 초기화 요청");

            String body = "<div style='font-family: Arial, sans-serif;'>"
                    + "<h1>비밀번호 초기화 요청</h1>"
                    + "<br>"
                    + "<p>아래 링크를 클릭하여 비밀번호를 변경하세요.</p>"
                    + "<a href='http://localhost:3000/user/password/reset?token=" + token
                    + "' style='color: blue; text-decoration: underline;'>비밀번호 변경하기</a>"
                    + "</div>";

            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void changePassword(PasswordChangeDTO passwordChangeDTO) {
        Optional<User> userOptional = userMapper.findByUserId(passwordChangeDTO.getUserId());
        if (userOptional.isPresent()) {
            if (!passwordChangeDTO.getPassword().equals(passwordChangeDTO.getConfirmPassword())) {
                throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }

            String encodedPassword = passwordEncoder.encode(passwordChangeDTO.getPassword());
            userMapper.updatePassword(passwordChangeDTO.getUserId(), encodedPassword);
        } else {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
    }

    @Transactional
    public void changeNickname(NicknameChangeDTO nicknameChangeDTO) {
        Optional<User> userOptional = userMapper.findByUserId(nicknameChangeDTO.getUserId());

        if (userOptional.isPresent()) {
            userMapper.updateNickname(nicknameChangeDTO);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void deleteUser(String userId) {
        if (userMapper.findByUserId(userId).isPresent()) {
            userMapper.deleteUser(userId);
        } else {
            throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
        }
    }
}
