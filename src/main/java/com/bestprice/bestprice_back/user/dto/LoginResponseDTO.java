package com.bestprice.bestprice_back.user.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String name; // Access Token
    private String nickname; // Access Token
    private String accessToken; // Access Token
    private String refreshToken; // Refresh Token
}

