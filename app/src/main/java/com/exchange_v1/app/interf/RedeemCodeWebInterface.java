package com.exchange_v1.app.interf;

import android.webkit.JavascriptInterface;


/**
 * Created by liangxg on 2016/12/13.
 */
public interface RedeemCodeWebInterface {

    /**
     * 跳转储油卡 一年，两年,三年，天天加油卡
     */
    public void goCard_B(String jsonString);


    /**
     * 跳转大众储油卡,消费卡
     */
    @JavascriptInterface
    public void goCard_A(String product_id);

    /**
     *   加油卡充值
     */
    @JavascriptInterface
    public void goRefulCardRecharge_C();


    /**
     *   储油卡列表
     */
    @JavascriptInterface
    public void goOilStorageCard_D();


    /**
     *   加油首页
     */
    @JavascriptInterface
    public void goMain_E();



    /**
     *   光汇加油/购买加油卡
     */
    @JavascriptInterface
    public void goGuangHuiRefue_F();

    /**
     *   登陆的方法
     */
    @JavascriptInterface
    public void toLogin();

}
