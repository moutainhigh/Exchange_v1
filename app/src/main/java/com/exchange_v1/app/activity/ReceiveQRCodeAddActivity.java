package com.exchange_v1.app.activity;

import android.view.View;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;

public class ReceiveQRCodeAddActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_qrcode;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("通用收款码");
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {

    }
}
