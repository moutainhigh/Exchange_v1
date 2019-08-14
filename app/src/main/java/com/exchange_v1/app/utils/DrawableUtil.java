package com.exchange_v1.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import java.util.Random;

/**
 * 获取Drawable工具
 * 
 * @Description
 * @author 綦巍
 * @date 2015-7-28
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
 */
public class DrawableUtil {
	/**
	 * 获取一个5dp圆角可以带边框的drawable
	 * 
	 * @updateTime 2015-5-11,上午11:52:14
	 * @updateAuthor 綦巍
	 * @param strokeColor 边框颜色 "#DFDFE0"
	 * @param fillColor 内部填充颜色 "#DFDFE0"
	 */
	public static GradientDrawable getRadiusDrawable(Context context, String strokeColor, String fillColor) {
		int roundRadius = dip2px(context, 5); // 5dp 圆角半径
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(Color.parseColor(fillColor));
		gd.setCornerRadius(roundRadius);
		if (strokeColor != null) {
			int strokeWidth = dip2px(context, 1); // 1dp 边框宽度
			gd.setStroke(strokeWidth, Color.parseColor(strokeColor));
		}
		return gd;
	}

	/**
	 * 获取一个圆图片
	 * 
	 * @updateTime 2015-5-11,下午4:14:56
	 * @updateAuthor 綦巍
	 * @param strokeColor 边框颜色 "#DFDFE0"
	 * @param fillColor 内部填充颜色 "#DFDFE0"
	 */
	public static GradientDrawable getOvalDrawable(Context context, String strokeColor, String fillColor) {
		GradientDrawable gd = new GradientDrawable();
		gd.setShape(GradientDrawable.OVAL);
		gd.setUseLevel(false);
		gd.setColor(Color.parseColor(fillColor));
		if (strokeColor != null) {
			int strokeWidth = dip2px(context, 1); // 1dp 边框宽度
			gd.setStroke(strokeWidth, Color.parseColor(strokeColor));
		}
		return gd;
	}

	public static GradientDrawable getSquareDrawable(Context context, String strokeColor, String fillColor) {
		GradientDrawable gd = new GradientDrawable();
		gd.setShape(GradientDrawable.RECTANGLE);
		gd.setUseLevel(false);
		gd.setColor(Color.parseColor(fillColor));
		if (strokeColor != null) {
			int strokeWidth = dip2px(context, 1); // 1dp 边框宽度
			gd.setStroke(strokeWidth, Color.parseColor(strokeColor));
		}
		return gd;
	}

	public static StateListDrawable getPressedSelector(Drawable normalBg, Drawable pressedBg) {
		StateListDrawable selector = new StateListDrawable();
		selector.addState(new int[] {}, normalBg);
		selector.addState(new int[] { android.R.attr.state_pressed }, pressedBg);
		return selector;
	}

	private static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static String getRandomColor() {
		Random rdm = new Random();
		int alpha = 0xff;// 0-255
		int red = rdm.nextInt(170) + 30;// 0-255 30--->200
		int green = rdm.nextInt(170) + 30;// 0-255
		int blue = rdm.nextInt(170) + 30;// 0-255
		int argb = Color.argb(alpha, red, green, blue);
		return "#" + Integer.toHexString(argb);
	}
}