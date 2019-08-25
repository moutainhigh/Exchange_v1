package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.utils.ToastUtil;

//重置密码第二步
public class ForgetPwdNextActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_next_password;
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
                ToastUtil.showToast(context,"请求重置密码接口...");
                break;
            default:
                break;
        }
    }
}
