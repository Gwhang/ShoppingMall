package com.gwh.sell.enums;

import lombok.Getter;

/**
 * 异常类枚举
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11,"库存不足"),

    ORDER_NOT_EXIST(12,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(13,"订单明细不存在"),


    ;
    private Integer code;
    private String name;

    ResultEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
