package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.utils.IntentUtil;

public class demoActivity extends BaseActivity implements View.OnClickListener{

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
    protected int getContentViewId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void findViews() {
//首页Banner图
        btn = (TextView) findViewById(R.id.tv_btn);
        btn2 = (TextView) findViewById(R.id.tv_btn2);
        btn3 = (TextView) findViewById(R.id.tv_btn3);
        btn4 = (TextView) findViewById(R.id.tv_btn4);
        btn5 = (TextView) findViewById(R.id.tv_btn5);
        btn6 = (TextView) findViewById(R.id.tv_btn6);
        btn7 = (TextView) findViewById(R.id.tv_btn7);
        btn8 = (TextView) findViewById(R.id.tv_btn8);
        btn9 = (TextView) findViewById(R.id.tv_btn9);
        btn10 = (TextView) findViewById(R.id.tv_btn10);
        btn11 = (TextView) findViewById(R.id.tv_btn11);
        btn12 = (TextView) findViewById(R.id.tv_btn12);
    }

    @Override
    protected void initGetData() {

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
}
