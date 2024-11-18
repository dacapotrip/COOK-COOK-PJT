package com.bestprice.bestprice_back.rank.ctrl;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestprice.bestprice_back.rank.DTO.RankDTO;
import com.bestprice.bestprice_back.rank.service.RankService;

@RestController
@RequestMapping("/rank")
public class RankController {
    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("/inquiry")
    public List<RankDTO> getTopInquiryRank() {
        return rankService.getTopInquiryRank();
    }

    @GetMapping("/recommendation")
    public List<RankDTO> getTopRecommendationRank() {
        return rankService.getTopRecommendationRank();
    }

    @GetMapping("/scrap")
    public List<RankDTO> getTopScrapRank() {
        return rankService.getTopScrapRank();
    }
    
}
