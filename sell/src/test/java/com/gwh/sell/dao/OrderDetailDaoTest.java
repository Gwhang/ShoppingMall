package com.gwh.sell.dao;

import com.gwh.sell.dataObject.OrderDetail;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单明细
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest extends TestCase {

    @Autowired
    OrderDetailDao orderDetailDao;

    @Test
    public void testSave(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("123243245235");
        orderDetail.setOrderId("11111222");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId("2");
        orderDetail.setProductName("冰冰碎");
        orderDetail.setProductPrice(new BigDecimal(23.1));
        orderDetail.setProductQuantity(20);

        OrderDetail save = this.orderDetailDao.save(orderDetail);
        Assert.assertNotNull(save);

    }

    /**
     * 根据订单ID查询商品信息
     */
    @Test
    public void testFindByOrderId() {

        List<OrderDetail> orderDetails=this.orderDetailDao.findByOrderId("11111222");

        Assert.assertNotEquals(0,orderDetails.size());

    }
}