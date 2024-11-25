package com.bestprice.bestprice_back.rank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.bestprice.bestprice_back.rank.DTO.RankDTO;

@Mapper
public interface RankMapper {
    
    List<RankDTO> getTopInqRank();

    List<RankDTO> getTopRcmmRank();

    List<RankDTO> getTopWeeklyViewsRank();
    
    List<RankDTO> getTopWeeklyRecommendationsRank();
}
