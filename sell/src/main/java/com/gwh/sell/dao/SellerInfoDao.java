package com.gwh.sell.dao;

import com.gwh.sell.dataObject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户信息 dao
 */
@Repository
public interface SellerInfoDao extends JpaRepository<SellerInfo,String> {

    SellerInfo getByOpenid(String openID);
}
