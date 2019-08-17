package com.exchange_v1.app.utils;

public class ISDoubleClickUtils {
    private static long lastClickTime;
    private final static int SPACE_TIME = 2000;

    public static void initLastClickTime() {
        lastClickTime = 0;
    }

    //判断是否在2秒内连续点击
    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime >
                SPACE_TIME) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }

    //判断在多少秒内连续点击
    public synchronized static boolean isTimeDoubleClick(int time) {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime >
                time) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }
}