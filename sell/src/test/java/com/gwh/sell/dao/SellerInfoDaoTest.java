package com.gwh.sell.dao;

import com.gwh.sell.dataObject.SellerInfo;
import com.gwh.sell.utils.KeyUtil;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest extends TestCase {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    /**
     * 根据OpenID查询
     */
    @Test
    public void testGetByOpenid() {
        SellerInfo sellerInfo = this.sellerInfoDao.getByOpenid("123456");
        Assert.assertNotNull(sellerInfo);
    }

    @Test
    public void testSave(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setId(KeyUtil.genUniqueKey());
        sellerInfo.setOpenid("123456");
        sellerInfo.setPassword("admin");
        sellerInfo.setUsername("admin");
        this.sellerInfoDao.save(sellerInfo);
    }

}