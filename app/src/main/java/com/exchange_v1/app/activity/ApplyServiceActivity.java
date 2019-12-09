package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.StringUtils;
import com.exchange_v1.app.utils.ToastUtil;

//申请服务商
public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener {
    private EditText etQq;
    private EditText etPhone;
    private TextView tvSubmit;
    private TextView tvAccountMount;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apply_service;
    }

    @Override
    protected void findViews() {
        etQq = (EditText) findViewById(R.id.et_qq);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        tvAccountMount = (TextView) findViewById(R.id.tv_account_mount);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("申请服务商");

        MineUserInfoBean mineUserInfo = TApplication.getMineUserInfo();
        if (mineUserInfo.getBalance()<50000.00){
            tvAccountMount.setText("您的余额不足50,000 DDB，请先 充值");
            //发广播跳转到充值模块？

        }else {
            tvAccountMount.setText("余额为："+mineUserInfo.getBalance()+" DDB");
        }

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
                commitData();
                break;
            default:
                break;
        }
    }

    //提交数据到后台
    private void commitData() {
        String qq = etQq.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (!StringUtils.isEmpty(qq)&&!StringUtils.isEmpty(phone)){

            UserBiz.servicesRequest(context,qq,phone, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {

                    ToastUtil.showToast(context,"申请成功");
                    finish();
                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }else {
            ToastUtil.showToast(context,"输入框内容不能为空");
        }

    }

}
