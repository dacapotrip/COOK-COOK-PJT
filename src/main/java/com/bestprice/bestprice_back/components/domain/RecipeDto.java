package com.bestprice.bestprice_back.components.domain;

public class RecipeDto {
    private Long rcpSno;           // RCP_SNO (Bigint PK)
    private String rcpTtl;         // RCP_TTL (Varchar(255))
    private String ckgNm;          // CKG_NM (Varchar(255))
    private String rgtrId;         // RGTR_ID (Varchar(50))
    private String rgtrNm;         // RGTR_NM (Varchar(50))
    private int inqCnt;            // INQ_CNT (Int)
    private int rcmmCnt;           // RCMM_CNT (Int)
    private int srapCnt;           // SRAP_CNT (Int)
    private String ckgMthActoNm;  // CKG_MTH_ACTO_NM (Varchar(50))
    private String ckgStaActoNm;  // CKG_STA_ACTO_NM (Varchar(50))
    private String ckgMtrlActoNm; // CKG_MTRL_ACTO_NM (Varchar(50))
    private String ckgKndActoNm;  // CKG_KND_ACTO_NM (Varchar(50))
    private String ckgIpdc;        // CKG_IPDC (Text)
    private String ckgMtrlCn;      // CKG_MTRL_CN (Text)
    private String ckgInbunNm;     // CKG_INBUN_NM (Varchar(50))
    private String ckgDodfNm;      // CKG_DODF_NM (Varchar(50))
    private String ckgTimeNm;      // CKG_TIME_NM (Varchar(50))
    private String firstRegDt;
}
