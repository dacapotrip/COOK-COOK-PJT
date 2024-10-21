package com.bestprice.bestprice_back.user.service;

import com.bestprice.bestprice_back.user.dao.UserMapper;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;

import lombok.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Builder
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(UserRegisterDTO userRegisterDTO) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodedPassword);
        userMapper.joinUser(userRegisterDTO);
    }
}