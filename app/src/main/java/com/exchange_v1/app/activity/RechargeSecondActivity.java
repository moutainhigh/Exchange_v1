package com.exchange_v1.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.PrepareRechargeBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.RechargeBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.ToastUtil;

/**
 * 充值页面第二步
 */
public class RechargeSecondActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvMoneyTitle;
    private TextView etCard;
    private TextView etBankName;
    private TextView tvLeftCancle;
    private TextView rightConfirm;
    private PrepareRechargeBean bean;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_recharge_second;
    }

    @Override
    protected void findViews() {
        tvMoneyTitle =  findViewById(R.id.tv_money_title);
        etCard =  findViewById(R.id.et_card);
        etBankName =  findViewById(R.id.et_bank_name);
        tvLeftCancle =  findViewById(R.id.tv_left_cancle);
        rightConfirm =  findViewById(R.id.right_confirm);
    }

    @Override
    protected void initGetData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bean = (PrepareRechargeBean) bundle.getSerializable(FieldConfig.intent_bean);
            tvMoneyTitle.setText(bean.getAmount()+"");
            etCard.setText(bean.getBankNo());
            etBankName.setText(bean.getBankName());
        }
    }

    @Override
    protected void widgetListener() {
        tvLeftCancle.setOnClickListener(this);
        rightConfirm.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left_cancle://取消订单
                cancleRequest();
                break;
            case R.id.right_confirm://确认订单
                confirmRequest();
                break;
            default:
                break;
        }
    }

    private void confirmRequest() {
        RechargeBiz.confirm(context, bean.getId(), new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(context,"确认充值成功！");
                finish();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }

    private void cancleRequest() {
        RechargeBiz.cancel(context, bean.getId(), new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(context,"取消订单成功！");
                finish();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }
}
