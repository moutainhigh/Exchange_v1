package com.exchange_v1.app.utils;

// 配置密匙　　　　　http://lbsyun.baidu.com/apiconsole/key
// 获取签名SHA1　　keytool -list -v -keystore xxx.keystore
// 打包的配置                3B:EC:BC:ED:BC:9B:BF:26:B8:49:8E:44:24:71:46:F5:60:2D:3F:AC;com.brightoilonline.c2b_phone
// demo.sdk下载　http://lbsyun.baidu.com/sdk/download?selected=location
// 相关类查看　　　　http://wiki.lbsyun.baidu.com/cms/androidloc/doc/v6_0_3/doc/index.html

/**
 * 百度定位工具类
 * 
 * @Description
 * @author qw
 * @date 2015-9-13
 */
public class BDLocationUtil {
//	/** 定位类 */
//	private LocationClient mLocationClient;
//	/** 回调接口 */
//	private MLocListener mLocListener;
//	/** 网络、GPS、离线定位成功，且拥有省市数据 isLocOK=true */
//	public static final int LOCATION_OK = 10000;
//	/** 定位成功,但不拥有省市数据 isLocOK=false */
//	public static final int LOCATION_NO_CITY = 1;
//	/** 定位失败,服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因 */
//	public static final int LOCATION_SERVER_ERROR = 2;
//	/** 定位失败,网络不通导致定位失败，请检查网络是否通畅 */
//	public static final int LOCATION_NET_ERROR = 3;
//	/** 定位失败,无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机 */
//	public static final int LOCATION_OTHER_ERROR = 4;
//
//	/**
//	 * @param context 上下文
//	 * @param mLocListener 回调接口,默认回调一次后停止
//	 */
//	public BDLocationUtil(Context context, MLocListener mLocListener) {
//		mLocationClient = new LocationClient(context);
//		this.mLocListener = mLocListener;
//		mLocationClient.registerLocationListener(defualtLocListener);
//		LocationClientOption option = new LocationClientOption();
//		initLocation(option);
//		mLocationClient.setLocOption(option);
//	}
//
//	/** 初始化定位属性 */
//	private void initLocation(LocationClientOption option) {
//		// 定位模式: LocationMode.Hight_Accuracy-高精度　LocationMode.Battery_Saving－低功耗　LocationMode.Device_Sensors－仅设备
//		option.setLocationMode(LocationMode.Hight_Accuracy);
//		// 定位结果坐标系: gcj02－ 国家测绘局标准　bd09ll－百度经纬度标准　bd09－百度墨卡托标准
////		option.setCoorType("gcj02");
//		option.setCoorType("bd09ll");
//		// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//		option.setScanSpan(2000);
//		// 可选，设置是否需要地址信息，默认不需要
//		option.setIsNeedAddress(true);
//		// 可选，默认false,设置是否使用gps
//		option.setOpenGps(true);
//		// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//		option.setLocationNotify(true);
//		// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//		option.setIgnoreKillProcess(true);
//		// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
//		option.setEnableSimulateGps(false);
//		// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//		option.setIsNeedLocationDescribe(false);
//		// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//		option.setIsNeedLocationPoiList(false);
//	}
//
//	/** 定位监听回调 */
//	public BDLocationListener defualtLocListener = new BDLocationListener() {
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			stop();
//			if (mLocListener == null) {
//				// 没有回调接口
//			} else if (location == null) {
//				// 没有数据返回,基本不可能发生
//				mLocListener.onReceiveLocation(false, LOCATION_OTHER_ERROR, null);
//			} else if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation
//					|| location.getLocType() == BDLocation.TypeOffLineLocation) {
//				// 定位正常
//				if (location.getProvince() != null && location.getCity() != null) {
//					mLocListener.onReceiveLocation(true, LOCATION_OK, location);
//				} else {
//					mLocListener.onReceiveLocation(false, LOCATION_NO_CITY, location);
//				}
//			} else if (location.getLocType() == BDLocation.TypeServerError) {
//				mLocListener.onReceiveLocation(false, LOCATION_SERVER_ERROR, location);
//			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//				mLocListener.onReceiveLocation(false, LOCATION_NET_ERROR, location);
//			} else {
//				mLocListener.onReceiveLocation(false, LOCATION_OTHER_ERROR, location);
//			}
//		}
//
//		@Override
//		public void onConnectHotSpotMessage(String s, int i) {
//
//		}
//	};
//
//	/** 开启定位 */
//	public void start() {
//		mLocationClient.start();
//	}
//
//	/** 停止定位 */
//	public void stop() {
//		mLocationClient.stop();
//	}
//
//	/**
//	 * 定位的回调接口
//	 *
//	 * @Description
//	 * @author 綦巍
//	 * @date 2015-9-14
//	 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
//	 */
//	public interface MLocListener {
//		/**
//		 * @param isLocOK 是否定位成功
//		 * @param status 状态码
//		 * @param location
//		 *            定位结果　纬度-location.getLatitude()　经度－location.getLongitude()　　　　　　　　　　国家－location.getCountry()　省份－location.getProvince()　城市－location
//		 *            .getCity()　　　　　　　城市编号－location.getCityCode()
//		 */
//		public void onReceiveLocation(boolean isLocOK, int status, BDLocation location);
//	}
//
//	public static final int LOCATION_REQUEST_CODE = 4;
//
//	public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,
//												  Context context,MLocListener mLocListener){
//		if (requestCode == LOCATION_REQUEST_CODE) {
//			boolean isPassPermissions = true;
//			for (int i = 0; i < grantResults.length; i++) {
//				if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//					isPassPermissions = false;
//				};
//			}
//			if (isPassPermissions) {
//				// Permission Granted
//				new BDLocationUtil(context, mLocListener).start();
//			}else{
//				Toast.makeText(context, "定位功能受限无法使用", Toast.LENGTH_SHORT).show();
//			}
//		}
//	}
//
//	public static LatLng ChangeLatLng(LatLng latLng){
//		CoordinateConverter converter  = new CoordinateConverter();
//		converter.from(CoordinateConverter.CoordType.GPS);
//		// sourceLatLng待转换坐标
//		converter.coord(latLng);
//		LatLng result = converter.convert();
//		return result;
//	}

}