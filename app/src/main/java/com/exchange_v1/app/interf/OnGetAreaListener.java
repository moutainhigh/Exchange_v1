package com.exchange_v1.app.interf;

/**
 * 获取帐号监听
 * 
 * @author liangxg
 *
 */
public interface OnGetAreaListener {

	public void onCacheGetJson(String jsonString);

	public void onHttpsGetJson(String jsonString);

}
