package com.exchange_v1.app.fragment;

import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.utils.ToastUtil;

public class LoginEmailFragment extends BaseFragment implements View.OnClickListener{


    private TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_login_email;
    }

    @Override
    protected void findViews() {
        tvSubmit = view_Parent.findViewById(R.id.tv_submit);
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
                ToastUtil.showToast(thisA, "请求登录接口...");
                break;
            default:
                break;
        }
    }
}
