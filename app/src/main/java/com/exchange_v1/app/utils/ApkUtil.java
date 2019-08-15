package com.exchange_v1.app.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;


import com.exchange_v1.app.base.TApplication;

import java.io.File;
import java.util.List;

/**
 * apk工具类
 * 
 *
 */
public class ApkUtil {

	/**
	 * 检测应用是否已经安装
	 * 
	 * @version 1.0
	 * @createTime 2013-11-18,上午12:41:45
	 * @updateTime 2013-11-18,上午12:41:45
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param packageName
	 *            路径包名
	 * @return true 已经安装 ， false 未安装
	 */
	public static boolean checkPackageInstall(String packageName) {
		PackageInfo packageInfo;
		try {
			packageInfo = TApplication.context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 安装apk
	 *
	 * @version 1.0
	 * @createTime 2013-11-18,上午1:43:05
	 * @updateTime 2013-11-18,上午1:43:05
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param file
	 */
	public static Intent installApk(File file){
		Intent intent = new Intent();
		Context context = TApplication.context;

		intent.setAction(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(context, getFileProviderAuthority(context), file);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		}else{
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		}
		context.startActivity(intent);
		return intent;
	}

	/**
	 * 获取FileProvider的auth
	 *
	 * @return
	 */
	public static String getFileProviderAuthority(Context context) {
		try {
			for (ProviderInfo provider : context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PROVIDERS).providers) {
				if (FileProvider.class.getName().equals(provider.name) && provider.authority.endsWith(".fileProvider")) {
					return provider.authority;
				}
			}
		} catch (NameNotFoundException ignore) {
			ignore.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取该app是否在进程里面并且是否在前台进程
	 *
	 * @version 1.0
	 * @createTime 2015年2月4日,下午4:03:41
	 * @updateTime 2015年2月4日,下午4:03:41
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 *  @param context
	 *  @return true 在前台进程 false不在前台进程
	 */
	public static boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;
		for (RunningAppProcessInfo appProcess : appProcesses) {
			// 判断该工程的包名是否在前台 是就返回true
			if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前运行的进程名称
	 * @param cxt
	 * @param pid
	 * @return
	 */
	public static String getProcessName(Context cxt, int pid) {
		ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}
		for (RunningAppProcessInfo procInfo : runningApps) {
			if (procInfo.pid == pid) {
				return procInfo.processName;
			}
		}
		return null;
	}

}
