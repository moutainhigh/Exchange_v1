package com.exchange_v1.app.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuwd on 2017/7/21.
 * 序列化map供Bundle传递map使用
 */

public class SerializableMap<T> implements Serializable {

    private Map<String,T> map;

    public Map<String, T> getMap() {
        return map;
    }

    public void setMap(HashMap<String, T> map) {
        this.map = map;
    }
}
