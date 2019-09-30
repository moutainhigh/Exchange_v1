package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.bean.RechargeBean;

import java.util.List;

public class RechargeListAdapter extends BaseAdapter {

    private List<RechargeBean> list;
    private Context context;

    public RechargeListAdapter(Context context, List<RechargeBean> list) {
        this.context = context;
        this.list = list;
    }

    public List<RechargeBean> getList() {
        return list;
    }

    public void setList(List<RechargeBean> list) {
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
        final itemHolder holder;
        RechargeBean bean = list.get(position);
        if (convertView == null) {
            holder = new itemHolder();
            convertView = View.inflate(context, R.layout.item_recharge_list, null);

            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvState = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tvOrderNo = (TextView) convertView.findViewById(R.id.tv_orderNo);
            convertView.setTag(holder);
        } else {
            holder = (itemHolder) convertView.getTag();
        }

        //数据填充
        holder.tvMoney.setText("充值金额: "+bean.getAmount());
        holder.tvTime.setText("充值时间: "+bean.getCreateTime());
        if ("0".equals(bean.getState())){
            holder.tvState.setText("充值状态: "+"待转账");
        }else if ("1".equals(bean.getState())){
            holder.tvState.setText("充值状态: "+"已转账");
        }else if ("2".equals(bean.getState())){
            holder.tvState.setText("充值状态: "+"已确认");
        }else if ("3".equals(bean.getState())){
            holder.tvState.setText("充值状态: "+"已超时/已取消");
        }

        holder.tvOrderNo.setText("充值单号："+ bean.getOrderNo());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showToast(context,"点击了条目"+position);
//            }
//        });
        return convertView;
    }

    private class itemHolder {

        private TextView tvMoney;
        private TextView tvTime;
        private TextView tvState;
        private TextView tvOrderNo;

    }

}
