package com.gwh.sell.service.impl;

import com.gwh.sell.converter.OrderMaster2OrderDTOConverter;
import com.gwh.sell.dao.OrderDetailDao;
import com.gwh.sell.dao.OrderMasterDao;
import com.gwh.sell.dataObject.OrderDetail;
import com.gwh.sell.dataObject.OrderMaster;
import com.gwh.sell.dataObject.ProductInfo;
import com.gwh.sell.dto.CartDTO;
import com.gwh.sell.dto.OrderDTO;
import com.gwh.sell.enums.OrderStatusEnum;
import com.gwh.sell.enums.PayStatusEnum;
import com.gwh.sell.enums.ResultEnum;
import com.gwh.sell.exception.SellException;
import com.gwh.sell.service.OrderMasterService;
import com.gwh.sell.service.ProductInfoService;
import com.gwh.sell.utils.KeyUtil;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 订单表服务层
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductInfoService productInfoService;


    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        // 查询商品信息
        // 定义订单ID
        String orderId= KeyUtil.genUniqueKey();
        // 定义订单金额
        BigDecimal amount=BigDecimal.ZERO;

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            //查询商品信息
            ProductInfo productInfo = this.productInfoService.findOne(orderDetail.getProductId());
            if (productInfo.equals(null)) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 计算商品总价格
            amount = new BigDecimal(orderDetail.getProductQuantity()).multiply(productInfo.getProductPrice())
                    .add(amount);
            //设置商品信息
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            this.orderDetailDao.save(orderDetail);

        }
        //创建订单
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(amount);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        this.orderMasterDao.save(orderMaster);
        //减库存
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(p ->
             new CartDTO(p.getProductId(),p.getProductQuantity())
        ).collect(Collectors.toList());
        this.productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    /**
     * 查询商品信息
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        log.info("[查询订单信息] 传输的参数为：{}",orderId);
        Optional<OrderMaster> orderMaster = this.orderMasterDao.findById(orderId);
        if (!orderMaster.isPresent()){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = this.orderDetailDao.findByOrderId(orderId);
        if (orderDetailList.equals(null)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster.get(),orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 分页查询
     * @param openId
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findAll(String openId, Pageable pageable) {

        Page<OrderMaster> masterPage = this.orderMasterDao.findByBuyerOpenid(openId, pageable);

        List<OrderDTO> orderDTOS = OrderMaster2OrderDTOConverter.converter(masterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOS,pageable,masterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}