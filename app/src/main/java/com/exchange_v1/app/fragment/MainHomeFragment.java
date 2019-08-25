package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.RegisterActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.utils.IntentUtil;

/**
 * 首页
 */
public class MainHomeFragment extends BaseFragment implements View.OnClickListener {

    private TextView btn;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_home, null);
    }

    @Override
    protected void findViews() {
        //首页Banner图
        btn = view_Parent.findViewById(R.id.tv_btn);
    }


    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        btn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn:
                IntentUtil.gotoActivity(context, RegisterActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }


}
