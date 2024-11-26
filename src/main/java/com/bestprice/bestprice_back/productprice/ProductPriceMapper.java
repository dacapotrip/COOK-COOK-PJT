package com.bestprice.bestprice_back.productprice;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProductPriceMapper {
    List<Map<String, Object>> selectPriceAndLastUpdatedByProductId(String productId);
}
