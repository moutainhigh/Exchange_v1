package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.BaseBean;
import com.exchange_v1.app.bean.CashFree;
import com.exchange_v1.app.bean.CashingBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.HashMap;

//提现 请求
public class WithdrawtoBiz extends BaseBiz{

    //佣金提现
    public static void brokerage(Context context, String money,String msg_code,final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();
        params.put("money", money);
        params.put("msg_code", msg_code);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.BROKERAGE_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

    //跑分币提现
    public static void integral(Context context, String money,final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();
        params.put("money", money);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.INTEGRAL_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

    //提现列表
    public static void brokerageList(Context context, String page,String limit,final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();
        params.put("page", page);
        params.put("limit", limit);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.WITHDRAW_PAGE_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObjectList(responseBean, CashingBean.class,"");
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

    //钱包变动记录
    public static void walletHistory(Context context, String page,String limit,final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();
        params.put("page", page);
        params.put("limit", limit);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.WALLETHISTORY_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

    //提现费率接口
    public static void withCharge(Context context,String money,final MyRequestHandle mhandle) {
        HashMap<String, String> params = getPostHeadMap();
        params.put("money",money);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.WITH_CHARGE_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObject(responseBean,CashFree.class);
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }
}