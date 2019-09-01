package com.exchange_v1.app.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.BankBindListActivity;
import com.exchange_v1.app.activity.CoinDetailActivity;
import com.exchange_v1.app.activity.LoginActivity;
import com.exchange_v1.app.activity.OrderListActivity;
import com.exchange_v1.app.activity.ReceiveQRCodeActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.ToastUtil;


/**
 * 我的账户
 *
 */
public class MainMineFragment extends BaseFragment implements OnClickListener {

    private TextView tvExitLogin;
    private LinearLayout llBank;
    private TextView tvUserName;
    private TextView tvUserId;
    private LinearLayout llActiveAccount;
    private TextView tvBalance;
    private TextView btCoinDetail;
    private RelativeLayout rlHositoryOrder;
    private LinearLayout llAlipay;
    private LinearLayout llWechatPay;


    private MineUserInfoBean userBean;
    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_mine, null);
    }

    @Override
    protected void findViews() {
        tvExitLogin = findViewById(R.id.tv_exit_login);
        llBank = findViewById(R.id.ll_bank);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserId = findViewById(R.id.tv_user_id);
        llActiveAccount = findViewById(R.id.ll_active_account);
        tvBalance = findViewById(R.id.tv_balance);
        btCoinDetail = findViewById(R.id.bt_coin_detail);
        rlHositoryOrder = findViewById(R.id.rl_hository_order);
        llAlipay = findViewById(R.id.ll_alipay);
        llWechatPay = findViewById(R.id.ll_wechatPay);

    }


    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvExitLogin.setOnClickListener(this);
        llBank.setOnClickListener(this);
        llActiveAccount.setOnClickListener(this);
        btCoinDetail.setOnClickListener(this);
        rlHositoryOrder.setOnClickListener(this);
        llAlipay.setOnClickListener(this);
        llWechatPay.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void init() {
        getUserInfo();
    }

    private void getUserInfo() {
        UserBiz.userInfo(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                userBean = (MineUserInfoBean) result.getObject();
                //保存用户登录信息
                TApplication.setMineUserInfo(userBean);
                setUserUI();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }

    /**
     * 设置UI
     */
    private void setUserUI() {
        tvUserName.setText(userBean.getAccount());
        tvUserId.setText("ID:"+userBean.getMobile());
        tvBalance.setText(userBean.getBalance()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_login:
                IntentUtil.gotoActivity(context, LoginActivity.class);
                break;
            case R.id.ll_bank:
                IntentUtil.gotoActivity(context, BankBindListActivity.class);
                break;
            case R.id.ll_active_account://激活账户

                break;
            case R.id.bt_coin_detail://游戏币明细
                IntentUtil.gotoActivity(context, CoinDetailActivity.class);

                break;
            case R.id.rl_hository_order://历史订单
                IntentUtil.gotoActivity(context, OrderListActivity.class);

                break;
            case R.id.ll_alipay://支付宝
                IntentUtil.gotoActivity(context, ReceiveQRCodeActivity.class);
                break;
            case R.id.ll_wechatPay://微信
                IntentUtil.gotoActivity(context, ReceiveQRCodeActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
