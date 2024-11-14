package com.bestprice.bestprice_back.components.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestprice.bestprice_back.components.domain.ShopSearchDto;
import com.bestprice.bestprice_back.components.domain.apiDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class apiService {

    private final ObjectMapper objectMapper;

    public apiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<ShopSearchDto> parsingJson(String jsonString) throws Exception {
        apiDto response = objectMapper.readValue(jsonString, apiDto.class);
        List<ShopSearchDto> shopResults = new ArrayList<>();

        for (apiDto.Item item : response.getItems()) {
            ShopSearchDto dto = new ShopSearchDto();
            dto.setProductName(item.getTitle());
            dto.setLink(item.getLink());
            dto.setImgUrl(item.getImage());
            dto.setPrice(item.getLprice());
            dto.setProductId(item.getProductId());
            // dto.setMallName(item.getMallName());
            dto.setCategory(item.getCategory1());
            shopResults.add(dto);
        }

        return shopResults;
    }
}
