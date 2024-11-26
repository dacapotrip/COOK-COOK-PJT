package com.bestprice.bestprice_back.productprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductPriceService {

    @Autowired
    private ProductPriceMapper productPriceMapper;

    public List<Map<String, Object>> getPricesAndLastUpdatedByProductId(String productId) {
        return productPriceMapper.selectPriceAndLastUpdatedByProductId(productId);
    }
}
