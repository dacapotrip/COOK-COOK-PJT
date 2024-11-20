package com.bestprice.bestprice_back.recipe;

import com.bestprice.bestprice_back.components.domain.RecipeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeService {

    private final RecipeMapper recipeMapper;
    private final RecommendationMapper recommendationMapper;
    private final BookmarkMapper bookmarkMapper;

    // 생성자에서 의존성 주입
    public RecipeService(RecipeMapper recipeMapper,
            RecommendationMapper recommendationMapper,
            BookmarkMapper bookmarkMapper) {
        this.recipeMapper = recipeMapper;
        this.recommendationMapper = recommendationMapper;
        this.bookmarkMapper = bookmarkMapper;
    }

    // 조회수 증가
    public void inqCNTCount(Long rcpSno) {
        recipeMapper.inqCNTCount(rcpSno);
    }

    // 추천 여부 확인
    public boolean checkRecommendation(Long rcpSno, String userId) {
        return recommendationMapper.existsByUserIdAndrcpsno(userId, rcpSno) > 0;
    }

    // 추천하기
    @Transactional
    public void recommendRecipe(Long rcpSno, String userId) {
        if (checkRecommendation(rcpSno, userId)) {
            throw new IllegalStateException("이미 추천한 레시피입니다.");
        }
        recommendationMapper.insertRecommendation(userId, rcpSno);
        recommendationMapper.incrementRecommendCount(rcpSno);
    }

    // 추천 취소
    @Transactional
    public void cancelRecommendation(Long rcpSno, String userId) {
        if (!checkRecommendation(rcpSno, userId)) {
            throw new IllegalStateException("추천하지 않은 레시피입니다.");
        }
        recommendationMapper.deleteRecommendation(userId, rcpSno);
        recommendationMapper.decrementRecommendCount(rcpSno);
    }

    // ID로 레시피 조회
    public RecipeDto findRecipeById(Long rcpSno) {
        return recipeMapper.findById(rcpSno);
    }

    // 특정 레시피 조회
    public RecipeDto getRecipeBySno(Long rcpSno) {
        return recipeMapper.getRecipeBySno(rcpSno);
    }

    // 북마크 상태 토글
    @Transactional
    public boolean toggleBookmark(String userId, Long rcpSno) {
        BookmarkDto existingBookmark = bookmarkMapper.findByUserIdAndRcpSno(userId, rcpSno);

        if (existingBookmark != null) {
            bookmarkMapper.deleteBookmark(userId, rcpSno);
            return false; // 북마크 삭제 후 false 반환
        } else {
            bookmarkMapper.insertBookmark(userId, rcpSno);
            return true; // 북마크 추가 후 true 반환
        }
    }

    // 북마크 상태 확인
    public boolean isBookmarked(String userId, Long rcpSno) {
        return bookmarkMapper.findByUserIdAndRcpSno(userId, rcpSno) != null;
    }
}
