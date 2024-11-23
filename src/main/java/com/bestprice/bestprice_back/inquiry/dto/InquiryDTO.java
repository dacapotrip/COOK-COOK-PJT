package com.bestprice.bestprice_back.inquiry.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InquiryDTO {
    private int inquiryId;       // inquiry_id
    private String inquiryTitle; // inquiry_title
    private String inquiry;      // inquiry
    private LocalDateTime inquiryDate; // inquiry_date
    private String inquiryType;  // inquiry_type
    private boolean secret;      // secret
    private String userId;       // user_id
}

