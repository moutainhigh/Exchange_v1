package com.exchange_v1.app.base;

import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.exchange_v1.R;
import com.exchange_v1.app.adapter.ViewPagerAdapter;
import com.exchange_v1.app.utils.DisplayUtil;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.ViewUtils;

import java.util.ArrayList;

/**
 * 程序加载等待界面
 * 
 */
public class SplashActivity extends BaseActivity {
	private ViewPager viewpager_guide;
	/** 提示点 */
	private LinearLayout ll_guide;
	/** 亮点存放的Group */
	private RelativeLayout group_guide;
	/** 所有页面 */
	private ArrayList<View> list = new ArrayList<View>();
	/** 引到页 */
	private int[] colors = new int[] {R.mipmap.page_1, R.mipmap.page_2, R.mipmap.page_3};
	/** 高亮点 */
	private View view;
	/** 高亮点的属性 */
	private RelativeLayout.LayoutParams params;

	@Override
	protected int getContentViewId() {
		return R.layout.a_splash;
	}

	@Override
	protected void findViews() {
		viewpager_guide = (ViewPager) findViewById(R.id.splash_viewpager);
		ll_guide = (LinearLayout) findViewById(R.id.splash_ll);

		group_guide = (RelativeLayout) findViewById(R.id.splash_group);
		ViewUtils.setGone(group_guide);
	}

	@Override
	protected void initGetData() {
		for (int i = 0; i < colors.length; i++) {
			View view = new View(this);
			view.setBackgroundResource(R.drawable.shape_point_gray);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(
					context, 15), DisplayUtil.dip2px(context, 15));
			if (i != 0) {
				params.leftMargin = DisplayUtil.dip2px(context, 15);
			}
			params.rightMargin = 0;
			view.setLayoutParams(params);
			ll_guide.addView(view);
			View inflate = View.inflate(context, R.layout.item_viewpager_guide, null);
			// inflate.findViewById(R.id.view_guide).setBackgroundResource(colors[0]);
			if (i == (colors.length-1)) {
				inflate.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						SpUtil.getSpUtil().putSPValue(SpUtil.FIRST_INTO, false);
                        IntentUtil.gotoActivityAndFinish(context, MainActivity.class);
                    }
				});
			}
			list.add(inflate);
		}
		view = new View(this);
		view.setBackgroundResource(R.drawable.shape_point_white);
		params = new RelativeLayout.LayoutParams(DisplayUtil.dip2px(context, 15),
				DisplayUtil.dip2px(context, 15));
		view.setLayoutParams(params);
		group_guide.addView(view);
		ViewPagerAdapter vaiewPage = new ViewPagerAdapter(list);
		viewpager_guide.setAdapter(vaiewPage);

	}

	@Override
	protected boolean getStatusBar() {

		return false;
	}

	@Override
	protected void widgetListener() {
		viewpager_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
//				 findViewById(R.id.splash_btn_go).setVisibility(
//				 position == colors.length - 1 ? View.VISIBLE : View.GONE);
//				  group_guide.setVisibility(position==colors.length-1?View.GONE:View.VISIBLE);
				ViewUtils.setGone(group_guide);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				params.leftMargin = (int) (arg0 * DisplayUtil.dip2px(context, 30) + arg1
						* DisplayUtil.dip2px(context, 30));
				view.setLayoutParams(params);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		findViewById(R.id.splash_btn_go).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpUtil.getSpUtil().putSPValue(SpUtil.FIRST_INTO, false);
				IntentUtil.gotoActivityAndFinish(context, MainActivity.class);
			}
		});
	}

	@Override
	protected void init() {
		//initArea();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	int i = 0;

}