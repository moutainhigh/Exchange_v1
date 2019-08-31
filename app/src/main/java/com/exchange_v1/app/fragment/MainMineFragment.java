package com.exchange_v1.app.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.BankBindListActivity;
import com.exchange_v1.app.activity.LoginActivity;
import com.exchange_v1.app.base.BaseFragment;
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

    private MineUserInfoBean userBean;
    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_mine, null);
    }

    @Override
    protected void findViews() {
        tvExitLogin = findViewById(R.id.tv_exit_login);
        llBank = findViewById(R.id.ll_bank);
    }


    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvExitLogin.setOnClickListener(this);
        llBank.setOnClickListener(this);
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

            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }
        });
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
