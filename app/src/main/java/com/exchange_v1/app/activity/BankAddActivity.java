package com.exchange_v1.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.EqueitmentBindBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

/**
 * 绑定银行卡界面
 *
 */
public class BankAddActivity extends BaseActivity implements View.OnClickListener{

    private EditText etName;
    private EditText etIdCard;
    private EditText etIdentCard;
    private TextView tvSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bank_add;
    }

    @Override
    protected void findViews() {
        etName = (EditText) findViewById(R.id.et_name);
        etIdCard = (EditText) findViewById(R.id.et_id_card);
        etIdentCard = (EditText) findViewById(R.id.et_ident_card);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("绑定银行卡");
        //身份证号码
        String cardNo = TApplication.getMineUserInfo().getCardNo();

        //回显数据
        Bundle bundle = getBundle();
        if (bundle!=null){
            EqueitmentBindBean bean = (EqueitmentBindBean) bundle.get(FieldConfig.intent_bean);
            etName.setText(bean.getName());
            etIdCard.setText(bean.getBankCard());
            etIdentCard.setText(cardNo);
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
                bindBank();
                break;
            default:
                break;
        }
    }

    //绑定银行卡接口
    private void bindBank() {
        String name = etName.getText().toString().trim();
        String card = etIdCard.getText().toString().trim();
        String ident = etIdentCard.getText().toString().trim();
        if (!StringUtil.isEmpty(name)&&!StringUtil.isEmpty(card)&&!StringUtil.isEmpty(ident)){
            UserBiz.bankValid(context, card, name, ident, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    ToastUtil.showToast(context,"绑定成功！");
                    finish();
                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }else {
            ToastUtil.showToast(context,"输入框不能为空");
        }
    }
}
