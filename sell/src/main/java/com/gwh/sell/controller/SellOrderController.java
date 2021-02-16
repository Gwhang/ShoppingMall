package com.gwh.sell.controller;

import com.gwh.sell.dto.OrderDTO;
import com.gwh.sell.enums.ResultEnum;
import com.gwh.sell.exception.SellException;
import com.gwh.sell.service.OrderMasterService;
import com.lly835.bestpay.rest.type.Get;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单控制层
 */
@Controller
@RequestMapping("/seller/order")
public class SellOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 分页查询订单数据
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "size",defaultValue = "10" )Integer size , Map<String,Object> map){
        PageRequest pageRequest=new PageRequest(page-1,size);
        Page<OrderDTO> list = orderMasterService.findList(pageRequest);

        map.put("orderDTOPage", list);
        map.put("currentPage", page);
        map.put("size", size);
        ModelAndView mav =  new ModelAndView("order/list",map);
        return mav;
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,Map<String,Object> map){

        //根据订单ID查询 订单详情信息
        OrderDTO orderDTO = this.orderMasterService.findOne(orderId);
        //查询为空 返回异常
        if(StringUtils.isEmpty(orderDTO)){
           throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        try{
            //取消订单
            this.orderMasterService.cancel(orderDTO);
        }catch (Exception e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getName());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){

        OrderDTO orderDTO = this.orderMasterService.findOne(orderId);
        //查询为空 返回异常
        if(StringUtils.isEmpty(orderDTO)){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){

        //根据订单ID查询 订单详情信息
        OrderDTO orderDTO = this.orderMasterService.findOne(orderId);
        //查询为空 返回异常
        if(StringUtils.isEmpty(orderDTO)){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        try{
            //完结订单
            this.orderMasterService.finish(orderDTO);
        }catch (Exception e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getName());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

}
