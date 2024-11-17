package com.bestprice.bestprice_back.fridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestprice.bestprice_back.fridge.DTO.fridgeDTO;
import com.bestprice.bestprice_back.fridge.mapper.fridgeMapper;

@Service
public class frdigeService {

    @Autowired
    private fridgeMapper fridgeMapper;

    public List<fridgeDTO> getAllFoodItems() {
        List<fridgeDTO> items = fridgeMapper.getAllFoodItems();
        System.out.println("DB 조회: " + items); // 데이터 확인
        return items;
    }
    
    public void saveFoodItem(fridgeDTO foodItem) {
        System.out.println(foodItem); // 저장 전 데이터 확인
        fridgeMapper.insertFoodItem(foodItem);
    }

    public boolean deleteFoodItemById(int refrigerator) {
        int deletedRows = fridgeMapper.deleteFoodItemById(refrigerator);
        return deletedRows > 0; // 삭제된 행이 있으면 true 반환
    }
    
}
