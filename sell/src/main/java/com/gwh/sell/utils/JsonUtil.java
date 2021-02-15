package com.gwh.sell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json转换工具
 */
public class JsonUtil {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=new Gson();
        return gson.toJson(object);
    }

}
