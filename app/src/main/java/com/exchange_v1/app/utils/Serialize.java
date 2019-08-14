package com.exchange_v1.app.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by penglian on 2016/5/3.
 */
public class Serialize {
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseJsonToMap(String jsonStr){
        return JSON.parseObject(jsonStr, Map.class);
    }
}
