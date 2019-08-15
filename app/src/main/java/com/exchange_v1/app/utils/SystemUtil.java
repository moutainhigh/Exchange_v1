package com.exchange_v1.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;


import com.exchange_v1.app.base.TApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 应用工具类》
 *
 * @author 刘艺谋
 * @Description
 * @date 2013-7-19
 */
public class SystemUtil {
    //小米系统
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    //vivo系统
    private static final String KEY_VIVO_ROM = "ro.vivo.rom";
    private static final String KEY_VIVO_VERSION = "ro.vivo.os.version";
    private static final String KEY_VIVO_NAME = "ro.vivo.os.name";

    /**
     * 获取当前系统版本号
     *
     * @return
     * @version 1.0
     * @createTime 2013-10-21,下午3:45:06
     * @updateTime 2013-10-21,下午3:45:06
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static int getCurrentVersionCode() {
        int versionCode = 1;
        // 获取packagemanager的实例
        PackageManager packageManager = TApplication.context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(TApplication.context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 1;
        }
        return versionCode;
    }

    /**
     * 获取当前系统版本名称
     *
     * @return
     * @version 1.0
     * @createTime 2013-10-21,下午3:45:17
     * @updateTime 2013-10-21,下午3:45:17
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getCurrentVersionName() {
        String versionName = "";
        // 获取packagemanager的实例
        PackageManager packageManager = TApplication.context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(TApplication.context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            versionName = "1.0.0";
        }
        return versionName;
    }

    /**
     * 获取本机的Mac地址
     *
     * @return 本机mac
     * @version 1.0
     * @createTime 2013-11-13,上午11:58:43
     * @updateTime 2013-11-13,上午11:58:43
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getPhoneMac() {
        WifiManager wifi = (WifiManager) TApplication.context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        return mac;
    }

    /**
     * 判断系统语言是否中文
     *
     * @return
     * @version 1.0
     * @createTime 2014年9月29日, 下午3:09:00
     * @updateTime 2014年9月29日, 下午3:09:00
     * @createAuthor WangYuWen
     * @updateAuthor WangYuWen
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static boolean IsSystemLanguage() {
        if (TApplication.context.getResources().getConfiguration().locale.getCountry().equals("CN") || TApplication.context.getResources().getConfiguration().locale.getCountry().equals("TW")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取系统语言
     *
     * @return 只返回英文和中文
     * @version 1.0
     * @createTime 2014年9月29日, 下午3:09:00
     * @updateTime 2014年9月29日, 下午3:09:00
     * @createAuthor WangYuWen
     * @updateAuthor WangYuWen
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getSystemLanguage() {
        if (TApplication.context.getResources().getConfiguration().locale.getCountry().equals("CN") || TApplication.context.getResources().getConfiguration().locale.getCountry().equals("TW")) {
            return "zh";
        } else {
            return "en";
        }
    }

    /**
     * 获取友盟的渠道号
     *
     * @param c
     * @return
     */
    public static String getUmengChannel(Context c) {
        String channel = "";
        try {
            channel = c.getPackageManager().getApplicationInfo(c.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("UMENG_CHANNEL");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 是否小米系统
     *
     * @return
     */
    public static boolean isMiSystem() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (properties.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || properties.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || properties.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否vivo系统
     *
     * @return
     */
    public static boolean isVivoSystem() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (properties.getProperty(KEY_VIVO_ROM, null) != null
                    || properties.getProperty(KEY_VIVO_NAME, null) != null
                    || properties.getProperty(KEY_VIVO_VERSION, null) != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取本机的ip地址
     *
     * @return
     */
    public static String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String getSystemTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取设备的经纬度
     *
     * @return
     */
    public static String getLocation() {
//        if (TApplication.bdLocation != null) {
//            return TApplication.bdLocation.getLatitude() + "," + TApplication.bdLocation.getLongitude();
//        }
        return "";
    }

    /**
     * 获取设备的经度
     *
     * @return
     */
    public static String getLongitude() {
//        if (TApplication.bdLocation != null) {
//            return TApplication.bdLocation.getLongitude() + "";
//        }
        return "";
    }

    /**
     * 获取设备的纬度
     *
     * @return
     */
    public static String getLatitude() {
//        if (TApplication.bdLocation != null) {
//            return TApplication.bdLocation.getLatitude() + "";
//        }
        return "";
    }
}
