package com.bestprice.bestprice_back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailVerificationDTO {
    private String email;
    private String token;
}
