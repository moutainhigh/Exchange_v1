package com.exchange_v1.app.activity;

import android.view.View;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;

//通用收款码界面
public class ReceiveQRCodeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_receive_qrcode;
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
