package com.gwh.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 订单参数校验
 */
@Data
public class OrderForm {

    /**
     * 买家名称
     */
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "买家手机号不能为空")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "买家地址不能为空")
    private String address;

    /**
     * 买家openID
     */
    @NotEmpty(message = "买家openid不能为空")
    private String openid;

    /**
     * 购物车信息
     */
    @NotEmpty(message = "买家购物车信息")
    private String items;

}
