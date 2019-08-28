package com.exchange_v1.app.fragment;

import android.view.View;
import android.widget.ListView;

import com.exchange_v1.R;
import com.exchange_v1.app.adapter.OrderListAdapter;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.bean.OrderItemBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment implements View.OnClickListener {

    private SmartRefreshLayout refreshLayout;
    private ListView lvListview;
    private OrderListAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void findViews() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.order_refresh_layout);
        lvListview = (ListView) findViewById(R.id.lv_listview);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        List<OrderItemBean> list = getData();
        mAdapter = new OrderListAdapter(context,list);
        lvListview.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    private List<OrderItemBean> getData() {
        List<OrderItemBean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            OrderItemBean bean = new OrderItemBean();
            bean.setTvGotMoney("到账金额：2000.00");
            bean.setTvOrderMoney("订单金额：2000.00");
            bean.setTvCardName("建设银行卡");
            bean.setTvCardType("商户充值");
            bean.setTvTime("2019.8.27 18:31:22");
            bean.setTvOrderInfo("ID:2057  商户订单/内部订单：ssni4470");
            list.add(bean);
        }
        return list;
    }
}
