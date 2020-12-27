package com.gwh.sell.service.impl;

import com.gwh.sell.dataObject.ProductInfo;
import com.gwh.sell.enums.ProductStatusEnum;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest extends TestCase {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void testFindOne() {
        ProductInfo productInfo = this.productInfoService.findOne("123456");
        Assert.assertNotNull(productInfo);
    }
    @Test
    public void testFindUpAll() {
        List<ProductInfo> list=this.productInfoService.findUpAll();
        Assert.assertNotEquals(0,list.size());

    }
    @Test
    public void testFindAll() {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> all = this.productInfoService.findAll(request);
        Assert.assertNotEquals(0,all.getSize());

    }
    @Test
    public void testSave() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123654");
        productInfo.setProductName("灌汤包");
        productInfo.setProductDescription("很好吃");
        productInfo.setProductPrice(new BigDecimal(1.5));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setCategoryType(2);
        ProductInfo result=this.productInfoService.save(productInfo);
        Assert.assertNotNull(result);

    }
}