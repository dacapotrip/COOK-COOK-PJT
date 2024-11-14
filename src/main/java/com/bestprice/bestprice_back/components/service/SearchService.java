package com.bestprice.bestprice_back.components.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestprice.bestprice_back.components.domain.ProductDto;
import com.bestprice.bestprice_back.components.domain.ShopSearchDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchService {


    public List<ShopSearchDto> search(String query) {
        
        List<ShopSearchDto> shopResults = new ArrayList<>();
        StringBuilder output = new StringBuilder();

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
        System.out.println("검색 종료");
        return shopResults; // 결과 DTO 반환
    }
}
