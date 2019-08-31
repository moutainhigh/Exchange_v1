package com.exchange_v1.app.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.AddLoadingActivity;
import com.exchange_v1.app.config.Constant;
import com.exchange_v1.app.utils.C2bPushUtil;
import com.exchange_v1.app.utils.DateUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.SystemUtil;
import com.exchange_v1.app.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;


/**
 * 基类 FragmentActivity
 *
 *
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

	/** 广播接收器 */
	public BroadcastReceiver receiver;
	/** 广播过滤器 */
	public IntentFilter filter;
	/** 4.4版本以上的沉浸式 */
	//protected SystemBarTintManager mTintManager;

	Handler mHandler;
	Activity mActivity = BaseFragmentActivity.this;
	private PowerManager pm;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		switchLanguage();
		View view = View.inflate(this, getContentViewId(), null);
		// 布局内容会从actionbar以下开始
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			view.setFitsSystemWindows(true);
		}
		setContentView(view);
		findViews();
		widgetListener();
		initGetData();
		init();
		mHandler=new Handler();
		registerReceiver();
//		mTintManager = new SystemBarTintManager(this);
//		if (getStatusBar()) {
//			setStatusBarState();
//		}

		if (useEventBus()) {
			EventBus.getDefault().register(this);
		}

		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	}

//	private void setStatusBarState() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//			mTintManager = new SystemBarTintManager(this);
//			mTintManager.setStatusBarTintEnabled(true);
//			// 使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
//			mTintManager.setStatusBarTintResource(R.color.black);
//			// 设置状态栏的文字颜色
//			// mTintManager.setStatusBarDarkMode(false, this);
//		}
//	}

	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		Configuration config=new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config,res.getDisplayMetrics() );
		return res;
	}

	protected boolean getStatusBar() {
		return true;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	protected void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		LogUtil.out("BaseFragmentActivity:" + "isLock=" + TApplication.isLock());
//		if(TApplication.isLock()){
//			Intent intent1 = new Intent();
//			intent1.setAction(BroadcastFilters.ACTION_OPEN_GESTURE);
//			Util.sendBroadcast(this, intent1);
//		}

		TApplication.IsOnTop = false;
//		JPushInterface.onResume(this);
		C2bPushUtil.onResume(this);

		String jPushMsg = SpUtil.getSPJPushMsg();
		String jPushTitle= SpUtil.getSPJPushTitle();

		if (mActivity instanceof AddLoadingActivity ) {
			return;
		}

		if (!TextUtils.isEmpty(jPushMsg) ) {
			if (DateUtil.getGapCount(SpUtil.getSPJPushTime(), DateUtil.getDate("yyyy-MM-dd")) <= 1) {

//				DialogV273 dialog =new DialogV273(mActivity);
//				DialogV273.Callback callback=null;
//				dialog.setTitle(jPushTitle);
//				dialog.setMessage(jPushMsg);
//				dialog.hideTitleTv();
//				dialog.hideCancelBtn();
//				dialog.setBtnText("","我知道了!");
//				dialog.setBtnColor(R.color.pas_bule,R.color.pas_bule);
//				dialog.show();

			}
//			if (DateUtil.getGapCount(SpUtil.getSPJPushTime(), DateUtil.getDate("yyyy-MM-dd")) <= 1) {
//				DialogUtil.showMessageDgBtnNoBg(mActivity
//						, jPushTitle
//						, jPushMsg
//						, "我知道了"
//						, new CustomDialog.OnClickListener() {
//							@Override
//							public void onClick(CustomDialog dialog, int id, Object object) {
//								dialog.dismiss();
//							}
//						});
//			}

//			PushMsgActivity.openActivity(thisA, jPushTitle, jPushMsg);
			SpUtil.setSPJPushMsg("");
			SpUtil.setSPJPushTitle("");
			SpUtil.setSPJPushTime("");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		TApplication.IsOnTop = true;
//		JPushInterface.onPause(this);
		C2bPushUtil.onPause(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
//		if (!ApkUtil.isAppOnForeground(this)) {// 判断该app进程是否在前台进程
		if (Util.isApplicationBroughtToBackground(this) || !pm.isScreenOn()) {//屏幕黑屏也算切换到后台，否则无法拉起手势密码
			TApplication.IS_FRONT = false;// 出去了就置为false
			//出去后将此刻的计数值保存下来
			SpUtil.getSpUtil().putSPValue(getString(R.string.spkey_app_timmer), TApplication.c2b_count);

		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (!TApplication.IS_FRONT) {// 是否唤醒操作
			TApplication.IS_FRONT = true;
			//唤醒的时候重新计数并切发送广播，将数据上传给后台
			TApplication.c2b_count = 0;

		}
	}

	/**
	 * 获取显示view的xml文件ID
	 *
	 * 在Activity的 {@link #onCreate(Bundle)}里边被调用
	 *
	 * @version 1.0
	 * @createTime 2014年4月21日,下午2:31:19
	 * @updateTime 2014年4月21日,下午2:31:19
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @return xml文件ID
	 */
	protected abstract int getContentViewId();

	/**
	 * 控件查找
	 *
	 * 在 {@link #getContentViewId()} 之后被调用
	 *
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:58:20
	 * @updateTime 2014年4月21日,下午1:58:20
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected abstract void findViews();

	/**
	 * 初始化应用程序，设置一些初始化数据都获取数据等操作
	 *
	 * 在{@link #widgetListener()}之后被调用
	 *
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:55:15
	 * @updateTime 2014年4月21日,下午1:55:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected abstract void init();

	/**
	 * 获取上一个界面传送过来的数据
	 *
	 * 在{@link BaseActivity#init()}之前被调用
	 *
	 * @version 1.0
	 * @createTime 2014年4月21日,下午2:42:56
	 * @updateTime 2014年4月21日,下午2:42:56
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected void initGetData() {
	};

	/**
	 * 组件监听模块
	 *
	 * 在{@link #findViews()}后被调用
	 *
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:56:06
	 * @updateTime 2014年4月21日,下午1:56:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected abstract void widgetListener();

	/**
	 * 设置广播过滤器
	 *
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:43:15
	 * @updateTime 2014年5月22日,下午1:43:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected void setFilterActions() {
		filter = new IntentFilter();
	}

	/**
	 * 注册广播
	 *
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:41:25
	 * @updateTime 2014年5月22日,下午1:41:25
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	protected void registerReceiver() {
		setFilterActions();
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				BaseFragmentActivity.this.onReceive(context, intent);
			}
		};
//		filter.addAction(BroadcastFilters.ACTION_TEST);

		String permission= "Manifest.permission.bwoilpermiss";
		registerReceiver(receiver, filter,permission,mHandler);

	}

	/**
	 * 重置视图
	 *
	 * @version 1.0
	 * @createTime 2014年9月14日,下午4:10:05
	 * @updateTime 2014年9月14日,下午4:10:05
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	private void resetView() {

		switchLanguage();
		setContentView(getContentViewId());
		findViews();
		widgetListener();
		initGetData();
		init();
	}

	/**
	 * 切换语言
	 *
	 * @version 1.0
	 * @createTime 2014年9月14日,下午3:27:58
	 * @updateTime 2014年9月14日,下午3:27:58
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	private void switchLanguage() {
		int language = SpUtil.getSPValue(this,
				getString(R.string.spkey_file_config), MODE_PRIVATE,
				getString(R.string.spkey_value_language),
				Constant.LANGUAGE_SYSTEM);
		Resources resources = getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		switch (language) {
		case Constant.LANGUAGE_SYSTEM:// 系统语言
			if (SystemUtil.IsSystemLanguage()) {
				config.locale = Locale.SIMPLIFIED_CHINESE;
			} else {
				config.locale = Locale.ENGLISH;
			}
			// config.locale = Locale.getDefault();
			break;
		case Constant.LANGUAGE_CHINESE:// 中文
			config.locale = Locale.SIMPLIFIED_CHINESE;
			break;
		case Constant.LANGUAGE_ENGLISH:// 英文
			config.locale = Locale.ENGLISH;
			break;
		default:
			config.locale = Locale.getDefault();
			break;
		}
		// 应用内配置语言
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
		resources.updateConfiguration(config, dm);

	}

	/**
	 * 广播监听回调
	 *
	 */
	protected void onReceive(Context context, Intent intent) {
//		if (intent.getAction().equals(BroadcastFilters.ACTION_CHANGE_LANGUAGE)) {// 语言切换
//			resetView();
//		}
	}

	protected boolean useEventBus() {
		return false;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		if (useEventBus()) {
			EventBus.getDefault().unregister(this);
		}

		super.onDestroy();
	}

}
