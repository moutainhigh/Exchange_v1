package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.Logger;

public class HomeOrderReceiveFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_order_receive;
    }

    @Override
    protected void findViews() {
        tv = findViewById(R.id.tv_order);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

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
        if (intent.getAction().equals(BroadcastFilters.ACTION_ORDER)) {// 收到订单通知，展示订单
            String order = intent.getStringExtra(FieldConfig.intent_str);
            Logger.i("订单号为："+order);
            tv.setText(order);
        }
    }
}
