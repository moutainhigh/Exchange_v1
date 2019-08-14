package com.exchange_v1.app.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 * 
 * @Description
 * @author 綦巍
 * @date 2015-5-26
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
 */
public class AnimUtil {
	public static TranslateAnimation translateDown() {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
		animation.setFillAfter(true);
		animation.setDuration(1000);
		return animation;
	}

	public static TranslateAnimation translateUp() {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
		animation.setFillAfter(true);
		animation.setDuration(1000);
		return animation;
	}

	public static void translateDown(View view) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
		animation.setFillAfter(true);
		animation.setDuration(1000);
		view.setAnimation(animation);
		view.setVisibility(View.GONE);
	}

	public static void translateUp(View view) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
		animation.setFillAfter(true);
		animation.setDuration(1000);
		view.setAnimation(animation);
		view.setVisibility(View.VISIBLE);
	}

}
