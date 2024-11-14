package com.bestprice.bestprice_back.components.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RecipeDetailDto {
    private String mainThumb; // 메인 사진 Url
    private List<String> comp;      // 결과 사진 Url
    private List<StepsDTO> steps; // 단계 정보를 리스트로 저장

    public RecipeDetailDto() {
        this.steps = new ArrayList<>(); // 초기화
    }

    @Data
    public class StepsDTO { // 내부 클래스
        private String stepText;  // 단계 텍스트
        private String stepsImg;   // 단계 이미지
    }
}
