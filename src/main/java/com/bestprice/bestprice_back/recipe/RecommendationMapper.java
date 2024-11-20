package com.bestprice.bestprice_back.recipe;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendationMapper {

    int existsByUserIdAndrcpsno(@Param("userId") String userId, @Param("rcp_sno") Long rcp_sno);

    void insertRecommendation(@Param("userId") String userId, @Param("rcp_sno") Long rcp_sno);

    void deleteRecommendation(@Param("userId") String userId, @Param("rcp_sno") Long rcp_sno);

    void incrementRecommendCount(@Param("rcp_sno") Long rcp_sno);

    void decrementRecommendCount(@Param("rcp_sno") Long rcp_sno);
}
