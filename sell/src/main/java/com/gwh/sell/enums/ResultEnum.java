package com.gwh.sell.enums;

import lombok.Getter;

/**
 * 异常类枚举
 */
@Getter
public enum ResultEnum {

    PARAM_ERROR(1,"参数不正确"),

    JSON_ERROR(2,"json转换错误"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11,"库存不足"),

    ORDER_NOT_EXIST(12,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(13,"订单明细不存在"),

    ORDER_STATUS_ERROR(14,"订单状态不正确"),

    ORDER_UPDATE_FAIL(15,"订单更新失败"),

    ORDER_DETAIL_EMPTY(16,"订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17,"支付状态不正确"),

    CART_ERROR(18,"购物车为空"),

    ORDER_OPENID_ERROR(19,"当前订单不属于当前用户"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(20,"订单金额与系统金额不一致"),

    ORDER_CANCEL_SUCCESS(21,"取消订单成功"),

    ORDER_FINISH_SUCCESS(22,"完结订单成功"),

    PRODUCT_STATUS_ERROR(23,"商品上架状态不正确"),

    LOGIN_FAIL(24,"登录失败"),

    LOGOUT_SUCCESS(25,"登出成功"),

    ;
    private Integer code;
    private String name;

    ResultEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
