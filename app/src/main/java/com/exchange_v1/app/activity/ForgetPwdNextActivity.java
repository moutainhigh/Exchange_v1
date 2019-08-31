package com.exchange_v1.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.Util;

//重置密码第二步
public class ForgetPwdNextActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSubmit;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private String phone;
    private String code;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_next_password;
    }

    @Override
    protected void findViews() {
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
    }

    @Override
    protected void initGetData() {
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
        code = bundle.getString("code");
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
                submit();
                break;
            default:
                break;
        }
    }

    //重置密码
    private void submit() {
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (!StringUtil.isEmpty(password)&&!StringUtil.isEmpty(password)){
            UserBiz.resetPwd(context, phone, password, confirmPassword, code, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    //发广播关闭 重置密码一
                    Intent intent = new Intent();
                    intent.setAction(BroadcastFilters.ACTION_FORGET_PWD_CLOSE);
                    Util.sendBroadcast(context, intent);

                    ToastUtil.showToast(context,"重置密码成功！");
                    IntentUtil.gotoActivity(context,LoginActivity.class);
                    finish();
                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }else {
            ToastUtil.showToast(context,"重置密码不能为空");
        }
    }
}
