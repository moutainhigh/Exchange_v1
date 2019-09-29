package com.exchange_v1.app.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.adapter.RechargeListAdapter;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.bean.RechargeBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.RechargeBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值中界面
 */
public class RechargeingFragment extends BaseFragment implements View.OnClickListener {

    private SmartRefreshLayout refreshLayout;
    private ListView lvListview;

    private RechargeListAdapter mAdapter;
    private List<RechargeBean> rechargeList;

    private int page = 1;
    private String limit="10";//每页条数
    private boolean isLoadMoreToast = false;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.fragment_recharging,null);
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
                getRechargeList(true, false);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getRechargeList(false, false);
            }
        });
    }

    @Override
    protected void init() {
        rechargeList = new ArrayList<RechargeBean>();
        mAdapter = new RechargeListAdapter(context,rechargeList);
        lvListview.setAdapter(mAdapter);
        //获取数据
        refreshLayout.autoRefresh();
    }

    private void getRechargeList(final boolean isRefresh, boolean needDialog) {
        String processText = "";
        if (needDialog) {
            processText = getString(R.string.process_dialog_wait);
        }

        RechargeBiz.list(context,page+"", limit, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                rechargeList = (List<RechargeBean>) result.getObject();

                if (null == rechargeList || rechargeList.size() < 10) {
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
                    mAdapter.getList().addAll(rechargeList);
                    lvListview.setAdapter(mAdapter);
                } else {
                    refreshLayout.finishLoadMore();
//                    int currentPostion = mAdapter.getList().size();
                    mAdapter.getList().addAll(rechargeList);
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
