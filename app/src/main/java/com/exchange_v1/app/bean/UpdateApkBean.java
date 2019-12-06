package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateApkBean extends BaseBean {

//    {
//        androidUrl：安卓下载地址
//        iosUrl：苹果下载地址
//        androidVersion：安卓版本
//        iosVersion：苹果版本
//    }

    private String androidUrl;
    private String iosUrl;
    private String androidVersion;
    private String iosVersion;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }


}
