package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.utils.ToastUtil;

//申请搬运代理
public class ApplyCarryActivity extends BaseActivity implements View.OnClickListener {
    private EditText etQq;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etBalance;
    private TextView tvSubmit;
    private TextView tvAccountMount;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apply_carry;
    }

    @Override
    protected void findViews() {
        etQq = (EditText) findViewById(R.id.et_qq);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etBalance = (EditText) findViewById(R.id.et_balance);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        tvAccountMount = (TextView) findViewById(R.id.tv_account_mount);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("申请搬运代理");

        MineUserInfoBean mineUserInfo = TApplication.getMineUserInfo();
        tvAccountMount.setText("余额为："+mineUserInfo.getBalance()+" DDB");
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
                ToastUtil.showToast(context,"功能待开放,敬请期待");
                break;
            default:
                break;
        }
    }
}
