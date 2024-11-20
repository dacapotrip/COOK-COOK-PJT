package com.bestprice.bestprice_back.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserSignOutDTO {
    @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
    private String userId;
}
