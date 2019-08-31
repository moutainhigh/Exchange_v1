package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.exchange_v1.R;
import com.exchange_v1.app.adapter.RechargeVpAdapter;
import com.exchange_v1.app.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * 充值页面
 */

public class MainRechargeFragment extends BaseFragment {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private RechargeVpAdapter mAdapter;
    private int currentItem = 0;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_recharge, null);
    }

    @Override
    protected void findViews() {
        mFragments.add(new RechargeFragment());
        mFragments.add(new RechargeFragment());

        ViewPager vp = getView(R.id.vp);
        mAdapter = new RechargeVpAdapter(getChildFragmentManager(),mFragments);
        vp.setOffscreenPageLimit(1);
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
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
    }

    @Override
    protected void init() {

    }


    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

}
