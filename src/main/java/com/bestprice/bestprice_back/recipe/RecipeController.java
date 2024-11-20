package com.bestprice.bestprice_back.components.ctrl;

import com.bestprice.bestprice_back.components.domain.RecipeDto;
import com.bestprice.bestprice_back.components.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 조회수 증가 API
    @PostMapping("/{recipeId}/view")
    public ResponseEntity<Void> inqCNTCount(@PathVariable Long recipeId) {
        recipeService.inqCNTCount(recipeId);
        return ResponseEntity.ok().build();
    }

    // 추천하기
    @PostMapping("/{recipeId}/recommend")
    public ResponseEntity<Void> recommendRecipe(
            @PathVariable Long recipeId,
            @RequestParam String userId) {
        recipeService.recommendRecipe(recipeId, userId);
        return ResponseEntity.ok().build();
    }

    // 추천 취소
    @DeleteMapping("/{recipeId}/recommend")
    public ResponseEntity<Void> cancelRecommendation(
            @PathVariable Long recipeId,
            @RequestParam String userId) {
        recipeService.cancelRecommendation(recipeId, userId);
        return ResponseEntity.ok().build();
    }

    // 추천 여부 확인
    @GetMapping("/{recipeId}/recommend")
    public ResponseEntity<Boolean> checkRecommendation(
            @PathVariable Long recipeId,
            @RequestParam String userId) {
        boolean isRecommended = recipeService.checkRecommendation(recipeId, userId);
        return ResponseEntity.ok(isRecommended);
    }

    // 레시피 조회
    @GetMapping("/{rcp_sno}")
    public ResponseEntity<RecipeDto> getRecipeBySno(@PathVariable Long rcp_sno) {
        RecipeDto recipe = recipeService.getRecipeBySno(rcp_sno);
        return ResponseEntity.ok(recipe);
    }

}
