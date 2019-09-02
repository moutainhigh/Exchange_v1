package com.exchange_v1.app.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.RechargeBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

//充值item页面
public class RechargeFragment extends BaseFragment implements View.OnClickListener {

    private EditText etMoney;
    private EditText etAccount;
    private TextView tvSubmit;




    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void findViews() {
        etMoney = findViewById(R.id.et_money);
        etAccount = findViewById(R.id.et_account);
        tvSubmit = findViewById(R.id.tv_submit);
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

    private void getPrepareRechargeInfo() {
        String money = etMoney.getText().toString().trim();
        if (!StringUtil.isEmpty(money)){
            RechargeBiz.prepare(context, money, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {

                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }else {
            ToastUtil.showToast(context,"金额不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                //提交
                getPrepareRechargeInfo();
                break;
            default:
                break;
        }
    }
}
