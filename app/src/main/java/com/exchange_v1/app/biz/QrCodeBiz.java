package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.BaseBean;
import com.exchange_v1.app.bean.QrCodeBean;
import com.exchange_v1.app.bean.QrInfoBean;
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
        //ALI_BY-支付宝,WX_BY-微信
        params.put("paymentType", category);
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
                        BaseBean.setGsonResponseObjectList(responseBean, QrCodeBean.class,"");
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }


    //二维码回显  type：ALI_BY-支付宝。JH_BY-聚合。WX_BY-微信
    public static void getQrCodeInfo(Context context,String type, final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();
        params.put("type",type);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.QRCODE_INFO_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObject(responseBean, QrInfoBean.class);
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

}
