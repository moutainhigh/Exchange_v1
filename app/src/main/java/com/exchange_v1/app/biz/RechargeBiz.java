package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.BaseBean;
import com.exchange_v1.app.bean.PrepareRechargeBean;
import com.exchange_v1.app.bean.RechargeBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.HashMap;

/**
 * 充值接口
 */
public class RechargeBiz extends BaseBiz{

    /**
     * 预充值接口
     *
     * @param context
     * @param mhandle
     * @param cardType 0-个人账户，1-公司账户
     *
     */
    public static void prepare(Context context,String amount,String cardType, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("amount",amount);
        params.put("cardType",cardType);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.RECHARGE_REQUEST_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObject(responseBean, PrepareRechargeBean.class);
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

    /**
     * 确认充值接口
     *
     * @param context
     * @param mhandle
     */
    public static void confirm(Context context,String rechargeId, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("rechargeId",rechargeId);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.RECHARGE_CONFIRM_API,
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

    /**
     * 取消充值接口
     *
     * @param context
     * @param mhandle
     */
    public static void cancel(Context context,String rechargeId, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("rechargeId",rechargeId);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.RECHARGE_CANCEL_API,
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

    /**
     * 充值列表
     *
     */
    public static void list(Context context,String page,String limit, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("page",page);
        params.put("limit",limit);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.RECHARGE_LIST_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObjectList(responseBean, RechargeBean.class,"");
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

}
