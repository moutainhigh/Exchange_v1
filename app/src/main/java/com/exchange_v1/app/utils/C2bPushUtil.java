package com.exchange_v1.app.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.JpushBiz;
import com.exchange_v1.app.network.RequestHandle;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by zhuwd on 2017/6/21.
 * 推送工具类
 * 避免直接使用sdk方法，统一调用
 */
public class C2bPushUtil {

    public static final int WRITE_EXTERNAL_STORAGE_JPUSH_BIND_CODE = 5;
    public static final int WRITE_EXTERNAL_STORAGE_JPUSH_UNBIND_CODE = 6;

    public static final String ACTION_REGISTRATION_ID = JPushInterface.ACTION_REGISTRATION_ID;
    public static final String EXTRA_CONNECTION_CHANGE = JPushInterface.EXTRA_CONNECTION_CHANGE;
    public static final String ACTION_CONNECTION_CHANGE = JPushInterface.ACTION_CONNECTION_CHANGE;
    public static final String ACTION_NOTIFICATION_RECEIVED = JPushInterface.ACTION_NOTIFICATION_RECEIVED;
    public static final String EXTRA_NOTIFICATION_ID = JPushInterface.EXTRA_NOTIFICATION_ID;
    public static final String EXTRA_NOTIFICATION_TITLE = JPushInterface.EXTRA_NOTIFICATION_TITLE;
    public static final String ACTION_NOTIFICATION_OPENED = JPushInterface.ACTION_NOTIFICATION_OPENED;
    public static final String ACTION_MESSAGE_RECEIVED = JPushInterface.ACTION_MESSAGE_RECEIVED;
    public static final String EXTRA_MESSAGE = JPushInterface.EXTRA_MESSAGE;

    public static final String EXTRA_EXTRA = JPushInterface.EXTRA_EXTRA;
    public static final String EXTRA_ALERT = JPushInterface.EXTRA_ALERT;
    public static final String EXTRA_REGISTRATION_ID = JPushInterface.EXTRA_REGISTRATION_ID;

    public static final String ACTION_RICHPUSH_CALLBACK = JPushInterface.ACTION_RICHPUSH_CALLBACK;

//    /**获取推送标识
//     * @param context 上下文
//     * @return 返回推送ID
//     */
//    public static String getPushID(Context context) {
//        return JPushInterface.getRegistrationID(context);
//    }

    /**
     * 初始化推送
     */
    public static void init(Context context){
        JPushInterface.setDebugMode(false);
        JPushInterface.init(context);
        setDefaultPushNotificationBuilder(context);
    }

    /**
     * 设置默认的极光通知栏样式
     * @param context
     */
    public static void setDefaultPushNotificationBuilder(final Context context) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.statusBarDrawable = R.mipmap.jpush_notification_icon;
        }else {
            builder.statusBarDrawable = R.mipmap.ic_launcher;
        }
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;  // 设置为铃声,震动,呼吸灯都要
        JPushInterface.setDefaultPushNotificationBuilder(builder);


//                CustomPushNotificationBuilder builder = new
//                        CustomPushNotificationBuilder(context
//                        ,R.layout.a_notification,
//                        R.id.icon,
//                        R.id.title,
//                        R.id.text);
//                // 指定定制的 Notification Layout
//                builder.statusBarDrawable = R.drawable.ic_launcher;
//                // 指定最顶层状态栏小图标
//                builder.layoutIconDrawable = R.drawable.ic_launcher;
//                // 指定下拉状态栏时显示的通知图标
//                JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    /**
     * 推送
     * 绑定/解除绑定 与服务器的关系
     * @param activity  绑定的Activity
     * @param bindFla
     * @param userNo
     */
    public static void bindingJPush(final Activity activity, boolean bindFla, String userNo){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(bindFla){
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_JPUSH_BIND_CODE);
            }else{
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_JPUSH_UNBIND_CODE);
            }
        }else{

            if(!TextUtils.isEmpty(userNo)){
                String regId=JPushInterface.getRegistrationID(activity);
                //获取不到regId不进行操作
                if(TextUtils.isEmpty(regId)){
                    return;
                }
                TApplication.jpush_regId=regId;
                final String device_id = Installation.idNoLocation(activity);
                final String alias=TApplication.getMineUserInfo().getId();
                if(bindFla){
                    JPushInterface.setAlias(activity,userNo,new TagAliasCallback(){

                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            if(i ==0) {
                                //成功设置
                                JpushBiz.bindJpush(activity,TApplication.jpush_regId,device_id, alias, new RequestHandle() {
                                    @Override
                                    public void onSuccess(ResponseBean result) {
                                    }

                                    @Override
                                    public void onFail(ResponseBean result) {
                                    }
                                });
                            }
                        }
                    });
                }else{
                    JPushInterface.setAlias(activity, "", new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            if(i==0) {
                                //取消成功
                                JpushBiz.unBindJpush(activity, new RequestHandle() {
                                    @Override
                                    public void onSuccess(ResponseBean result) {

                                    }

                                    @Override
                                    public void onFail(ResponseBean result) {

                                    }
                                });
                            }
                        }
                    });
                }

            }
        }

    }

    public static void onResume(Context context) {
        JPushInterface.onResume(context);
    }

    public static void onPause(Context context) {
        JPushInterface.onPause(context);
    }

}
