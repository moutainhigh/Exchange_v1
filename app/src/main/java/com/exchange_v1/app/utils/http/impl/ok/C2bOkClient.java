package com.exchange_v1.app.utils.http.impl.ok;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.exchange_v1.R;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.utils.DES3;
import com.exchange_v1.app.utils.HandlerUtil;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.http.AsyncRequestParams;
import com.exchange_v1.app.utils.http.HttpsUtils;
import com.exchange_v1.app.utils.http.IBinaryResponseHandler;
import com.exchange_v1.app.utils.http.IHttpMethod;
import com.exchange_v1.app.utils.http.IResponseHandler;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.exchange_v1.app.network.NewsBaseBiz.descResult;


/**
 * Created by zhuwd on 2017/3/20.
 * <p>
 * okHttp的封装
 */

public class C2bOkClient implements IHttpMethod {
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private static C2bOkClient mInstance;
    public static OkHttpClient mOkHttpClient;

    private Callback mOkCallback;

    public static Call call;

    private C2bOkClient() {
        initClient();
    }

    public static C2bOkClient getInstance() {
        if (mInstance == null) {
            mInstance = new C2bOkClient();
        }
        return mInstance;
    }

    /**
     * 对外提供okHttpClient 对象
     *
     * @return 获取方式 C2bOkClient.getInstance().getOkHttpClient()
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    @Override
    public void initClient() {
        if (mOkHttpClient == null) {
            //            InputStream inputStream = null;
            //            try {
            //                inputStream = TApplication.context.getAssets().open("bwoil1.cer");
            //            } catch (IOException e) {
            //                e.printStackTrace();
            //            }
            //由于证书取消,不验证证书
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(ServerConfig.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)//连接超时时间
                    .readTimeout(ServerConfig.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(ServerConfig.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(new LoggerInterceptor("C2B"))// 拦截器
                    .cookieJar(new CookieJar() {//Cookie自动管理
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .hostnameVerifier(new HostnameVerifier() {//在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                            boolean localHost = hv.verify("brightoilonline.com", session);
                            return localHost;
                        }
                    })
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .build();
            //addhead()方法在Request对象中设置
            // "Accept-Language", Locale.getDefault().toString()
            // "Connection", "Keep-Alive"
            // if (TApplication.iSOUTNET == 0) {// 添加HTTP 请求头密文
            //client.addHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");// 主要是为了添加服务器验证
        }
    }

    public Callback getC2bCallbackByBinary(final IBinaryResponseHandler mHandler) {
        return mOkCallback = new Callback() {
            ResponseBean okResponseBean;

            @Override
            public void onFailure(Call call, IOException e) {
                okResponseBean = new ResponseBean();
                okResponseBean.setStatus(call.hashCode() + "");
                okResponseBean.setInfo(TApplication.context.getString(R.string.exception_net_work_time_out_message));
                HandlerUtil.runOnUI(new Runnable() {//切换到主线程
                    @Override
                    public void run() {
                        mHandler.onFail(okResponseBean);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final byte[] bytes = response.body().bytes();
                Headers headers = response.headers();

                final Header[] headersArr = new Header[headers.size()];
                Header header = null;
                for (int i = 0; i < headers.size(); i++) {
                    headersArr[i] = new BasicHeader(headers.name(i), headers.value(i));
                }


                HandlerUtil.runOnUI(new Runnable() {//切换到主线程中
                    @Override
                    public void run() {
                        mHandler.onSuccess(response.code(), (com.exchange_v1.app.utils.http.Header[]) headersArr, bytes);
                    }
                });
            }
        };
    }

    public Callback getC2bCallback(final IResponseHandler mHandler) {
        return mOkCallback = new Callback() {
            ResponseBean okResponseBean;

            @Override
            public void onFailure(Call call, IOException e) {
                okResponseBean = new ResponseBean();
                okResponseBean.setStatus(call.hashCode() + "");
                okResponseBean.setInfo(TApplication.context.getString(R.string.exception_net_work_time_out_message));
                HandlerUtil.runOnUI(new Runnable() {//切换到主线程
                    @Override
                    public void run() {
                        mHandler.onFail(okResponseBean);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = "";
                if (StringUtil.isEmpty(jsonStr)) {
                    jsonStr = response.body().string();//.string()方法调用两次会报错，所以做判断
                }
                okResponseBean = new ResponseBean();
                if (StringUtil.isEmpty(jsonStr)) {
                    okResponseBean = new ResponseBean();
                    okResponseBean.setStatus(204 + "");
                    okResponseBean.setInfo(TApplication.context.getString(R.string.exception_net_work_time_out_message));
                    HandlerUtil.runOnUI(new Runnable() {//切换到主线程
                        @Override
                        public void run() {
                            mHandler.onFail(okResponseBean);
                        }
                    });
                } else {
                    descResult(jsonStr, okResponseBean, TApplication.getSecretKey());
                }

                HandlerUtil.runOnUI(new Runnable() {//切换到主线程中
                    @Override
                    public void run() {
                        if ("200".equals(okResponseBean.getStatus())){
                            mHandler.onSuccess(okResponseBean);
                        }else {
                            mHandler.onFail(okResponseBean);
                        }
                    }
                });
            }
        };
    }

    ;

    @Override
    public void get(String url, IBinaryResponseHandler callback) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("Accept-Language", Locale.getDefault().toString())
                .header("Connection", "Keep-Alive");
        if (TApplication.iSOUTNET == 0) {
            builder = builder.addHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");
        }
        if (!TextUtils.isEmpty(TApplication.getToken())) {
            builder = builder.addHeader("bwoil-token", TApplication.getToken());
        }
        Request request = builder.build();
        call = mOkHttpClient.newCall(request);
        call.enqueue(getC2bCallback(callback));
    }

    @Override
    public void get(String url, Map params, IResponseHandler callback) throws Exception {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("Accept-Language", Locale.getDefault().toString())
                .header("Connection", "Keep-Alive");

        if (TApplication.iSOUTNET == 0) {
            builder = builder.addHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");
        }
        if (!TextUtils.isEmpty(TApplication.getToken())) {
            builder = builder.addHeader("bwoil-token", TApplication.getToken());
        }
        Request request = builder.get().build();
        call = mOkHttpClient.newCall(request);
        call.enqueue(getC2bCallback(callback));
    }

    @Override
    public void post(String url, IResponseHandler callback) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("Accept-Language", Locale.getDefault().toString())
                .header("Connection", "Keep-Alive");

        if (TApplication.iSOUTNET == 0) {
            builder = builder.addHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");
        }

        if (!TextUtils.isEmpty(TApplication.getToken())) {
            builder = builder.addHeader("bwoil-token", TApplication.getToken());
        }
        Request request = builder.post(null).build();
        call = mOkHttpClient.newCall(request);
        call.enqueue(getC2bCallback(callback));
    }

    @Override
    public void post(String url, AsyncRequestParams params, IResponseHandler callback) throws Exception {
        post(url, params, true, callback);
    }

    @Override
    public void post(String url, AsyncRequestParams params, boolean isEncrypt, IResponseHandler callback) throws Exception {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("Accept-Language", Locale.getDefault().toString())
                .header("encrypt", String.valueOf(isEncrypt))
                .header("Connection", "Keep-Alive");

        if (TApplication.iSOUTNET == 0) {
            builder = builder.addHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");
        }

        Request request = builder.post(okJsonRequestPram2(params.getUrlParams())).build();
        call = mOkHttpClient.newCall(request);
        call.enqueue(getC2bCallback(callback));

    }

    @Override
    public void OtherMethod(String url, IResponseHandler callback) {
    }

    @Override
    public void cancle(Context context) {
        call.cancel();
    }

    @Override
    public void post(String url, String path, IResponseHandler callback) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("Accept-Language", Locale.getDefault().toString())
                .header("encrypt", String.valueOf(true))
                .header("Content-Type", "multipart/form-data")
                .header("Connection", "Keep-Alive");

        if (TApplication.iSOUTNET == 0) {
            builder = builder.addHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");
        }

        if (!TextUtils.isEmpty(TApplication.getToken())) {
            builder = builder.addHeader("bwoil-token", TApplication.getToken());
        }

        File files = new File(path);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", files.getName(), RequestBody.create(MediaType.parse("image/png"), files))//暂时指定图片
                .build();

        Request request = builder.post(requestBody).build();
        call = mOkHttpClient.newCall(request);
        call.enqueue(getC2bCallback(callback));
    }


    private RequestBody okJsonRequestPram2(Map<String, String> map) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            //追加表单信息
            builder.add(key, map.get(key));
        }
        RequestBody body = builder.build();
        return body;
    }

    private String getPostEncode(Map<String, String> map, boolean isEncrypt) throws Exception {
        //请求的数据字符串进行加密
        if (isEncrypt) {
            return DES3.encode(JSON.toJSONString(map), TApplication.getSecretKey());
        }
        return JSON.toJSONString(map);
    }
}
