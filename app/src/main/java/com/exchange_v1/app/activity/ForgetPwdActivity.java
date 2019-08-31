package com.exchange_v1.app.activity;

import android.content.Context;
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

//找回密码第一步
public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvSubmit;
    private EditText etPhone;
    private EditText etCode;
    private TextView tvSendMsg;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void findViews() {
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        tvSendMsg = (TextView) findViewById(R.id.tv_send_msg);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvSubmit.setOnClickListener(this);
        tvSendMsg.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }


    @Override
    protected void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BroadcastFilters.ACTION_FORGET_PWD_CLOSE)) {
            finish();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                NextPwdActivity();
                break;
            case R.id.tv_send_msg:
                sendCode();
                break;
            default:
                break;
        }
    }

    private void NextPwdActivity() {
        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        if (!StringUtil.isEmpty(phone)&&!StringUtil.isEmpty(code)){
            Bundle bundle = new Bundle();
            bundle.putString("phone",phone);
            bundle.putString("code",code);
            IntentUtil.gotoActivity(context, ForgetPwdNextActivity.class,bundle);
        }else {
            ToastUtil.showToast(context,"手机号和验证码不能为空");
        }

    }

    private void sendCode() {
        String phone = etPhone.getText().toString().trim();
        if (StringUtil.isEmpty(phone)){
            ToastUtil.showToast(context,"手机号不能为空");
        }else {
            UserBiz.sendMSG(context, phone,"1", new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    ToastUtil.showToast(context,"短信发送成功");
                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }
    }
}
