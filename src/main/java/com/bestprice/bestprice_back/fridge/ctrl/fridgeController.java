package com.bestprice.bestprice_back.fridge.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestprice.bestprice_back.fridge.DTO.fridgeDTO;
import com.bestprice.bestprice_back.fridge.service.frdigeService;

@RestController
@RequestMapping("/refrigerator")
public class fridgeController {
    @Autowired
    private frdigeService foodItemService;

    @GetMapping
    public List<fridgeDTO> getAllFoodItems() {
        List<fridgeDTO> foodItems = foodItemService.getAllFoodItems();
        System.out.println("식료품 데이터" + foodItems); // 데이터 확인
        return foodItems;
    }
    
    @PostMapping
    public String saveFoodItem(@RequestBody fridgeDTO foodItem) {
        System.out.println("저장 데이터: " + foodItem); // 요청 받은 데이터를 출력
        foodItemService.saveFoodItem(foodItem);
        return "식료품이 성공적으로 저장되었습니다!";
    }

    @DeleteMapping("/{refrigerator}")
    public ResponseEntity<String> deleteFoodItem(@PathVariable int refrigerator) {
        if (refrigerator <= 0) {
            return ResponseEntity.badRequest().body("유효하지 않은 ID");
        }

        boolean isDeleted = foodItemService.deleteFoodItemById(refrigerator);
        if (isDeleted) {
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.status(404).body("삭제 실패");
        }
    }    
}
