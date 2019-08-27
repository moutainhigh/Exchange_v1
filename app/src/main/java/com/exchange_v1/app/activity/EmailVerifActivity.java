package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;

//邮箱验证接口
public class EmailVerifActivity extends BaseActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etMsgCode;
    private TextView tvSendMsg;
    private TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_email_verif;
    }

    @Override
    protected void findViews() {
        etEmail = (EditText) findViewById(R.id.et_email);
        etMsgCode = (EditText) findViewById(R.id.et_msg_code);
        tvSendMsg = (TextView) findViewById(R.id.tv_send_msg);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("邮箱验证");
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {

    }
}
