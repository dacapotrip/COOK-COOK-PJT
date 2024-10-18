package com.bestprice.bestprice_back.components.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bestprice.bestprice_back.components.domain.RecipeDto;
import com.bestprice.bestprice_back.components.service.WebCrawlerService;

@RestController
public class WebCrawlerController {

    @Autowired
    private WebCrawlerService webCrawlerService;

    @GetMapping("/crawl")
    public RecipeDto crawl(@RequestParam String query) {
        
        RecipeDto list = null;

        list = webCrawlerService.crawl(query);
        return list;

    }
}
