package com.gwh.sell.dao;

import com.gwh.sell.dataObject.OrderMaster;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 订单主表测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest extends TestCase {

    @Autowired
    private OrderMasterDao orderMasterDao;

    /**
     * 测试保存方法
     */
    @Test
    public void testSave(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12345567");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("1354235223");
        orderMaster.setBuyerAddress("北京");
        orderMaster.setBuyerOpenid("OK123368er234sedfs");
        orderMaster.setOrderAmount(new BigDecimal(123.23));
        OrderMaster save = this.orderMasterDao.save(orderMaster);
        Assert.assertNotNull(save);
    }


    /**
     * 根据客户opernId分页查询客户订单信息
     */
    @Test
    public void testFindByBuyerOpenId() {

        PageRequest request=new PageRequest(0,2);

        Page<OrderMaster> byBuyerOpenid = this.orderMasterDao.findByBuyerOpenid("OK123368er234sedfs", request);

        System.out.println(byBuyerOpenid.getTotalElements());

        System.out.println(byBuyerOpenid.getContent());


    }
}