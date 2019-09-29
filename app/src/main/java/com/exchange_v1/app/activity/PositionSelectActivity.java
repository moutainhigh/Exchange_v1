package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.PositionBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.PositionBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ISDoubleClickUtils;
import com.exchange_v1.app.utils.ToastUtil;

import java.util.List;

//位置选择
public class PositionSelectActivity extends BaseActivity implements View.OnClickListener {

    private Spinner provinceSpiner;
    private Spinner citySpiner;
    private TextView tvSubmit;
    private List<PositionBean> positionList;
    private ArrayAdapter<String> provinceAdapter;
    private ArrayAdapter<String> cityAdapter;
    private PositionBean currentPosition;
    private PositionBean.ChildsBean currentChildsBean;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_position_select;
    }

    @Override
    protected void findViews() {
        provinceSpiner = (Spinner) findViewById(R.id.sp_province_location);
        citySpiner = (Spinner) findViewById(R.id.sp_city_location);
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
        //适配器
        provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        //设置样式
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        provinceSpiner.setAdapter(provinceAdapter);
        citySpiner.setAdapter(cityAdapter);

        for (PositionBean bean:positionList){
            provinceAdapter.add(bean.getName());
        }
        provinceAdapter.notifyDataSetChanged();//刷新

        provinceSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = positionList.get(position);
                cityAdapter.clear();
                for (PositionBean.ChildsBean city:currentPosition.getChilds()){
                    cityAdapter.add(city.getC_name());
                }
                cityAdapter.notifyDataSetChanged();//刷新
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentChildsBean = currentPosition.getChilds().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        int selectedItemPosition = provinceSpiner.getSelectedItemPosition();
        if (selectedItemPosition>0){
            PositionBean bean = positionList.get(selectedItemPosition);

            if (currentPosition!=null&&currentChildsBean!=null){
                PositionBiz.setPosition(context, currentPosition.getId(),currentChildsBean.getC_id(), new RequestHandle() {
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
}
