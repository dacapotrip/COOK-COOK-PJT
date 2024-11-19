package com.bestprice.bestprice_back.components.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bestprice.bestprice_back.components.domain.RecipeDto;
import com.bestprice.bestprice_back.components.domain.ShopSearchDto;

@Mapper
public interface SearchMapper {

    public void searchResultRow(@Param("params") List<ShopSearchDto> params);

    public List<ShopSearchDto> existChk(String query);

    public List<RecipeDto> allRecipes();

    public List<RecipeDto> getRecipe(String query);

    public List<RecipeDto> oneRecipe();

    public void imageSet(Integer RCPNUM, String temp);

}
