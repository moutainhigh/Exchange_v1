package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.bean.CashingBean;

import java.util.List;

public class CashingListAdapter extends BaseAdapter {

    private List<CashingBean> list;
    private Context context;

    public CashingListAdapter(Context context, List<CashingBean> list) {
        this.context = context;
        this.list = list;
    }

    public List<CashingBean> getList() {
        return list;
    }

    public void setList(List<CashingBean> list) {
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
        CashingBean bean = list.get(position);
        if (convertView == null) {
            holder = new itemHolder();
            convertView = View.inflate(context, R.layout.item_cashing_list, null);

            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvState = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tvOrderNo = (TextView) convertView.findViewById(R.id.tv_orderNo);
            convertView.setTag(holder);
        } else {
            holder = (itemHolder) convertView.getTag();
        }

        //数据填充
        holder.tvMoney.setText("提现金额: "+bean.getAmount());
        holder.tvTime.setText("提现时间: "+bean.getCreateTime());
        if ("0".equals(bean.getState())){
            holder.tvState.setText("提现状态: "+"提现中");
        }else if ("1".equals(bean.getState())){
            holder.tvState.setText("提现状态: "+"已转账");
        }else if ("2".equals(bean.getState())){
            holder.tvState.setText("提现状态: "+"已拒绝");
        }

        holder.tvOrderNo.setText("提现记录ID："+ bean.getId());

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
