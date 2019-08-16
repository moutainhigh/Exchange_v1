package com.exchange_v1.app.interf;


/**
 * 密码支付监听
 * 
 * @Description
 * @author 綦巍
 * @date 2015-8-28
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
 */
public interface OnPasMaxListener {
	/** 密码满了后回调 */
	public void onPasMax(String pas);
}
