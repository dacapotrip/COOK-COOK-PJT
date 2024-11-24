package com.bestprice.bestprice_back.preference.domain;

import lombok.Data;

@Data
public class Preference {
    private int preferenceId;
    private String userId;
    private String CKG_DODF_NM;
    private String CKG_INBUN_NM;
    private String CKG_KND_ACTO_NM;
    private String CKG_MTH_ACTO_NM;
}
