package com.bestprice.bestprice_back.fridge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bestprice.bestprice_back.fridge.DTO.fridgeDTO;

@Mapper
public interface fridgeMapper {
    
    List<fridgeDTO> getAllFoodItems();

    void insertFoodItem(fridgeDTO foodItem);
}
