package com.gwh.sell.service.impl;

import com.gwh.sell.dataObject.OrderDetail;
import com.gwh.sell.dataObject.OrderMaster;
import com.gwh.sell.dto.OrderDTO;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 订单service 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterServiceImplTest extends TestCase {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;


    /**
     * 创建订单测试
     */
    @Test
    public void testCreate() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("张三");
        orderDTO.setBuyerPhone("1231241231");
        orderDTO.setBuyerOpenid("ok123456789");
        orderDTO.setBuyerAddress("北京市 昌平区 上地八街");

        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

        OrderDetail orderDetail1  = new OrderDetail();

        orderDetail1.setProductId("123456");
        orderDetail1.setProductQuantity(2);

        OrderDetail orderDetail2=new OrderDetail();
        orderDetail2.setProductQuantity(5);
        orderDetail2.setProductId("324123");

        orderDetails.add(orderDetail1);
        orderDetails.add(orderDetail2);

        orderDTO.setOrderDetailList(orderDetails);

        OrderDTO result = this.orderMasterService.create(orderDTO);

        Assert.assertNotNull(result);

    }

    public void testFindOne() {
    }

    public void testFindAll() {
    }

    public void testCancel() {
    }

    public void testFinish() {
    }

    public void testPaid() {
    }
}