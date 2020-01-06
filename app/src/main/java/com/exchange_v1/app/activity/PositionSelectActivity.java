package com.exchange_v1.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.PositionBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.PositionBiz;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ISDoubleClickUtils;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.Util;

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
                String s = JSON.toJSONString(positionList);
                setSpanerView();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context, result.getInfo());
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

        int Prosition = 0;
        for (int i = 0; i < positionList.size(); i++) {
            //通过循环遍历id，获取位置
            if (TApplication.getMineUserInfo().getProvinceId().equals(positionList.get(i).getId())) {
                Prosition = i;
            }
            provinceAdapter.add(positionList.get(i).getName());
        }
        //回显
        provinceSpiner.setSelection(Prosition, true);
        provinceAdapter.notifyDataSetChanged();//刷新

        //城市
        if (Prosition != 0) {
            int cityPos = 0;
            cityAdapter.clear();
            List<PositionBean.ChildsBean> childs = positionList.get(Prosition).getChilds();
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).getC_id().equals(TApplication.getMineUserInfo().getCityId())) {
                    cityPos = j;
                }
                cityAdapter.add(childs.get(j).getC_name());
            }

            //回显城市
            citySpiner.setSelection(cityPos, true);
            cityAdapter.notifyDataSetChanged();//刷新

        }

        provinceSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = positionList.get(position);
                cityAdapter.clear();
                for (PositionBean.ChildsBean city : currentPosition.getChilds()) {
                    cityAdapter.add(city.getC_name());
                }
                cityAdapter.notifyDataSetChanged();//刷新

                setCitySpinerItemSelectedListener();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setCitySpinerItemSelectedListener() {
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
        if (selectedItemPosition > 0) {
            PositionBean bean = positionList.get(selectedItemPosition);

            if (currentPosition != null && currentChildsBean != null) {
                PositionBiz.setPosition(context, currentPosition.getId(), currentChildsBean.getC_id(), new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean result) {
                        ToastUtil.showToast(context, "设置地区成功！");
                        //设置成功后更新用户数据
                        getUserInfo();

                    }

                    @Override
                    public void onFail(ResponseBean result) {
                        ToastUtil.showToast(context, result.getInfo());
                    }
                });
            } else {
                ToastUtil.showToast(context, "获取地区信息不完整，请返回后重试");
            }
        }

    }

    private void getUserInfo() {
        UserBiz.userInfo(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                MineUserInfoBean userBean = (MineUserInfoBean) result.getObject();
                //保存用户登录信息
                TApplication.setMineUserInfo(userBean);
                //用户更新了发广播
                Intent intent = new Intent();
                intent.setAction(BroadcastFilters.ACTION_UPDATE_USER_INFO);
                Util.sendBroadcast(context, intent);
                //更新完用户信息以后再关闭
                finish();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context, result.getInfo());
            }
        });
    }
}
