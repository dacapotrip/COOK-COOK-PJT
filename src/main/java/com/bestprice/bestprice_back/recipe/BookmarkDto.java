package com.bestprice.bestprice_back.recipe;

import lombok.Data;

@Data
public class BookmarkDto {
    private Long id;
    private String userId;
    private Long rcpSno;
}