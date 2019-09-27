package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.BaseBean;
import com.exchange_v1.app.bean.OrderItemBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.HashMap;

//订单相关
public class OrderBiz extends BaseBiz{

    /**
     * 支付订单记录接口
     *
     * @param context
     * @param search  订单类型, 0-全部,1-已完成,2-已超时
     * @param page 第几页
     * @param limit 每页查询多少数据
     * @param mhandle
     */
    public static void getOrderList(Context context,String processMsg, String search, String page, String limit, MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("search",search);
        params.put("page",page);
        params.put("limit",limit);

        NewsBaseBiz.postRequest(context, processMsg, true, ServerConfig.ORDER_LIST_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObjectList(responseBean, OrderItemBean.class,"");
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

    /**
     * 抢单
     *
     */
    public static void GrabOrder(Context context, String orderId, MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("orderId",orderId);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.ORDER_GRAB_API,
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

}
