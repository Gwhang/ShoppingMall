package com.gwh.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品明细
 * 出于产品安全，不建议将数据所有属性都返回
 */
@Data
public class ProductInfoVo {

    /**
     * 商品ID
     */
    @JsonProperty("id")
    private String productId;

    /**
     * 商品名称
     */
    @JsonProperty("name")
    private String productName;

    /**
     * 商品金额
     */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /**
     * 商品描述
     */
    @JsonProperty("description")
    private String productDescription;

    /**
     * 商品小图
     */
    @JsonProperty("icon")
    private String productIcon;



}
