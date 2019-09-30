package com.exchange_v1.app.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.adapter.CashingListAdapter;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.bean.CashingBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.WithdrawtoBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现中
 */
public class CashingFragment extends BaseFragment implements View.OnClickListener {

    private SmartRefreshLayout refreshLayout;
    private ListView lvListview;

    private CashingListAdapter mAdapter;
    private List<CashingBean> cashingList;

    private int page = 1;
    private String limit="10";//每页条数
    private boolean isLoadMoreToast = false;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_cashing,null);
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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isLoadMoreToast = false;
                getCashingList(true, false);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getCashingList(false, false);
            }
        });
    }

    @Override
    protected void init() {
        cashingList = new ArrayList<CashingBean>();
        mAdapter = new CashingListAdapter(context,cashingList);
        lvListview.setAdapter(mAdapter);
        //获取数据
        refreshLayout.autoRefresh();
    }

    private void getCashingList(final boolean isRefresh, boolean needDialog) {
        String processText = "";
        if (needDialog) {
            processText = getString(R.string.process_dialog_wait);
        }

        WithdrawtoBiz.brokerageList(context,page+"", limit, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                cashingList = (List<CashingBean>) result.getObject();

                if (null == cashingList || cashingList.size() < 10) {
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
                    mAdapter.getList().addAll(cashingList);
                    lvListview.setAdapter(mAdapter);
                } else {
                    refreshLayout.finishLoadMore();
//                    int currentPostion = mAdapter.getList().size();
                    mAdapter.getList().addAll(cashingList);
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
