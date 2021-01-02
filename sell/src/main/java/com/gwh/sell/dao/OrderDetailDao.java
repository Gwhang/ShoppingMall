package com.gwh.sell.dao;

import com.gwh.sell.dataObject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单明细DAO
 * @author gwh
 */
@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {

    /**
     * 根据订单ID查询订单详情
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
