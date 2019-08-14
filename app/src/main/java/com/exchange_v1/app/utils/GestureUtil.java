package com.exchange_v1.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.brightoilonline.c2b_phone.config.TApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 使用手势密码相关工具类
 * @Description:
 * @author: zhuwd
 * @date: 2017/12/25
 * @Copyright: Copyright (c) 2017 深圳光汇云油电商有限公司.
 */
public class GestureUtil {

    public static void putString(String key, String value){
        SharedPreferences sp1 = TApplication.context.getSharedPreferences("config_preferences_rich", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp1.edit();
        edit.putString(key,value);
        edit.commit();
    }

    public static String getString(String key, String defaultValue){
        SharedPreferences sp1 = TApplication.context.getSharedPreferences("config_preferences_rich", Context.MODE_PRIVATE);
        return sp1.getString(key, defaultValue);
    }

    public static void putBoolean(String key, Boolean value){
        SharedPreferences sp1 = TApplication.context.getSharedPreferences("config_preferences_rich", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp1.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    public static boolean getBoolean(String key, Boolean defaultValue){
        SharedPreferences sp1 = TApplication.context.getSharedPreferences("config_preferences_rich", Context.MODE_PRIVATE);
        return sp1.getBoolean(key, defaultValue);
    }

    /**
     * 保存手势密码
     * @param account
     * @param password
     * @param isSave
     */
    public static void saveCode(String account, String password, boolean isSave){
        //2018-8-23 银联过检 本地不再保存手势密码
//        putString(account, password);
        putBoolean(account+"_able", isSave);
    }

    /**
     * 清空手势密码
     * @param account
     */
    public static void clearCode(String account){
        //2018-8-23 银联过检 本地不再保存手势密码
//        putString(account, "");
        putBoolean(account+"_able", false);
    }

    public static String getPwd(String account){
        return getString(account, "");
    }

    public static boolean getSetting(String account){
        return getBoolean(account+"_able", false);
    }

    public static void putIsAutoString(String key, boolean value){
        SharedPreferences sp1 = TApplication.context.getSharedPreferences("config_preferences_rich", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp1.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    public static boolean getIsAutoString(String key, boolean defaultValue){
        SharedPreferences sp1 = TApplication.context.getSharedPreferences("config_preferences_rich", Context.MODE_PRIVATE);
        return sp1.getBoolean(key, defaultValue);
    }

    /**
     * 手势密码加密，先base64,后md5
     * @param code
     * @return
     */
    public static String encode(String code){
        String base64 = Base64.encode(code.getBytes());
        return md5(base64);
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
