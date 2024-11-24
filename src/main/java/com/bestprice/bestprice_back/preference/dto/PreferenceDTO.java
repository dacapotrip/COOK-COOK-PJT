package com.bestprice.bestprice_back.preference.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PreferenceDTO {

    private String userId;

    @JsonProperty("difficulty")
    private String CKG_DODF_NM; // JSON의 "difficulty"를 데이터베이스 컬럼 "CKG_DODF_NM"에 매핑

    @JsonProperty("portion")
    private String CKG_INBUN_NM; // JSON의 "portion"을 데이터베이스 컬럼 "CKG_INBUN_NM"에 매핑

    @JsonProperty("category")
    private String CKG_KND_ACTO_NM; // JSON의 "category"를 데이터베이스 컬럼 "CKG_KND_ACTO_NM"에 매핑

    @JsonProperty("method")
    private String CKG_MTH_ACTO_NM; // JSON의 "method"를 데이터베이스 컬럼 "CKG_MTH_ACTO_NM"에 매핑
}
