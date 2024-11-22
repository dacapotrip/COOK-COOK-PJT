package com.bestprice.bestprice_back.recipe;

import com.bestprice.bestprice_back.components.domain.RecipeDto;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 조회수 증가 API
    @PostMapping("/{rcpSno}/view")
    public ResponseEntity<Void> inqCNTCount(@PathVariable Long rcpSno) {
        recipeService.inqCNTCount(rcpSno);
        return ResponseEntity.ok().build();
    }

    // 추천하기
    @PostMapping("/{rcpSno}/recommend")
    public ResponseEntity<Void> recommendRecipe(
            @PathVariable Long rcpSno,
            @RequestParam String userId) {
        recipeService.recommendRecipe(rcpSno, userId);
        return ResponseEntity.ok().build();
    }

    // 추천 취소
    @DeleteMapping("/{rcpSno}/recommend")
    public ResponseEntity<Void> cancelRecommendation(
            @PathVariable Long rcpSno,
            @RequestParam String userId) {
        recipeService.cancelRecommendation(rcpSno, userId);
        return ResponseEntity.ok().build();
    }

    // 추천 여부 확인
    @GetMapping("/{rcpSno}/recommend")
    public ResponseEntity<Boolean> checkRecommendation(
            @PathVariable Long rcpSno,
            @RequestParam String userId) {
        boolean isRecommended = recipeService.checkRecommendation(rcpSno, userId);
        return ResponseEntity.ok(isRecommended);
    }

    // 레시피 조회
    @GetMapping("/{rcpSno}")
    public ResponseEntity<RecipeDto> getRecipeBySno(@PathVariable Long rcpSno) {
        RecipeDto recipe = recipeService.getRecipeBySno(rcpSno);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/{rcpSno}/bookmark")
    public ResponseEntity<Boolean> toggleBookmark(
            @PathVariable Long rcpSno,
            @RequestParam String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        boolean isBookmarked = recipeService.toggleBookmark(userId, rcpSno);
        return ResponseEntity.ok(isBookmarked);
    }

    @GetMapping("/{rcpSno}/bookmark")
    public ResponseEntity<Boolean> isBookmarked(
            @PathVariable Long rcpSno,
            @RequestParam String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        boolean isBookmarked = recipeService.isBookmarked(userId, rcpSno);
        return ResponseEntity.ok(isBookmarked);
    }

    @DeleteMapping("/{rcpSno}/bookmark")
    public ResponseEntity<Void> removeBookmark(
            @PathVariable Long rcpSno,
            @RequestParam String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        recipeService.toggleBookmark(userId, rcpSno);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookmarks")
    public List<Long> getBookmarksByUserId(@RequestParam String userId) {
        return recipeService.getRecipesByUserId(userId);
    }
}
