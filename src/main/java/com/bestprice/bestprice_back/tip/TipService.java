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

    public void addLike(String userId, int tipId) {
        tipMapper.addLike(userId, tipId);
    }

    public void removeLike(String userId, int tipId) {
        tipMapper.removeLike(userId, tipId);
    }

    public List<Integer> getUserLikes(String userId) {
        return tipMapper.getUserLikes(userId);
    }
}
