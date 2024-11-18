package com.bestprice.bestprice_back.rank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bestprice.bestprice_back.rank.DTO.RankDTO;
import com.bestprice.bestprice_back.rank.mapper.RankMapper;

@Service
public class RankService {
    private final RankMapper rankMapper;

    public RankService(RankMapper rankMapper) {
        this.rankMapper = rankMapper;
    }

    public List<RankDTO> getTopInquiryRank() {
        List<RankDTO> ranks = rankMapper.getTopInqRank();
        System.out.println("Inquiry Rank Data: " + ranks);
    
        return rankMapper.getTopInqRank();
    }

    public List<RankDTO> getTopRecommendationRank() {
        List<RankDTO> ranks = rankMapper.getTopRcmmRank();
        System.out.println("Recommendation Rank Data: " + ranks);
    
        return rankMapper.getTopRcmmRank();
    }

    public List<RankDTO> getTopScrapRank() {
        List<RankDTO> ranks = rankMapper.getTopScrapRank();
        System.out.println("Scrap Rank Data: " + ranks);
    
        return rankMapper.getTopScrapRank();
    }
    
}
