package com.bestprice.bestprice_back.components.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendationMapper {

    int existsByUserIdAndRecipeId(@Param("userId") String userId, @Param("recipeId") Long recipeId);

    void insertRecommendation(@Param("userId") String userId, @Param("recipeId") Long recipeId);

    void deleteRecommendation(@Param("userId") String userId, @Param("recipeId") Long recipeId);

    void incrementRecommendCount(@Param("recipeId") Long recipeId);

    void decrementRecommendCount(@Param("recipeId") Long recipeId);
}
