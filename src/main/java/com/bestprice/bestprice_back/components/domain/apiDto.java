package com.bestprice.bestprice_back.components.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class apiDto {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    // Getters and Setters

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String title;
        private String link;
        private String image;
        private String lprice;
        private String hprice;
        private String mallName;
        private String productId;
        private String productType;
        private String brand;
        private String maker;
        private String category1;
        private String category2;
        private String category3;
        private String category4;

        public void setTitle(String title) {
            this.title = removeHtmlTags(title);
        }

        private String removeHtmlTags(String input) {
            return input.replaceAll("<[^>]*>", ""); // 정규 표현식으로 HTML 태그 제거
        }
        
    }
}