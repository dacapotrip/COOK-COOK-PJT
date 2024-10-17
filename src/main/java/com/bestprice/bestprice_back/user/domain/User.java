package com.bestprice.bestprice_back.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;      // user_id
    private String password;     // password
    private String name;         // name
    private String nickname;     // nickname
    private String email;        // email
    private String role;         // role
    private boolean verified;     // verified (tinyint -> boolean)
}