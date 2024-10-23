package com.bestprice.bestprice_back.user.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String accessToken; // Access Token
    private String refreshToken; // Refresh Token
}

