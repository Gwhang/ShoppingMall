package com.gwh.sell.utils;

import com.gwh.sell.vo.ResultVo;

/**
 * 结果返回公共方法
 * @author  gwh
 */
public class ResultVoUtil {

    public static ResultVo success(Object object){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("执行成功");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;

    }



}
