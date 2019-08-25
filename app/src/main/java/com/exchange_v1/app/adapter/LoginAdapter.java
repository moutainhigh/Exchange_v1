package com.exchange_v1.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LoginAdapter extends FragmentStatePagerAdapter{

    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"手机", "邮箱"};

    public LoginAdapter(FragmentManager fm) {
        super(fm);
    }

    public LoginAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
