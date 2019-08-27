package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ISDoubleClickUtils;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

//注册页面
public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvOpenLogin;
    private TextView tvSubmit;
    private TextView tvSendMsg;
    private EditText etName;
    private EditText etId;
    private EditText etPhone;
    private EditText etMsgCode;
    private EditText etPassWorld;
    private EditText etComforPassworld;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void findViews() {
        tvOpenLogin = (TextView) findViewById(R.id.tv_open_login);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        etName = (EditText) findViewById(R.id.et_name);
        etId = (EditText) findViewById(R.id.et_id);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etMsgCode = (EditText) findViewById(R.id.et_msg_code);
        tvSendMsg = (TextView) findViewById(R.id.tv_send_msg);
        etPassWorld = (EditText) findViewById(R.id.et_passworld);
        etComforPassworld = (EditText) findViewById(R.id.et_comfor_passworld);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvOpenLogin.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvSendMsg.setOnClickListener(this);
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
                if (!ISDoubleClickUtils.isDoubleClick()) {//防止多次点击
                    register();
                }
                break;
            case R.id.tv_send_msg:
                sendCode();
                break;
            default:
                break;
        }
    }

    private void sendCode() {
        String phone = etPhone.getText().toString().trim();
        if (StringUtil.isEmpty(phone)){
            ToastUtil.showToast(context,"手机号不能为空");
        }else {
            UserBiz.sendMSG(context, phone, new RequestHandle() {
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

    private void register() {
        String name = etName.getText().toString().trim();
        String id = etId.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String msgCode = etMsgCode.getText().toString().trim();
        String pwd = etPassWorld.getText().toString().trim();
        String comforPwd = etComforPassworld.getText().toString().trim();

        UserBiz.register(context, phone, pwd, comforPwd, msgCode, name, id, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(context,"注册成功！");
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }
}
