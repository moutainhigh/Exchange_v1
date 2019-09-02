package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;

//申请服务商
public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener {
    private EditText etQq;
    private EditText etPhone;
    private TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apply_service;
    }

    @Override
    protected void findViews() {
        etQq = (EditText) findViewById(R.id.et_qq);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("申请服务商");
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
