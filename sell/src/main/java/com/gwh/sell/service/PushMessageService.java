package com.gwh.sell.service;

import com.gwh.sell.dto.OrderDTO;

/**
 * 消息推送service
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

}
