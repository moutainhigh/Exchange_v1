package com.exchange_v1.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.FileBiz;
import com.exchange_v1.app.network.RequestHandle;

//通用收款码界面
public class ReceiveQRCodeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlQrPng;
    private EditText etPeopleName;
    private EditText etIdCard;
    private EditText etUID;
    private TextView btSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_receive_qrcode;
    }

    @Override
    protected void findViews() {
        rlQrPng = (RelativeLayout) findViewById(R.id.rl_qr_png);
        etPeopleName = (EditText) findViewById(R.id.et_people_name);
        etIdCard = (EditText) findViewById(R.id.et_id_card);
        etUID = (EditText) findViewById(R.id.et_UID);
        btSubmit = (TextView) findViewById(R.id.bt_submit);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("通用收款码");
    }

    @Override
    protected void widgetListener() {
        btSubmit.setOnClickListener(this);
        rlQrPng.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                upLoadQrCode();
                break;
            case R.id.rl_qr_png://上传二维码按钮
                selectPng();
                break;
            default:
                break;
        }
    }

    private void selectPng() {

    }

    //上传文件接口
    private void upLoadQrCode() {
        FileBiz.uploadFile(context, "", new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {

            }

            @Override
            public void onFail(ResponseBean result) {

            }
        });
    }
}
