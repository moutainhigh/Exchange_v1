package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseFragment;


/**
 * 我的账户
 *
 */
public class MainMineFragment extends BaseFragment implements OnClickListener {


    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_mine, null);
    }

    @Override
    protected void findViews() {

    }


    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
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
