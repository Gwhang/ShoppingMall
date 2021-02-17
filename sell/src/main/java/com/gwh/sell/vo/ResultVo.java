package com.gwh.sell.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 公共返回类
 */
@Data
public class ResultVo<T> implements Serializable {

    /**
     * 返回状态
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回内容
     */
    private T data;

}
