package com.bestprice.bestprice_back.tip;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TipMapper {

    // 팁 다 가져오기
    @Select("SELECT tip_id AS tipId, tip_title AS tipTitle, tips, recommendation FROM tips")
    List<TipDto> getAllTips();

    // 유저의 좋아요 가져오기
    @Select("SELECT tip_id FROM tips_recommendation WHERE user_id = #{userId}")
    List<Integer> getUserRecommendations(@Param("userId") String userId);

    // 좋아요
    @Insert("INSERT INTO tips_recommendation (user_id, tip_id) VALUES (#{userId}, #{tipId})")
    void addRecommendation(@Param("userId") String userId, @Param("tipId") int tipId);

    // 좋아요 취소
    @Delete("DELETE FROM tips_recommendation WHERE user_id = #{userId} AND tip_id = #{tipId}")
    void removeRecommendation(@Param("userId") String userId, @Param("tipId") int tipId);

    // 팁ID로 데이터 가져오기
    @Select("SELECT tip_id AS tipId, tip_title AS tipTitle, tips, recommendation FROM tips WHERE tip_id = #{tipId}")
    TipDto getTipById(@Param("tipId") int tipId);

    @Update("UPDATE tips SET recommendation = recommendation + 1 WHERE tip_id = #{tipId}")
    void incrementRecommendation(@Param("tipId") int tipId);

    @Update("UPDATE tips SET recommendation = recommendation - 1 WHERE tip_id = #{tipId}")
    void decrementRecommendation(@Param("tipId") int tipId);

}
