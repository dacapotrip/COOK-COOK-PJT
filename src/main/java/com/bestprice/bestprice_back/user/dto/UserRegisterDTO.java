package com.bestprice.bestprice_back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {
    private String userId;      // user_id
    private String password;     // password
    private String name;         // name
    private String nickname;     // nickname
    private String email;        // email
    private String role;         // role
    private boolean verified;     // verified
    private String token;     // token
}