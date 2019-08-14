package com.exchange_v1.app.utils.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.exchange_v1.app.utils.http.HttpsUtils;

import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

public class OkHttpGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient mHttpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(new HostnameVerifier() {//在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                        boolean qaHost = hv.verify("demoapi.brightoilonline.com", session);//预生产
                        boolean releaseHost = hv.verify("api.brightoilonline.com", session);//生产环境
                        return qaHost || releaseHost;
                    }
                })
                .build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mHttpClient));
    }
}
