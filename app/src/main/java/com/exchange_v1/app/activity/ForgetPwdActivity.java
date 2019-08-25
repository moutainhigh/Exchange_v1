package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.utils.IntentUtil;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void findViews() {
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                IntentUtil.gotoActivity(context, ForgetPwdNextActivity.class);
                break;
            default:
                break;
        }
    }
}
