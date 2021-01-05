package com.gwh.sell.service.impl;

import com.gwh.sell.dataObject.OrderDetail;
import com.gwh.sell.dataObject.OrderMaster;
import com.gwh.sell.dto.OrderDTO;
import com.gwh.sell.enums.OrderStatusEnum;
import com.gwh.sell.enums.PayStatusEnum;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    /**
     * 查询订单信息
     */
    @Test
    public void testFindOne() {
        OrderDTO orderDTO = this.orderMasterService.findOne("1609769672506405621");
        System.out.println(orderDTO);
        Assert.assertNotNull(orderDTO);

    }

    /**
     * 分页查询
     */
    @Test
    public void testFindAll() {
        PageRequest request= new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = this.orderMasterService.findAll("ok123456789", request);
        Assert.assertNotEquals(0,orderDTOPage.getSize());

    }

    /**
     * 订单取消
     */
    @Test
    public void testCancel() {
        OrderDTO orderDTO = this.orderMasterService.findOne("1609769672506405621");
        OrderDTO orderinfo = this.orderMasterService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),orderinfo.getOrderStatus());

    }

    /**
     * 完结订单
     */
    @Test
    public void testFinish() {
        OrderDTO orderDTO = this.orderMasterService.findOne("1609769672506405621");
        OrderDTO orderinfo = this.orderMasterService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),orderinfo.getOrderStatus());
    }

    @Test
    public void testPaid() {
        OrderDTO orderDTO = this.orderMasterService.findOne("1609769672506405621");
        OrderDTO orderinfo = this.orderMasterService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),orderinfo.getPayStatus());
    }
}