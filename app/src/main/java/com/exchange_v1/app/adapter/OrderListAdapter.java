package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.bean.OrderItemBean;
import com.exchange_v1.app.utils.ToastUtil;

import java.util.List;

public class OrderListAdapter extends BaseAdapter {

    private List<OrderItemBean> list;
    private Context context;

    public OrderListAdapter(Context context, List<OrderItemBean> list) {
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final itemHolder holder;
        OrderItemBean bean = list.get(position);
        if (convertView == null) {
            holder = new itemHolder();
            convertView = View.inflate(context, R.layout.item_order_list, null);
            holder.tvGotMoney = (TextView) convertView.findViewById(R.id.tv_got_money);
            holder.tvOrderMoney = (TextView) convertView.findViewById(R.id.tv_order_money);
            holder.tvPic = (TextView) convertView.findViewById(R.id.tv_pic);
            holder.tvCardName = (TextView) convertView.findViewById(R.id.tv_card_name);
            holder.tvCardType = (TextView) convertView.findViewById(R.id.tv_card_type);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvOrderInfo = (TextView) convertView.findViewById(R.id.tv_order_info);
            convertView.setTag(holder);
        } else {
            holder = (itemHolder) convertView.getTag();
        }

        holder.tvGotMoney.setText(bean.getTvGotMoney());
        holder.tvOrderMoney.setText(bean.getTvOrderMoney());
        holder.tvCardName.setText(bean.getTvCardName());
        holder.tvCardType.setText(bean.getTvCardType());
        holder.tvOrderInfo.setText(bean.getTvOrderInfo());
        holder.tvTime.setText(bean.getTvTime());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(context,"点击了条目"+position);
            }
        });
        return convertView;
    }

    private class itemHolder {

        private TextView tvGotMoney;
        private TextView tvOrderMoney;
        private TextView tvPic;
        private TextView tvCardName;
        private TextView tvCardType;
        private TextView tvTime;
        private TextView tvOrderInfo;

    }
}
