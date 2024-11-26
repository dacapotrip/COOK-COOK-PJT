package com.bestprice.bestprice_back.productprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductPriceController {

    @Autowired
    private ProductPriceService productPriceService;

    @GetMapping("/prices")
    public List<Map<String, Object>> getPrices(@RequestParam("productId") String productId) {
        return productPriceService.getPricesAndLastUpdatedByProductId(productId);
    }
}
