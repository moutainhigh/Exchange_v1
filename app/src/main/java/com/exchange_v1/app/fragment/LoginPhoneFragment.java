package com.exchange_v1.app.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.activity.ForgetPwdActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.MainActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.Util;

/**
 * 手机登录fragment
 */
public class LoginPhoneFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvSubmit;
    private TextView tvForgetPwd;
    private EditText etPhone;
    private EditText etPassworld;
    private CheckBox cbPhone;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_login_phone;
    }

    @Override
    protected void findViews() {
        tvSubmit = findViewById(R.id.tv_submit);
        tvForgetPwd = findViewById(R.id.tv_forgetPwd);
        etPhone = findViewById(R.id.et_phone);
        etPassworld = findViewById(R.id.et_passworld);
        cbPhone = findViewById(R.id.cb_phone);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvSubmit.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);
    }

    @Override
    protected void init() {
        //设置保存账号
        String account = TApplication.getAccount();
        if (!StringUtil.isEmpty(account)){
            etPhone.setText(account);
            cbPhone.setChecked(true);
        }else {
            etPhone.setText("");
            cbPhone.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                login();
                break;
            case R.id.tv_forgetPwd:
                IntentUtil.gotoActivity(thisA, ForgetPwdActivity.class);
                break;
            default:
                break;
        }
    }

    private void login() {
        final String phone = etPhone.getText().toString().trim();
        String password = etPassworld.getText().toString().trim();
        if (StringUtil.isEmpty(phone)||StringUtil.isEmpty(password)){
            ToastUtil.showToast(context,"账号密码不能为空");
        }else {
            UserBiz.login(context, phone, password, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    //登录成功保存token
                    String token = (String) result.getObject();
                    if (!StringUtil.isEmpty(token)){
                        TApplication.setToken(token);
                    }

                    //主动登录后，更新UI
                    Intent intent = new Intent();
                    intent.setAction(BroadcastFilters.ACTION_UPDATE_LOGIN_USER);
                    Util.sendBroadcast(context, intent);

                    if (cbPhone.isChecked()){
                        TApplication.setAccount(phone);
                    }

                    IntentUtil.gotoActivityAndFinish(context, MainActivity.class);
                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }
    }
}
