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
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.OrderBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.DisplayUtil;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.Logger;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

/**
 * 接单中....
 */
public class HomeOrderReceivingFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llView;
    private TextView tvNone;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_order_receiving;
    }

    @Override
    protected void findViews() {
        llView = findViewById(R.id.ll_view);
        tvNone = findViewById(R.id.tv_none);
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
        String order = intent.getStringExtra(FieldConfig.intent_str);
        if (intent.getAction().equals(BroadcastFilters.ACTION_ORDER_ING)) {// 收到进行中的订单
            Logger.i("进行中的订单号为："+order);
            View viewContainer = LayoutInflater.from(thisA).inflate(R.layout.item_jpush_order_ing, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 60));
            viewContainer.setLayoutParams(layoutParams);
            //动态创建UI
            TextView tvOrderId = viewContainer.findViewById(R.id.tv_order_id);
            TextView affirm = viewContainer.findViewById(R.id.tv_affirm);
            TextView cancle = viewContainer.findViewById(R.id.tv_cancle);
            tvOrderId.setText(order);

            affirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.i("进行中订单确认："+order);
                    //进行中确认
                    grabOrdercComfirm(context, order, viewContainer);
                }
            });
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.i("进行中订单取消："+order);

                }
            });

            llView.addView(viewContainer);
            tvNone.setVisibility(View.GONE);
        }

    }

    private void grabOrdercComfirm(Context context, String order, View childView) {
        if (!StringUtil.isEmpty(order)){
            OrderBiz.GrabOrderConfirm(context, order, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    llView.removeView(childView);
                    ToastUtil.showToast(context,"已确认");
                }

                @Override
                public void onFail(ResponseBean result) {
                    Integer status = result.getStatus();
                    ToastUtil.showToast(context,result.getInfo());
                }

            });
        }
    }


}
