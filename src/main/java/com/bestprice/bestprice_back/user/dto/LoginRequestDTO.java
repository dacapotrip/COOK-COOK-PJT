package com.bestprice.bestprice_back.user.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userId; // 사용자 ID
    private String password; // 비밀번호
}
