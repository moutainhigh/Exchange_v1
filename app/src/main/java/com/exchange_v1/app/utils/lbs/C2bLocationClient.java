//package com.exchange_v1.app.utils.lbs;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//
///**
// * Created by zhuwd on 2017/5/4.
// * 定位服务管理类，默认使用百度定位
// *
// */
//
//public class C2bLocationClient {
//
//    private static C2bLocationClient ourInstance;
//    private ILocation locationClient;
//
//    /** 构造方法 */
//    private C2bLocationClient(){
//        locationClient = BaiDuLocation.getInstance();//使用百度地图API
//    }
//
//    public static C2bLocationClient getInstance() {
//        if(ourInstance == null){
//            ourInstance = new C2bLocationClient();
//        }
//        return ourInstance;
//    }
//
//    private ILocation client() {
//        return getInstance().locationClient;
//    }
//
//    public void start(){
//        client().start();
//    }
//
//    public void stop(){
//        client().stop();
//    }
//
//    public void initOption(){
//        client().initOption();
//    }
//
//    //返回本类对象是为了可以链式调用定位方法
//    public C2bLocationClient setLocationCallback(Context context, ILocation.LBSlocationListener listener){
//        client().setLocationCallback(context,listener);
//        return ourInstance;
//    }
//
//    public void setonRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,
//                                              Context context, ILocation.LBSlocationListener mLocListener){
//        client().setonRequestPermissionsResult(requestCode, permissions, grantResults,context,mLocListener);
//    }
//
//}
