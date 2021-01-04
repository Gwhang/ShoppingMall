package com.gwh.sell.exception;

import com.gwh.sell.enums.ResultEnum;

/**
 * 异常
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getName());

        this.code=resultEnum.getCode();

    }

}
