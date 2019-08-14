package com.exchange_v1.app.utils.http;


import com.exchange_v1.app.bean.ResponseBean;

/**
 * Created by zhuwd on 2017/3/16.
 * 网络请求回调接口
 */

public interface IResponseHandler {

    void onSuccess(ResponseBean result);

    void onFail(ResponseBean result);

    void onCancel();

    void onSaveCache(ResponseBean result);

    ResponseBean getCache();

}
