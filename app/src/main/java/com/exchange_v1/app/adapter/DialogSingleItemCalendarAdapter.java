package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exchange_v1.app.R;

import java.util.ArrayList;

/**
 * 对话框单选列表适配器
 * 
 *
 */
public class DialogSingleItemCalendarAdapter extends BaseAdapter {

	/** 上下文 */
	private Context context;
	/** 选项列表 */
	private ArrayList<String> list_Items;
	private int checkItem = -1;

	/**
	 * 初始化
	 * 
	 * @param context
	 *                上下文
	 * @param list_Items
	 *                选项列表
	 * @param checkItem
	 *                选中item
	 */
	public DialogSingleItemCalendarAdapter(Context context, ArrayList<String> list_Items, int checkItem) {
		this.context = context;
		this.list_Items = list_Items;
		this.checkItem = checkItem;

	}

	@Override
	public int getCount() {
		return list_Items.size();
	}

	@Override
	public Object getItem(int position) {
		return list_Items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final viewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_listview, parent, false);
			holder = new viewHolder();
			holder.txt_name = (TextView) convertView.findViewById(R.id.item_dialog_listview_txt_name);
			holder.img_Check = (ImageView) convertView.findViewById(R.id.item_dialog_listview_img_check);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		if (!holder.img_Check.isShown()) {
			holder.img_Check.setVisibility(View.VISIBLE);
		}
		holder.txt_name.setText(list_Items.get(position));

		// 显示选中项
		if (checkItem > -1 && position == checkItem) {
			holder.img_Check.setImageResource(R.mipmap.img_dialog_calendar_change);
		} else {
			holder.img_Check.setImageResource(R.mipmap.img_dialog_calendar_df);
		}

		return convertView;
	}

	public int getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(int checkItem) {
		this.checkItem = checkItem;
	}

	/**
	 * 内部容器类
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-8-2
	 * 
	 */
	private class viewHolder {

		/** 选项名称 */
		TextView txt_name;
		/** 选中图标 */
		ImageView img_Check;

	}

}
