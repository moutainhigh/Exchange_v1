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
                    Logger.i("手动抢单的单号："+order);
                    //手动抢单，调用抢单接口
                    grabOrder(context, order, viewContainer,false);

                }
            });

            llView.addView(viewContainer);
            //自动抢单
            if (autoOpen){
                grabOrder(context, order, viewContainer,true);
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
     * 抢单的接口
     *
     * @param context
     * @param order 订单号
     * @param childView 添加的View
     * @param auto 是否自动抢单
     */
    private void grabOrder(Context context, String order, View childView,Boolean auto) {
        if (!StringUtil.isEmpty(order)){
            OrderBiz.GrabOrder(context, order, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    if (!auto){
                        ToastUtil.showToast(context,"抢单成功");
                    }
                    llView.removeView(childView);
                    //把单号挪到进行中去
                    Intent intent = new Intent();
                    intent.setAction(BroadcastFilters.ACTION_ORDER_ING);
                    intent.putExtra(FieldConfig.intent_str,order);
                    context.sendBroadcast(intent);
                }

                @Override
                public void onFail(ResponseBean result) {
                    Integer status = result.getStatus();
                    if (!auto){
                        ToastUtil.showToast(context,result.getInfo());
                    }

                    if (202 == status){//重新上传二维码

                    }else if (201 == status){//已被别人抢单
                        llView.removeView(childView);
                    }
                    Logger.i("抢单接口失败："+result.getInfo());
                }

            });
        }
    }

}
