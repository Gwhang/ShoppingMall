package com.gwh.sell.utils;

import com.gwh.sell.enums.CodeEnum;

/**
 * 根据code 返回枚举name
 */
public class EnumUtil {

    /**
     * 根据code 返回枚举name
     * @param code
     * @param enumClass 枚举类
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

}
