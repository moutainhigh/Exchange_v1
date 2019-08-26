package com.exchange_v1.app.utils.http;

import android.content.Context;

import com.exchange_v1.app.utils.http.impl.ok.C2bOkClient;

import java.util.Map;

//import com.brightoilonline.c2b_phone.utils.http.impl.async.AsyncClient;

/**
 * Created by zhuwd on 2017/3/15.
 * 网络切换框架入口，代替AppHttpClient类
 */

public class MyHttpClient {
    private static MyHttpClient ourInstance;
    private static IHttpMethod networkClient;

    public static MyHttpClient getInstance() {
        if (ourInstance == null) {
            ourInstance = new MyHttpClient();
        }
        return ourInstance;
    }

    private static IHttpMethod with() {
        return getInstance().networkClient;
    }

    private MyHttpClient() {
        //在此处可以切换不同类型的框架
        //        networkClient = AsyncClient.getInstance();  //Asynchttp
        networkClient = C2bOkClient.getInstance();  //okhttp
    }

    //    public AsyncClient getC2bHandler(){
    //        return AsyncClient.getInstance();
    //    }


    public static void initClient() {
        with().initClient();
    }

    public static void get(String url, IBinaryResponseHandler callback) {
        with().get(url, callback);
    }

    public static void get(String url, Map params, IResponseHandler callback) throws Exception {
        with().get(url, params, callback);
    }

    public static void post(String url, IResponseHandler callback) {
        with().post(url, callback);
    }

    public static void post(String url, Map params, IResponseHandler callback) throws Exception {
        AsyncRequestParams requestParams = new AsyncRequestParams();
        AsyncRequestParams.convertMapAllToOtherMap(params, requestParams.getUrlParams());
        post(url, requestParams, false, callback);
    }

    public static void post(String url, AsyncRequestParams params, IResponseHandler callback) throws Exception {
        post(url, params, false, callback);
    }

    public static void post(String url, AsyncRequestParams params, boolean isEncrypt, IResponseHandler callback) throws Exception {
        with().post(url, params, isEncrypt, callback);
    }

    public static void OtherMethod(String url, IResponseHandler callback) {
        with().OtherMethod(url, callback);
    }

    public static void cancle(Context context) {
        with().cancle(context);
    }

    public static void post(String url, String path, IResponseHandler callback) {
        with().post(url, path, callback);
    }

}
