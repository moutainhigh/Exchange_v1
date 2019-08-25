package com.exchange_v1.app.fragment;

import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.ForgetPwdActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.ToastUtil;

public class LoginPhoneFragment extends BaseFragment implements View.OnClickListener {


    private TextView tvSubmit;
    private TextView tvForgetPwd;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_login_phone;
    }

    @Override
    protected void findViews() {
        tvSubmit = view_Parent.findViewById(R.id.tv_submit);
        tvForgetPwd = view_Parent.findViewById(R.id.tv_forgetPwd);
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
                ToastUtil.showToast(thisA, "请求登录接口...");
                break;
            case R.id.tv_forgetPwd:
                IntentUtil.gotoActivity(thisA, ForgetPwdActivity.class);
                break;
            default:
                break;
        }
    }
}
