package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.activity.ApplyCarryActivity;
import com.exchange_v1.app.activity.ApplyServiceActivity;
import com.exchange_v1.app.activity.CoinDetailActivity;
import com.exchange_v1.app.activity.EmailVerifActivity;
import com.exchange_v1.app.activity.EqueitmentAddActivity;
import com.exchange_v1.app.activity.EqueitmentBindListActivity;
import com.exchange_v1.app.activity.GoogleCheckActivity;
import com.exchange_v1.app.activity.OrderListActivity;
import com.exchange_v1.app.activity.PositionSelectActivity;
import com.exchange_v1.app.activity.ReceiveQRCodeActivity;
import com.exchange_v1.app.activity.ReceiveQRCodeAddActivity;
import com.exchange_v1.app.activity.RegisterActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.utils.IntentUtil;

/**
 * 首页
 */
public class MainHomeFragment extends BaseFragment implements View.OnClickListener {

    private TextView btn;
    private TextView btn2;
    private TextView btn3;
    private TextView btn4;
    private TextView btn5;
    private TextView btn6;
    private TextView btn7;
    private TextView btn8;
    private TextView btn9;
    private TextView btn10;
    private TextView btn11;
    private TextView btn12;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_home, null);
    }

    @Override
    protected void findViews() {
        //首页Banner图
        btn = view_Parent.findViewById(R.id.tv_btn);
        btn2 = view_Parent.findViewById(R.id.tv_btn2);
        btn3 = view_Parent.findViewById(R.id.tv_btn3);
        btn4 = view_Parent.findViewById(R.id.tv_btn4);
        btn5 = view_Parent.findViewById(R.id.tv_btn5);
        btn6 = view_Parent.findViewById(R.id.tv_btn6);
        btn7 = view_Parent.findViewById(R.id.tv_btn7);
        btn8 = view_Parent.findViewById(R.id.tv_btn8);
        btn9 = view_Parent.findViewById(R.id.tv_btn9);
        btn10 = view_Parent.findViewById(R.id.tv_btn10);
        btn11 = view_Parent.findViewById(R.id.tv_btn11);
        btn12 = view_Parent.findViewById(R.id.tv_btn12);
    }


    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
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
            case R.id.tv_btn2:
                IntentUtil.gotoActivity(context, GoogleCheckActivity.class);
                break;
            case R.id.tv_btn3:
                IntentUtil.gotoActivity(context, ReceiveQRCodeActivity.class);
                break;
            case R.id.tv_btn4:
                IntentUtil.gotoActivity(context, ReceiveQRCodeAddActivity.class);
                break;
            case R.id.tv_btn5:
                IntentUtil.gotoActivity(context, EqueitmentAddActivity.class);
                break;
            case R.id.tv_btn6:
                IntentUtil.gotoActivity(context, EqueitmentBindListActivity.class);
                break;
            case R.id.tv_btn7:
                IntentUtil.gotoActivity(context, CoinDetailActivity.class);
                break;
            case R.id.tv_btn8:
                IntentUtil.gotoActivity(context, OrderListActivity.class);
                break;
            case R.id.tv_btn9:
                IntentUtil.gotoActivity(context, ApplyCarryActivity.class);
                break;
            case R.id.tv_btn10:
                IntentUtil.gotoActivity(context, EmailVerifActivity.class);
                break;
            case R.id.tv_btn11:
                IntentUtil.gotoActivity(context, PositionSelectActivity.class);
                break;
            case R.id.tv_btn12:
                IntentUtil.gotoActivity(context, ApplyServiceActivity.class);
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
