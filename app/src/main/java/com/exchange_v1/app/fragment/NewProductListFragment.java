package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseFragment;

/**
 * 产品列表
 */

public class NewProductListFragment extends BaseFragment {
    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_product, null);
    }

    @Override
    protected void findViews() {

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
