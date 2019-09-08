package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.HashMap;

public class JpushBiz extends BaseBiz{

    //绑定
    public static void bindJpush(Context context, String registration_id,String device_id,String alias, final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();
        params.put("registration_id",registration_id);
        params.put("device_id",device_id);
        params.put("alias",alias);
        params.put("platform","Android");

        NewsBaseBiz.postRequest(context, "", true, ServerConfig.JPUSH_BIND_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }
                });
    }

    //解除绑定
    public static void unBindJpush(Context context, final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();

        NewsBaseBiz.postRequest(context, "", true, ServerConfig.JPUSH_UNBIND_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }
                });
    }

}
