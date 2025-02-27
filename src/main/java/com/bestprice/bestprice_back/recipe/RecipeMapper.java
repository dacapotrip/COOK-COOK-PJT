package com.bestprice.bestprice_back.recipe;

import com.bestprice.bestprice_back.components.domain.RecipeDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecipeMapper {
    RecipeDto findById(@Param("id") Long id);

    void inqCNTCount(@Param("rcp_sno") Long id);

    void incrementWeeklyViewsCount(@Param("rcp_sno") Long id);

    RecipeDto getRecipeBySno(@Param("rcp_sno") Long rcpSno);

    List<RecipeDto> findRecipesByIngredient(@Param("ingredient") String ingredient);

    List<RecipeDto> findRecipesByExactIngredient(@Param("ingredient") String ingredient);
}