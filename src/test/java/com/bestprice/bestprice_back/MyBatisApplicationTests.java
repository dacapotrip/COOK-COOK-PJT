package com.bestprice.bestprice_back;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bestprice.bestprice_back.user.dao.UserMapper;
import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;


@SpringBootTest
public class MyBatisApplicationTests {
    
    @Autowired
    private UserMapper userMapper;

    @Test
    public void joinTest() {
        System.out.println("debug mapper >>> " + userMapper);
        // UserRegisterDTO를 사용하여 사용자 정보 설정
        UserRegisterDTO userDTO = UserRegisterDTO.builder()
                .userId("testUser")
                .password("testPassword")
                .name("Test Name")
                .nickname("TestNick")
                .email("test@example.com")
                .role("USER")
                .verified(false)
                .build();

                userMapper.joinUser(userDTO);
                System.out.println("debug >> success!!");
    }
}
