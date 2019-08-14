package com.exchange_v1.app.utils.http.impl.async;//package com.brightoilonline.c2b_phone.utils.http.impl.async;
//
//import android.content.Context;
//
//import com.alibaba.fastjson.JSON;
//import com.brightoilonline.c2b_phone.R;
//import com.brightoilonline.c2b_phone.bean.ResponseBean;
//import com.brightoilonline.c2b_phone.config.ServerConfig;
//import com.brightoilonline.c2b_phone.config.TApplication;
//import com.brightoilonline.c2b_phone.utils.DES3;
//import com.brightoilonline.c2b_phone.utils.HttpsUtil;
//import com.brightoilonline.c2b_phone.utils.LogUtil;
//import com.brightoilonline.c2b_phone.utils.http.AsyncRequestParams;
//import com.brightoilonline.c2b_phone.utils.http.IHttpMethod;
//import com.brightoilonline.c2b_phone.utils.http.IResponseHandler;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//
//import java.security.GeneralSecurityException;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//import javax.net.ssl.SSLContext;
//
//import cz.msebera.android.httpclient.Header;
//import cz.msebera.android.httpclient.client.params.ClientPNames;
//import cz.msebera.android.httpclient.conn.params.ConnManagerParams;
//import cz.msebera.android.httpclient.conn.params.ConnPerRoute;
//import cz.msebera.android.httpclient.conn.params.ConnPerRouteBean;
//import cz.msebera.android.httpclient.conn.scheme.PlainSocketFactory;
//import cz.msebera.android.httpclient.conn.scheme.Scheme;
//import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
//import cz.msebera.android.httpclient.conn.scheme.SocketFactory;
//import cz.msebera.android.httpclient.conn.ssl.BrowserCompatHostnameVerifier;
//import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
//import cz.msebera.android.httpclient.params.BasicHttpParams;
//import cz.msebera.android.httpclient.params.HttpConnectionParams;
//import cz.msebera.android.httpclient.params.HttpParams;
//import cz.msebera.android.httpclient.params.HttpProtocolParams;
//import cz.msebera.android.httpclient.protocol.HTTP;
//
//import static com.brightoilonline.c2b_phone.network.NewsBaseBiz.descResult;
//
///**
// * Created by zhuwd on 2017/3/15.
// * AsyncHttpClient的封装操作，在这里
// * 增加handler在此的实现方法
// *
// */
//
//public class AsyncClient implements IHttpMethod {
//
//    private static final int MAX_CONN_PER_ROUTE = 10;
//    private static final int MAX_CONNECTIONS = 20;
//    private static AsyncClient mInstance;
//    public static AsyncHttpClient mHttpClient;
//
//    public AsyncHttpResponseHandler mAsyncHttpResponseHandler;//网络请求的  handler
//
//    private AsyncClient(){
//        initClient();
//    }
//
//    public static AsyncClient getInstance(){
//        if (mInstance == null)
//        {
//            mInstance = new AsyncClient() {
//
//            };
//        }
//        return mInstance;
//    }
//
////    private AsyncHttpClient getHttpClient() {//TODO  这里需要new
////        return mHttpClient;
////    }
//
//
//    //统一的handler对象
//    public AsyncHttpResponseHandler getC2bHandler(final IResponseHandler mHandler){
//
//        return mAsyncHttpResponseHandler = new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String responseStr = new String(responseBody);
//                responseBean = new ResponseBean();
//                descResult(responseStr, responseBean);
//                mHandler.onSuccess(responseBean);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
//                ResponseBean responseBean = new ResponseBean();
//                responseBean.setStatus(statusCode + "");
//                responseBean.setInfo(TApplication.context.getString(R.string.exception_net_work_time_out_message));
//                mHandler.onFail(responseBean);
//            }
//
//            @Override
//            public void onCancel() {
//                super.onCancel();
//                mHandler.onCancel();
//            }
//        };
//    }
//
//
//    @Override
//    public void initClient() {
//        if (mHttpClient == null) {
//            SSLContext sslContext=null;
//            setSSL(sslContext);
//            mHttpClient.addHeader("Accept-Language", Locale.getDefault().toString());
//            // client.addHeader("Host", HOST);
//            mHttpClient.addHeader("Connection", "Keep-Alive");
//            mHttpClient.setResponseTimeout(ServerConfig.SERVER_CONNECT_TIMEOUT);
//            mHttpClient.getHttpClient().getParams()
//                    .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
//
//            if (TApplication.iSOUTNET == 0) {// 添加HTTP 请求头密文
//                mHttpClient.addHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");// 主要是为了添加服务器验证
//            }
//        }
//    }
//
//
//
//    private void setSSL(SSLContext sslContext) {
//        try {
//            sslContext = HttpsUtil.createSslContext(false);
//        } catch (GeneralSecurityException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        MySSLSocketFactory socketFactory = new MySSLSocketFactory(
//                sslContext, new BrowserCompatHostnameVerifier());
//        SchemeRegistry registry = createScheme(socketFactory);
//        mHttpClient = new AsyncHttpClient(registry);
//    }
//
//    public static SchemeRegistry createScheme(SocketFactory socketFactory) {
//        HttpParams params = new BasicHttpParams();
//        HttpProtocolParams.setContentCharset(params,
//                HTTP.DEFAULT_CONTENT_CHARSET);
//        HttpConnectionParams.setConnectionTimeout(params, 5 * 1000);
//        ConnPerRoute connPerRoute = new ConnPerRouteBean(MAX_CONN_PER_ROUTE);
//        ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
//        ConnManagerParams.setMaxTotalConnections(params, MAX_CONNECTIONS);
//        SchemeRegistry schemeRegistry = new SchemeRegistry();
//        schemeRegistry.register(new Scheme("http", PlainSocketFactory
//                .getSocketFactory(), 80));
//        SocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
//        if (socketFactory != null) {
//            sslSocketFactory = socketFactory;
//        }
//        schemeRegistry.register(new Scheme("http", PlainSocketFactory
//                .getSocketFactory(), 80));
//        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
//        // ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
//        // schemeRegistry);
//
//        return schemeRegistry;
//    }
//
//    @Override
//    public void get(String url, IResponseHandler callback) {
//        mHttpClient.get(url, getC2bHandler(callback));
//
//        LogUtil.out(new StringBuilder("GET ").append(url).toString());
//    }
//
//    @Override
//    public void get(String url, Map params, IResponseHandler callback) throws Exception {
//        mHttpClient.get(url, toJsonRequestPram2(params), getC2bHandler(callback));
//
//        LogUtil.out(new StringBuilder("GET ").append(url).append(params)
//                .toString());
//    }
//
//    private ResponseBean responseBean;
//
//    @Override
//    public void post(String url,final IResponseHandler callback) {
//        mHttpClient.post(url, getC2bHandler(callback));
//
//        LogUtil.out(new StringBuilder("POST ").append(url).toString());
//    }
//
//    // TODO: 2017/3/22
//    @Override
//    public void post(String url, AsyncRequestParams params, IResponseHandler callback) throws Exception {
////        mHttpClient.post(url, params.getP(), callback.getRealAsyncHandler());
//
//        mHttpClient.post(url,toJsonRequestPram2(params.getUrlParams()), getC2bHandler(callback));
//        LogUtil.out(new StringBuilder("POST ").append(url).append(params)
//                .toString());
//    }
//
//    @Override
//    public void OtherMethod(String url, IResponseHandler callback) {
//        mHttpClient.put(url, getC2bHandler(callback));
//        LogUtil.out(new StringBuilder("PUT ").append(url).toString());
//    }
//
//    @Override
//    public void cancle(Context context) {
//        mHttpClient.cancelRequests(context, true);
//    }
//
//    @Override
//    public void post(String url, String path, IResponseHandler callback) {
//
//    }
//
//    private static RequestParams toJsonRequestPram2(Map<String, String> params)
//            throws Exception {
//
//        String encode = DES3.encode(JSON.toJSONString(params));// 请求的数据字符串进行MD5加密
//        HashMap<String, String> hashMap = new HashMap<String, String>();
//        RequestParams requestParams=new RequestParams();
//        //StringEntity entity = new StringEntity(paramStr, "UTF-8");
//        requestParams.put("params", encode);
//        //String paramStr = getParams(hashMap);
//
//        return requestParams;
//    }
//}
