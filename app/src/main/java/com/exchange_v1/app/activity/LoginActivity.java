package com.exchange_v1.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.adapter.LoginAdapter;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.fragment.LoginEmailFragment;
import com.exchange_v1.app.fragment.LoginPhoneFragment;
import com.exchange_v1.app.utils.IntentUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

//登录界面
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private LoginAdapter mAdapter;
    private int currentItem = 0;
    private TextView tvOpenRegister;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        Bundle bundle = getBundle();
        if (bundle != null) {
            currentItem = bundle.getInt("LoginViewPageItem", 0);
        }
        mFragments.add(new LoginPhoneFragment());
        mFragments.add(new LoginEmailFragment());

        ViewPager vp = getView(R.id.vp);
        mAdapter = new LoginAdapter(getSupportFragmentManager(),mFragments);
        //预加载页面数，最小为1
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
        tvOpenRegister = (TextView) findViewById(R.id.tv_open_register);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvOpenRegister.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_open_register:
                IntentUtil.gotoActivity(this,RegisterActivity.class);
                break;
            default:
                break;
        }
    }
}
