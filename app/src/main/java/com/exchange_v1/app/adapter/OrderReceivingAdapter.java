package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.bean.OrderReceivingBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.OrderBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.Logger;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

import java.util.List;

public class OrderReceivingAdapter extends BaseAdapter {
    private List<OrderReceivingBean> list;
    private Context context;

    public OrderReceivingAdapter(Context context, List<OrderReceivingBean> list) {
        this.context = context;
        this.list = list;
    }

    public List<OrderReceivingBean> getList() {
        return list;
    }

    public void setList(List<OrderReceivingBean> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderReceivingAdapter.itemHolder holder;
        OrderReceivingBean bean = list.get(position);
        if (convertView == null) {
            holder = new OrderReceivingAdapter.itemHolder();
            convertView = View.inflate(context, R.layout.item_jpush_order_ing, null);

            holder.tvOrderId = (TextView) convertView.findViewById(R.id.tv_order_id);
            holder.tvAffirm = (TextView) convertView.findViewById(R.id.tv_affirm);
            convertView.setTag(holder);
        } else {
            holder = (OrderReceivingAdapter.itemHolder) convertView.getTag();
        }

        //数据填充
        holder.tvOrderId.setText(bean.getSystemNo());
        holder.tvAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.i("进行中订单确认："+bean.getSystemNo());
                    //进行中确认
                grabOrdercComfirm(context, bean.getSystemNo());

            }
        });

        return convertView;
    }

    private void grabOrdercComfirm(Context context, String systemNo) {
        if (!StringUtil.isEmpty(systemNo)){
            OrderBiz.GrabOrderConfirm(context, systemNo, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
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

    private class itemHolder {

        private TextView tvOrderId;
        private TextView tvAffirm;

    }
}
