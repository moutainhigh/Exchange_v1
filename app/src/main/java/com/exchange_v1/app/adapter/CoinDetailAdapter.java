package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.bean.CoinDetailBean;

import java.util.List;

public class CoinDetailAdapter extends BaseAdapter {

    private List<CoinDetailBean> list;
    private Context context;

    public CoinDetailAdapter(Context context, List<CoinDetailBean> list) {
        this.context = context;
        this.list = list;
    }

    public List<CoinDetailBean> getList() {
        return list;
    }

    public void setList(List<CoinDetailBean> list) {
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
        CoinDetailBean bean = list.get(position);
        if (convertView == null) {
            holder = new itemHolder();
            convertView = View.inflate(context, R.layout.item_coin_detail_list, null);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tvNumber1 = (TextView) convertView.findViewById(R.id.tv_ID);
            holder.tvBalance = (TextView) convertView.findViewById(R.id.tv_balance);
            holder.tvNumber2 = (TextView) convertView.findViewById(R.id.tv_number2);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (itemHolder) convertView.getTag();
        }

        String walletChange = bean.getWalletChange();
        if ("PLEDGE".equals(walletChange)){
            holder.tvType.setText("押金");
        }else if ("RECHARGE".equals(walletChange)){
            holder.tvType.setText("充值");
        }else if ("STOCK".equals(walletChange)){
            holder.tvType.setText("佣金");
        }else if ("PAYMENT".equals(walletChange)){
            holder.tvType.setText("订单");
        }else if ("BROKERAGE".equals(walletChange)){
            holder.tvType.setText("提现");
        }

        holder.tvMoney.setText(""+bean.getAmount());
        holder.tvNumber1.setText("流水号: "+bean.getId());
        holder.tvBalance.setText("余额: "+bean.getAfterAmount());
        holder.tvNumber2.setText("变动前钱包余额："+bean.getBeforAmount());
        holder.tvTime.setText(bean.getCreateTime());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showToast(context,"点击了条目"+position);
//            }
//        });
        return convertView;
    }

    private class itemHolder {

        private TextView tvType;
        private TextView tvMoney;
        private TextView tvNumber1;
        private TextView tvBalance;
        private TextView tvNumber2;
        private TextView tvTime;

    }
}
