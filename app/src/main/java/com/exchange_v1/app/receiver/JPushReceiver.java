package com.exchange_v1.app.receiver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.exchange_v1.R;
import com.exchange_v1.app.base.MainActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.event.JPushRegIdEvent;
import com.exchange_v1.app.utils.ActivityUtils;
import com.exchange_v1.app.utils.C2bPushUtil;
import com.exchange_v1.app.utils.DateUtil;
import com.exchange_v1.app.utils.DialogUtil;
import com.exchange_v1.app.utils.HandlerUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.Util;
import com.exchange_v1.app.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;


/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    private static final int NOTIFICATION_FLAG = 1;
    private int badgeCount = 0;//记录图标的角标数量
    public static final String BADGECOUNT = "badgeCount";

    @Override
    public void onReceive(final Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //			Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        }

        if (C2bPushUtil.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(C2bPushUtil.EXTRA_REGISTRATION_ID);
            TApplication.jpush_regId = regId;
            //获取regId成功
            //ToastUtil.showToast(context,TApplication.jpush_regId);
            EventBus.getDefault().post(new JPushRegIdEvent());
            //			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (C2bPushUtil.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //			Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(C2bPushUtil.EXTRA_MESSAGE));
            acceptCustomMessage(context, bundle, -1);

        } else if (C2bPushUtil.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            final int notifactionId = bundle.getInt(C2bPushUtil.EXTRA_NOTIFICATION_ID);
            String extra_data = bundle.getString(C2bPushUtil.EXTRA_MESSAGE);

            String msg = bundle.getString(C2bPushUtil.EXTRA_ALERT);
            String title = bundle.getString(C2bPushUtil.EXTRA_NOTIFICATION_TITLE);
            badgeCount = SpUtil.getSPValue(context, BADGECOUNT, 0, BADGECOUNT, 0);
            badgeCount++;
            SpUtil.putSPValue(context, BADGECOUNT, 0, BADGECOUNT, badgeCount);
            if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
//                String title2 = context.getResources().getString(R.string.app_name);
//                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//                getNotification(context, title2, content, notifactionId);
//                ShortcutBadger.applyNotification(context, mNotification, badgeCount);
            } else {
                ShortcutBadger.applyCount(context, badgeCount);
            }
            HandlerUtil.runOnUI(new Runnable() {
                @Override
                public void run() {
                    processCustomMessage(context, bundle, notifactionId);
                }
            }, 500);


        } else if (C2bPushUtil.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            /**
             * 跳转指定的页面
             */
            jumpActivityByType(context, bundle);

            //            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //        	//打开自定义的Activity
            //        	Intent i = new Intent(context, MainActivity.class);
            //        	i.putExtras(bundle);
            //        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            //        	context.startActivity(i);

        } else if (C2bPushUtil.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            //			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(C2bPushUtil.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (C2bPushUtil.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(C2bPushUtil.EXTRA_CONNECTION_CHANGE, false);
            //			Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else if ("com.brightoilonline.c2b_phone.REMOVE_BADGE".equals(intent.getAction())) {
            //接收到移除所有badge
            removeBadgeNum(context);
        } else {
            //			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 获取notification
     *
     * @param context
     * @param title          标题
     * @param message        内容
     * @param notificationId notification的id
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getNotification(Context context, String title, String message, int notificationId) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        mManager.cancel(notificationId);

        mBuilder = new Notification.Builder(context);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        mBuilder.setSmallIcon(R.mipmap.add_img);
        mNotification = mBuilder.build();
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;

        //添加点击事件
        Intent intent = new Intent();
        intent.setAction(JPushInterface.ACTION_NOTIFICATION_OPENED);
        intent.addCategory("com.rg.fragmentdemo");
        Bundle bundle = new Bundle();
        bundle.putInt(JPushInterface.EXTRA_NOTIFICATION_ID, notificationId);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        mManager.notify(notificationId, mNotification);
    }

    NotificationManager mManager;
    Notification mNotification;
    Notification.Builder mBuilder;

    private void removeBadgeNum(Context context) {
        if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
//            ShortcutBadger.applyNotification(context, mNotification, 0);
        } else {
            ShortcutBadger.applyCount(context, 0);
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(C2bPushUtil.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(C2bPushUtil.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(C2bPushUtil.EXTRA_EXTRA)) {
                if (bundle.getString(C2bPushUtil.EXTRA_EXTRA).isEmpty()) {
                    //					Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(C2bPushUtil.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    //					Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    /**
     * 处理通知信息
     *
     * @param context
     * @param title
     * @param msg
     */
    private void processMessage(Context context, final String title, String msg, int notifactionId) {

        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (!Util.isApplicationBroughtToBackground(context)) {
            Activity activity = ActivityUtils.getCurrentActivity();
            //当前应用开着
            if (activity != null) {
                if (!TextUtils.isEmpty(title)) {
                    //好的掉
                    NotificationManager notiManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notiManager.cancel(notifactionId);
                    DialogUtil.showMessageDgBtnNoBg(activity
                            , title
                            , msg
                            , "我知道了"
                            , new CustomDialog.OnClickListener() {
                                @Override
                                public void onClick(CustomDialog dialog, int id, Object object) {
                                    dialog.dismiss();
                                }
                            });

                }
            }
        } else {
            SpUtil.setSPJPushTime(DateUtil.getDate("yyyy-MM-dd"));
            SpUtil.setSPJPushMsg(msg);
            SpUtil.setSPJPushTitle(title);
        }

    }

    //弹出框是否已经弹出显示
    private boolean isDialogShow = false;


    //send msg to MainActivity
    private void processCustomMessage(final Context context, final Bundle bundle, int notifactionId) {

        String msg = bundle.getString(C2bPushUtil.EXTRA_ALERT);
        String extras = bundle.getString(C2bPushUtil.EXTRA_EXTRA);
        String title = null;
        String type_bn = null;
        String product_name = null;
        String product_id = null;
        String period = null;
        String is_show_h5 = null;
        String url = null;

        com.alibaba.fastjson.JSONObject json = JSON.parseObject(extras);
        if (json.containsKey("title")) {
            title = json.getString("title");
        }
        if (json.containsKey("url")) {
            url = json.getString("url");
        }
        if (json.containsKey("type_bn")) {
            type_bn = json.getString("type_bn");
        }
        if (json.containsKey("product_name")) {
            product_name = json.getString("product_name");
        }
        if (json.containsKey("product_id")) {
            product_id = json.getString("product_id");
        }
        if (json.containsKey("period")) {
            period = json.getString("period");
        }

        //判断是否是跳到微众联名卡首页
        if (json.containsKey("is_show_h5")) {
            is_show_h5 = json.getString("is_show_h5");
        }

        if (TextUtils.isEmpty(extras) || TextUtils.isEmpty(msg)) {
            return;
        }

        if (!Util.isApplicationBroughtToBackground(context) && !isDialogShow) {
            Activity activity = ActivityUtils.getCurrentActivity();
            if (activity != null) {
                //				if (activity instanceof AddLoadingActivity) {
                //					delayedMsg(context, bundle);
                //					return;
                //				}
                //				if (activity instanceof LoadingActivity) {
                //					delayedMsg(context, bundle);
                //					return;
                //				}
                //创建弹出框
            }
        }
        //		else {
        //			SpUtil.setSPJPushTime(DateUtil.getDate("yyyy-MM-dd"));
        //			SpUtil.setSPJPushMsg(msg);
        //			SpUtil.setSPJPushTitle(title);
        //		}
    }

    //自定义消息
    private void acceptCustomMessage(final Context context, final Bundle bundle, int notifactionId) {

        String msg = bundle.getString(C2bPushUtil.EXTRA_MESSAGE);
        String extras = bundle.getString(C2bPushUtil.EXTRA_EXTRA);
        String title = null;
        String type_bn = null;
        String product_name = null;
        String product_id = null;
        String period = null;
        String is_show_h5 = null;
        String url = null;

        com.alibaba.fastjson.JSONObject json = JSON.parseObject(extras);
        if (json.containsKey("title")) {
            title = json.getString("title");
        }
        if (json.containsKey("url")) {
            url = json.getString("url");
        }
        if (json.containsKey("type_bn")) {
            type_bn = json.getString("type_bn");
        }
        if (json.containsKey("product_name")) {
            product_name = json.getString("product_name");
        }
        if (json.containsKey("product_id")) {
            product_id = json.getString("product_id");
        }
        if (json.containsKey("period")) {
            period = json.getString("period");
        }

        //判断是否是跳到微众联名卡首页
        if (json.containsKey("is_show_h5")) {
            is_show_h5 = json.getString("is_show_h5");
        }


        if (TextUtils.isEmpty(extras) || TextUtils.isEmpty(msg)) {
            return;
        }

        if (!Util.isApplicationBroughtToBackground(context) && !isDialogShow) {
            Activity activity = ActivityUtils.getCurrentActivity();
            if (activity != null) {
                //				if (activity instanceof AddLoadingActivity) {
                //					delayedMsg(context, bundle);
                //					return;
                //				}
                //				if (activity instanceof LoadingActivity) {
                //					delayedMsg(context, bundle);
                //					return;
                //				}
            }
        } else {
            SpUtil.setSPJPushTime(DateUtil.getDate("yyyy-MM-dd"));
            SpUtil.setSPJPushMsg(msg);
            SpUtil.setSPJPushTitle(title);
        }
    }


    private void delayedMsg(final Context context, final Bundle bundle) {
        HandlerUtil.runOnUI(new Runnable() {
            @Override
            public void run() {
                processCustomMessage(context, bundle, -1);
            }
        }, 500);
    }



    /**
     * 根据类型跳转指定的产品页面
     *
     * @param context
     */
    public void jumpActivityByType(Context context, Bundle bundle) {
        String extras = bundle.getString(C2bPushUtil.EXTRA_EXTRA);
        String msg = bundle.getString(C2bPushUtil.EXTRA_ALERT);
        com.alibaba.fastjson.JSONObject json = JSON.parseObject(extras);
        String type_bn = json.getString("type_bn");
        String product_name = json.getString("product_name");
        String product_id = json.getString("product_id");
        String period = json.getString("period");
        String is_show_h5 = json.getString("is_show_h5");
        String url = json.getString("url");
        //数据传递
        Bundle productInfoBundle = new Bundle();
        productInfoBundle.putString("product_id", product_id);
        productInfoBundle.putString("product_name", product_name);
        productInfoBundle.putString("type_bn", type_bn);
        productInfoBundle.putString("period", period);
        productInfoBundle.putString("is_show_h5", is_show_h5);
        productInfoBundle.putString("url", url);
        productInfoBundle.putBoolean("isNotification", true);

        String lastActivityName = ActivityUtils.lastActivityName(context);
        //没有activity活动的时候 跳转首页
        if (TextUtils.isEmpty(lastActivityName)) {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtras(productInfoBundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }
    }


}
