package com.exchange_v1.app.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.exchange_v1.app.R;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.UpdateBean;
import com.exchange_v1.app.bean.UserInfoBean;
import com.exchange_v1.app.navition.NativeAction;
import com.exchange_v1.app.utils.C2bPushUtil;
import com.exchange_v1.app.utils.DES3;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.UserInfoUtil;
import com.exchange_v1.app.utils.imageloader.ImageLoaderUtil;
import com.exchange_v1.app.utils.imageloader.ImageOptions;
import com.exchange_v1.app.utils.lbs.C2bLocationClient;
import com.exchange_v1.app.utils.lbs.ILocation;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


/**
 * 全局公用Application类,单例初始化操作,静态成员储存
 *
 */
public class TApplication extends Application {
    /**
     * 全局上下文，可用于文本、图片、sp数据的资源加载，不可用于视图级别的创建和展示
     */
    public static Context context;
    /**
     * 屏幕的宽
     */
    public static int screenWidth = 0;

    /**
     * 屏幕的高
     */
    public static int screenHight = 0;
    /**
     * 更新属性
     */
    public static UpdateBean updateBean;
    /**
     * 是否已经显示更新对话框
     */
    public static boolean isShowUpdate = false;
    /**
     * 应用版本名称
     */
    public static String VERSION_NAME;
    /**
     * 应用版本号
     */
    public static int VERSION_CODE;
    /**
     * 应用是否在最前
     */
    public static boolean IsOnTop = true;
    /**
     * 应用是否在前台进程
     */
    public static boolean IS_FRONT = true;
    /**
     * 当前登录用户的详细信息
     */
    public static UserInfoBean userInfoBean;
    /**
     * 指纹
     */
    private static String appMd5;
    /**
     * token
     */
    public static String token = null;
    /**
     * secretKey
     */
    public static String secretKey = null;

    public static int APP_VERSION = 1;

    /**
     * 是否请求外网，0表示外网，1表示内网
     */
    public static int iSOUTNET = 1;
//    /**
//     * 图片加载工具
//     */
//    public static FinalBitmap finalbitmap;
//    public static BitmapDisplayConfig bitmapDisplayConfig;
    //当前优惠卷是否有新增
    private static boolean isCpnChange=false;
    //当前油箱抵扣是否有新增
    private static boolean isOilTankChange=false;

    //我的图标是否需要显示红点
    private  static boolean isShowMineRed=false;

    public static boolean needSafe = false;


    /**
     * 极光推送的唯一标识
     */
    public static String jpush_regId;

    /**
     * app留存时长计数值
     */
    public static int c2b_count = 0;

    //是否点击启动页广告
    public static boolean isClickLoading = false;
    //设备Id
    public static String deviceId;
    public static C2bLocationClient c2bLocationClient;
    //保存的账号
    public static String account = null;
    //当前登录用户信息
    public static MineUserInfoBean mineInfo = null;

    //省份选择的位置
    public static int proPos ;
    //城市选择的位置
    public static int cityPos ;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化MultiDex
        MultiDex.install(this);
        context = getApplicationContext();

        init_VmPolicy();

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHight = getResources().getDisplayMetrics().heightPixels;

//        initBaiduLBS();

//        initImageLoader(this);
//        BitmapUtil.init(context);
//
//        initNetBitmap();
//
////        ApplicationConfig.isOutNet = (iSOUTNET == 0) ? 0 : 1;
        C2bPushUtil.init(context);
//
//        init_UM_Share();
//
//        deviceId = Installation.idNoLocation(this);

        // 开启自动埋点统计，为保证所有页面都能准确统计，建议在Application中调用。
        // 第三个参数：autoTrackWebview：
        // 如果设置为true，则自动track所有webview；如果设置为false，则不自动track webview，
        // 如需对webview进行统计，需要对特定webview调用trackWebView() 即可。
        // 重要：如果有对webview设置过webchromeclient，则需要调用trackWebView() 接口将WebChromeClient对象传入，
        // 否则开发者自定义的回调无法收到。
//        StatService.autoTrace(this, true, true);
    }

    public static boolean isCpnChange() {
        return isCpnChange;
    }

    public static void setCpnChange(boolean cpnChange) {
        isCpnChange = cpnChange;
    }

    public static boolean isOilTankChange() {
        return isOilTankChange;
    }

    public static void setOilTankChange(boolean oilTankChange) {
        isOilTankChange = oilTankChange;
    }

    public static boolean isShowMineRed() {
        return isShowMineRed;
    }

    public static void setShowMineRed(boolean showMineRed) {
        isShowMineRed = showMineRed;
    }


    /**
     * 初始化友盟分享
     */
    private void init_UM_Share() {
//        UMShareAPI.get(this);
    }

    /**
     * 网络图片加载
     */
    private void initNetBitmap() {
//        finalbitmap = new FinalBitmap(getApplicationContext());// 网络图片缓存类
//        bitmapDisplayConfig = new BitmapDisplayConfig();// 设置网络图片加载失败的缓存类
//        bitmapDisplayConfig.setLoadfailDrawable(getResources().getDrawable(
//                R.drawable.cloud_storage_added_card));
        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
        // ImageLoader.getInstance().clearMemoryCache();//清除内存缓存
        // ImageLoader.getInstance().clearDiscCache();//清除SD卡缓存
    }

    /**
     * 百度定位初始化
     */
    private void initBaiduLBS() {
        SDKInitializer.initialize(this);

        ILocation.LBSlocationListener locListener = (ILocation.LBSlocationListener) context;
        c2bLocationClient = C2bLocationClient.getInstance().setLocationCallback(context, locListener);
//        startBdLocation();
    }

    public static void startBdLocation() {
        c2bLocationClient.start();
    }

    /**
     * android 7.0相机调用系统相机崩溃的解决方案
     */
    private void init_VmPolicy() {
        //		7.0之后你的app就算有权限，给出一个URI之后手机也认为你没有权限。
        //		不用修改原有代码，在Application的oncreate方法中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }






    public static String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = (String) SpUtil.getSpUtil().getObject(context, "token");
        }
        return token;
    }

    public static String getServerToken() throws Exception {
        return DES3.encode(getToken() + getAppMd5());
    }

    public static void setToken(String token) {
        TApplication.token = token;
        SpUtil.getSpUtil().setObject(context, "token", token);
    }

    public static void setSecretKey(String secretKeyValue) {
        TApplication.secretKey = secretKeyValue;
        SpUtil.getSpUtil().setObject(context, "secretKey", secretKeyValue);
    }

    public static String getSecretKey() {
        if (TextUtils.isEmpty(secretKey)) {
            if (TextUtils.isEmpty((String) SpUtil.getSpUtil().getObject(context, "secretKey"))) {
//                secretKey = new JniUtil().getKey();
                secretKey = "";
            } else {
                secretKey = (String) SpUtil.getSpUtil().getObject(context, "secretKey");
            }
        }
        return secretKey;
    }

    /**
     * 清除token
     */
    public static void clearToken() {
        TApplication.token = null;
        SpUtil.getSpUtil().setObject(context, "token", "");
    }


    /**
     * 获取用户信息
     *
     * @return
     * @version 1.0
     * @createTime 2015年9月14日, 上午10:33:00
     * @updateTime 2015年9月14日, 上午10:33:00
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static UserInfoBean getUserInfoBean() {
        if (null == userInfoBean) {
            userInfoBean = (UserInfoBean) SpUtil.getObject(TApplication.context, "userInfo" + UserInfoUtil.getLoginBean().getAccount());
        }
        return userInfoBean;
    }

    /**
     * 设置用户信息
     *
     * @param userInfoBean
     * @version 1.0
     * @createTime 2015年9月14日, 上午10:33:11
     * @updateTime 2015年9月14日, 上午10:33:11
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo
     */
    public static void setUserInfoBean(UserInfoBean userInfoBean) {
        TApplication.userInfoBean = userInfoBean;
//        SpUtil.setObject(context, "userInfo" + UserInfoUtil.getLoginBean().getAccount(), userInfoBean);
    }






    /**
     * 初始化ImageLoader配置
     *
     * @param context
     */
    public static void initImageLoader(Context context) {

        ImageOptions imageOptions = new ImageOptions.Builder()
                .placeHolder(R.mipmap.home_img_defualt)
                .errorDrawable(R.mipmap.home_img_defualt)
                .build();

        //初始化图片框架.
        ImageLoaderUtil.init(imageOptions);

    }

    /**
     * 获取指纹
     */
    public static String getAppMd5() {
        if (appMd5 == null) {
            synchronized (TApplication.class) {
                if (appMd5 == null) {
                    appMd5 = NativeAction.createMD5(context
                            .getPackageCodePath());
                }
            }
        }
        return appMd5;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Resources getRes() {
        return context.getResources();
    }

    {
        //增加QQ的配置
//        PlatformConfig.setQQZone("1105007983", "C6iiGIhUpdTvuMB5");
    }

    //static 代码段可以防止内存泄露
    //下拉刷新 Header和Footer 全局设定
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    /**
     * 保存登录账号
     *
     * @param account 登录账号
     */
    public static void setAccount(String account) {
        TApplication.account = account;
        SpUtil.getSpUtil().setObject(context, "account", account);
    }

    public static String getAccount() {
        if (TextUtils.isEmpty(account)) {
            account = (String) SpUtil.getSpUtil().getObject(context, "account");
        }
        return account;
    }

    /**
     * 保存登录用户的信息
     *
     * @param mineInfo
     */
    public static void setMineUserInfo(MineUserInfoBean mineInfo) {
        TApplication.mineInfo = mineInfo;
        SpUtil.setObject(context, "mineInfo" + mineInfo.getMobile(), mineInfo);
    }

    /**
     * 清除用户信息
     */
    public static void clearMineUserInfo() {
        if (mineInfo!=null){
            SpUtil.setObject(context, "mineInfo" + mineInfo.getMobile(), null);
            TApplication.mineInfo = null;
        }
    }

    /**
     * 获取用户登录信息
     * @return
     */
    public static MineUserInfoBean getMineUserInfo() {
        if (null == mineInfo) {
            mineInfo = (MineUserInfoBean) SpUtil.getObject(TApplication.context, "mineInfo" + getAccount());
        }
        return mineInfo;
    }

    public static void setProPosition(int proPos) {
        TApplication.proPos = proPos;
        SpUtil.setObject(context, "proPos", proPos);
    }

    public static int getProPosition() {
        if (0 == proPos) {
//            proPos = (int) SpUtil.getObject(TApplication.context, "proPos");
        }
        return proPos;
    }

    public static void setCityPosition(int cityPosition) {
        TApplication.cityPos = cityPosition;
        SpUtil.setObject(context, "cityPosition", cityPosition);
    }

    public static int getCityPosition() {
        if (0 == cityPos) {
//            cityPos = (int) SpUtil.getObject(TApplication.context, "cityPosition");
        }
        return cityPos;
    }

}
