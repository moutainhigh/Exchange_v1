package com.exchange_v1.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.adapter.OrderListAdapter;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.bean.OrderItemBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.OrderBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment implements View.OnClickListener {

    private SmartRefreshLayout refreshLayout;
    private ListView lvListview;
    private OrderListAdapter mAdapter;

    private List<OrderItemBean> orderList;
    private String OrderType;//请求的订单类型

    private int page = 1;
    private String limit="10";//每页条数
    private boolean isLoadMoreToast = false;

    public static OrderFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("OrderType", type);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        Bundle arguments = getArguments();
        OrderType = arguments.getString("OrderType");
    }

    @Override
    protected void widgetListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isLoadMoreToast = false;
                getOrderList(true, false);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getOrderList(false, false);
            }
        });
    }

    @Override
    protected void init() {
        orderList = new ArrayList<OrderItemBean>();
        mAdapter = new OrderListAdapter(context,orderList);
        lvListview.setAdapter(mAdapter);
        //获取数据
        refreshLayout.autoRefresh();
    }

    private void getOrderList(final boolean isRefresh, boolean needDialog) {
        String processText = "";
        if (needDialog) {
            processText = getString(R.string.process_dialog_wait);
        }

        OrderBiz.getOrderList(context,processText, OrderType, "1", limit, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                orderList = (List<OrderItemBean>) result.getObject();

                if (null == orderList || orderList.size() < 10) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    if (isLoadMoreToast) {
                        ToastUtil.showToast(context, "没有更多数据了");
                    }
                    isLoadMoreToast = true;
                } else {
                    refreshLayout.finishLoadMore();
                }

                if (isRefresh) {
                    refreshLayout.finishRefresh();
                    mAdapter.getList().clear();
                    mAdapter.getList().addAll(orderList);
                    lvListview.setAdapter(mAdapter);
                } else {
                    refreshLayout.finishLoadMore();
//                    int currentPostion = mAdapter.getList().size();
                    mAdapter.getList().addAll(orderList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
