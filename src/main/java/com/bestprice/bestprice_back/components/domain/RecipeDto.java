package com.bestprice.bestprice_back.components.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecipeDto {
    
    private Long RCP_SNO;            // RCP_SNO bigint PK
    private String RCP_TTL;          // RCP_TTL varchar(255)
    private String CKG_NM;           // CKG_NM varchar(255)
    private String RGTR_ID;          // RGTR_ID varchar(50)
    private String RGTR_NM;          // RGTR_NM varchar(50)
    private int INQ_CNT;             // INQ_CNT int
    private int RCMM_CNT;            // RCMM_CNT int
    private int SRAP_CNT;            // SRAP_CNT int
    private String CKG_MTH_ACTO_NM;   // CKG_MTH_ACTO_NM varchar(50)
    private String CKG_STA_ACTO_NM;   // CKG_STA_ACTO_NM varchar(50)
    private String CKG_MTRL_ACTO_NM;  // CKG_MTRL_ACTO_NM varchar(50)
    private String CKG_KND_ACTO_NM;   // CKG_KND_ACTO_NM varchar(50)
    private String CKG_IPDC;        // CKG_IPDC text
    private String CKG_MTRL_CN;      // CKG_MTRL_CN text
    private String CKG_INBUN_NM;     // CKG_INBUN_NM varchar(50)
    private String CKG_DODF_NM;      // CKG_DODF_NM varchar(50)
    private String CKG_TIME_NM;      // CKG_TIME_NM varchar(50)
    private LocalDateTime FIRST_REG_DT;  // FIRST_REG_DT datetime
    private String IMG_URL;
    
}
