package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.LoginActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.utils.IntentUtil;


/**
 * 我的账户
 *
 */
public class MainMineFragment extends BaseFragment implements OnClickListener {

    private TextView tvExitLogin;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_mine, null);
    }

    @Override
    protected void findViews() {
        tvExitLogin = findViewById(R.id.tv_exit_login);
    }


    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        tvExitLogin.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_login:
                IntentUtil.gotoActivity(context, LoginActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
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
