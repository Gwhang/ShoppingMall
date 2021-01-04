package com.gwh.sell.dto;

import com.gwh.sell.dataObject.OrderDetail;
import com.gwh.sell.enums.OrderStatusEnum;
import com.gwh.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单表
 */
@Data
public class OrderDTO {

    /**
     * 订单单号
     */
    @Id
    private String orderId;

    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家电话
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家微信openid
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态, 默认为0新下单
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * 支付状态, 默认0未支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 订单明细表List
     * Transient 数据库进行增加和修改时 忽略该字段
     */
    @Transient
    private List<OrderDetail> orderDetailList;

}
