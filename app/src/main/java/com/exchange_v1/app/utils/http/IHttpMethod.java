package com.exchange_v1.app.utils.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by zhuwd on 2017/3/20.
 * 网络请求的共同方法  get，post
 */

public interface IHttpMethod {
    /**
     * HttpClient初始化操作
     */
    void initClient();

    /**
     * 无参数的get方法
     *
     * @param url
     * @param callback
     */
    void get(String url, IBinaryResponseHandler callback);

    /**
     * 有参数的get方法
     *
     * @param url
     * @param params
     * @param callback
     */
    void get(String url, Map params, IResponseHandler callback) throws Exception;

    /**
     * 无参数的post方法
     *
     * @param url
     * @param callback
     */
    void post(String url, IResponseHandler callback);

    /**
     * 有参数的post方法
     *
     * @param url
     * @param params
     * @param callback
     */
    void post(String url, AsyncRequestParams params, IResponseHandler callback) throws Exception;
    void post(String url, AsyncRequestParams params, boolean isEncrypt, IResponseHandler callback) throws Exception;

    /**
     * 其他除post，get的请求方法
     *
     * @param url
     * @param callback
     */
    void OtherMethod(String url, IResponseHandler callback);

    /**
     * 取消网络请求
     *
     * @param context
     */
    void cancle(Context context);

    /**
     * post上传文件
     *
     * @param url
     * @param path
     * @param callback
     */
    void post(String url, String path, IResponseHandler callback);
}
