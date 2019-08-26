package com.exchange_v1.app.network;
import com.exchange_v1.app.bean.ResponseBean;

public interface MyRequestHandle {
		/** 网络请求成功 */
		public void onSuccess(ResponseBean result);

		/** 网络请求失败 */
		public void onFail(ResponseBean result);

		/** 网络请求取消 */
		public void onCancel();

		/** 获取缓存 */
		public ResponseBean getRequestCache();

		/** 缓存 */
		public void onRequestCache(ResponseBean result);
	}