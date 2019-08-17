package com.exchange_v1.app.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.exchange_v1.R;
import com.exchange_v1.app.fragment.MainCashFragment;
import com.exchange_v1.app.fragment.MainMineFragment;
import com.exchange_v1.app.fragment.MainHomeFragment;
import com.exchange_v1.app.fragment.MainRechargeFragment;
import com.exchange_v1.app.receiver.JPushReceiver;
import com.exchange_v1.app.utils.C2bPushUtil;
import com.exchange_v1.app.utils.CountIdUtil;
import com.exchange_v1.app.utils.CountUtil;
import com.exchange_v1.app.utils.DisplayUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.UserInfoUtil;
import com.exchange_v1.app.utils.Util;
import com.exchange_v1.app.view.MyRadioView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.RuntimePermissions;


/**
 * 主界面
 *
 */
@RuntimePermissions
public class MainActivity extends BaseFragmentActivity {
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 3;
    /**
     * fragment模块集合
     */
    private List<Fragment> list_Fragments = new ArrayList<Fragment>();
    /**
     * 上次按退出的时间
     */
    private long lastExitTime = 0;
    /**
     * 当前实体类
     */
    private Class<? extends Activity> c = this.getClass();
    /**
     * 云储油
     */
    private MyRadioView radio_cloud_oil;
    /**
     * 推荐有奖
     */
    private MyRadioView radio_recommend;
    /**
     * 我的账户
     */
    private MyRadioView radio_mine;
    /**
     * 加油服务
     */
    private MyRadioView radio_refuel;
    /**
     * 当前选择项
     */
    private MyRadioView radio_current;


    /**
     * 当前显示的Fragment
     */
    private int current_Fragment = -1;
    /**
     * 主页 （2.7版本主页变成加油页面）
     */
    public final static int FRAGMENT_HOME = 0;
    //	/** 加油服务 */
    //	public final static int FRAGMENT_OIL = 1;
    /**
     * 储油服务
     */
    public final static int FRAGMENT_OIL = 1;
    /**
     * 推荐有奖
     */
    public final static int FRAGMENT_RECOMMEND = 2;
    /**
     * 我的账户
     */
    public final static int FRAGMENT_MINE = 3;

    /**
     * 主页fragment （2.7版本主页变成加油页面）
     */
    //	private MainCloudOilFragment fragment_home;
    private MainHomeFragment fragment_home;
    /**
     * 我的账户fragment
     */
    private MainMineFragment fragment_mine;
    /**
     * 加油服务fragment
     */
    private MainRechargeFragment fragment_oil;
    /** 菜单fragment */
    //	private MenuFragment menuFragment;
    /** 有奖分享fragment */
    //	private ShareRewardActivity fragment_reWard;
    /**
     * 底部第三个Tab点击的内容
     */
    private MainCashFragment tabThirdF;
    /** 油价走势fragment */
    //	private OilPriceFragment fragment_oilPrice;


    /**
     * 切换进度条
     */
    private View view_slider;
    /**
     * 第几个视图
     */
    private int currentPage = 0;

    /**左滑动按钮*/
    //	RelativeLayout user_rl;

    /**
     * 侧滑控件
     */
    //	 DrawerLayout mDrawerLayout;


    Handler mHander;
    private boolean isNotification = false;
    private boolean isFastOpenApp = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;

    }

    //阻止activity保存fragment的状态,fragment防止重绘
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void findViews() {
        //设置全透状态栏
        setStatusBarFullTransparent();
        setFitSystemWindow(false);

        radio_cloud_oil = (MyRadioView) findViewById(R.id.main_radio_recharge);
        radio_recommend = (MyRadioView) findViewById(R.id.main_radio_cash);
        radio_mine = (MyRadioView) findViewById(R.id.main_radio_mine);
        radio_refuel = (MyRadioView) findViewById(R.id.main_radio_home);
        view_slider = findViewById(R.id.view_slider);
        Intent intentmain = getIntent();
        LoadingActivity.hasMain = 1;

    }


    /**
     * 摩窗对应跳转
     */
    @Override
    protected void init() {
        // 记录程序运行状态
        mHander = new Handler();
        SpUtil.getSpUtil().putSPValue(SpUtil.IS_APPRUNING, true);
        initFragment();
        radio_refuel.setImgRes(R.mipmap.index, R.mipmap.index_c);
        radio_refuel.setCheck(true);
        radio_current = radio_refuel;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view_slider
                .getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / 4;
        layoutParams.height = DisplayUtil.dip2px(MainActivity.this, 2);
        // 检查更新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
//                    checkUpdate();
                }
            }
        }, 3000);
        //		MainActivityPermissionsDispatcher.needsPermissionWithCheck(this);


        //		menuFragment.getView().getLayoutParams().width= (int)(DisplayUtil.getScreenWidth(this)-DisplayUtil.dip2px(this,80));
        //		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        // 绑定极光推送
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Util.isLogin(MainActivity.this)) {
                    C2bPushUtil.bindingJPush(MainActivity.this, true, TApplication.getUserInfoBean().getUserNo());
                    //					bindingJPush(true,TApplication.getUserInfoBean().getUserNo());
                }
            }
        }, 1000);


        String account = UserInfoUtil.getLoginBean().getAccount();

        Intent intent = getIntent();
        isFastOpenApp = intent.getBooleanExtra("fastOpenAppFlag", false);
        intent.putExtra("fastOpenAppFlag", false);
        //推送内容
        Bundle bundle = getIntent().getExtras();
        String productId = "";
        String productName = "";
        String typeBn = "";
        String period = "";
        String url = "";
        if (bundle != null) {
            isNotification = bundle.getBoolean("isNotification", false);
            productId = bundle.getString("product_id");
            productName = bundle.getString("product_name");
            typeBn = bundle.getString("type_bn");
            period = bundle.getString("period");
            url = bundle.getString("url");
            bundle.putBoolean("isFastOpenApp", isFastOpenApp);
        }
        String token = TApplication.getToken();
            //用户未登陆， 去监测是否在启动页点击启动图片

    }


    @Override
    protected void widgetListener() {
        radio_cloud_oil.setOnClickListener(radioClick);
        radio_recommend.setOnClickListener(radioClick);
        radio_mine.setOnClickListener(radioClick);
        radio_refuel.setOnClickListener(radioClick);


    }

    /**
     * 初始化子模块
     *
     * @version 1.0
     * @createTime 2015年8月19日, 下午1:46:34
     * @updateTime 2015年8月19日, 下午1:46:34
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private void initFragment() {

        fragment_home = new MainHomeFragment();
        //		fragment_oilPrice = new OilPriceFragment();
        //tabThirdF = new MainActivitiesFragment();
        tabThirdF = new MainCashFragment();
        fragment_mine = new MainMineFragment();
        fragment_oil = new MainRechargeFragment();

        addFragment(fragment_home);
        addFragment(fragment_oil);
        addFragment(tabThirdF);
        addFragment(fragment_mine);

        switchView(FRAGMENT_HOME);

    }

    private void addFragment(BaseFragment o) {
        if (!list_Fragments.contains(o)) {
            list_Fragments.add(o);
        }
    }

    /**
     * 选择界面
     */
    public void switchView(int position) {
        try {
            if (current_Fragment == position) {
                return;
            }


            //友盟、百度统计埋点--app底部四个tab按钮
            CountUtil.sendDataForStatistics(this, CountIdUtil.MainTab_ + position);
            // 获取Fragment的操作对象
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();

            if (!list_Fragments.get(position).isAdded()) {
                transaction.add(R.id.content_frame, list_Fragments.get(position));
            }
            if (current_Fragment != -1) {
                transaction.hide(list_Fragments.get(current_Fragment)).show(list_Fragments.get(position));
            }
            transaction.commitAllowingStateLoss();// 提交更改
            current_Fragment = position;
            // 设置导航条
            int toXDelta = 0;
            int fromXDelta = 0;
            fromXDelta = currentPage * view_slider.getWidth();
            toXDelta = position * view_slider.getWidth();
            currentPage = position;
            translateSlider(fromXDelta, toXDelta);

            //邮箱活动
            if (position==3&&TApplication.isShowMineRed()){
                TApplication.setShowMineRed(false);
            }
            updateMineBtn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        BaseActivity.clazzName = c.getName();
        BaseActivity.isBack = false;
        super.onResume();
        MobclickAgent.onResume(this);
        if (null != switchPageRunable) {
            mHander.postDelayed(switchPageRunable, 200);
        }
        int badgeCount = SpUtil.getSPValue(this, JPushReceiver.BADGECOUNT, 0, JPushReceiver.BADGECOUNT, 0);
        if(badgeCount > 0){
            Intent intent = new Intent();
            intent.setAction("com.brightoilonline.c2b_phone.REMOVE_BADGE");
            SpUtil.putSPValue(this, JPushReceiver.BADGECOUNT, 0, JPushReceiver.BADGECOUNT, 0);
            sendBroadcast(intent);
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.isApplicationBroughtToBackground(MainActivity.this)) {
            BaseActivity.isBack = true;
        }
    }

    /**
     * 底部按钮点击事件处理
     */
    private OnClickListener radioClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            MyRadioView myRadioView = (MyRadioView) v;
            if (myRadioView.isCheck) {
                return;
            }
            radio_current.setCheck(false);
            radio_current = myRadioView;
            radio_current.setCheck(true);
            switch (v.getId()) {
                case R.id.main_radio_home:
                    switchView(FRAGMENT_HOME);
                    break;
                case R.id.main_radio_recharge:
                    switchView(FRAGMENT_OIL);
                    break;

                case R.id.main_radio_cash:
                    switchView(FRAGMENT_RECOMMEND);
                    break;
                case R.id.main_radio_mine:
                    switchView(FRAGMENT_MINE);
//                    setOilTankActivity();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 移动滑块
     */
    private void translateSlider(int fromXDelta, int toXDelta) {
        TranslateAnimation animation = new TranslateAnimation(fromXDelta,
                toXDelta, 0, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);
        view_slider.startAnimation(animation);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                // 判断2次点击事件时间
                if ((System.currentTimeMillis() - lastExitTime) > 2000) {
                    ToastUtil.showToast(MainActivity.this,
                            getString(R.string.hint_exit_application));
                    lastExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }

            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    Runnable loginCallback = new Runnable() {
        @Override
        public void run() {
            if (isFastOpenApp) {
                isFastOpenApp = false;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    /**
     * 跳转页面线程
     */
    Runnable switchPageRunable;



    private void updateMineBtn() {
        /**
         * 邮箱活动的时候替换图标
         */
        if ( TApplication.isShowMineRed()&&(TApplication.isOilTankChange()||TApplication.isCpnChange())) {
            radio_mine.showRedCircle(true);
        } else {
            radio_mine.showRedCircle(false);
        }
    }


    @Override
    protected void onDestroy() {
        SpUtil.getSpUtil().getSPValue(SpUtil.IS_APPRUNING, false);
        LoadingActivity.hasMain = 0;
        super.onDestroy();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            }

        } else if (requestCode == C2bPushUtil.WRITE_EXTERNAL_STORAGE_JPUSH_BIND_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                if (Util.isLogin(MainActivity.this)) {
                    //					bindingJPush(true,TApplication.getUserInfoBean().getUserNo());
                    C2bPushUtil.bindingJPush(MainActivity.this, true, TApplication.getUserInfoBean().getUserNo());
                }
            }
        } else if (requestCode == C2bPushUtil.WRITE_EXTERNAL_STORAGE_JPUSH_UNBIND_CODE) {
            if (Util.isLogin(MainActivity.this)) {
                //				bindingJPush(false,TApplication.getUserInfoBean().getUserNo());
                C2bPushUtil.bindingJPush(MainActivity.this, false, TApplication.getUserInfoBean().getUserNo());
            }
        }
    }




    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 23) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(getResources().getColor(R.color.common_color_black_text_stress));
        }
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    protected int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}