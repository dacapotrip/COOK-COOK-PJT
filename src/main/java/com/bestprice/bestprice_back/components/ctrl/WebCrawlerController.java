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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bestprice.bestprice_back.components.domain.RecipeDto;
import com.bestprice.bestprice_back.components.domain.ShopSearchDto;
import com.bestprice.bestprice_back.components.service.SearchService;
import com.bestprice.bestprice_back.components.service.WebCrawlerService;
import com.bestprice.bestprice_back.components.service.apiService;
import com.bestprice.bestprice_back.dao.SearchMapper;

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

    @GetMapping("/recipe")
    public RecipeDto crawl(@RequestParam String query) {

        RecipeDto list = null;

        list = webCrawlerService.crawl(query);
        return list;

    }

    @GetMapping("/shop")
    public List<ShopSearchDto> shop(@RequestParam("query") String query) {

        StringBuilder title = new StringBuilder(query);
        if (title.length() > 0) {
            title.deleteCharAt(query.length() - 1);
        }

        System.out.println("Query: " + query);

        List<ShopSearchDto> shopResults = new ArrayList<>();
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

    /*
        StringBuilder title = new StringBuilder(query);
        if(title.length()>0){
            title.deleteCharAt(query.length()-1);
        }


        System.out.println("Query: " + query);

        List<ShopSearchDto> shopResults = new ArrayList<>();
        StringBuilder output = new StringBuilder();


        HttpURLConnection http = null ;
        InputStream       stream = null ;
        String            result = null;
        List<ShopSearchDto>    list = null;

        try {
            URL url = new URL("https://openapi.naver.com/v1/search/shop.json?category=식품&display=20&query=" + URLEncoder.encode(query, "UTF-8"));
            http = (HttpURLConnection)url.openConnection();
            System.out.println("http connection = " + http);

            http.setRequestProperty("X-Naver-Client-Id", "U5XJu0QoqAfI3zM_ikQY");
            http.setRequestProperty("X-Naver-Client-Secret", "MTs00_kALb");

            int code = http.getResponseCode();
            System.out.println("http response code = " + code);
            
            if (code == 200) {
                stream = http.getInputStream();
                result = readString(stream); // readString 메서드 호출
                // System.out.println(result);
                shopResults = apiservice.parsingJson(result); // JSON 파싱
                System.out.println("API search complete.....");
            } else {
                System.out.println("Error response: " + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (http != null) {
                http.disconnect();  // 연결 해제
            }
        }
        
        try {
            // Python Flask 서버에 HTTP GET 요청 보내기
            URL url = new URL("http://localhost:5000/search?keyword=" + URLEncoder.encode(query, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line);
                    }
                }
                
                ObjectMapper objectMapper = new ObjectMapper();
                List<ProductDto> products = objectMapper.readValue(output.toString(),
                new TypeReference<List<ProductDto>>() {
                });
                for (ProductDto product : products) {
                    ShopSearchDto dto = new ShopSearchDto();
                    dto.setProductId(product.getProductId()); // ID 변환
                    dto.setPrice(product.getPrice()); // 가격 변환
                    dto.setLink(product.getLink());
                    dto.setProductName(product.getProductName());
                    dto.setImgUrl(product.getImgUrl());
                    dto.setBasePrice(product.getBasePrice());
                    dto.setDiscount(product.getDiscount());
                    dto.setCategory(product.getCategory());
                    shopResults.add(dto);
                }
                
            } else {
                System.err.println("서버 응답 오류: " + responseCode);
            }
            
        } catch (IOException e) {
            System.err.println("IO 오류: " + e.getMessage());
            e.printStackTrace();
        }
              
        // System.out.println("result >>>>>>> " + list);
        return shopResults; // 결과 DTO 반환
*/

        searchMapper.searchResultRow(shopResults);
        System.out.println("db 업로드 완료"); 
        
        return shopResults;
}

    //===========================================================================================

    public String readString(InputStream stream) throws IOException{
        System.out.println("readString");
        BufferedReader br = new BufferedReader( new InputStreamReader(stream, "UTF-8") ) ;  
        String input = null;
        StringBuilder result = new StringBuilder();
        while( (input = br.readLine() ) != null){
            result.append(input + "\n\r");
        }
        br.close();
        return result.toString();
    }

}
