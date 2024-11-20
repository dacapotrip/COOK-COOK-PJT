package com.bestprice.bestprice_back.components.service;

import com.bestprice.bestprice_back.components.dao.RecipeMapper;
import com.bestprice.bestprice_back.components.dao.RecommendationMapper;
import com.bestprice.bestprice_back.components.domain.RecipeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeService {

    private final RecipeMapper recipeMapper;
    private final RecommendationMapper recommendationMapper;

    public RecipeService(RecipeMapper recipeMapper, RecommendationMapper recommendationMapper) {
        this.recipeMapper = recipeMapper;
        this.recommendationMapper = recommendationMapper;
    }

    // 조회수 증가
    public void inqCNTCount(Long recipeId) {
        recipeMapper.inqCNTCount(recipeId);
    }

    // 추천 여부 확인
    public boolean checkRecommendation(Long recipeId, String userId) {
        // 추천 여부를 확인하는 메서드 호출
        return recommendationMapper.existsByUserIdAndRecipeId(userId, recipeId) > 0;
    }

    // 추천하기
    @Transactional
    public void recommendRecipe(Long recipeId, String userId) {
        // 추천 여부 확인
        if (checkRecommendation(recipeId, userId)) {
            throw new IllegalStateException("이미 추천한 레시피입니다.");
        }
        // 추천 기록 추가
        recommendationMapper.insertRecommendation(userId, recipeId);

        // 추천수 증가
        recommendationMapper.incrementRecommendCount(recipeId);
    }

    // 추천 취소
    @Transactional
    public void cancelRecommendation(Long recipeId, String userId) {
        // 추천 여부 확인
        if (!checkRecommendation(recipeId, userId)) {
            throw new IllegalStateException("추천하지 않은 레시피입니다.");
        }
        // 추천 기록 삭제
        recommendationMapper.deleteRecommendation(userId, recipeId);

        // 추천수 감소
        recommendationMapper.decrementRecommendCount(recipeId);
    }

    // ID로 레시피 조회
    public RecipeDto findRecipeById(Long recipeId) {
        return recipeMapper.findById(recipeId);
    }

    public RecipeDto getRecipeBySno(Long rcpSno) {
        return recipeMapper.getRecipeBySno(rcpSno);
    }
}
