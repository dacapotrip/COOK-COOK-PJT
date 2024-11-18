package com.bestprice.bestprice_back.components.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.bestprice.bestprice_back.components.domain.RecipeDetailDto;
import com.bestprice.bestprice_back.components.domain.RecipeDto;
import com.bestprice.bestprice_back.dao.SearchMapper;

@Service
public class WebCrawlerService {

    @Autowired
    private SearchMapper searchMapper;

    public RecipeDetailDto crawl(String query) {
        List<RecipeDetailDto> recipes = new ArrayList<>(); // 리스트 초기화
        String url = "https://www.10000recipe.com/recipe/" + query;
        System.out.println(url);
        RecipeDetailDto recipeDto = new RecipeDetailDto();

        try {
            // URL에서 HTML 문서 가져오기
            Document document = Jsoup.connect(url).get();

            // 메인 이미지
            Element mainImg = document.getElementById("main_thumbs");
            String mainThumb = mainImg != null ? mainImg.attr("src") : null;

            // 특정 요소만 선택
            for (int q = 1; q <= 10000; q++) {
                String stepId = "stepDiv" + q;
                Element stepsElement = document.getElementById(stepId);

                if (stepsElement != null) {
                    Elements results = stepsElement.select("div.media-body");
                    Elements images = stepsElement.select("img");

                    recipeDto.setMainThumb(mainThumb); // 메인 이미지 설정

                    for (int i = 0; i < results.size(); i++) {
                        Element result = results.get(i);
                        String stepsText = result.text();
                        String stepsImg = images.size() > i ? images.get(i).attr("src") : ""; // 이미지 URL

                        RecipeDetailDto.StepsDTO stepsDTO = recipeDto.new StepsDTO(); // 내부 클래스 인스턴스 생성
                        stepsDTO.setStepText(stepsText); // 단계 텍스트 설정
                        stepsDTO.setStepsImg(stepsImg); // 단계 이미지 설정

                        recipeDto.getSteps().add(stepsDTO); // StepsDTO 리스트에 추가
                    }

                    recipes.add(recipeDto); // 리스트에 추가
                } else {
                    System.out.println("steps is null!");
                    break;
                }
            }
            Elements comps = document.getElementsByClass("carouItem");
            Elements compImg = comps.select("img");
            List<String> compList = new ArrayList<>();
            for (Element compResult : compImg) {
                String compImgUrl = compResult.attr("src");
                compList.add(compImgUrl);
                System.out.println(compImgUrl);
            }
            recipeDto.setComp(compList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeDto; // 결과 반환
    }

    public RecipeDto imgSet(String query){
        List<RecipeDetailDto> recipes = new ArrayList<>(); // 리스트 초기화
        String url = "https://www.10000recipe.com/recipe/" + query;
        System.out.println(url);
        RecipeDto recipeImage = new RecipeDto();
        
        try {
            Document document = Jsoup.connect(url).get();
            Element mainImg = document.getElementById("main_thumbs");
            String mainThumb = mainImg != null ? mainImg.attr("src") : null;

            recipeImage.setIMG_URL(mainThumb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<RecipeDto> allRecipes (){

        List<RecipeDto> list = null;

        list = searchMapper.allRecipes();

        return list;
    }

    public List<RecipeDto> getRecipes (@RequestParam("query") String qeury){

        List<RecipeDto> list = null;

        list = searchMapper.getRecipe(qeury);

        return list;
    }


    // private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    
}

