package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.bean.EqueitmentBindBean;
import com.exchange_v1.app.interf.AdapterListener;

import java.util.List;

public class EqueitmentBindListAdapter extends BaseAdapter {

    private Context context;
    private AdapterListener listener;
    private List<EqueitmentBindBean> list;

    public EqueitmentBindListAdapter(Context context, AdapterListener listener,
                                     List<EqueitmentBindBean> list) {
        super();
        this.context = context;
        this.listener = listener;
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

    //复用对象内容
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final itemHolder holder;
        EqueitmentBindBean bean = list.get(position);
        if (convertView == null) {
            holder = new itemHolder();
            convertView = View.inflate(context, R.layout.item_equitment_bind_list, null);
            holder.tvBankName = (TextView) convertView.findViewById(R.id.tv_bank_name);
            holder.tvBankStatus = (TextView) convertView.findViewById(R.id.tv_bank_status);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvBankCard = (TextView) convertView.findViewById(R.id.tv_bank_card);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            holder.btCard = (TextView) convertView.findViewById(R.id.bt_card);
            holder.btModifiy = (TextView) convertView.findViewById(R.id.bt_modifiy);
            holder.btDisable = (TextView) convertView.findViewById(R.id.bt_disable);
            holder.btDelete = (TextView) convertView.findViewById(R.id.bt_delete);
            convertView.setTag(holder);
        } else {
            holder = (itemHolder) convertView.getTag();
        }

        holder.tvBankName.setText(bean.getBankName());
        holder.tvBankStatus.setText(bean.getBankStatus());
        holder.tvName.setText(bean.getName());
        holder.tvBankCard.setText(bean.getBankCard());
        holder.tvMoney.setText(bean.getMoney());

        holder.btCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btModifiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position, v);
            }
        });
        return convertView;
    }

    private class itemHolder {
        public TextView tvBankName;
        public TextView tvBankStatus;
        public TextView tvName;
        public TextView tvBankCard;
        public TextView tvMoney;
        public TextView btCard;
        public TextView btModifiy;
        public TextView btDisable;
        public TextView btDelete;
    }

}
