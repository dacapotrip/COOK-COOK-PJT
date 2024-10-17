package com.bestprice.bestprice_back.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailVerificationDTO {
    private String email;
    private String token;
}
