package com.bestprice.bestprice_back.rank.DTO;

import lombok.Data;

@Data
public class RankDTO {
    private int rank;
    private String rcpSno;
    private String rcpTtl;
    private String rgtrNm;
    private int inqCnt;
    private int rcmmCnt;
    private int weeklyViews;
    private int weeklyRecommendations;
}
