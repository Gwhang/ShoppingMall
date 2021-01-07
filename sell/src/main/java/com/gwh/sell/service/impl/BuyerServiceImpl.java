package com.gwh.sell.service.impl;

import com.gwh.sell.dto.OrderDTO;
import com.gwh.sell.enums.ResultEnum;
import com.gwh.sell.exception.SellException;
import com.gwh.sell.service.BuyerService;
import com.gwh.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单关联
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {


    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 查询买家订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findBuyerOder(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    /**
     * 取消买家订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO cancelBuyerOder(String openid, String orderId) {
        OrderDTO orderDTO =  checkOrderOwner(openid,orderId);
        if(orderDTO == null){
            log.error("[取消订单] 查询订单数据为null");
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 订单校验方法
     * @param openid
     * @param orderId
     * @return
     */
    private OrderDTO checkOrderOwner(String openid, String orderId){

        OrderDTO orderDTO = this.orderMasterService.findOne(orderId);

        if(orderDTO == null){
            return  null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[校验订单] 查询的订单不属于当前客户");
            throw new SellException(ResultEnum.ORDER_OPENID_ERROR);
        }
        return orderDTO;
    }

}
