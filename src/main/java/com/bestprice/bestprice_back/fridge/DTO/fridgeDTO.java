package com.bestprice.bestprice_back.fridge.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class fridgeDTO {
    private int refrigerator; 
    //private int userId;     
    private String category;  
    private String emoji;
    private String name;
    private int quantity;
    private LocalDate expiration_date;
    private Integer is_frozen;
}
