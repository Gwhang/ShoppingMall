package com.gwh.sell.dao;

import com.gwh.sell.dataObject.ProductInfo;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest extends TestCase {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void saveTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductDescription("很好喝");
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setCategoryType(2);
        ProductInfo result=this.productInfoDao.save(productInfo);
        Assert.assertNotNull(result);

    }

    @Test
    public void testFindByProductStatus() {
        List<ProductInfo> productInfos = this.productInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfos.size());
    }
}