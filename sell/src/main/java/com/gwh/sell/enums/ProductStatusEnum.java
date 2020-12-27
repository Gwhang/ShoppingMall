package com.gwh.sell.enums;

import lombok.Getter;

/**
 * 商品状态枚举
 */
@Getter
public enum ProductStatusEnum {
    UP(0,"在架"),
    DOWN(1,"下架")

    ;

    private Integer code;

    private String name;

    ProductStatusEnum() {
    }

    ProductStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
