package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;

//申请服务商
public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener {
    private EditText etQq;
    private EditText etPhone;
    private TextView tvSubmit;
    private TextView tvAccountMount;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apply_service;
    }

    @Override
    protected void findViews() {
        etQq = (EditText) findViewById(R.id.et_qq);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        tvAccountMount = (TextView) findViewById(R.id.tv_account_mount);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("申请服务商");

        MineUserInfoBean mineUserInfo = TApplication.getMineUserInfo();
        tvAccountMount.setText("余额为："+mineUserInfo.getBalance()+" DDB");

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
