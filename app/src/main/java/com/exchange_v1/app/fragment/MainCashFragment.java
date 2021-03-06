package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.exchange_v1.app.R;
import com.exchange_v1.app.adapter.CashVpAdapter;
import com.exchange_v1.app.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;


/**
 * 提现页面
 */
public class MainCashFragment extends BaseFragment implements OnClickListener {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private CashVpAdapter mAdapter;
    private int currentItem = 0;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_cash, null);
    }

    @Override
    protected void findViews() {
        mFragments.add(new CashFragment());
        mFragments.add(new CashingFragment());

        ViewPager vp = getView(R.id.vp);
        mAdapter = new CashVpAdapter(getChildFragmentManager(),mFragments);
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
    public void onClick(View v) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){

        }
    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
