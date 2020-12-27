package com.gwh.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品类目
 */
@Data
public class ProductVo {

    /**
     * 类目名称
     */
    @JsonProperty("name") //注解 序列化的时候，会将其序列化为name
    private String categoryName;

    /**
     * 类目编号
     */
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVo> data;

}
