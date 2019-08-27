package com.exchange_v1.app.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.ForgetPwdActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.MainActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

public class LoginPhoneFragment extends BaseFragment implements View.OnClickListener {


    private TextView tvSubmit;
    private TextView tvForgetPwd;
    private EditText etPhone;
    private EditText etPassworld;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_login_phone;
    }

    @Override
    protected void findViews() {
        tvSubmit = view_Parent.findViewById(R.id.tv_submit);
        tvForgetPwd = view_Parent.findViewById(R.id.tv_forgetPwd);
        etPhone = view_Parent.findViewById(R.id.et_phone);
        etPassworld = view_Parent.findViewById(R.id.et_passworld);
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
        String phone = etPhone.getText().toString().trim();
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
