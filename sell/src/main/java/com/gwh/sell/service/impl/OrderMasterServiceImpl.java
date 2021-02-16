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
import com.gwh.sell.service.PayService;
import com.gwh.sell.service.ProductInfoService;
import com.gwh.sell.utils.EnumUtil;
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

    @Autowired
    private PayService payService;


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
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(amount);
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

    /**
     * 订单取消
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //转换实体类
        OrderMaster orderMaster = new OrderMaster();
        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("[取消订单] 订单状态不正确 orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断订单是否为空
        if(orderDTO == null){
            log.info("[取消订单] 订单不存在 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //修改订单状态为 已取消
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMaster1 = this.orderMasterDao.save(orderMaster);
        if (orderMaster1==null){
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if(orderDTO.getOrderDetailList()==null){
            log.info("[取消订单] 订单明细不存在 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //设置购物车集合信息 进行库存返回
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(p ->
                new CartDTO(p.getProductId(),p.getProductQuantity())).collect(Collectors.toList());
        this.productInfoService.increaseStock(cartDTOList);
        //如果订单已经支付 则修改进行退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            // 订单支付进行退款
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //查询订单状态
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(orderDTO.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode())){
            log.info("[完结订单] 订单状态不正确 orderid={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态为已完结
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        this.orderMasterDao.save(orderMaster);
        return orderDTO;
    }

    /**
     * 支付完成
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态为 支付成功
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        this.orderMasterDao.save(orderMaster);
        return orderDTO;
    }

    /**
     * 分页查下全量数据
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> list = this.orderMasterDao.findAll(pageable);

        List<OrderDTO> orderDTOS = OrderMaster2OrderDTOConverter.converter(list.getContent());
        orderDTOS.stream().forEach(p -> {p.setOrderStatusName(EnumUtil.getByCode(p.getOrderStatus(),OrderStatusEnum.class).getName());
            p.setPayStatusName(EnumUtil.getByCode(p.getPayStatus(),PayStatusEnum.class).getName());
        });
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOS,pageable,list.getTotalElements());
        return orderDTOPage;
    }
}
