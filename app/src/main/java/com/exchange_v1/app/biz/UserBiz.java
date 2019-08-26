package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.HashMap;

/**
 * 封装model层的网络请求操作，减轻Activity的负担
 * create by zhuwd
 *
 */
public class UserBiz extends BaseBiz{

    //注册接口
    public static void register(Context context, String mobile, String password, String confromPwd, String code
            , String account,String invite_code, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("try_password",confromPwd);
        params.put("msg_code",code);
        params.put("account",account);
        params.put("invite_code",invite_code);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.REGISTER_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
//                        String token = BaseBean.getToken(responseBean);
//                        UserInfoUtil.setLoginStatus(true);
//                        BaseBean.setResponseObjectList(responseBean, UserInfoBean.class);
//                        TApplication.setToken(token);
//                        responseBean.setObject((responseBean.getArraylist(UserInfoBean.class)).get(0));
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }
}
