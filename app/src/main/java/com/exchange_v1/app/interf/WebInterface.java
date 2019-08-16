package com.exchange_v1.app.interf;

import android.webkit.JavascriptInterface;

/**
 * Created by liangxg on 2016/8/22.
 */
public interface WebInterface {

    public void btnActionBuy(String string);

    /**
     * 扫描加油
     */
    public void callScanRefulCamera();

    /**
     * 打开订单列表
     */
    void callOrderList();
    /**
     *   登陆的方法
     */
    @JavascriptInterface
    public void toLogin();
}
