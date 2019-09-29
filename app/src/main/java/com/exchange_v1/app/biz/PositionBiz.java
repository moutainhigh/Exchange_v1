package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.BaseBean;
import com.exchange_v1.app.bean.PositionBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.HashMap;

//省市地区
public class PositionBiz extends BaseBiz{

    public static void getPosition(Context context, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.DISTRICT_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObjectList(responseBean,PositionBean.class,"");
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

    /**
     * 设置地区信息
     * @param context
     * @param provinceId 省份ID
     * @param cityId 地区ID
     * @param mhandle
     */
    public static void setPosition(Context context,String provinceId,String cityId, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("provinceId",provinceId);
        params.put("cityId",cityId);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.SET_DISTRICT_API,
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
