package com.gwh.sell.utils;

import java.util.Random;

/**
 * 数据库主键生成
 */
public class KeyUtil {

    /**
     * 生产唯一主键ID
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }

}
