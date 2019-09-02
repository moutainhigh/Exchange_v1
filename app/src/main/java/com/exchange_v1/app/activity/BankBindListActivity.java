package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.adapter.EqueitmentBindListAdapter;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.EqueitmentBindBean;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.interf.AdapterListener;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

//设备绑定银行卡列表
public class BankBindListActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvListview;
    private LinearLayout llAddView;
    private EqueitmentBindListAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bank_bind_list;
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
//                IntentUtil.gotoActivity(BankBindListActivity.this, DealDetailActivity.class, bundle);
            }
        }, list);

        lvListview.setAdapter(mAdapter);

    }

    private List<EqueitmentBindBean> getData() {

        MineUserInfoBean mineUserInfo = TApplication.getMineUserInfo();
        List<EqueitmentBindBean> list = new ArrayList<>();
        if (mineUserInfo!=null){
                EqueitmentBindBean bean = new EqueitmentBindBean();
                bean.setBankName(mineUserInfo.getBankName());
                if (mineUserInfo.getBankAuth() == 1){
                    bean.setBankStatus("已审核");
                }else {
                    bean.setBankStatus("未审核");
                }

                bean.setName(mineUserInfo.getCardName());
                bean.setBankCard(mineUserInfo.getBankNo());
                bean.setMoney(mineUserInfo.getBalance()+"/"+mineUserInfo.getFreezeBalance());
                list.add(bean);
        }

        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_view:
                IntentUtil.gotoActivity(context,BankAddActivity.class);
                break;
            default:
                break;
        }
    }
}
