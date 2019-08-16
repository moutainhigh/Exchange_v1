package com.exchange_v1.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 等待提醒
 * 
 */
public class LoadingView extends View {

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingView(Context context) {
		super(context);
		init();
	}

	private void init() {
		RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(1000);
		LinearInterpolator lin = new LinearInterpolator();
		ra.setInterpolator(lin);
		ra.setRepeatCount(Animation.INFINITE);
		startAnimation(ra);
	}
}
