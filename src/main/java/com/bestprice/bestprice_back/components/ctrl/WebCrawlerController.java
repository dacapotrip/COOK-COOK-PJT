package com.bestprice.bestprice_back.components.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bestprice.bestprice_back.components.dao.SearchMapper;
import com.bestprice.bestprice_back.components.domain.RecipeDetailDto;
import com.bestprice.bestprice_back.components.domain.RecipeDto;
import com.bestprice.bestprice_back.components.domain.ShopSearchDto;
import com.bestprice.bestprice_back.components.service.SearchService;
import com.bestprice.bestprice_back.components.service.WebCrawlerService;
import com.bestprice.bestprice_back.components.service.apiService;

@RestController
public class WebCrawlerController {

    @Autowired
    private WebCrawlerService webCrawlerService;

    @Autowired
    private SearchService searchSerivce;

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private apiService apiservice;

    /* 레시피 이미지 추가 코드
    @GetMapping("image") // 
    public String recipeSet(){

        List<RecipeDto> list = null;

        list = webCrawlerService.insertImg();

        for (RecipeDto result : list) {
            Integer RCPNUM = result.getRCP_SNO();
            String temp = webCrawlerService.imgSet(RCPNUM);
            searchMapper.imageSet(RCPNUM, temp);
            int t = 1;
            System.out.println(t + (",") + RCPNUM + " Set Image");
            t++;
        }

        return ("End..");
    }
    */

    @GetMapping("/recipe")
    public RecipeDetailDto crawl(@RequestParam("query") String query) {

        RecipeDetailDto list = null;

        list = webCrawlerService.crawl(query);
        return list;

    }

    @GetMapping("/allrecipes") // 전체 레시피 검색
    public List<RecipeDto> recipe() {

        List<RecipeDto> list = null;

        list = webCrawlerService.allRecipes();

        return list;
    }

    @GetMapping("/getrecipe") // "http://localhost:8001/getrecipe?query=음식이름" 으로 검색
    public List<RecipeDto> searchRecipe(@RequestParam("query") String query) {

        List<RecipeDto> recipe = null;

        recipe = webCrawlerService.getRecipes(query);

        return recipe;
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDto>> getFilteredRecipes(
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String portion,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String method
    ) {
        List<RecipeDto> recipes = webCrawlerService.getFilteredRecipes(difficulty, portion, category, method);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/shop")
    public List<ShopSearchDto> shop(@RequestParam("query") String query) {

        StringBuilder title = new StringBuilder(query);
        if (title.length() > 0) {
            title.deleteCharAt(query.length() - 1);
        }
        List<ShopSearchDto> shopResults = new ArrayList<>();

        shopResults = searchMapper.existChk(query);

        if (!shopResults.isEmpty()) {
            return shopResults;
        } else {

            System.out.println("Query: " + query);

            // StringBuilder output = new StringBuilder();

            HttpURLConnection http = null;
            InputStream stream = null;
            String StringResult = null;
            // List<ShopSearchDto> list = null;

            try {
                URL url = new URL("https://openapi.naver.com/v1/search/shop.json?category=식품&display=20&query="
                        + URLEncoder.encode(query, "UTF-8"));
                http = (HttpURLConnection) url.openConnection();
                System.out.println("http connection = " + http);

                http.setRequestProperty("X-Naver-Client-Id", "U5XJu0QoqAfI3zM_ikQY");
                http.setRequestProperty("X-Naver-Client-Secret", "MTs00_kALb");

                int code = http.getResponseCode();
                System.out.println("http response code = " + code);

                if (code == 200) {
                    stream = http.getInputStream();
                    StringResult = readString(stream); // readString 메서드 호출
                    // System.out.println(result);
                    shopResults = apiservice.parsingJson(StringResult); // JSON 파싱
                    System.out.println("API search complete.....");
                } else {
                    System.out.println("Error response: " + code);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (http != null) {
                    http.disconnect(); // 연결 해제
                }
            }

            List<ShopSearchDto> CoupResult = searchSerivce.search(query);

            shopResults.addAll(CoupResult);

            searchMapper.searchResultRow(shopResults);
            System.out.println("db 업로드 완료");

            return shopResults;
        }

    }

    // ===========================================================================================

    public String readString(InputStream stream) throws IOException {
        System.out.println("readString");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String input = null;
        StringBuilder result = new StringBuilder();
        while ((input = br.readLine()) != null) {
            result.append(input + "\n\r");
        }
        br.close();
        return result.toString();
    }

}
