package com.exchange_v1.app.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.brightoilonline.c2b_phone.bean.FuYouPayBean;
import com.brightoilonline.c2b_phone.bean.WxPayBean;
import com.brightoilonline.c2b_phone.wxapi.Constants;
import com.fuiou.pay.FyPay;
import com.fuiou.pay.activity.RequestOrder;
import com.fuiou.pay.util.AppConfig;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by liangxg on 2016/5/30.
 */
public class PayUtil {
    /**
     * 威富通支付--微信
     */
    public static void wft_wx_pay(Activity context, WxPayBean wxPayBean) {
        if (wxPayBean != null) {
            // wxPay(bean);
            RequestMsg msg = new RequestMsg();
            //msg.setMchId(wxPayBean.getMch_id());
            //msg.setOutTradeNo(wxPayBean.getOut_trade_no());
            msg.setAppId(Constants.APP_ID);
            msg.setTokenId(wxPayBean.getToken_id());
            msg.setTradeType(MainApplication.WX_APP_TYPE);
            //msg.setSign(wxPayBean.getSign());
            PayPlugin.unifiedAppPay(context, msg);
        }
    }

    /**
     * 富友支付
     *
     * @param context
     * @param payBean
     */
    public static void fuyou_pay(Activity context, FuYouPayBean payBean) {
        FyPay.init(context);
        RequestOrder requestOrder = new RequestOrder(context);
        if (!TextUtils.isEmpty(payBean.getMCHNTCD())) {
            AppConfig.setData(context, AppConfig.MCHNT_CD, payBean.getMCHNTCD());
        }
        if (!TextUtils.isEmpty(payBean.getAMT())) {
            AppConfig.setData(context, AppConfig.MCHNT_AMT, payBean.getAMT());
        }
        if (!TextUtils.isEmpty(payBean.getBACKURL())) {
            AppConfig.setData(context, AppConfig.MCHNT_BACK_URL, payBean.getBACKURL());
        }
        if (!TextUtils.isEmpty(payBean.getBANKCARD())) {
            AppConfig.setData(context, AppConfig.MCHNT_BANK_NUMBER, payBean.getBANKCARD().toString().trim());
        }
        if (!TextUtils.isEmpty(payBean.getMCHNTORDERID())) {
            AppConfig.setData(context, AppConfig.MCHNT_ORDER_ID, payBean.getMCHNTORDERID());
        }
        if (!TextUtils.isEmpty(payBean.getIDTYPE())) {
            AppConfig.setData(context, AppConfig.MCHNT_USER_IDCARD_TYPE, payBean.getIDTYPE().toString());
        }
        if (!TextUtils.isEmpty(payBean.getUSERID())) {
            AppConfig.setData(context, AppConfig.MCHNT_USER_ID, payBean.getUSERID().toString());
        }
        if (!TextUtils.isEmpty(payBean.getIDNO())) {
            AppConfig.setData(context, AppConfig.MCHNT_USER_IDNU, payBean.getIDNO().toString());
        }
        if (!TextUtils.isEmpty(payBean.getNAME())) {
            AppConfig.setData(context, AppConfig.MCHNT_USER_NAME, payBean.getNAME().toString());
        }
        if (!TextUtils.isEmpty(payBean.getSIGN())) {
            AppConfig.setData(context, AppConfig.MCHNT_SING_KEY, payBean.getSIGN().toString());
        }
        AppConfig.setData(context, AppConfig.MCHNT_SDK_SIGNTP, "MD5");
        AppConfig.setData(context, AppConfig.MCHNT_SDK_TYPE, "02");
        AppConfig.setData(context, AppConfig.MCHNT_SDK_VERSION, "2.0");
        requestOrder.Request();
    }

    /**
     * 微信支付
     */
    public static void wx_pay(Activity context, WxPayBean wxPayBean) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        msgApi.registerApp(wxPayBean.getAppid());
        PayReq request = new PayReq();
        request.appId = wxPayBean.getAppid();
        request.partnerId = wxPayBean.getPartnerid();
        request.prepayId = wxPayBean.getPrepayid();
        request.packageValue = wxPayBean.getPackageFalg();
        request.nonceStr = wxPayBean.getNoncestr();
        request.timeStamp = wxPayBean.getTimestamp();
        request.sign = wxPayBean.getSign();
        msgApi.sendReq(request);
    }
}
