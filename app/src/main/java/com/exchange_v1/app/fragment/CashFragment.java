package com.exchange_v1.app.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.biz.WithdrawtoBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.Logger;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

//提现Fragment页面
public class CashFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvBalance;
    private EditText etMoney;
    private TextView tvBankName;
    private TextView tvBankCard;
    private EditText etMsgCode;
    private TextView tvSendMsg;
    private TextView tvCharge;
    private TextView tvSubmit;
    private MineUserInfoBean mineUserInfo;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_cash;
    }

    @Override
    protected void findViews() {
        tvBalance =  findViewById(R.id.tv_balance);
        etMoney =  findViewById(R.id.et_money);
        tvBankName =  findViewById(R.id.tv_bank_name);
        tvBankCard =  findViewById(R.id.tv_bank_card);
        etMsgCode =  findViewById(R.id.et_msg_code);
        tvSendMsg =  findViewById(R.id.tv_send_msg);
        tvCharge =  findViewById(R.id.tv_charge);
        tvSubmit =  findViewById(R.id.tv_submit);

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvSendMsg.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setInitUi();
        getRate();
    }

    /**
     * 请求费率的接口
     */
    private void getRate() {

        WithdrawtoBiz.withCharge(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                String rate = (String) result.getObject();
                tvCharge.setText(rate+"%");

            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }

    /**
     * 设置界面ui
     */
    private void setInitUi() {
        //通过用户数据，获取用户信息
        mineUserInfo = TApplication.getMineUserInfo();
        if (mineUserInfo == null){
            UserBiz.userInfo(context, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    mineUserInfo = (MineUserInfoBean)result.getObject();
                    setCashView();
                }

                @Override
                public void onFail(ResponseBean result) {
                    Logger.i("获取用户数据失败");
                }
            });
        }else {
            setCashView();
        }

    }


    private void setCashView() {
        //赋值
        tvBalance.setText(mineUserInfo.getBalance()+"");
        tvBankName.setText(mineUserInfo.getBankName());
        tvBankCard.setText(mineUserInfo.getBankNo());
        tvCharge.setText("1.00");//手续费
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                    submit();
                break;
            case R.id.tv_send_msg:
                    sendCode();
                break;
            default:
                break;
        }
    }

    private void sendCode() {

        UserBiz.sendMSG(context, mineUserInfo.getMobile(), "2", new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(context,"验证码已发送");
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }

    private void submit() {
        String money = etMoney.getText().toString().trim();
        if (!StringUtil.isEmpty(money)){
            WithdrawtoBiz.brokerage(context, money, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    ToastUtil.showToast(context,"提现成功");
                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }else {
            ToastUtil.showToast(context,"提现金额不能为空");
        }

    }
}
