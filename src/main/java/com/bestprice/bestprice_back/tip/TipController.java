package com.bestprice.bestprice_back.tip;

import org.apache.ibatis.annotations.Select;
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

    // 특정 팁 가져오기
    @GetMapping("/{tipId}")
    public TipDto getTipById(@PathVariable int tipId) {
        return tipService.getTipById(tipId);
    }

    // 사용자의 좋아요 목록 가져오기
    @GetMapping("/likes")
    public List<Integer> getUserLikes(@RequestParam("userId") String userId) {
        return tipService.getUserLikes(userId);
    }

    // 좋아요 추가
    @PostMapping("/like")
    public void addLike(@RequestParam("userId") String userId, @RequestParam("tipId") int tipId) {
        tipService.addLike(userId, tipId);
    }

    // 좋아요 삭제
    @DeleteMapping("/like")
    public void removeLike(@RequestParam("userId") String userId, @RequestParam("tipId") int tipId) {
        tipService.removeLike(userId, tipId);
    }
}
