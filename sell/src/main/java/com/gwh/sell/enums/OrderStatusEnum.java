package com.gwh.sell.enums;

/**
 * 订单状态枚举
 * @author gwh
 */
public enum OrderStatusEnum {

    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;

    OrderStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    OrderStatusEnum() {
    }

    /**
     * 状态
     */
    private Integer code;

    /**
     * 名称
     */
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
