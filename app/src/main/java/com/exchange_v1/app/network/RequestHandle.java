package com.exchange_v1.app.network;


import com.exchange_v1.app.bean.ResponseBean;

public abstract class RequestHandle implements MyRequestHandle {

	@Override
	public void onCancel() {

	}

	@Override
	public ResponseBean getRequestCache() {
		return null;
	}

	@Override
	public void onRequestCache(ResponseBean result) {

	}

}
