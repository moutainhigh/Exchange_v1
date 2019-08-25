package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.ToastUtil;

//注册页面
public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvOpenLogin;
    private TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void findViews() {
        tvOpenLogin = (TextView) findViewById(R.id.tv_open_login);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvOpenLogin.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_open_login:
                IntentUtil.gotoActivity(context, LoginActivity.class);
                break;
            case R.id.tv_submit:
                ToastUtil.showToast(context, "请求登录接口");
                break;
            default:
                break;
        }
    }
}
