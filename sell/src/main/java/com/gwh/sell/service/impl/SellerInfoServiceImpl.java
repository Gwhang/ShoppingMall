package com.gwh.sell.service.impl;

import com.gwh.sell.dao.SellerInfoDao;
import com.gwh.sell.dataObject.SellerInfo;
import com.gwh.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return this.sellerInfoDao.save(sellerInfo);
    }

    @Override
    public SellerInfo getByOpenid(String openId) {
        return this.sellerInfoDao.getByOpenid(openId);
    }
}
