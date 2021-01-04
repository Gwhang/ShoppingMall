package com.gwh.sell.converter;

import com.gwh.sell.dataObject.OrderMaster;
import com.gwh.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类转换器
 */
@Configuration
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasters){
        return orderMasters.stream().map(p -> converter(p))
                .collect(Collectors.toList());
    }


}
