package com.exchange_v1.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.exchange_v1.app.bean.EqueitmentBindBean;
import com.exchange_v1.app.interf.AdapterListener;

import java.util.List;

public class EqueitmentBindListAdapter extends BaseAdapter {

    private Context context;
    private AdapterListener listener;
    private List<EqueitmentBindBean> list;

    public EqueitmentBindListAdapter(Context context, AdapterListener listener,
                                     List<EqueitmentBindBean> list){

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
