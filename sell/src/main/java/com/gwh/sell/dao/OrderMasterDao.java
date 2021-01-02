package com.gwh.sell.dao;

import com.gwh.sell.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 订单主表
 * @author gwh
 */
@Repository
public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {

    /**
     * 根据买家openId查询买家相关商品进行分页
     * @param openId
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String openId,Pageable pageable);
}
