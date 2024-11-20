package com.bestprice.bestprice_back.components.dao;

import com.bestprice.bestprice_back.components.domain.RecipeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecipeMapper {
    RecipeDto findById(Long id);

    void inqCNTCount(@Param("id") Long id);

    RecipeDto getRecipeBySno(@Param("rcp_sno") Long rcpSno);
}