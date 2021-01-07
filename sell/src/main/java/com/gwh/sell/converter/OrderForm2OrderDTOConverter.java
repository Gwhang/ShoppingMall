package com.gwh.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwh.sell.dataObject.OrderDetail;
import com.gwh.sell.dto.OrderDTO;
import com.gwh.sell.enums.ResultEnum;
import com.gwh.sell.exception.SellException;
import com.gwh.sell.form.OrderForm;
import javafx.collections.ObservableList;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * orderForm转换为orderDto
 */
@Configuration
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        Gson gson=new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        try{
            orderDetailList =  gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.info("转换异常 string={}",orderForm.getItems());
            throw new SellException(ResultEnum.JSON_ERROR);
        }
            orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }


}
