package com.exchange_v1.app.executor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.utils.ActivityUtils;


/**
 * 等待提示框封装类
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2014年4月19日
 * @Copyright: Copyright (c) 2014 深圳光汇云油电商有限公司. z
 */
public class ProcessDialogUtil {

	/** 等待提示框对象 */
	public static Dialog progressDialog = null;

	/**
	 * 显示等待提示框
	 * 
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:26:03
	 * @updateTime 2014年4月19日,下午2:26:03
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context 上下文
	 * @param cancelable 提示框是否可取消 true 可取消， false 不可取消
	 */
	public static void showDialog(Context context, String processMsg, final boolean cancelable) {
		/**
		 * 如果没有Activity已经关闭的话，就不用关闭了
		 */
		if (ActivityUtils.isDestroyed(context)) {
			return;
		}
		if (null != progressDialog) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			progressDialog = null;
		}
		progressDialog = new Dialog(context, R.style.NobackDialog);
		View view = View.inflate(context, R.layout.dialog_loading, null);
		TextView loading_txt = (TextView) view.findViewById(R.id.loading_txt);
		loading_txt.setText(processMsg);
		progressDialog.setContentView(view);
		WindowManager.LayoutParams params = progressDialog.getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.MATCH_PARENT;
		progressDialog.getWindow().setAttributes(params);
		progressDialog.setCancelable(cancelable);
		progressDialog.show();

	}

	/**
	 * 设置对话框取消监听
	 * 
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:43:37
	 * @updateTime 2014年4月19日,下午2:43:37
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 */
	public static void setOnDismissListener(OnDismissListener listener) {
		if (null != progressDialog) {
			progressDialog.setOnDismissListener(listener);
		}
	}

	/**
	 * 关闭加载提示框
	 * 
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:25:46
	 * @updateTime 2014年4月19日,下午2:25:46
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public static void dismissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			/**
			 * 如果没有Activity已经关闭的话，就不用关闭了
			 */
			if (ActivityUtils.isDestroyed(progressDialog.getContext())) {
				return;
			}
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
