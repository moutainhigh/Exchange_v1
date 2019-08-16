package com.exchange_v1.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.exchange_v1.app.base.TApplication;


/**
 * Created by Administrator on 2015/9/14.
 */
public class Installation {
    private static String sID = null;

    public static String id(Context context) {
        /**
         * 允许授权
         */
        if (sID == null) {
            sID = getDevId(context);
        }
//        if (TApplication.bdLocation != null) {
//            return sID + "|" + TApplication.bdLocation.getLatitude() + "," + TApplication.bdLocation.getLongitude();
//        }
        return sID;

    }

    /**
     * 获取android设备唯一的ID
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    private static String getDevId(Context context) {
        String id;
        id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(id)) {
            // Pseudo-Unique ID, 这个在任何Android手机中都有效
            //可以通过取出ROM版本、制造商、CPU型号、以及其他硬件信息来实现这一点
            id = "35" + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10; //13 digits
        }

        return id;
    }


    /**
     * 设备号带没有经纬度
     *
     * @param context
     * @return
     */
    public synchronized static String idNoLocation(Context context) {
        /**
         * 允许授权
         */
        if (sID == null) {
            sID = getDevId(context);
        }
        return sID;
    }


    // 获取IMEI码
    public static String getIMIEStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
        return deviceId;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }
}
