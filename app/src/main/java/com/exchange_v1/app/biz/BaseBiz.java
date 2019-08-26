package com.exchange_v1.app.biz;

import android.text.TextUtils;

import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.config.Constant;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.utils.UserInfoUtil;
import com.exchange_v1.app.utils.http.AsyncRequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 系统业务逻辑
 */
public class BaseBiz {
    /**
     * post请求统一参数
     */
    protected static HashMap<String, String> getPostHeadMap() {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(TApplication.getToken())) {
            map.put(ServerConfig.SERVER_METHOD_KEY, TApplication.getToken());

            UserInfoUtil.setLoginStatus(true);
        } else {
            UserInfoUtil.setLoginStatus(false);
        }
        map.put("ver", Constant.VERSION_CODE);
        map.put("plat", Constant.ANDROID_PLAT);
        map.put("timeMillis", String.valueOf(System.currentTimeMillis()));
        return map;
    }

    /**
     * 转化参数
     */
    public static AsyncRequestParams map2Requestparams(HashMap<String, String> map) {
        AsyncRequestParams params = new AsyncRequestParams();
        Iterator iter = map.entrySet().iterator();//125444
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String val = entry.getValue();
            params.put(key, val);
        }
        return params;
    }

}
