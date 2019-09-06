package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.CashFree;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.biz.WithdrawtoBiz;
import com.exchange_v1.app.config.BroadcastFilters;
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
    private CashFree rateBean;

    private Handler handler = new Handler();
    private String editString;

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

        etMoney.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(delayRun!=null){
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();

                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 800);


            }
        });
    }

    @Override
    protected void init() {
        setCashView(TApplication.getMineUserInfo());
    }

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            getRate(editString);
        }
    };



    /**
     * 请求费率的接口
     * @param editString 输入的金额
     */
    private void getRate(String editString) {

        WithdrawtoBiz.withCharge(context, editString,new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                rateBean = (CashFree) result.getObject();
                //设置手续费
                tvCharge.setText(rateBean.getFee());
            }

            @Override
            public void onFail(ResponseBean result) {
                tvCharge.setText(result.getInfo());
            }
        });
    }

    private void setCashView(MineUserInfoBean bean) {
        if (bean!=null){
            tvBalance.setText(bean.getBalance()+"");
            tvBankName.setText(bean.getBankName());
            tvBankCard.setText(bean.getBankNo());
        }
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

        UserBiz.sendMSG(context, TApplication.getMineUserInfo().getMobile(), "2", new RequestHandle() {
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

    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(BroadcastFilters.ACTION_UPDATE_USER_INFO)) {
            Logger.i("提现收到了广播");
            setCashView(TApplication.getMineUserInfo());
        }
    }
}
