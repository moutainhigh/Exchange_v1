package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.activity.ApplyCarryActivity;
import com.exchange_v1.app.activity.ApplyServiceActivity;
import com.exchange_v1.app.activity.BankBindListActivity;
import com.exchange_v1.app.activity.CoinDetailActivity;
import com.exchange_v1.app.activity.OrderListActivity;
import com.exchange_v1.app.activity.PositionSelectActivity;
import com.exchange_v1.app.activity.ReceiveQRCodeActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.DialogUtil;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


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
    private TextView tvReceptive;
    private RelativeLayout rlHositoryOrder;
    private LinearLayout llAlipay;
    private LinearLayout llWechatPay;
    private TextView tvActiveState;

    private LinearLayout llApplyService;
    private LinearLayout llApplyCarry;
    private LinearLayout llGoogleVerify;
    private LinearLayout llPosition;
    private LinearLayout llEmailVerify;
    private LinearLayout llRealName;

    private SmartRefreshLayout mainMineRefreshLayout;


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
        tvReceptive = findViewById(R.id.tv_receptive);
        rlHositoryOrder = findViewById(R.id.rl_hository_order);
        llAlipay = findViewById(R.id.ll_alipay);
        llWechatPay = findViewById(R.id.ll_wechatPay);
        tvActiveState = findViewById(R.id.tv_active_state);

        llApplyService =  findViewById(R.id.ll_apply_service);
        llApplyCarry =  findViewById(R.id.ll_apply_carry);
        llGoogleVerify =  findViewById(R.id.ll_google_verify);
        llPosition =  findViewById(R.id.ll_position);
        llEmailVerify =  findViewById(R.id.ll_email_verify);
        llRealName =  findViewById(R.id.ll_real_name);

        mainMineRefreshLayout = findViewById(R.id.main_mine_refresh_layout);
        mainMineRefreshLayout.setEnableLoadMore(false);
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

        llApplyService.setOnClickListener(this);
        llApplyCarry.setOnClickListener(this);
        llGoogleVerify.setOnClickListener(this);
        llPosition.setOnClickListener(this);
        llEmailVerify.setOnClickListener(this);
        llRealName.setOnClickListener(this);

        mainMineRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getUserInfo();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //更新用户信息
            getUserInfo();
        }
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

                if (mainMineRefreshLayout != null) {
                    mainMineRefreshLayout.finishRefresh();
                }

                //用户更新了发广播
                Intent intent = new Intent();
                intent.setAction(BroadcastFilters.ACTION_UPDATE_USER_INFO);
                Util.sendBroadcast(context, intent);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
                if (mainMineRefreshLayout != null) {
                    mainMineRefreshLayout.finishRefresh();
                }
            }
        });
    }

    /**
     * 设置UI
     */
    private void setUserUI() {
        if ("1".equals(userBean.getReceptive())){
            tvReceptive.setText("正常发单");
            tvReceptive.setBackgroundResource(R.mipmap.mine_yellow_top);
        }else {
            tvReceptive.setText("禁止接单");
            tvReceptive.setBackgroundResource(R.mipmap.mine_red_top);
        }
        if (userBean.getPledgeState() == 1){
            tvActiveState.setText("已激活");
            llActiveAccount.setEnabled(false);
        }else {
            tvActiveState.setText("激活账户");
            llActiveAccount.setEnabled(true);
        }
        tvUserName.setText(userBean.getAccount());
        tvUserId.setText("ID:"+userBean.getId());
        tvBalance.setText(userBean.getBalance()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_login:
                DialogUtil.showExitsDg(context);

                break;
            case R.id.ll_bank://绑定银行卡
                IntentUtil.gotoActivity(context, BankBindListActivity.class);
                break;
            case R.id.ll_active_account://激活账户
                activeAccount();
                break;
            case R.id.bt_coin_detail://游戏币明细
                IntentUtil.gotoActivity(context, CoinDetailActivity.class);

                break;
            case R.id.rl_hository_order://历史订单
                IntentUtil.gotoActivity(context, OrderListActivity.class);

                break;
            case R.id.ll_alipay://支付宝
                ReceiveQRCodeActivity.openActivity(context,ReceiveQRCodeActivity.ALIBY);
                break;
            case R.id.ll_wechatPay://微信
                ReceiveQRCodeActivity.openActivity(context,ReceiveQRCodeActivity.WXBY);
                break;
            case R.id.ll_apply_service://申请服务商
                IntentUtil.gotoActivity(context, ApplyServiceActivity.class);
                break;
            case R.id.ll_apply_carry://申请搬运工代理
                IntentUtil.gotoActivity(context, ApplyCarryActivity.class);
                break;
            case R.id.ll_google_verify://谷歌邮箱验证
//                IntentUtil.gotoActivity(context, GoogleCheckActivity.class);
                ToastUtil.showToast(context,"此功能暂未开放");
                break;
            case R.id.ll_position://位置选择
                IntentUtil.gotoActivity(context, PositionSelectActivity.class);
                break;
            case R.id.ll_email_verify://邮箱认证
//                IntentUtil.gotoActivity(context, EmailVerifActivity.class);
                ToastUtil.showToast(context,"此功能暂未开放");
                break;
            case R.id.ll_real_name://实名认证
                ToastUtil.showToast(context,"此功能暂未开放");
                break;
            default:
                break;
        }
    }

    /**
     * 调用用户激活接口
     */
    private void activeAccount() {
        UserBiz.active(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(context,"用户激活成功");
                tvActiveState.setText("已激活");
                llActiveAccount.setEnabled(false);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(BroadcastFilters.ACTION_UPDATE_LOGIN_USER)){
            getUserInfo();
        }
    }
}
