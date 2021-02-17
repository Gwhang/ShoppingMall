package com.gwh.sell.service;

import com.gwh.sell.dataObject.SellerInfo;

/**
 * 用户信息
 */
public interface SellerInfoService {

    SellerInfo save(SellerInfo sellerInfo);

    SellerInfo getByOpenid(String openId);

}
