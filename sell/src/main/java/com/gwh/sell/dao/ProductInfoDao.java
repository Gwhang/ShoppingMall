package com.gwh.sell.dao;

import com.gwh.sell.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  商品明细
 */
public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {

    /**
     * 查询上架商品
     * @param status
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer status);

}
