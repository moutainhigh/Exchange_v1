package com.exchange_v1.app.utils;

import android.graphics.PointF;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 排序工具
 * 
 * @Description
 * @author qw
 * @date 2015-9-25
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
 */
public class SortUtil {
	public static void sortList(List<? extends PointF> list) {
		Comparator<PointF> comparator = new Comparator<PointF>() {
			@Override
			public int compare(PointF o1, PointF o2) {
				if (o1.x > o2.x) {
					return 1;
				}
				return -1;
			}
		};
		Collections.sort(list, comparator);
	}
}
