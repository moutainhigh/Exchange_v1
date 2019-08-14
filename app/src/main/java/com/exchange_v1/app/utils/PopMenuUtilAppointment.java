package com.exchange_v1.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.brightoilonline.c2b_phone.R;
import com.brightoilonline.c2b_phone.adapter.PopMenuAdapter;
import com.brightoilonline.c2b_phone.bean.PopMenuItem;
import com.brightoilonline.c2b_phone.widget.WrapWidthListView;

import java.util.ArrayList;

/**
 * 弹出菜单
 * 
 * @Description
 * @author yeqing
 * @version 1.0
 * @date 2015-4-28
 * @Copyright: Copyright (c) 2015 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 * 
 */
public class PopMenuUtilAppointment extends PopMenuUtil {

	public PopMenuUtilAppointment(Context context) {
		super(context);
	}

	/**
	 * 构造方法
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午11:33:52
	 * @updateTime 2015-4-30,上午11:33:52
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @param type 通过type控制菜单item的背景色和文字颜色
	 */
	public PopMenuUtilAppointment(Context context, int type) {
		super(context, type);
	}

	@Override
	protected ListView findListView(View view) {
		return (ListView) view.findViewById(R.id.menu_listview);
	}

	@SuppressLint("NewApi")
	@Override
	protected View onCreateView(Context context, int type) {
		View view = View.inflate(context, R.layout.pop_menu, null);
		WrapWidthListView listView = (WrapWidthListView) view.findViewById(R.id.menu_listview);
		LinearLayout view_pop = (LinearLayout) view.findViewById(R.id.view_pop);

		// 设置菜单背景色
		// listView.setBackgroundResource(R.drawable.pop_menu_appointment_apply);
		// listView.setBackgroundColor(context.getResources().getColor(R.color.title_bg_gray));
		// 设置菜单背景色
		// listView.setBackground(context.getResources().getDrawable(R.drawable.pop_menu_bg));
		// 设置菜单位置
		// view_pop.setPadding(0, 0, 0, 0);

		return view;
	}

	@Override
	protected PopMenuAdapter onCreateAdapter(Context context, ArrayList<PopMenuItem> items, int type) {
		return new PopMenuAdapter(context, items, R.color.font_content);
		// return new PopMenuAdapter(context, items, R.color.white);
		// return new PopMenuAdapter(context, items, R.color.font_content);
	}
}
