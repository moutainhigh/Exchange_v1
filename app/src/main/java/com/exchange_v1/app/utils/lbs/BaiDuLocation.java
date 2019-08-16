package com.exchange_v1.app.utils.lbs;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.exchange_v1.app.base.TApplication;


/**
 * Created by zhuwd on 2017/5/4.
 * 百度地图定位具体实现类
 */

public class BaiDuLocation implements ILocation {

    /** 百度定位类 */
    private static LocationClient mLocationClient;

    private static BaiDuLocation mInstance;

    /** 网络、GPS、离线定位成功，且拥有省市数据 isLocOK=true */
    public static final int LOCATION_OK = 10000;
    /** 定位成功,但不拥有省市数据 isLocOK=false */
    public static final int LOCATION_NO_CITY = 1;
    /** 定位失败,服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因 */
    public static final int LOCATION_SERVER_ERROR = 2;
    /** 定位失败,网络不通导致定位失败，请检查网络是否通畅 */
    public static final int LOCATION_NET_ERROR = 3;
    /** 定位失败,无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机 */
    public static final int LOCATION_OTHER_ERROR = 4;

    private BaiDuLocation(){
        //1.new 定位类 2，设置定位配置
        if (mLocationClient == null){
            mLocationClient = new LocationClient(TApplication.context);
        }
        initOption();
    }

    public static BaiDuLocation getInstance(){
        if (mInstance == null)
        {
            mInstance = new BaiDuLocation();
        }
        return mInstance;
    }

    @Override
    public void start() {
        mLocationClient.start();
    }

    @Override
    public void stop() {
        mLocationClient.stop();
    }

    @Override
    public void initOption() {
        LocationClientOption option = new LocationClientOption();
        initLocation(option);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void setLocationCallback(Context context, final LBSlocationListener listener) {
        //注册定位位置监听回调
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                stop();
                if (listener == null) {
                    // 没有回调接口
                } else if (location == null) {
                    // 没有数据返回,基本不可能发生
                    listener.onReceiveLocation(false, LOCATION_OTHER_ERROR, null);
                } else if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation
                        || location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 定位正常
                    if (location.getProvince() != null && location.getCity() != null) {
                        listener.onReceiveLocation(true, LOCATION_OK, location);
                    } else {
                        listener.onReceiveLocation(false, LOCATION_NO_CITY, location);
                    }
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    listener.onReceiveLocation(false, LOCATION_SERVER_ERROR, location);
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    listener.onReceiveLocation(false, LOCATION_NET_ERROR, location);
                } else {
                    listener.onReceiveLocation(false, LOCATION_OTHER_ERROR, location);
                }

            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });
    }


    @Override
    public void setonRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Context context, LBSlocationListener mLocListener) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            boolean isPassPermissions = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isPassPermissions = false;
                };
            }
            if (isPassPermissions) {
                // Permission Granted
                getInstance().setLocationCallback(context, mLocListener);
                getInstance().start();
//                new BDLocationUtil(context, mLocListener).start();
            }else{
                Toast.makeText(context, "定位功能受限无法使用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** 初始化定位属性 */
    private void initLocation(LocationClientOption option) {
        // 定位模式: LocationMode.Hight_Accuracy-高精度　LocationMode.Battery_Saving－低功耗　LocationMode.Device_Sensors－仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 定位结果坐标系: gcj02－ 国家测绘局标准　bd09ll－百度经纬度标准　bd09－百度墨卡托标准
        option.setCoorType("bd09ll");
        // 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(2000);
        // 可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        // 可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(true);
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true);
        // 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);
        // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(false);
        // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(false);
    }




//    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,
//                                                  Context context, ILocation.LBSlocationListener mLocListener){
//        if (requestCode == LOCATION_REQUEST_CODE) {
//            boolean isPassPermissions = true;
//            for (int i = 0; i < grantResults.length; i++) {
//                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                    isPassPermissions = false;
//                };
//            }
//            if (isPassPermissions) {
//                // Permission Granted
//                getInstance().setLocationCallback(context, mLocListener);
//                getInstance().start();
////                new BDLocationUtil(context, mLocListener).start();
//            }else{
//                Toast.makeText(context, "定位功能受限无法使用", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public static LatLng ChangeLatLng(LatLng latLng){
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        converter.coord(latLng);
        LatLng result = converter.convert();
        return result;
    }

}
