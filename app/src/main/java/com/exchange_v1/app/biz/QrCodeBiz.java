package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.HashMap;

//收款二维码请求接口
public class QrCodeBiz extends BaseBiz{

    //二维码绑定
    public static void bindQrCode(Context context, String fileId,String category,String account,String name, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("fileId", fileId);
        params.put("category", category);
        params.put("account", account);
        params.put("name", name);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.BIND_QRCODE_API,
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

    //用户二维码列表
    public static void getQrCodeList(Context context, final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.QRCODE_LIST_API,
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
