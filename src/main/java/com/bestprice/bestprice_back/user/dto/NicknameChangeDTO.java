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
public class NicknameChangeDTO {
    @NotBlank(message = "User ID는 필수 입력 항목입니다.")
    private String userId;

    @NotBlank(message = "Nickname은 필수 입력 항목입니다.")
    private String newNickname;
}
