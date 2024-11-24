package com.bestprice.bestprice_back.tip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipService {
    @Autowired
    private TipMapper tipMapper;

    public List<TipDto> getAllTips() {
        return tipMapper.getAllTips();
    }

    public TipDto getTipById(int tipId) {
        return tipMapper.getTipById(tipId);
    }

    public void addRecommendation(String userId, int tipId) {
        tipMapper.addRecommendation(userId, tipId);
        tipMapper.incrementRecommendation(tipId);
    }

    public void removeRecommendation(String userId, int tipId) {
        tipMapper.removeRecommendation(userId, tipId);
        tipMapper.decrementRecommendation(tipId);
    }

    public List<Integer> getUserRecommendations(String userId) {
        return tipMapper.getUserRecommendations(userId);
    }
}