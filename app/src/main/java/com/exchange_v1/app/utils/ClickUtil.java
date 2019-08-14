package com.exchange_v1.app.utils;

import android.util.SparseArray;

/**
 * 处理双击事件
 * 
 * @Description
 * @author qw
 * @date 2015-6-22
 */
public class ClickUtil {

	/** 集合 */
	private SparseArray<Long> sparseArray = new SparseArray<Long>();
	/** 重复点击时间差 */
	private int timeDiffer = 1000;
	private static ClickUtil clickUtil = new ClickUtil();

	private ClickUtil() {
	}

	public static ClickUtil getInstance() {
		return clickUtil;
	}

	/**
	 * 是否在timeDiffer内重复点击
	 * 
	 * @updateTime 2015-4-14,下午6:59:01
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param i
	 * @return true重复 false没有
	 */
	public boolean noClick(Integer i) {
		Long lastTime = sparseArray.get(i);
		Long nowTime = System.currentTimeMillis();
		if (lastTime == null || nowTime - lastTime > timeDiffer) {
			sparseArray.clear();
			sparseArray.put(i, nowTime);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否在timeDiffer内重复点击
	 * 
	 * @updateTime 2015-4-14,下午6:59:01
	 * @updateAuthor 綦巍
	 * @param i
	 * @param timeDiffer
	 * @return true重复 false没有
	 */
	public boolean noClick(Integer i, int timeDiffer) {
		Long lastTime = sparseArray.get(i);
		Long nowTime = System.currentTimeMillis();
		if (lastTime == null || nowTime - lastTime > timeDiffer) {
			sparseArray.clear();
			sparseArray.put(i, nowTime);
			return false;
		} else {
			return true;
		}
	}
}