package com.exchange_v1.app.network;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.exchange_v1.R;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.BaseBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.executor.ProcessDialogUtil;
import com.exchange_v1.app.utils.DES3;
import com.exchange_v1.app.utils.LogUtil;
import com.exchange_v1.app.utils.UserInfoUtil;
import com.exchange_v1.app.utils.http.AsyncRequestParams;
import com.exchange_v1.app.utils.http.IResponseHandler;
import com.exchange_v1.app.utils.http.MyHttpClient;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by zhuwd
 */
public class NewsBaseBiz {


    /**
     * post请求
     *
     * @param context
     * @param processMsg
     * @param cancelable
     * @param url
     * @param params
     * @param mhandle
     */
    public static void postRequest(Context context,
                                   String processMsg, boolean cancelable, String url,
                                   HashMap<String, String> params, MyRequestHandle mhandle) {
        postRequest(context, processMsg, cancelable, url, params, false, mhandle);
    }

    public static void postRequest(final Context context,
                                   final String processMsg, boolean cancelable, final String url,
                                   final HashMap<String, String> params, boolean isEncrypt, final MyRequestHandle mhandle) {
        if (mhandle == null) {
            return;
        }
        ResponseBean cacheData = mhandle.getRequestCache();
        if (cacheData != null) {
            mhandle.onRequestCache(cacheData);
        }
        final IResponseHandler handler = new IResponseHandler() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                LogUtil.out("get_URL:" + url);
                if (null != params) {
                    LogUtil.out("get_params:" + params.toString());
                }
                LogUtil.out("get_Json:" + responseBean.getObject());

                if (!TextUtils.isEmpty(processMsg)) {
                    ProcessDialogUtil.dismissDialog();
                }
                if (isSuccess(responseBean.getStatus())) {
                    mhandle.onSuccess(responseBean);
                } else {
                    mhandle.onFail(responseBean);
                    /**
                     * 处理token失效的问题 重新登录
                     */
                    if ("90000".equals(responseBean.getStatus())) {
                        TApplication.clearToken();
                        String userNo = UserInfoUtil.getLoginBean().getAccount();
                        TApplication.setUserInfoBean(null);
                        UserInfoUtil.logout();
                        Intent intent = new Intent();
                        intent.setAction(BroadcastFilters.ACTION_LGOIN_LOGOUT);
                    }
                }
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                mhandle.onFail(responseBean);
                if (!TextUtils.isEmpty(processMsg)) {
                    ProcessDialogUtil.dismissDialog();
                }
            }

            @Override
            public void onCancel() {
                mhandle.onCancel();
            }

            @Override
            public void onSaveCache(ResponseBean result) {

            }

            @Override
            public ResponseBean getCache() {
                return null;
            }

        };

        if (!TextUtils.isEmpty(processMsg)) {
            ProcessDialogUtil.showDialog(context, processMsg, cancelable);
            /** loading 结束取消网络请求 */
            ProcessDialogUtil.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //handler.sendCancelMessage();
                }
            });
        }

        if (params == null) {
            MyHttpClient.post(url, handler);
        } else {
            try {
                MyHttpClient.post(url, params, handler);
            } catch (Exception e) {
                ResponseBean responseBean = new ResponseBean();
                responseBean.setStatus(TApplication.context
                        .getString(R.string.exception_local_json_code));
                responseBean.setInfo(TApplication.context
                        .getString(R.string.exception_local_json_message));
                mhandle.onFail(responseBean);
                e.printStackTrace();
            }

        }
    }

    /**
     * post请求,此请求为软件版本更新用,其它地方不能用此请求
     *
     * @param context
     * @param processMsg
     * @param cancelable
     * @param url
     * @param params
     * @param mhandle
     */
    public static void post2Request(final Context context,
                                    final String processMsg, boolean cancelable, final String url,
                                    final HashMap<String, String> params, final MyRequestHandle mhandle) {
        if (mhandle == null) {
            return;
        }
        ResponseBean cacheData = mhandle.getRequestCache();
        if (cacheData != null) {
            mhandle.onRequestCache(cacheData);
        }
        if (null != params) {
            LogUtil.out((params.toString()));
        }
        final IResponseHandler handler = new IResponseHandler() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (!TextUtils.isEmpty(processMsg)) {
                    ProcessDialogUtil.dismissDialog();
                }

                if (isSuccess(responseBean.getStatus())) {
                    mhandle.onSuccess(responseBean);
                } else {
                    mhandle.onFail(responseBean);

                }
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                // if (null != responseBody) {
                // responseBean.setInfo(new String(responseBody));
                // }
                mhandle.onFail(responseBean);
                if (!TextUtils.isEmpty(processMsg)) {
                    ProcessDialogUtil.dismissDialog();
                }


            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                mhandle.onCancel();
            }

            @Override
            public void onSaveCache(ResponseBean result) {

            }

            @Override
            public ResponseBean getCache() {
                return null;
            }

        };

        if (!TextUtils.isEmpty(processMsg)) {
            ProcessDialogUtil.showDialog(context, processMsg, cancelable);
            /** loading 结束取消网络请求 */
            ProcessDialogUtil.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //handler.sendCancelMessage();
                }
            });
        }

        if (params == null) {
            MyHttpClient.post(url, handler);
        } else {
            try {
                //				MyHttpClient.post(context, url,getParams(params),
                //						handler);
                MyHttpClient.post(url, BaseBiz.map2Requestparams(params), handler);
            } catch (Exception e) {
                ResponseBean responseBean = new ResponseBean();
                responseBean.setStatus(TApplication.context
                        .getString(R.string.exception_local_json_code));
                responseBean.setInfo(TApplication.context
                        .getString(R.string.exception_local_json_message));

                // if (null != responseBody) {
                // responseBean.setInfo(new String(responseBody));
                // }
                mhandle.onFail(responseBean);
                e.printStackTrace();
            }

        }
    }

    /**
     * 上传图片
     *
     * @param context
     * @param processMsg
     * @param cancelable
     * @param url
     * @param uploadFilePath 被上传的文件路径
     * @param mhandle
     */
    public static void postFile(final Context context,
                                final String processMsg, boolean cancelable, final String url,
                                String uploadFilePath, final MyRequestHandle mhandle) {
        if (mhandle == null) {
            return;
        }
        //		AsyncRequestParams params = null;
        //		if (uploadFilePath != null){
        //			File files = new File(uploadFilePath);
        //			params = new AsyncRequestParams();
        //			try {
        //				params.put("file",files);
        //			}catch (Exception e){
        //
        //			}
        //		}

        ResponseBean cacheData = mhandle.getRequestCache();
        if (cacheData != null) {
            mhandle.onRequestCache(cacheData);
        }
        if (null != uploadFilePath) {
            LogUtil.out((uploadFilePath.toString()));
        }
        final IResponseHandler handler = new IResponseHandler() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                //				descResult(responseStr, responseBean);
                if (!TextUtils.isEmpty(processMsg)) {
                    ProcessDialogUtil.dismissDialog();
                }

                if (isSuccess(responseBean.getStatus())) {
                    mhandle.onSuccess(responseBean);
                } else {
                    mhandle.onFail(responseBean);
                }
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                mhandle.onFail(responseBean);
                if (!TextUtils.isEmpty(processMsg)) {
                    ProcessDialogUtil.dismissDialog();
                }
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                mhandle.onCancel();
            }

            @Override
            public void onSaveCache(ResponseBean result) {

            }

            @Override
            public ResponseBean getCache() {
                return null;
            }

        };

        if (!TextUtils.isEmpty(processMsg)) {
            ProcessDialogUtil.showDialog(context, processMsg, cancelable);
            /** loading 结束取消网络请求 */
            ProcessDialogUtil.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //handler.sendCancelMessage();
                }
            });
        }


        try {
            MyHttpClient.post(url, uploadFilePath, handler);
        } catch (Exception e) {
            ResponseBean responseBean = new ResponseBean();
            responseBean.setStatus(TApplication.context
                    .getString(R.string.exception_local_json_code));
            responseBean.setInfo(TApplication.context
                    .getString(R.string.exception_local_json_message));
            // if (null != responseBody) {
            // responseBean.setInfo(new String(responseBody));
            // }
            mhandle.onFail(responseBean);
            e.printStackTrace();
        }

    }


    /**
     * 5.6.6重构版本与 5.7.4 有冲突
     *
     * @param result
     * @param responseBean
     * @param signValue
     */
    public static void descResult(String result, ResponseBean responseBean, String signValue) {
        JSONObject operationJson = null;
//        String decode;
//        try {
//            decode = DES3.decode(result, signValue);// 请求返回的数据字符串进行解密
//            // 返回的字符串不能进行json解析，故添加这样的处理
//            int index = decode.indexOf("{");
//            if (index >= 0) {
//                decode = decode.substring(decode.indexOf("{"), decode.length());
//            }
//            Logger.d("decode = "+decode);
        try {
            operationJson = new JSONObject(result);
        } catch (JSONException e) {
            responseBean.setStatus(TApplication.context
                    .getString(R.string.exception_local_json_code));
            responseBean.setInfo(TApplication.context
                    .getString(R.string.exception_local_json_message));
            e.printStackTrace();
        } catch (Exception e) {
            responseBean.setStatus(TApplication.context
                    .getString(R.string.exception_net_work_md5_code));
            responseBean.setInfo(TApplication.context
                    .getString(R.string.exception_get_phone_md5_message));
            e.printStackTrace();
        }
        if (null != operationJson) {
            responseBean.setStatus(operationJson.optString("code"));
            responseBean.setInfo(operationJson.optString("msg"));
            responseBean.setObject(operationJson.optString("data"));
        }
    }

    public static boolean isSuccess(String code) {
        if (ServerConfig.RESPONSE_STATUS_SUCCESS.equals(code)) {
            return true;
        } else {
            return false;
        }
    }

    private static StringEntity toJsonRequestPram(HashMap<String, String> params)
            throws Exception {

        String encode = DES3.encode(JSON.toJSONString(params));// 请求的数据字符串进行MD5加密
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("params", encode);
        String paramStr = getParams(hashMap);
        StringEntity entity = new StringEntity(paramStr, "UTF-8");
        return entity;
    }


    private static AsyncRequestParams toJsonRequestPram2(HashMap<String, String> params)
            throws Exception {

        String encode = DES3.encode(JSON.toJSONString(params));// 请求的数据字符串进行MD5加密
        HashMap<String, String> hashMap = new HashMap<String, String>();
        AsyncRequestParams AsyncRequestParams = new AsyncRequestParams();
        //StringEntity entity = new StringEntity(paramStr, "UTF-8");
        AsyncRequestParams.put("params", encode);
        //String paramStr = getParams(hashMap);

        return AsyncRequestParams;
    }


    private static String getParams(HashMap<String, String> attribute) {
        Set<String> keys = attribute.keySet(); // 获取所有参数名
        Iterator<String> iterator = keys.iterator(); // 将所有参数名进行跌代
        StringBuffer params = new StringBuffer();
        // 取出所有参数进行构造
        while (iterator.hasNext()) {
            try {
                String key = iterator.next();
                @SuppressWarnings("deprecation")
                String param = key + "="
                        + URLEncoder.encode(attribute.get(key)) + "&";
                params.append(param);
            } catch (Exception e) {

            }

        }
        // 返回构造结果
        return params.toString().substring(0, params.toString().length() - 1);
    }

}
