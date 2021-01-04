package com.gwh.sell.enums;

import lombok.Getter;

/**
 * 异常类枚举
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11,"库存不足"),



    ;
    private Integer code;
    private String name;

    ResultEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
