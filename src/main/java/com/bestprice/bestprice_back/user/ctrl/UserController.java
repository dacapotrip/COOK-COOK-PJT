package com.bestprice.bestprice_back.user.ctrl;

import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;
import com.bestprice.bestprice_back.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}