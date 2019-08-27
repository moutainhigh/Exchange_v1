package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.ListView;

import com.exchange_v1.R;
import com.exchange_v1.app.adapter.CoinDetailAdapter;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.CoinDetailBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

//游戏币明细
public class CoinDetailActivity extends BaseActivity implements View.OnClickListener {

    private SmartRefreshLayout refreshLayout;
    private ListView lvListview;
    private CoinDetailAdapter mAdapter;

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

    }

    @Override
    protected void init() {

        List<CoinDetailBean> list = getData();
        mAdapter = new CoinDetailAdapter(context,list);
        lvListview.setAdapter(mAdapter);
    }

    private List<CoinDetailBean> getData() {
        List<CoinDetailBean> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CoinDetailBean bean = new CoinDetailBean();
            bean.setType("收益");
            bean.setMoney("100 DDB");
            bean.setNumber1("123456788");
            bean.setBalance("1000.88 yun");
            bean.setNumber2("222 DDB");
            bean.setTime("2019.8.27 18:31:22");
            list.add(bean);
        }
        return list;
    }

    @Override
    public void onClick(View v) {

    }

}
