package com.exchange_v1.app.interf;

import android.view.View;

/**
 * Adapter的点击监听
 * 
 */
public interface AdapterListener {
	/** 当item某条目某控件被点击 */
	public void onItemClick(int position, View v);
}
