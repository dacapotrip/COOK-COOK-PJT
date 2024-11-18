package com.bestprice.bestprice_back.components.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class testDto {
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
}
