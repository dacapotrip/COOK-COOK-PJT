package com.bestprice.bestprice_back.tip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tips")
public class TipController {

    @Autowired
    private TipService tipService;

    // 모든 팁 가져오기
    @GetMapping
    public List<TipDto> getTips() {
        return tipService.getAllTips();
    }

    // 사용자의 좋아요 목록 가져오기
    @GetMapping("/likes")
    public List<Integer> getUserRecommendations(@RequestParam("userId") String userId) {
        return tipService.getUserRecommendations(userId);
    }

    // 좋아요 추가
    @PostMapping("/like")
    public void addRecommendation(@RequestParam("userId") String userId, @RequestParam("tipId") int tipId) {
        tipService.addRecommendation(userId, tipId);
    }

    // 좋아요 삭제
    @DeleteMapping("/like")
    public void removeRecommendation(@RequestParam("userId") String userId, @RequestParam("tipId") int tipId) {
        tipService.removeRecommendation(userId, tipId);
    }
}
