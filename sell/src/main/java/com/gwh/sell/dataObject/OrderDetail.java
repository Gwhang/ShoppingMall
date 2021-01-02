package com.gwh.sell.dataObject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单明细
 * @author gwh
 */
@Entity
@Data
public class OrderDetail {

    /**
     * 明细表主键ID
     */
    @Id
    private String detailId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 数量
     */
    private Integer productQuantity;

    /**
     * 商品图片
     */
    private String productIcon;


}
