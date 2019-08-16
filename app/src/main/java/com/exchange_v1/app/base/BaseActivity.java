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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.interf.DoAfterFinish;
import com.exchange_v1.app.utils.ActivityUtils;
import com.exchange_v1.app.utils.BundleUtils;
import com.exchange_v1.app.utils.C2bPushUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.TVUtils;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.Util;
import com.exchange_v1.app.utils.ViewUtils;
import com.exchange_v1.app.utils.lbs.C2bLocationClient;
import com.exchange_v1.app.utils.softkeypaddetect.OnSoftKeyPadListener;
import com.exchange_v1.app.utils.softkeypaddetect.SoftKeyPadDetector;
import com.exchange_v1.app.view.TitleView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * Acitivity基类
 * 
 */
public abstract class BaseActivity extends AppCompatActivity implements OnSoftKeyPadListener {

	public static final String KEY_TITLE = "title";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_LIST = "list";
	public static final String KEY_IDTO = "KEY_IDTO";
	public static final String KEY_ODTO = "KEY_ODTO";
	public static final String KEY_DO_AFTER = "afterDo";

	public String title = "";
	public String description = "";

	protected Context context = BaseActivity.this;
	protected Activity thisA = BaseActivity.this;
	/** 广播监听 */
	public BroadcastReceiver receiver;
	/** 广播过滤字段 */
	public IntentFilter filter;
	/** 获取当前类 */
	private Class<? extends Activity> c = this.getClass();
	/** 当前Activity的类名,用于判断当前是哪个Activity在最前面 */
	public static String clazzName;
	/** 用于判断应用是否退出到后台 */
	public static boolean isBack = false;
	/** 标题 */
	protected TitleView titleView;

	public Handler mHandler;

	protected String pageName="BasePage";

	private SoftKeyPadDetector mSoftKeyPadDetector;

	//finish结束之后执行的操作,极光跳转对应的页面后，返回app的首页
	public static DoAfterFinish mDoAfterFinish;
	private Long mInTime;
	private PowerManager pm;
	public static C2bLocationClient c2bLocationClient;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filter = new IntentFilter();
		mHandler = new Handler();

		setContentView(getContentViewId());
		//Util.setFont(view, TApplication.face);
		titleView = (TitleView) findViewById(R.id.view_title);
		getInputTime();
		View contentView = findViewById(R.id.contentView);
		if (contentView != null) {
			contentView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					closeKeyboard();
				}
			});
		}
		//初始化标题加载
		if (titleView!=null) {
			//从子类activity重写读取
			if (isLoadBackBtn()){
				titleView.setBackBtn();
			}
			//从配置文件Androidmanifest中读取
			if (!TextUtils.isEmpty(getTitle())) {
				titleView.setTitle(getTitle());
			}
		}
		findViews();
		if (useEventBus()) {
			EventBus.getDefault().register(this);
		}
		if(mDoAfterFinish==null){
			mDoAfterFinish = (DoAfterFinish) getIntent().getSerializableExtra(KEY_DO_AFTER);
		}
		initGetData();
		widgetListener();
		init();
		registerReceiver();

		mSoftKeyPadDetector = new SoftKeyPadDetector(this);
		mSoftKeyPadDetector.setOnSoftInputListener(this);
		TApplication.startBdLocation();

		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	}


	protected void doTest() {
	}

	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		Configuration config=new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config,res.getDisplayMetrics() );
		return res;
	}

	public TitleView getTitleView() {
		return titleView;
	}

	public void addOnBackClickListener(OnClickListener onBackClickListener) {
		if (isLoadBackBtn()){
			titleView.addBackBtn(onBackClickListener);
		}
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

	protected boolean getStatusBar() {
		return true;
	}

	protected boolean useEventBus() {
		return false;
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

	/**
	 * 获取布局id
	 * 
	 * @updateTime 2015-6-22,下午2:58:56
	 * @updateAuthor 綦巍
	 * @return
	 */
	protected abstract int getContentViewId();

	/**
	 * 查询View对象
	 * 
	 * @updateTime 2015-6-22,下午2:58:56
	 * @updateAuthor 綦巍
	 */
	protected abstract void findViews();

	/**
	 * 初始化Intent,设置数据
	 * 
	 * @updateTime 2015-6-22,下午3:01:11
	 * @updateAuthor 綦巍
	 */
	protected abstract void initGetData();

	/**
	 * 设置监听
	 * 
	 * @updateTime 2015-6-22,下午3:01:11
	 * @updateAuthor 綦巍
	 */
	protected abstract void widgetListener();

	/**
	 * 获取网络数据
	 * 
	 * @updateTime 2015-6-22,下午3:03:46
	 * @updateAuthor 綦巍
	 */
	protected abstract void init();

	/**
	 * 是否现实返回按钮
	 * @return
     */
	protected boolean isLoadBackBtn(){
		return false;
	};

//	@Override
//	protected void onRestart() {
//		super.onRestart();
//
//		//唤醒的时候重新计数并切发送广播，将数据上传给后台
//		TApplication.c2b_count = 0;
//		Intent intent = new Intent();
//		intent.setAction(BroadcastFilters.ACTION_C2B_TIMER);
//		Util.sendBroadcast(context, intent);
//
//	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (!TApplication.IS_FRONT) {// 是否唤醒操作
			TApplication.IS_FRONT = true;
			//唤醒的时候重新计数并切发送广播，将数据上传给后台
			TApplication.c2b_count = 0;

			Intent intent = new Intent();
			intent.setAction(BroadcastFilters.ACTION_C2B_TIMER);
			Util.sendBroadcast(this, intent);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		clazzName = c.getName();
		isBack = false;

		//友盟统计
		MobclickAgent.onPageStart(this.getClass().getName());
		MobclickAgent.onResume(this);
		C2bPushUtil.onResume(this);
		mSoftKeyPadDetector.startDetect();

		String jPushMsg = SpUtil.getSPJPushMsg();
		String jPushTitle = SpUtil.getSPJPushTitle();



	}

	@Override
	protected void onPause() {
		mSoftKeyPadDetector.stopDetect();
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName());
		MobclickAgent.onPause(this);
		C2bPushUtil.onPause(this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (Util.isApplicationBroughtToBackground(context) || !pm.isScreenOn()) {//屏幕黑屏也算切换到后台，否则无法拉起手势密码
			TApplication.IS_FRONT = false;// 出去了就置为false

			isBack = true;
			SpUtil.getSpUtil().putSPValue(getString(R.string.spkey_app_timmer), TApplication.c2b_count);
			ToastUtil.showToast(thisA,"您的应用已切换到后台");

		}
	}

	private void setFilterActions() {
		filter.addAction(BroadcastFilters.ACTION_TEST);
	}

	private void registerReceiver() {
		setFilterActions();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				BaseActivity.this.onReceive(context, intent);
			}
		};
		filter.addAction(BroadcastFilters.ACTION_BIND_ESSE_OVER);
		filter.addAction(BroadcastFilters.ACTION_BUY_BACK); // 回购刷新
		filter.addAction(BroadcastFilters.ACTION_ATTORN_CARD); // 转让刷新
		filter.addAction(BroadcastFilters.ACTION_ATTORN_CASH); // 领取刷新
		filter.addAction(BroadcastFilters.ACTION_ATTORN_DELETE); // 购物车刷新
		filter.addAction(BroadcastFilters.ACTION_CLOSE_REGISTER); // 关闭注册界面
		filter.addAction(BroadcastFilters.ACTION_HOME_ORDER_REFURBISH); // 订单列表刷新
		filter.addAction(BroadcastFilters.ACTION_GOODSDETAILS_CLOSE); // 订单列表刷新
		filter.addAction(BroadcastFilters.ACTION_HOME_CLOSE);
		filter.addAction(BroadcastFilters.ACTION_C2B_UPDATA_USER_DATA);
		filter.addAction(BroadcastFilters.ACTION_CLOSE_FAST_PAY);// 关闭快捷支付添加绑卡的界面
		filter.addAction(BroadcastFilters.ACTION_CLOSE_FAST_PAY_PAYSELECT);// 关闭快捷支付 支付订单页 界面
		filter.addAction(BroadcastFilters.ACTION_CLOSE_OIL_CARD_LIST_SUCCESS);// 我的加油省钱看是否领取成功进行列表的刷新
		filter.addAction(BroadcastFilters.ACTION_CLOSE_PAY_RESULT_SUCCESS);// 支付成功后返回购买页面
		//todo
		String permission= "Manifest.permission.bwoilpermiss";
		registerReceiver(receiver, filter,permission,mHandler);


	}

	// 当有2个editText交替焦点 键盘就会闪
	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// if (ev.getAction() == MotionEvent.ACTION_DOWN) {
	// View v = getCurrentFocus();
	// if (isShouldHideInput(v, ev)) {
	// closeKeyboard();
	// }
	// }
	// return super.dispatchTouchEvent(ev);
	// }
	//
	// private boolean isShouldHideInput(View v, MotionEvent event) {
	// if (v != null && (v instanceof EditText)) {
	// Rect rect = new Rect();
	// v.getGlobalVisibleRect(rect);
	// if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 处理广播事件
	 * 
	 * @updateTime 2015-6-22,下午3:06:20
	 * @updateAuthor 綦巍
	 * @param context
	 * @param intent
	 */
	protected void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(BroadcastFilters.ACTION_TEST)) {
			if (c.getName().equals(clazzName)) {
				// ShowDialog
			}
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		if (useEventBus()) {
			EventBus.getDefault().unregister(this);
		}
		super.onDestroy();
	}





	@Override
	public void finish() {
		finishNoAnim();
		overridePendingTransition(R.anim.exit_enter, R.anim.exit_exit);
	}

	public void finishNoAnim() {
		closeKeyboard();

		overridePendingTransition(R.anim.activity_close_enter,
				R.anim.activity_close_exit);
		super.finish();
		//如果是最后一个活动的activity
		if(mDoAfterFinish!=null){
					String lastActivityName = ActivityUtils.lastActivityName(BaseActivity.this);
					//没有活动的activity的时候启动
					if(TextUtils.isEmpty(lastActivityName)){
						mDoAfterFinish.execute(BaseActivity.this);
						mDoAfterFinish=null;
					}
		}


	}

	public void closeKeyboard() {
		try {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getApplicationWindowToken() != null) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				//显示隐藏键盘的方法
				imm.hideSoftInputFromWindow(getCurrentFocus()
						.getApplicationWindowToken(), 0);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 因为Activity onActivityResult不对外公开，只能内部封装，再进行公开
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void onActivityResultSuper(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 *
	 * @param id View的Id
	 * @param <V> 不用管
     * @return 直接返回泛型的View
     */
	public final <V extends View> V getView (int id) {
		try {
			return (V) findViewById(id);
		} catch (ClassCastException ex) {
			System.err.println("Could not cast View to concrete class."+ ex);
			throw ex;
		}
	}

	@Override
	public void onSoftKeyPadChanged(boolean visible, int height) {
		if(visible) {
			showSoftKey();
		}else{
			hideSoftKey();
		}
	}

	/**
	 * 如果要触发该方法的话
	 * 必须设置
	 * android:windowSoftInputMode="stateHidden|adjustResize"
	 */
	public void hideSoftKey() {

	}
	/**
	 * 如果要触发该方法的话
	 * 必须设置
	 * android:windowSoftInputMode="stateHidden|adjustResize"
	 */
	public void showSoftKey() {

	}

	/**
	 * 获取前面一个页面的传过来的标题
	 * @return
     */
	protected String getTitleP() {
		Bundle b = getBundle();
		title = b.getString(KEY_TITLE);
		return title;
	}
	/**
	 * 传的标题
	 * @return
	 */
	protected static void putTitleP(Bundle b, String title) {
		if (b == null) {
			return ;
		}
		b.putString(KEY_TITLE,title);
	}
	/**
	 * 获取前面一个页面的传过来的描述
	 * @return
	 */
	protected String getDescriptionP() {
		Bundle b = getBundle();
		title = b.getString(KEY_DESCRIPTION);
		return title;
	}
	/**
	 * 传的描述
	 * @return
	 */
	protected static void putDescriptionP(Bundle b, String description) {
		if (b == null) {
			return ;
		}
		b.putString(KEY_DESCRIPTION,description);
	}

	/**
	 * 为了省多一句
	 * @return
     */
	protected Bundle getBundle() {
		return getIntent().getExtras();
	}

	/**
	 * 转换list中的字符值
	 * @param list
	 * @param fieldToString
     * @return
     */
	public static ArrayList<String> convertToStringList(ArrayList list, ObjFieldToString fieldToString) {
		ArrayList<String> strList = new ArrayList<>();
		if (fieldToString==null) {
			return strList;
		}
		for (Object obj:list) {
			strList.add(fieldToString.getStr(obj));
		}
		return strList;
	}

	public Long getInputTime() {
		Date date= new Date();
		mInTime = date.getTime()/1000L;
		return mInTime;
	}

	public Long getEndTime() {
		Date date= new Date();
		Long OutTime= date.getTime()/1000L;
		return OutTime;
	}

	public Long getNowTime() {
		return mInTime;
	}

	/**
	 * 字段值转文本
	 */
	public interface ObjFieldToString<T extends Object>{
		String getStr(T obj);
	}

	public int getDimen(int dimenId){
		return getResources().getDimensionPixelSize(dimenId);
	}

	/**
	 * @return 直接返回 "输出数据传输对象"
	 */
	public final <ODTO> ODTO getODTO (Intent activityResultData) {
		return BundleUtils.getODTO(activityResultData);
	}

	/**
	 * @return 直接返回 "输入数据传输对象"
	 */
	public final <IDTO> IDTO getIDTO () {
		return BundleUtils.getIDTO(thisA);
	}

	public static Bundle getBundleByHasIDTO(Serializable idto) {
		return BundleUtils.getBundleByHasIDTO(idto);
	}

	public static Bundle getBundleByHasODTO(Serializable odto) {
		return BundleUtils.getBundleByHasODTO(odto);
	}

	public void setResultOKHasOdto(Serializable odto) {
		BundleUtils.setResultOKHasOdto(thisA,odto);
	}


	public void disableSoldOutBtn(TextView soldOutBtn , int strRes) {
		disableSoldOutBtn(soldOutBtn,getString(strRes));
	}
	public void disableSoldOutBtn(TextView soldOutBtn , String str) {
		TVUtils.setText(soldOutBtn, str);
		ViewUtils.setEnabledFalse(soldOutBtn);
	}

	//RN主入口
	protected String getMainComponentName() {
		return "c2bphone";
	}
	//RN页面标识
	public Bundle getRNBundle() {
		return null;
	}

}
