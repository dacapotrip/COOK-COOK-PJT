package com.bestprice.bestprice_back.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordChangeDTO {

    @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
    private String userId;

    @NotEmpty(message = "새 비밀번호는 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String confirmPassword;

}
