package com.bestprice.bestprice_back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {
    @NotBlank(message = "User ID는 필수 입력 항목입니다.")
    private String userId;

    @NotBlank(message = "Password는 필수 입력 항목입니다.")
    private String password;

    @NotBlank(message = "Name은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "Nickname은 필수 입력 항목입니다.")
    private String nickname;

    @NotBlank(message = "Email은 필수 입력 항목입니다.")
    private String email;

    private String role = "USER"; // 기본값 설정
    private boolean verified;
    private String token;
}
