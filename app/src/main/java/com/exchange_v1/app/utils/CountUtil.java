package com.exchange_v1.app.utils;

import android.content.Context;
import android.text.TextUtils;

import com.exchange_v1.app.base.TApplication;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * 统计工具
 * Created by liangxg on 2016/8/1.
 */
public class CountUtil {

    public static boolean isDebug = false;



    /**
     * 统计访问支付页面
     */
    public static void viewPayView(Context context, String pageName) {
        if (!isDebug) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("pageName", "pageName");
            MobclickAgent.onEvent(context, pageName, map);
        }
    }

    /**
     * 统计访问支付页面
     */
    public static void doPayType(Context context, String type) {
        if (!isDebug) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(type, type);
            MobclickAgent.onEvent(context, CountIdUtil.payType, map);
        }
    }

    /**
     * 统计事件有参
     *
     * @param context
     * @param registerId 事件Id
     * @param data       Map数据集
     * @param count      次数
     */
    public static void sendDataForStatistics(Context context, String registerId, HashMap<String, String> data, int count) {
        if (!isDebug) {
            MobclickAgent.onEvent(context, registerId, data);
        }
    }

    /**
     * 统计事件有参
     *
     * @param context
     * @param registerId 事件Id
     * @param data       单条数据
     * @param count      次数
     */
    public static void sendDataForStatistics(Context context, String registerId, String data, int count) {
        if (!isDebug) {
            if (!TextUtils.isEmpty(data)) {
                HashMap<String, String> map = new HashMap<>();
                map.put(data, data);
                sendDataForStatistics(context, registerId, map, count);
            } else {
                MobclickAgent.onEvent(context, registerId);
            }
        }
    }

    /**
     * 统计事件无参
     *
     * @param context
     * @param registerId 事件Id
     * @param count      次数
     */
    public static void sendDataForStatistics(Context context, String registerId, int count) {
        if (!isDebug) {
            MobclickAgent.onEvent(context, registerId);
        }
    }

    /**
     * 统计事件有参
     *
     * @param context
     * @param registerId 事件Id
     * @param data       单条数据
     */
    public static void sendDataForStatistics(Context context, String registerId, String data) {
        sendDataForStatistics(context, registerId, data, 1);
    }

    /**
     * 统计事件有参
     *
     * @param context
     * @param registerId 事件Id
     * @param data       Map数据集
     */
    public static void sendDataForStatistics(Context context, String registerId, HashMap<String, String> data) {
        sendDataForStatistics(context, registerId, data, 1);
    }

    /**
     * 统计事件无参
     *
     * @param context
     * @param registerId 事件Id
     */
    public static void sendDataForStatistics(Context context, String registerId) {
        sendDataForStatistics(context, registerId, 1);
    }

    /**
     * 获取友盟渠道名称
     *
     * @param context
     * @return
     */
    public static String getChannelName(Context context) {
        return TextUtils.isEmpty(AnalyticsConfig.getChannel(context)) ? "BrightOil" : AnalyticsConfig.getChannel(context);
    }

    //定义c2b统计事件
//    start:启动
//    wake_up:唤醒
//    register_suc:注册成功
//    login_suc:登录成功
//    login_fail:登录失败
//    login_auto:自动登录
//    generate_order:下单
//    pay_suc:支付成功
//    pay_fail:支付失败
//    log_out:注销/退出
    public static String START = "start";
    public static String WAKE_UP = "wake_up";
    public static String REGISTER_SUC = "register_suc";
    public static String LOGIN_SUC = "login_suc";
    public static String LOGIN_FAIL = "login_fail";
    public static String LOGIN_AUTO = "login_auto";
    public static String GENERATE_ORDER = "generate_order";
    public static String PAY_SUC = "pay_suc";
    public static String PAY_FAIL = "pay_fail";
    public static String LOG_OUT = "log_out";

    public static String phoneNum  = "";
    public static String member_id = "";

    /**
     * 获取phone和member_id的参数
     */
    public static void getRecordParmas() {
//        if (TApplication.userAccountBean!=null){
//            phoneNum = TApplication.userAccountBean.getMoblie();
//        }
        if (TApplication.userInfoBean!=null){
            member_id = TApplication.userInfoBean.getMember_id();
        }
    }

    /**
     * C2B后台统计方法(启动/唤醒)
     * true 启动  false唤醒
     *
     * @param context     context
     * @param isStart     是否启动
     * @param duration    留存时长
     */
    public static void sendC2bDurating(Context context, Boolean isStart, String duration) {
        if (isStart){//启动
            sendC2bRecordData(context,START,duration,null,null,null);
        }else {//唤醒
            sendC2bRecordData(context,WAKE_UP,duration,null,null,null);
        }
    }

    /**
     * C2B后台统计方法(支付成功/支付失败)
     * true 支付成功  false支付失败
     *
     * @param context     context
     * @param paystatic   是否支付成功
     * @param orderID       订单号
     *
     */
    public static void sendC2bPay(Context context, Boolean paystatic, String orderID) {
        getRecordParmas();
        if (paystatic){//支付成功
            sendC2bRecordDataPaySucess(context,PAY_SUC,null,member_id,phoneNum,orderID);
        }
    }
    public static void sendC2bPay2(Context context, Boolean paystatic, String orderID, String exception_message) {
        getRecordParmas();
        if (!paystatic){//支付失败
            sendC2bRecordDataPayFail(context,PAY_FAIL,null,member_id,phoneNum,orderID,exception_message);
        }
    }
    /**
     * C2B后台统计方法(支付成功/支付失败)
     * true 支付成功  false支付失败
     *
     * @param context     context
     * @param paystatic   是否支付成功
     * @param member_id     用户编号
     * @param phone         手机号
     * @param orderID       订单号
     *
     */
    public static void sendC2bPay(Context context, Boolean paystatic, String member_id, String phone, String orderID) {
        if (paystatic){//支付成功
            sendC2bRecordData(context,PAY_SUC,null,member_id,phone,orderID);
        }else {
            sendC2bRecordData(context,PAY_FAIL,null,member_id,phone,orderID);
        }
    }

    /**
     * C2B后台统计方法(下单)
     *
     * @param context     context
     * @param orderID       订单号
     *
     */
    public static void sendC2bCraeatOrder(Context context, String orderID) {
        getRecordParmas();
        sendC2bRecordData(context,GENERATE_ORDER,null,member_id,phoneNum,orderID);
    }

    /**
     * C2B后台统计方法(下单)
     *
     * @param context     context
     * @param member_id     用户编号
     * @param phone         手机号
     * @param orderID       订单号
     *
     */
    public static void sendC2bCraeatOrder(Context context, String member_id, String phone, String orderID) {
        sendC2bRecordData(context,GENERATE_ORDER,null,member_id,phone,orderID);
    }

    /**
     * C2B后台统计方法(登录成功)
     *
     * @param context   context
     *
     */
    public static void sendC2bLoginSuccess(Context context) {
//        sendC2bRecordData(context,LOGIN_SUC,null,member_id,phone,null,null,null,null,null);
        sendC2bRecordData(context,LOGIN_SUC);
    }

    /**
     * C2B后台统计方法(登录成功)
     *
     * @param context   context
     * @param member_id 用户编号
     * @param phone     手机号
     *
     */
    public static void sendC2bLoginSuccess(Context context, String member_id, String phone) {
        sendC2bRecordData(context,LOGIN_SUC,null,member_id,phone,null);
    }

    /**
     * C2B后台统计方法(登录失败)
     * @param context
     */
    public static void sendC2bLoginFail(Context context, String exception_message) {
        sendC2bRecordDataForFail(context,LOGIN_FAIL,null,null,null,null,exception_message);
    }

    /**
     * C2B后台统计方法(注册成功)
     *
     * @param context   context
     */
    public static void sendC2bRegisterSuccess(Context context) {
//        sendC2bRecordData(context,REGISTER_SUC,null,member_id,phone,null,null,null,null,null);
        sendC2bRecordData(context,REGISTER_SUC);
    }

    /**
     * C2B后台统计方法(注册成功)
     *
     * @param context   context
     * @param member_id 用户编号
     * @param phone     电话号码
     */
    public static void sendC2bRegisterSuccess(Context context, String member_id, String phone) {

//        sendC2bRecordData(context,REGISTER_SUC);
        sendC2bRecordData(context,REGISTER_SUC,null,member_id,phone,null);
    }

    /**
     * C2B后台统计方法(自动登录)
     *
     * @param context   context
     */
    public static void sendC2bAutoLogin(Context context) {
//        sendC2bRecordData(context,LOGIN_AUTO,null,member_id,phone,null,null,null,null,null);
        sendC2bRecordData(context,LOGIN_AUTO);
    }

    /**
     * C2B后台统计方法(自动登录)
     *
     * @param context   context
     * @param member_id 用户编号
     * @param phone     电话号码
     */
    public static void sendC2bAutoLogin(Context context, String member_id, String phone) {
//        sendC2bRecordData(context,LOGIN_AUTO);
        sendC2bRecordData(context,LOGIN_AUTO,null,member_id,phone,null);
    }


    /**
     * C2B后台统计方法
     * 默认获取  用户编号 和 电话号码参数
     *
     * @param context   context
     * @param type      事件类型
     */
    public static void sendC2bRecordData(Context context, String type) {
        getRecordParmas();
        sendC2bRecordData(context,type,null,member_id,phoneNum,null);
    }


    /**
     * C2B后台统计方法(注销)
     *
     * @param context   context
     */
    public static void sendC2bLoginOut(Context context) {
        sendC2bRecordData(context,LOG_OUT);
//        sendC2bRecordData(context,LOG_OUT,null,member_id,phone,null,null,null,null,null);
    }

    /**
     * C2B后台统计方法(注销)
     *
     * @param context   context
     * @param member_id 用户编号
     * @param phone     电话号码
     */
    public static void sendC2bLoginOut(Context context, String member_id, String phone) {
//        sendC2bRecordData(context,LOG_OUT);
        sendC2bRecordData(context,LOG_OUT,null,member_id,phone,null);
    }

    /**
     * 9 个参数的方法名
     * @param context     context
     * @param event_type    事件类型
     * @param duration      留存时间
     * @param member_id   用户编号
     * @param phone         手机号
     * @param orderID       订单号
     *
     */
    public static void sendC2bRecordData(Context context, String event_type, String duration,
                                         String member_id, String phone, String orderID) {

        String equipment_id = Installation.idNoLocation(context);//设备id
        String from = SystemUtil.getUmengChannel(context);//渠道号
        String longitude = SystemUtil.getLongitude();//经度
        String latitude = SystemUtil.getLatitude();//纬度
        LogUtil.i("CU-->  equipment_id:"+equipment_id);
        LogUtil.i("CU-->  from:"+from);
        LogUtil.i("CU-->  longitude:"+longitude);
        LogUtil.i("CU-->  latitude:"+latitude);

        HashMap<String, String> data = new HashMap<>();
        data.put("equipment_id",equipment_id);
        data.put("from",from);
        data.put("longitude",longitude);
        data.put("latitude",latitude);
        //新增默认操作
        data.put("remark","");//备注，默认为空的
        data.put("terminal_type","APP");//终端类型 APP
        int  visioncode = 1;// 本地版本号
        String visioncodeS=visioncode+"";
        String ver_info=visioncodeS.substring(0,1)+"."+visioncodeS.substring(1,2)+"."+visioncodeS.substring(2,visioncodeS.length());
        String version_info="Android("+ver_info+")";
        data.put("version_info",version_info);
        if (!StringUtil.isEmpty(duration)){
            data.put("duration",duration);
            LogUtil.i("CU-->  duration:"+duration);
        }
        if (!StringUtil.isEmpty(event_type)){
            data.put("event_type",event_type);
            LogUtil.i("CU-->  event_type:"+event_type);

            if (event_type.equals(PAY_FAIL)||event_type.equals(LOGIN_FAIL)){
                data.put("log_type","异常操作"); /*异常操作*/
            }else {
                data.put("log_type","正常操作"); /*正常操作*/
            }

            //增加支付失败或成功的信息
            if (event_type.equals(PAY_SUC)){
                data.put("exception_message","");
            }else if (event_type.equals(PAY_FAIL)){
                data.put("exception_message","支付失败");
            }

        }
        if (!StringUtil.isEmpty(member_id)){
            data.put("member_id",member_id);
            LogUtil.i("CU-->  member_id:"+member_id);
        }
        if (!StringUtil.isEmpty(phone)){
            data.put("phone",phone);
            LogUtil.i("CU-->  phone:"+phone);
        }
        if (!StringUtil.isEmpty(orderID)){
            data.put("order_id",orderID);
            LogUtil.i("CU-->  order_id:"+orderID);
        }

    }

    /**
     * 9 个参数的方法名
     * @param context     context
     * @param event_type    事件类型
     * @param duration      留存时间
     * @param member_id   用户编号
     * @param phone         手机号
     * @param orderID       订单号
     *
     */
    public static void sendC2bRecordDataPaySucess(Context context, String event_type, String duration,
                                                  String member_id, String phone, String orderID) {

        String equipment_id = Installation.idNoLocation(context);//设备id
        String from = SystemUtil.getUmengChannel(context);//渠道号
        String longitude = SystemUtil.getLongitude();//经度
        String latitude = SystemUtil.getLatitude();//纬度
        LogUtil.i("CU-->  equipment_id:"+equipment_id);
        LogUtil.i("CU-->  from:"+from);
        LogUtil.i("CU-->  longitude:"+longitude);
        LogUtil.i("CU-->  latitude:"+latitude);

        HashMap<String, String> data = new HashMap<>();
        data.put("equipment_id",equipment_id);
        data.put("from",from);
        data.put("longitude",longitude);
        data.put("latitude",latitude);
        //新增默认操作
        data.put("remark","");//备注，默认为空的
        data.put("terminal_type","APP");//终端类型 APP
        int  visioncode = 1;// 本地版本号
        String visioncodeS=visioncode+"";
        String ver_info=visioncodeS.substring(0,1)+"."+visioncodeS.substring(1,2)+"."+visioncodeS.substring(2,visioncodeS.length());
        String version_info="Android("+ver_info+")";
        data.put("version_info",version_info);
        if (!StringUtil.isEmpty(duration)){
            data.put("duration",duration);
            LogUtil.i("CU-->  duration:"+duration);
        }
        if (!StringUtil.isEmpty(event_type)){
            data.put("event_type",event_type);
            LogUtil.i("CU-->  event_type:"+event_type);

            if (event_type.equals(PAY_FAIL)||event_type.equals(LOGIN_FAIL)){
                data.put("log_type","异常操作"); /*异常操作*/
            }else {
                data.put("log_type","正常操作"); /*正常操作*/
            }

            //增加支付失败或成功的信息
            if (event_type.equals(PAY_SUC)){
                data.put("exception_message","");
            }else if (event_type.equals(PAY_FAIL)){
                data.put("exception_message","支付失败");
            }

        }
        if (!StringUtil.isEmpty(member_id)){
            data.put("member_id",member_id);
            LogUtil.i("CU-->  member_id:"+member_id);
        }
        if (!StringUtil.isEmpty(phone)){
            data.put("phone",phone);
            LogUtil.i("CU-->  phone:"+phone);
        }
        if (!StringUtil.isEmpty(orderID)){
            data.put("order_id",orderID);
            LogUtil.i("CU-->  order_id:"+orderID);
        }
    }

    public static void sendC2bRecordDataForFail(Context context, String event_type, String duration,
                                                String member_id, String phone, String orderID, String exception_message) {

        String equipment_id = Installation.idNoLocation(context);//设备id
        String from = SystemUtil.getUmengChannel(context);//渠道号
        String longitude = SystemUtil.getLongitude();//经度
        String latitude = SystemUtil.getLatitude();//纬度

        LogUtil.i("CU-->  equipment_id:"+equipment_id);
        LogUtil.i("CU-->  from:"+from);
        LogUtil.i("CU-->  longitude:"+longitude);
        LogUtil.i("CU-->  latitude:"+latitude);

        HashMap<String, String> data = new HashMap<>();
        data.put("equipment_id",equipment_id);
        data.put("from",from);
        data.put("longitude",longitude);
        data.put("latitude",latitude);
        //新增默认操作
        data.put("remark","");//备注，默认为空的
        data.put("terminal_type","APP");//终端类型 APP
        int  visioncode = 1;// 本地版本号
        String visioncodeS=visioncode+"";
        String ver_info=visioncodeS.substring(0,1)+"."+visioncodeS.substring(1,2)+"."+visioncodeS.substring(2,visioncodeS.length());
        data.put("version_info","Android("+ver_info+")");
        if (!StringUtil.isEmpty(duration)){
            data.put("duration",duration);
            LogUtil.i("CU-->  duration:"+duration);
        }
        if (!StringUtil.isEmpty(event_type)){
            data.put("event_type",event_type);
            LogUtil.i("CU-->  event_type:"+event_type);

            if (event_type.equals(PAY_FAIL)||event_type.equals(LOGIN_FAIL)){
                data.put("log_type","异常操作"); /*异常操作*/
            }else {
                data.put("log_type","正常操作"); /*正常操作*/
            }
                data.put("exception_message",exception_message);
        }
        if (!StringUtil.isEmpty(member_id)){
            data.put("member_id",member_id);
            LogUtil.i("CU-->  member_id:"+member_id);
        }
        if (!StringUtil.isEmpty(phone)){
            data.put("phone",phone);
            LogUtil.i("CU-->  phone:"+phone);
        }
        if (!StringUtil.isEmpty(orderID)){
            data.put("order_id",orderID);
            LogUtil.i("CU-->  order_id:"+orderID);
        }
    }
    public static void sendC2bRecordDataPayFail(Context context, String event_type, String duration,
                                                String member_id, String phone, String orderID, String exception_message) {

        String equipment_id = Installation.idNoLocation(context);//设备id
        String from = SystemUtil.getUmengChannel(context);//渠道号
        String longitude = SystemUtil.getLongitude();//经度
        String latitude = SystemUtil.getLatitude();//纬度

        LogUtil.i("CU-->  equipment_id:"+equipment_id);
        LogUtil.i("CU-->  from:"+from);
        LogUtil.i("CU-->  longitude:"+longitude);
        LogUtil.i("CU-->  latitude:"+latitude);

        HashMap<String, String> data = new HashMap<>();
        data.put("equipment_id",equipment_id);
        data.put("from",from);
        data.put("longitude",longitude);
        data.put("latitude",latitude);
        //新增默认操作
        data.put("remark","");//备注，默认为空的
        data.put("terminal_type","APP");//终端类型 APP
        int  visioncode = 1;// 本地版本号
        String visioncodeS=visioncode+"";
        String ver_info=visioncodeS.substring(0,1)+"."+visioncodeS.substring(1,2)+"."+visioncodeS.substring(2,visioncodeS.length());
        data.put("version_info","Android("+ver_info+")");
        if (!StringUtil.isEmpty(duration)){
            data.put("duration",duration);
            LogUtil.i("CU-->  duration:"+duration);
        }
        if (!StringUtil.isEmpty(event_type)){
            data.put("event_type",event_type);
            LogUtil.i("CU-->  event_type:"+event_type);

            if (event_type.equals(PAY_FAIL)||event_type.equals(LOGIN_FAIL)){
                data.put("log_type","异常操作"); /*异常操作*/
            }else {
                data.put("log_type","正常操作"); /*正常操作*/
            }
            data.put("exception_message",exception_message);
        }
        if (!StringUtil.isEmpty(member_id)){
            data.put("member_id",member_id);
            LogUtil.i("CU-->  member_id:"+member_id);
        }
        if (!StringUtil.isEmpty(phone)){
            data.put("phone",phone);
            LogUtil.i("CU-->  phone:"+phone);
        }
        if (!StringUtil.isEmpty(orderID)){
            data.put("order_id",orderID);
            LogUtil.i("CU-->  order_id:"+orderID);
        }
    }
    /**点击网络请求事件
     *
     *  "event_id": "3",
     "event_name": "点击汇油宝",
     "event_time": "1463210320",
     "event_num": "3",
     "event_content": "100元",
     "event_remark": "加油宝"
     */
//    public static void clickProductNewCount(Context context, String event_id, String event_name, String event_num, String event_content, String event_remark) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        ProductClickNewCountBean databean = new ProductClickNewCountBean();
//          map.put("event_id",event_id);
//        map.put("event_name",event_name);
//        map.put("event_num",event_num);
//        map.put("event_content",event_content);
//        map.put("event_remark","");
//        databean.setParam_json(map);
//
//        Map<String, String> map2 = new HashMap<String, String>();
//                   map2.put("event_type",databean.getEvent_type());
//                   // map2.put("param_json",databean.getParam_json());
//
//
//                String json_str = "["+new JSONObject(map2).toString()+"]";
//              json_str=json_str.replace("\\","");
//                 json_str= json_str.replace("\"{","{");
//        json_str= json_str.replace("}\"","}");
//        sendPructCount(context,json_str);
//
//
//    }


//    /**发送网络请求产品统计事件*/
//   public   static   void  sendPructCount(Context context, String json_str){
//               SystemBiz.postC2bNewCount(context, json_str, new MyRequestHandle() {
//                   @Override
//                   public void onSuccess(ResponseBean result) {
//
//                   }
//
//                   @Override
//                   public void onFail(ResponseBean result) {
//
//                   }
//
//                   @Override
//                   public void onCancel() {
//
//                   }
//
//                   @Override
//                   public ResponseBean getRequestCache() {
//                       return null;
//                   }
//
//                   @Override
//                   public void onRequestCache(ResponseBean result) {
//
//                   }
//               });
//
//   }
}
