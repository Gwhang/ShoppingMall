package com.gwh.sell.service;

import com.gwh.sell.dto.OrderDTO;

/**
 * 买家Service
 */
public interface BuyerService {

    OrderDTO findBuyerOder(String openid,String orderId);

    OrderDTO cancelBuyerOder(String openid,String orderId);

}
