package com.exchange_v1.app.interf;

import android.webkit.JavascriptInterface;

/**
 * Created by liangxg on 2016/12/13.
 */
public interface SpecialH5WebInterface {
    /**
     * AdDeatilsActivity 并过来的方法
     */
    public void btnActionBuy(String string);

    /**
     * 分享
     */
    public void share(String num);
    /**
     * 扫码加油
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
    /**
     *   跳转到加息券祥情页面的方法
     */
    @JavascriptInterface
    public void goInterestInfo(String cpns_id);

    /**
     * 跳转大众储油卡,消费卡
     */
    @JavascriptInterface
    public void goCard_A(String product_id);

    /**
     * 跳转到支付选择页
     */
    @JavascriptInterface
    public void callPaySelect(String orderId);

    /**
     *   跳转到积分油箱
     */
    @JavascriptInterface
    public void goOilTank();
    /**
     *   H5跳转储油列表
     */
    @JavascriptInterface
    public void goOilStorageCardList();

    /**
     * 3.2.5新增方法
     * 通过参数去判断行为
     *
     */
    @JavascriptInterface
    public void goCommonAction(String json);

}
