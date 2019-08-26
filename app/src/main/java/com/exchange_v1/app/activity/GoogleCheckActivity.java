package com.exchange_v1.app.activity;

import android.view.View;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;

//谷歌验证页面
public class GoogleCheckActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_google_check;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("谷歌验证");
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {

    }
}
