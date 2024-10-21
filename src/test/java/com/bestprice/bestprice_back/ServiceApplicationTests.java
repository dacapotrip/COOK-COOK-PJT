package com.bestprice.bestprice_back;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bestprice.bestprice_back.user.dto.UserRegisterDTO;
import com.bestprice.bestprice_back.user.service.UserService;

@SpringBootTest
public class ServiceApplicationTests {
    
    @Autowired
    private UserService userService;

    @Test
    public void joinService(){
        System.out.println("debug >> junit service join" + userService);
        UserRegisterDTO request = UserRegisterDTO.builder()
        .userId("testeaj")
        .password("password")
        .name("박제형")
        .nickname("쩨")
        .email("rabbit3456@naver.com")
        .role("USER")
        .verified(false)
        .build();
        userService.register(request);
        System.out.println("debug >> join!!");
    }
}
