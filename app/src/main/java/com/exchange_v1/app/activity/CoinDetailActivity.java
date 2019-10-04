package com.exchange_v1.app.activity;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.adapter.CoinDetailAdapter;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.CoinDetailBean;
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

//游戏币明细
public class CoinDetailActivity extends BaseActivity implements View.OnClickListener {

    private SmartRefreshLayout refreshLayout;
    private ListView lvListview;
    private CoinDetailAdapter mAdapter;

    private List<CoinDetailBean> coinDetailList;
    private int page = 1;
    private String limit="10";//每页条数
    private boolean isLoadMoreToast = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_coin_detail;
    }

    @Override
    protected void findViews() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.coindetail_refresh_layout);
        lvListview = (ListView) findViewById(R.id.lv_listview);
    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("游戏币明细");
    }

    @Override
    protected void widgetListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isLoadMoreToast = false;
                getCoinDetailList(true, false);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getCoinDetailList(false, false);
            }
        });
    }

    @Override
    protected void init() {
        coinDetailList = new ArrayList<CoinDetailBean>();
        mAdapter = new CoinDetailAdapter(context,coinDetailList);
        lvListview.setAdapter(mAdapter);
        //获取数据
        refreshLayout.autoRefresh();
    }

    private void getCoinDetailList(final boolean isRefresh, boolean needDialog) {
        String processText = "";
        if (needDialog) {
            processText = getString(R.string.process_dialog_wait);
        }

        WithdrawtoBiz.walletHistory(context,page+"", limit, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                coinDetailList = (List<CoinDetailBean>) result.getObject();

                if (null == coinDetailList || coinDetailList.size() < 10) {
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
                    mAdapter.getList().addAll(coinDetailList);
                    lvListview.setAdapter(mAdapter);
                } else {
                    refreshLayout.finishLoadMore();
//                    int currentPostion = mAdapter.getList().size();
                    mAdapter.getList().addAll(coinDetailList);
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
