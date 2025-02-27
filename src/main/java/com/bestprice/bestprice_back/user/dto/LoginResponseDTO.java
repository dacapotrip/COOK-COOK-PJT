package com.bestprice.bestprice_back.user.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String userId;        // 사용자 아이디
    private String name;          // 사용자 이름
    private String nickname;      // 사용자 닉네임
    private String email;         // 사용자 이메일
    private String role;          // USER / MANAGER
    private boolean verified;     // 0이면 인증필요, 1이면 인증완료
    private String accessToken;   // Access Token
}
