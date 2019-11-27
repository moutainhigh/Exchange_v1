package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class HomeOrderReceiveFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llView;

    public final String TAG = "HomeOrderReceive";

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
        String order = intent.getStringExtra(FieldConfig.intent_str);
        Boolean autoOpen = intent.getBooleanExtra(FieldConfig.intent_str2,false);

        if (intent.getAction().equals(BroadcastFilters.ACTION_ORDER)) {// 收到订单通知，展示订单
            Logger.i("订单号为："+order);
            View viewContainer = LayoutInflater.from(thisA).inflate(R.layout.item_jpush_order, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 60));
            viewContainer.setLayoutParams(layoutParams);
            //动态创建UI
            TextView tvOrderId = viewContainer.findViewById(R.id.tv_order_id);
            TextView buy = viewContainer.findViewById(R.id.tv_buy);
            tvOrderId.setText(order);
            //给父容器设置TAG
            viewContainer.setTag(order);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(context,"点击了抢单 单号："+order);
                    llView.removeView(viewContainer);
                }
            });

            llView.addView(viewContainer);
            //自动抢单
            if (autoOpen){
                grabOrder(context, order, viewContainer);
            }

        }else if (intent.getAction().equals(BroadcastFilters.ACTION_ORDER_CANCLE)){//取消生成的orderid
            Log.i(TAG,"取消订单："+order);
            int count = llView.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = llView.getChildAt(i);
                if (order.equals(view.getTag())){
                    llView.removeView(view);
                }
            }
        }

    }

    /**
     * 自动抢单的接口
     *
     * @param context
     * @param order 订单号
     * @param viewContainer 添加的View
     */
    private void grabOrder(Context context, String order, View viewContainer) {
        //自动抢单接口
        if (!StringUtil.isEmpty(order)){
            OrderBiz.GrabOrder(context, order, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    //抢单成功
                    llView.removeView(viewContainer);
                    // TODO: 2019/11/26
                    //把单号挪到进行中去，进行中的单号怎么才算结束？


                }

                @Override
                public void onFail(ResponseBean result) {
                    Integer status = result.getStatus();
                    if (202 == status){//重新上传二维码
                        ToastUtil.showToast(context,result.getInfo());
                    }else if (201 == status){//已被别人抢单
                        ToastUtil.showToast(context,result.getInfo());
                        llView.removeView(viewContainer);
                    }

                }
            });
        }
    }

}
