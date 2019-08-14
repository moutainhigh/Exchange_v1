package com.exchange_v1.app.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.brightoilonline.c2b_phone.adapter.PopMenuAdapter;
import com.brightoilonline.c2b_phone.bean.PopMenuItem;

import java.util.ArrayList;

/**
 * 对弹出菜单PopupWindow的封装.
 * 
 * @Description
 * @author yeqing
 * @version 1.0
 * @date 2015-4-28
 * @Copyright: Copyright (c) 2015 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 * 
 */
public abstract class PopMenuUtil {
	/** 上下文 */
	private Context mContext;
	/** 菜单项 */
	private ArrayList<PopMenuItem> mItemList;
	/** 列表适配器 */
	private BaseAdapter mAdapter;
	/** 菜单选择监听. */
	private OnItemSelectedListener mListener;
	/** 列表. */
	private ListView mListView;
	/** 弹出窗口. */
	private PopupWindow mPopupWindow;
//	private int height;

	public PopMenuUtil(Context context) {
		init(context, 0);
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
	 * @param type
	 *            通过type控制菜单item的背景色和文字颜色
	 */
	public PopMenuUtil(Context context, int type) {
		init(context, type);
	}

	/**
	 * 初始化菜单
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午11:34:40
	 * @updateTime 2015-4-30,上午11:34:40
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @param type
	 */
	private void init(Context context, int type) {
		mContext = context;
		mItemList = new ArrayList<PopMenuItem>(2);
		View view = onCreateView(context, type);
		view.setFocusableInTouchMode(true);
		mAdapter = onCreateAdapter(context, mItemList, type);
		mListView = findListView(view);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				PopMenuItem item = (PopMenuItem) mAdapter.getItem(position);
				if (mListener != null) {
					mListener.selected(view, item, position);
				}
				mPopupWindow.dismiss();
			}
		});

		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
	}


	/**
	 * 菜单的界面视图.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:33:08
	 * @updateTime 2015-4-30,上午10:33:08
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @param type
	 *            通过type区分 设置菜单的背景
	 * @return
	 */
	protected abstract View onCreateView(Context context, int type);

	/**
	 * 菜单界面视图中的列表.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:33:30
	 * @updateTime 2015-4-30,上午10:33:30
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param view
	 *            弹出框view
	 * @return
	 */
	protected abstract ListView findListView(View view);

	/**
	 * 菜单列表中的适配器.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:33:51
	 * @updateTime 2015-4-30,上午10:33:51
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @param itemList
	 *            表示所有菜单项.
	 * @param type
	 *            通过type区分 设置菜单的文字颜色.
	 * @return
	 */
	protected abstract PopMenuAdapter onCreateAdapter(Context context, ArrayList<PopMenuItem> itemList, int type);

	/**
	 * 添加菜单项.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:34:01
	 * @updateTime 2015-4-30,上午10:34:01
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *            菜单项文字内容.
	 * @param id
	 *            菜单项的ID
	 */
	public void addItem(String text, int id) {
		mItemList.add(new PopMenuItem(text, id));
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置菜单项
	 * 
	 * @version 1.0
	 * @createTime 2015-4-28,下午6:14:18
	 * @updateTime 2015-4-28,下午6:14:18
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param itemList
	 */
	public void setItemList(ArrayList<PopMenuItem> itemList) {
		if (itemList != null) {
			mItemList.clear();
			mItemList.addAll(itemList);
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 刷新菜单列表
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:34:11
	 * @updateTime 2015-4-30,上午10:34:11
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 添加菜单项.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:34:24
	 * @updateTime 2015-4-30,上午10:34:24
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param resId
	 *            菜单项文字内容的资源ID
	 * @param id
	 *            菜单项的ID.
	 */
	public void addItem(int resId, int id) {
		addItem(mContext.getString(resId), id);
	}

	/**
	 * 作为指定View的下拉控制显示.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:34:34
	 * @updateTime 2015-4-30,上午10:34:34
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param parent
	 *            所指定的View
	 */
	public void showAsDropDown(View parent) {
		if (!mPopupWindow.isShowing()) {
			mPopupWindow.showAsDropDown(parent);
		}
	}

	/**
	 * 隐藏菜单.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:34:41
	 * @updateTime 2015-4-30,上午10:34:41
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void dismiss() {
		if (mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

	/**
	 * 设置菜单选择监听.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:34:48
	 * @updateTime 2015-4-30,上午10:34:48
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 *            监听器.
	 */
	public void setOnItemSelectedListener(OnItemSelectedListener listener) {
		mListener = listener;
	}

	/**
	 * 当前菜单是否正在显示.
	 * 
	 * @version 1.0
	 * @createTime 2015-4-30,上午10:34:56
	 * @updateTime 2015-4-30,上午10:34:56
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public boolean isShowing() {
		return mPopupWindow.isShowing();
	}

	/**
	 * 菜单项选择监听接口.
	 * 
	 * @Description
	 * @author yeqing
	 * @version 1.0
	 * @date 2015-4-30
	 * @Copyright: Copyright (c) 2015 Shenzhen Utoow Technology Co., Ltd. All
	 *             rights reserved.
	 * 
	 */
	public static interface OnItemSelectedListener {
		/**
		 * 菜单被选择时的回调接口.
		 * 
		 * @version 1.0
		 * @createTime 2015-4-30,上午10:35:10
		 * @updateTime 2015-4-30,上午10:35:10
		 * @createAuthor yeqing
		 * @updateAuthor yeqing
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param view
		 *            被选择的内容的View.
		 * @param item
		 *            被选择的菜单项.
		 * @param position
		 *            被选择的位置.
		 */
		public void selected(View view, PopMenuItem item, int position);
	}

	public PopupWindow getmPopupWindow() {
		return mPopupWindow;
	}
	
}
