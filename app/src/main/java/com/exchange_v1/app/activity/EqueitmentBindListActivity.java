package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.utils.ToastUtil;

//设备绑定银行卡列表
public class EqueitmentBindListActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvListview;
    private LinearLayout llAddView;

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
