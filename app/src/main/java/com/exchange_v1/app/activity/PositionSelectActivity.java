package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.PositionBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.PositionBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ISDoubleClickUtils;
import com.exchange_v1.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

//位置选择
public class PositionSelectActivity extends BaseActivity implements View.OnClickListener {

    private Spinner spLocation;
    private TextView tvSubmit;
    private List<PositionBean> positionList;
    private ArrayAdapter<String> arr_adapter;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_position_select;
    }

    @Override
    protected void findViews() {
        spLocation = (Spinner) findViewById(R.id.sp_location);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("位置选择");

    }

    @Override
    protected void widgetListener() {
        tvSubmit.setOnClickListener(this);
    }


    @Override
    protected void init() {
        getPosition();

    }

    private void getPosition() {
        PositionBiz.getPosition(context, new RequestHandle() {

            @Override
            public void onSuccess(ResponseBean result) {
                positionList = result.getArraylist(PositionBean.class);
                setSpanerView();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
            }

        });
    }

    private void setSpanerView() {
        ArrayList<String> data_list = new ArrayList<>();
        for (PositionBean bean:positionList){
            data_list.add(bean.getName());
        }

        //适配器
        arr_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spLocation.setAdapter(arr_adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (!ISDoubleClickUtils.isDoubleClick()) {//防止多次点击
                    commitPosition();
                }
                break;
            default:
                break;
        }
    }

    private void commitPosition() {
        int selectedItemPosition = spLocation.getSelectedItemPosition();
        if (selectedItemPosition>0){
            PositionBean bean = positionList.get(selectedItemPosition);

            PositionBiz.setPosition(context, bean.getId(), new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    ToastUtil.showToast(context,"设置地区成功！");
                    finish();
                }

                @Override
                public void onFail(ResponseBean result) {
                    ToastUtil.showToast(context,result.getInfo());
                }
            });
        }

    }
}
