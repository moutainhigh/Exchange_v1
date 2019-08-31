package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.BaseBean;
import com.exchange_v1.app.bean.MineUserInfoBean;
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

    /**
     * 短信发送接口
     * @param context
     * @param mobile 手机号
     * @param type 0注册，1找回密码
     * @param mhandle 回调
     */
    public static void sendMSG(Context context, String mobile,String type, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("mobile", mobile);
        params.put("type", type);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.SEND_MSG_API,
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

    //用户登录
    public static void login(Context context, String mobile,String password, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("mobile", mobile);
        params.put("password", password);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.LOGIN_API,
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

    //银行卡四要素验证
    public static void bankValid(Context context, String bankNo,String cardName,String cardNo, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("bankNo", bankNo);
        params.put("cardName", cardName);
        params.put("cardNo", cardNo);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.BANKVALID_API,
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

    //用户激活/缴纳押金
    public static void active(Context context, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.ACTIVE_API,
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
     * 重置密码操作
     *
     */
    public static void resetPwd(Context context, String mobile,String password,String try_password,String msg_code, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("try_password", try_password);
        params.put("msg_code", msg_code);

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.RESET_PWD_API,
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
     * 请求用户信息
     *
     */
    public static void userInfo(Context context, final MyRequestHandle mhandle) {

        HashMap<String, String> params = getPostHeadMap();

        NewsBaseBiz.postRequest(context, "系统正在加载...", true, ServerConfig.USER_INFO_API,
                params, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        BaseBean.setGsonResponseObject(responseBean, MineUserInfoBean.class);
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }
                });
    }

}
