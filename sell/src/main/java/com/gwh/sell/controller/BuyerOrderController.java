package com.gwh.sell.controller;

import com.gwh.sell.converter.OrderForm2OrderDTOConverter;
import com.gwh.sell.dto.OrderDTO;
import com.gwh.sell.enums.ResultEnum;
import com.gwh.sell.exception.SellException;
import com.gwh.sell.form.OrderForm;
import com.gwh.sell.service.BuyerService;
import com.gwh.sell.service.OrderMasterService;
import com.gwh.sell.utils.ResultVoUtil;
import com.gwh.sell.vo.ResultVo;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 买家端API
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     */
    @RequestMapping("create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        //必填校验
        if (bindingResult.hasErrors()){
            log.info("订单参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //参数转换
        OrderDTO orderDTO= OrderForm2OrderDTOConverter.converter(orderForm);
        if (orderDTO.getOrderDetailList().equals(null)){
            log.info("购物车为空");
            throw new SellException(ResultEnum.CART_ERROR);
        }
        OrderDTO result=this.orderMasterService.create(orderDTO);
        if(result.equals(null)){
            log.info("创建订单失败");
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        Map<String,String> map=new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVoUtil.success(map);
    }



    /**
     * 分页查询商品
     */
    @RequestMapping("list")
    public ResultVo<OrderDTO> list(@RequestParam("openid")String openid,
                                   @RequestParam(value = "page",defaultValue = "0") Integer page,
                                   @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.info("openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> info = this.orderMasterService.findAll(openid, pageRequest);

       return ResultVoUtil.success(info.getContent());
    }



    /**
     * 查询商品明细
     */
    @RequestMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
         OrderDTO orderDTO = this.buyerService.findBuyerOder(openid,orderId);
        return  ResultVoUtil.success(orderDTO);
    }

    /**
     * 取消订单
     */
    @RequestMapping("cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = this.buyerService.cancelBuyerOder(openid,orderId);
        this.orderMasterService.cancel(orderDTO);
        return ResultVoUtil.success();
    }

}
