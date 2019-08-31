package com.exchange_v1.app.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.exchange_v1.R;
import com.exchange_v1.app.adapter.OrderVpAdapter;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.fragment.OrderFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

//订单列表页面
public class OrderListActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private OrderVpAdapter mAdapter;
    private int currentItem = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void findViews() {

        mFragments.add(new OrderFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new OrderFragment());

        ViewPager vp = getView(R.id.vp);
        mAdapter = new OrderVpAdapter(getSupportFragmentManager(),mFragments);
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(mAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        SlidingTabLayout tabLayout = getView(R.id.tl);
        tabLayout.setViewPager(vp);
        vp.setCurrentItem(currentItem);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("历史订单");
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {

    }

}
