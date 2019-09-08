package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.utils.DisplayUtil;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.Logger;
import com.exchange_v1.app.utils.ToastUtil;

public class HomeOrderReceiveFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_order_receive;
    }

    @Override
    protected void findViews() {
        llView = findViewById(R.id.ll_view);


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
            View viewContainer = LayoutInflater.from(thisA).inflate(R.layout.item_jpush_order, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 60));
            viewContainer.setLayoutParams(layoutParams);
            //动态创建UI
            TextView tvOrderId = viewContainer.findViewById(R.id.tv_order_id);
            TextView buy = viewContainer.findViewById(R.id.tv_buy);
            tvOrderId.setText(order);
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(context,"点击了抢单 单号："+order);
                    llView.removeView(viewContainer);
                }
            });

            llView.addView(viewContainer);
        }
    }

}
