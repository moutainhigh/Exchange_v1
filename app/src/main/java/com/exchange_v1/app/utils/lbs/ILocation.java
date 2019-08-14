//package com.exchange_v1.app.utils.lbs;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//
//
///**
// * Created by zhuwd on 2017/5/4.
// * 地图定位方法接口
// */
//
//public interface ILocation {
//
//    public static final int LOCATION_REQUEST_CODE = 4;
//
//    /** 开始定位 */
//    void start();
//    /** 停止定位 */
//    void stop();
//    /** 设置定位属性 */
//    void initOption();
//    /** 设置定位结果回调 */
//    void setLocationCallback(Context context, LBSlocationListener listener);
//    /** 定位回调接口 */
//    interface LBSlocationListener{
//
//        void onReceiveLocation(boolean isLocOK, int status, BDLocation location);
//
//    }
//
//    void setonRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,
//                                       Context context, ILocation.LBSlocationListener mLocListener);
//
//
//}
