package com.exchange_v1.app.utils;


import org.json.JSONArray;

/**
 * Created by huanghh on 2017/4/15.
 */

public class JSONUtils {
    public static boolean isEmpty(JSONArray ja){
        boolean isEmpty = false;
        if (ja==null) {
            return true;
        }
        return ja.length() == 0;
    };
}
