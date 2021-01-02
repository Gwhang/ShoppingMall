package com.gwh.sell.enums;

/**
 * 支付状态枚举
 * @author gwh
 */
public enum PayStatusEnum {

    WAIT(0,"未支付"),
    SUCCESS(1,"已支付"),

    ;

    PayStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
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
