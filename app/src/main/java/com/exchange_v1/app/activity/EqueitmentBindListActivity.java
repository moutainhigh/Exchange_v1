package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.exchange_v1.R;
import com.exchange_v1.app.adapter.EqueitmentBindListAdapter;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.EqueitmentBindBean;
import com.exchange_v1.app.interf.AdapterListener;
import com.exchange_v1.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

//设备绑定银行卡列表
public class EqueitmentBindListActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvListview;
    private LinearLayout llAddView;
    private EqueitmentBindListAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_equitment_list;
    }

    @Override
    protected void findViews() {
        lvListview = (ListView) findViewById(R.id.lv_listview);
        llAddView = (LinearLayout) findViewById(R.id.ll_add_view);


    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("银行卡绑定");
    }

    @Override
    protected void widgetListener() {
        llAddView.setOnClickListener(this);
    }

    @Override
    protected void init() {

        List<EqueitmentBindBean> list = getData();

        mAdapter = new EqueitmentBindListAdapter(context, new AdapterListener() {
            @Override
            public void onItemClick(int position, View v) {
                ToastUtil.showToast(context,"点击了条目"+position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("dealItemInfo", mAdapter.list.get(position));
//                IntentUtil.gotoActivity(EqueitmentBindListActivity.this, DealDetailActivity.class, bundle);
            }
        }, list);

        lvListview.setAdapter(mAdapter);

    }

    private List<EqueitmentBindBean> getData() {
        List<EqueitmentBindBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EqueitmentBindBean bean = new EqueitmentBindBean();
            bean.setBankName("建设银行"+i);
            bean.setBankStatus("已审核"+i);
            bean.setName("猪猪侠"+i);
            bean.setBankCard("123123123123_"+i);
            bean.setMoney("123_"+i);
            list.add(bean);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_view:
                ToastUtil.showToast(context,"绑定银行卡按钮");
                break;
            default:
                break;
        }
    }
}
