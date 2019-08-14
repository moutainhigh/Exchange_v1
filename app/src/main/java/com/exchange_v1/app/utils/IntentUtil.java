package com.exchange_v1.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.brightoilonline.c2b_phone.R;
import com.brightoilonline.c2b_phone.activity.BaseActivity;
import com.brightoilonline.c2b_phone.activity.MainActivity;
import com.brightoilonline.c2b_phone.activity2.home.LoginActivity2;
import com.brightoilonline.c2b_phone.interf.DoAfterFinish;

/**
 * Intent跳转工具类
 * 
 * @Description 用于简化页面跳转搞得重复代码
 * @author CodeApe
 * @version 1.0
 * @date 2014年12月31日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 * 
 */
public class IntentUtil {


    /**
	 * 跳转首页
	 * @param context
	 * @param bundle
     */
	public static void gotoMainActivity(Context context, Bundle bundle) {
		Intent i = new Intent(context, MainActivity.class);
		i.putExtras(bundle);
		//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(i);
	}

	/**
	 * 普通的方式打开一个Activiy
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:07:13
	 * @updateTime 2014年12月31日,上午7:07:13
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 需要打开的Activity
	 */
	public static void gotoActivity(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}
	
	public static void gotoActivityTwo(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}

	/**
	 * 打开一个Activity并关闭当前页面
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:07:43
	 * @updateTime 2014年12月31日,上午7:07:43
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 需要打开的Activity
	 */
	public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 用单例模式打开一个Activity并关闭当前页面，可携带数据
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:08:40
	 * @updateTime 2014年12月31日,上午7:08:40
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 需要跳转的页面
	 * @param bundle 携带的数据
	 */
	public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 普通的方式打开一个activity，并携带数据
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:05:33
	 * @updateTime 2014年12月31日,上午7:05:33
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClassName 需要打开的Activity
	 * @param bundle 携带的数据
	 */
	public static void gotoActivity(Context context, String gotoClassName, Bundle bundle) {

		Intent intent = new Intent();
		intent.setClassName(context,gotoClassName);
		if (bundle!=null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
		if (context instanceof Activity) {
			((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
		}
	}

	/**
	 * 普通的方式打开一个activity，并携带数据
	 *
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:05:33
	 * @updateTime 2014年12月31日,上午7:05:33
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context 上下文
	 * @param gotoClass 需要打开的Activity
	 * @param bundle 携带的数据
	 */
	public static void gotoActivity(Context context, Class<?> gotoClass, Bundle bundle) {

		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.putExtras(bundle);
		context.startActivity(intent);
		if (context instanceof Activity) {
			((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
		}
	}

	/**
	 * 用Result的方式跳转到指定页面，不携带数据
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:03:59
	 * @updateTime 2014年12月31日,上午7:03:59
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 要跳转的Activity
	 * @param requestCode 页面跳转请求码
	 */
	public static void gotoActivityForResult(Context context, Class<?> gotoClass, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		((Activity) context).startActivityForResult(intent, requestCode);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 用Result的方式跳转到指定页面，不携带数据
	 *
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:03:59
	 * @updateTime 2014年12月31日,上午7:03:59
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param gotoClass 要跳转的Activity
	 * @param requestCode 页面跳转请求码
	 */
	public static void gotoActivityForResult(Fragment fragment, Class<?> gotoClass, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(fragment.getActivity(), gotoClass);
		fragment.startActivityForResult(intent, requestCode);
		fragment.getActivity().overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 用Result的形式跳转到指定页面，并携带数据
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:02:25
	 * @updateTime 2014年12月31日,上午7:02:25
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 要跳转的页面
	 * @param bundle 携带的数据
	 * @param requestCode 跳转搞到页面的请求码
	 */
	public static void gotoActivityForResult(Context context, Class<?> gotoClass, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		if (bundle!=null) {
			intent.putExtras(bundle);
		}
		((Activity) context).startActivityForResult(intent, requestCode);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 用Result的形式跳转到指定页面，并携带数据
	 *
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:02:25
	 * @updateTime 2014年12月31日,上午7:02:25
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context 上下文
	 * @param className 要跳转的页面
	 * @param bundle 携带的数据
	 * @param requestCode 跳转搞到页面的请求码
	 */
	public static void gotoActivityForResult(Context context, String className, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		intent.setClassName(context,className);
		if (bundle!=null) {
			intent.putExtras(bundle);
		}
		((Activity) context).startActivityForResult(intent, requestCode);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 跳转至指定activity,并关闭当前activity.
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:01:47
	 * @updateTime 2014年12月31日,上午7:01:47
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 需要跳转的Activity界面
	 */
	public static void gotoActivityAndFinish(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 携带传递数据跳转至指定activity,并关闭当前activity.
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午7:00:59
	 * @updateTime 2014年12月31日,上午7:00:59
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 需要跳转的页面
	 * @param bundle 附带数据
	 */
	public static void gotoActivityAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}





	/**
	 * 跳转至指定activity,不关闭当前页面
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午6:59:27
	 * @updateTime 2014年12月31日,上午6:59:27
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 需要跳转的界面Activity
	 */
	public static void gotoActivityToTop(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 携带传递数据跳转至指定activity,不关闭当前activity.
	 * 
	 * @version 1.0
	 * @createTime 2014年12月31日,上午6:58:46
	 * @updateTime 2014年12月31日,上午6:58:46
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param gotoClass 跳转的activity
	 * @param bundle 附带的数据
	 */
	public static void gotoActivityToTop(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 跳转到发送短信界面
	 * 
	 * @version 1.0
	 * @createTime 2013-12-19,上午10:12:04
	 * @updateTime 2013-12-19,上午10:12:04
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param phoneNum 手机号码
	 * @param content 短信内容
	 */
	public static void gotoSendMsmActivity(Context context, String phoneNum, String content) {
		Uri uri = Uri.parse("smsto:" + phoneNum);
		Intent intent = new Intent(android.content.Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);
	}

	/**
	 * 关闭某个activity
	 *
	 * @version 1.0
	 * @createTime 2014年12月31日,上午6:57:56
	 * @updateTime 2014年12月31日,上午6:57:56
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param activity 需要关闭的activity对象
	 */
	public static void finish(Activity activity) {
		activity.finish();
		activity.overridePendingTransition(R.anim.exit_enter, R.anim.exit_exit);
	}

	public static Intent setBundleStringData(String... str) {
		Bundle bundle = new Bundle();
		for (int i = 1; i < str.length; i = i + 2) {
			bundle.putString(str[i - 1], str[i]);
		}
		return new Intent().putExtras(bundle);
	}

	public static void gotoActivityAndFinishForNo(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.alpha_exit, R.anim.alpha_exit);
	}

	public static void gotoActivityAndFinishForNo(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(context, gotoClass);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.alpha_exit, R.anim.alpha_exit);
	}

	public static void gotoActivityByLogin(Activity thisA, Class thisC, Bundle b) {
		if(Util.isLogin(thisA)){
			gotoActivity(thisA, thisC, b);
		}else{
			LoginActivity2.startLoginAfterStartTarget(thisA,thisC.getName(),b);
		}
	}

	public static void gotoActivityForResultByLogin(Context thisA, Class thisC, Bundle b, int requestCode) {
		if(Util.isLogin(thisA)){
			gotoActivityForResult(thisA, thisC, b,requestCode);
		}else{
			LoginActivity2.startLoginAfterStartTargetNoFinish(thisA,thisC.getName(),b,requestCode);
		}
	}


	/**
	 * 极光推送直接打开
	 * @param thisA
	 */
	public static void openActivityJpush(Context thisA, JpushGo go) {
		Bundle b = new Bundle();
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Activity activity = ActivityUtils.getCurrentActivity();
		if (!Util.isApplicationBroughtToBackground(thisA)) {

		}else{
			//处于后台而且app没有启动
			if(!ActivityUtils.isAppRunable(thisA)){
				DoAfterFinish doAfterFinish =new DoAfterFinish() {
					@Override
					public void execute(Context context) {
						IntentUtil.gotoMainActivity(context,new Bundle());
					}
				};
				intent.putExtra(BaseActivity.KEY_DO_AFTER,doAfterFinish);
			}
		}

		if(activity!=null){
			intent.putExtras(b);
			go.jump(activity,intent);
		}else {
			intent.putExtras(b);
			go.jump(thisA,intent);
		}
	}


	public 	interface  JpushGo{
		//跳转方法，每个页面的调整方法不一样
		public void jump(Context context, Intent intent);
	}
}
