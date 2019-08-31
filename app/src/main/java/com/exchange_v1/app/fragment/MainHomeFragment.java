package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * 首页
 */
public class MainHomeFragment extends BaseFragment implements View.OnClickListener {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private HomeVpAdapter mAdapter;
    private int currentItem = 0;

    private RadioButton radioButton;
    private boolean radioCheck;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_home, null);
    }

    @Override
    protected void findViews() {
        mFragments.add(new HomeOrderReceiveFragment());
        mFragments.add(new HomeOrderReceiveFragment());

        ViewPager vp = getView(R.id.vp);
        mAdapter = new HomeVpAdapter(getChildFragmentManager(),mFragments);
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

        radioButton = findViewById(R.id.rb_1);
    }


    @Override
    public void initGetData() {

    }


    @Override
    protected void widgetListener() {
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioCheck) {
                    radioButton.setChecked(false);
                    radioCheck = false;
                }else{
                    radioButton.setChecked(true);
                    radioCheck = true;
                }

            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }


    public class HomeVpAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> mFragments;
        private final String[] mTitles = {"接单", "进行中"};

        public HomeVpAdapter(FragmentManager childFragmentManager, ArrayList<Fragment> list) {
            super(childFragmentManager);
            this.mFragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

    }
}
