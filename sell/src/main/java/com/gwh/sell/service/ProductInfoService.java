package com.gwh.sell.service;

import com.gwh.sell.dataObject.ProductInfo;
import com.gwh.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品service
 */
public interface ProductInfoService {

    /**
     * 查询单条数据
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询上架商品
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询商品
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存
     */
    ProductInfo save(ProductInfo productInfo);


    /**
     * 加库存
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);

}
