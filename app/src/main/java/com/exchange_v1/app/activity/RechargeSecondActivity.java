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
import com.exchange_v1.app.utils.Util;

/**
 * 充值页面第二步
 */
public class RechargeSecondActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvMoneyTitle;
    private TextView tvBack;
    private TextView etCard;
    private TextView etUserName;
    private TextView etBankName;
    private TextView tvLeftCancle;
    private TextView rightConfirm;
    private PrepareRechargeBean bean;
    private TextView tvCopyCardNo;
    private TextView tvCopyUserName;
    private TextView tvCopyBank;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_recharge_second;
    }

    @Override
    protected void findViews() {
        tvMoneyTitle =  findViewById(R.id.tv_money_title);
        tvBack =  findViewById(R.id.tv_back);
        etCard =  findViewById(R.id.et_card);
        etUserName =  findViewById(R.id.et_user_name);
        etBankName =  findViewById(R.id.et_bank_name);
        tvLeftCancle =  findViewById(R.id.tv_left_cancle);
        rightConfirm =  findViewById(R.id.right_confirm);

        tvCopyCardNo = (TextView) findViewById(R.id.tv_copy_cardNo);
        tvCopyUserName = (TextView) findViewById(R.id.tv_copy_user_name);
        tvCopyBank = (TextView) findViewById(R.id.tv_copy_bank);
    }

    @Override
    protected void initGetData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bean = (PrepareRechargeBean) bundle.getSerializable(FieldConfig.intent_bean);
            tvMoneyTitle.setText(bean.getAmount()+"");
            etCard.setText(bean.getBankNo());
            etUserName.setText(bean.getCardName());
            etBankName.setText(bean.getBankName());
        }
    }

    @Override
    protected void widgetListener() {
        tvBack.setOnClickListener(this);
        tvLeftCancle.setOnClickListener(this);
        rightConfirm.setOnClickListener(this);

        tvCopyCardNo.setOnClickListener(this);
        tvCopyUserName.setOnClickListener(this);
        tvCopyBank.setOnClickListener(this);
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
            case R.id.tv_back://返回
                cancleRequest();
                break;
            case R.id.tv_copy_cardNo://复制卡号
                if (Util.copy(context,etCard.getText().toString().trim())){
                    ToastUtil.showToast(context,"卡号已复制");
                }else {
                    ToastUtil.showToast(context,"复制失败");
                }
                break;
            case R.id.tv_copy_user_name://复制姓名
                if (Util.copy(context,etUserName.getText().toString().trim())){
                    ToastUtil.showToast(context,"姓名已复制");
                }else {
                    ToastUtil.showToast(context,"复制失败");
                }
                break;
            case R.id.tv_copy_bank://复制银行
                if (Util.copy(context,etBankName.getText().toString().trim())){
                    ToastUtil.showToast(context,"银行已复制");
                }else {
                    ToastUtil.showToast(context,"复制失败");
                }
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
                ToastUtil.showToast(context,"充值订单已取消");
                finish();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }
}
