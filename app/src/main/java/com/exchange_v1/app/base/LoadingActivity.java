package com.exchange_v1.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.AddLoadingActivity;
import com.exchange_v1.app.activity.LoginActivity;
import com.exchange_v1.app.bean.AddLoadBean;
import com.exchange_v1.app.bean.LoginBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.bean.UserInfoBean;
import com.exchange_v1.app.utils.CheckAppRoot;
import com.exchange_v1.app.utils.FinishPrograme;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.UserInfoUtil;


/**
 * @author liangxg
 * @开机启动
 */

public class LoadingActivity extends BaseActivity {

    private static final int LOCATION_REQUEST_CODE = 3;
    private LoginBean mLoginBean;

    private UserInfoBean mUserInfoBean;
    private boolean flag = false;
    private int i = 0;

    private String update_str;

    /**
     * 登录的次数
     */
    int c_login = 0;
    private AddLoadBean addLoadBean;
    private String key_str = "";
    public static int hasMain = 0;
    private String mMemberId = "null";

    @Override
    protected int getContentViewId() {
        return R.layout.a_loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //已防通过安装APK直接点击运行后->点击 home键回到桌面，->再次点击应用图标 会闪现 启动页
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
    }

    @Override
    protected void findViews() {
        if (TApplication.needSafe) {
            new FinishPrograme(context).exitPrograme();
        }
        //检查手机是否已经被ROOT
        if (CheckAppRoot.checkAppRoot()){
            ToastUtil.showToast(this,"检测到您的手机已经root，为了您的账户安全，请避免在高风险环境下操作");
        }
    }



    private void gotoHomeActivity() {
        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
        finish();
    }


    /**
     * 页面跳转逻辑
     */
    private void initGetData2() {
        //判断是否重新覆盖安装
        int appversion = SpUtil.getSpUtil().getSPValue(getString(R.string.spkey_app_version), -1);
        if (appversion != TApplication.APP_VERSION) {
            SpUtil.getSpUtil().putSPValue(getString(R.string.spkey_app_version), TApplication.APP_VERSION);
            //进行对象缓存的清除
            if (SpUtil.objectSp == null) {
                SpUtil.objectSp = TApplication.context.getSharedPreferences(SpUtil.OBJECT_KEY, Context.MODE_PRIVATE);
            }
            SpUtil.objectSp.edit().clear().commit();
        }


        if (this.isTaskRoot()) {
            boolean firstInto = SpUtil.getSpUtil().getSPValue(
                    SpUtil.FIRST_INTO, true);

//			getLoginInfo();//2.7暂时不需要跳引导页
            if (firstInto) {
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        IntentUtil.gotoActivityAndFinish(LoadingActivity.this,
                                SplashActivity.class);
                    }
                }, 0);
                //此处是获取广告功能，如果要不执行广告跳转MainActivity必须保持0.5s以上延时，为了快速启动，第一次不获取广告
                //toMainActivity(true);
            } else {
                getLoginInfo();
            }

        } else {
            finish();
        }

    }


    @Override
    protected void initGetData() {

    }

    @Override
    protected boolean getStatusBar() {
        return false;
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        //延迟两秒去 主页
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentUtil.gotoActivityAndFinish(LoadingActivity.this,
                        LoginActivity.class);
            }
        }, 1000);
    }


    private void getLoginInfo() {
        mLoginBean = UserInfoUtil.getLoginBean();
        toMainActivity(false);
        if (!TextUtils.isEmpty(TApplication.getToken())) {
            if (TextUtils.isEmpty(key_str)) {
                flag = true;
            }
        }
    }


    /**
     * 跳转广告
     *
     * @param addLoadBean
     */
    private void toAddloadingPage(AddLoadBean addLoadBean) {
        if (addLoadBean.getLaunchTimeIsOn().equals("false")) {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    IntentUtil.gotoActivityAndFinishForNo(context,
                            MainActivity.class);
                }
            }, 0);
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("AddLoadBean", addLoadBean);
            IntentUtil.gotoActivityAndFinishForNo(context, AddLoadingActivity.class, bundle);
        }
    }

    private void toMainActivity(boolean isFirst) {

        final ResponseBean cacheBean = (ResponseBean) SpUtil.getObject(context, getString(R.string.spkey_adimg_load));

    }

    Runnable toMainRunalbe = new Runnable() {
        @Override
        public void run() {
            mHandler.removeCallbacks(toAddLoadingRunalbe);
            IntentUtil.gotoActivityAndFinishForNo(context,
                    MainActivity.class);
        }
    };

    Runnable toAddLoadingRunalbe = new Runnable() {
        @Override
        public void run() {
            toAddloadingPage(addLoadBean);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(toAddLoadingRunalbe);
        mHandler.removeCallbacks(toMainRunalbe);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_exit, R.anim.alpha_exit);
    }
}