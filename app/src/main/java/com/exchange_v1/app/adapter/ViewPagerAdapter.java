package com.exchange_v1.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.exchange_v1.app.R;

import java.util.ArrayList;

/**
 * viewpager的适配器
 *
 *
 */
public class ViewPagerAdapter extends PagerAdapter {

	/** v]iew集合 */
	private ArrayList<View> views;
	private int[] colors = new int[] {R.mipmap.page_1, R.mipmap.page_2, R.mipmap.page_3};

	public ViewPagerAdapter(ArrayList<View> views) {
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(View view, int position) {
		ViewPager viewPage = (ViewPager) view;
		ImageView imageView = (ImageView) views.get(position).findViewById(R.id.view_guide);
		imageView.setImageResource(colors[position]);
		viewPage.addView(views.get(position));
		return views.get(position);
	}

	@Override
	public void destroyItem(ViewGroup group, int position, Object object) {
		ViewPager viewPage = (ViewPager) group;
		viewPage.removeView(views.get(position));
	}

}
