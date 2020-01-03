package com.exchange_v1.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.OrderReceivingBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.OrderBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.Logger;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.StringUtils;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.view.MyDialog;

//进行中订单的详情页
public class OrderReceivingDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView account;
    private TextView orderNo;
    private TextView tvSubmit;
    private TextView tvOrder;
    private TextView tvTime;
    private TextView tvName;
    private TextView orderId;
    private TextView receptiveId;
    private TextView tvType;


    @Override
    protected int getContentViewId() {
        return R.layout.order_ing_item_dialog;
    }

    @Override
    protected void findViews() {
        account = findViewById(R.id.tv_account);
        orderNo = findViewById(R.id.tv_orderNo);
        tvSubmit = findViewById(R.id.tv_submit);
        tvOrder = findViewById(R.id.tv_order);
        tvTime = findViewById(R.id.tv_time);
        tvName = findViewById(R.id.tv_name);
        orderId = findViewById(R.id.tv_orderId);
        receptiveId = findViewById(R.id.tv_receptive_id);
        tvType = findViewById(R.id.tv_type);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("详情");

        //回显数据
        Bundle bundle = getBundle();
        if (bundle!=null){
            OrderReceivingBean bean = (OrderReceivingBean) bundle.get(FieldConfig.intent_bean);

            account.setText(TApplication.getMineUserInfo().getAccount());
            orderNo.setText(bean.getId()+"/"+bean.getSystemNo());
            tvOrder.setText(bean.getId());
            tvTime.setText(bean.getCreateTime());
            tvName.setText(TApplication.getMineUserInfo().getCardName());
            orderId.setText(bean.getId());
            receptiveId.setText(bean.getSystemNo());
            String codeType = bean.getPaymentType();
            if ("ALI_BY".equals(codeType)){//支付宝
                tvType.setText("支付宝");
            }else if ("WX_BY".equals(codeType)){
                tvType.setText("微信");
            }else if ("JH_BY".equals(codeType)){
                tvType.setText("QQ");
            }


            tvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater LayoutInflater2 =
                            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view2= LayoutInflater2.inflate(R.layout.order_ing_confirm_dialog, null);
                    MyDialog mMyDialog2 = new MyDialog(context, 0, 0, view2, R.style.DialogTheme);

                    EditText amount = view2.findViewById(R.id.et_amout);
                    TextView cancelBtn = view2.findViewById(R.id.cancel_btn);
                    TextView confirmBtn = view2.findViewById(R.id.confirm_btn);

                    confirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String trimAmount = amount.getText().toString().trim();
                            double aDouble = 0;
                            if (!StringUtils.isEmpty(trimAmount)){
                                aDouble = Double.parseDouble(trimAmount);
                            }

                            if (aDouble == bean.getPaymentMoney()){
                                Logger.i("进行中订单确认："+bean.getId());
                                //进行中确认
                                grabOrdercComfirm(context, bean.getId(),mMyDialog2);
                            }else {
                                ToastUtil.showToast(context,"输入的金额与订单金额不相同");
                            }
                        }
                    });

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMyDialog2.dismiss();
                        }
                    });


                    mMyDialog2.setCancelable(true);
                    mMyDialog2.show();

                }
            });
        }

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {

    }

    private void grabOrdercComfirm(Context context, String systemNo, MyDialog mMyDialog2) {
        if (!StringUtil.isEmpty(systemNo)){
            OrderBiz.GrabOrderConfirm(context, systemNo, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    ToastUtil.showToast(context,"已确认");
                    mMyDialog2.dismiss();

                    //刷新
                    Intent intent = new Intent();
                    intent.setAction(BroadcastFilters.ACTION_ORDER_ING_REFRESH);
                    context.sendBroadcast(intent);

                    finish();
                }

                @Override
                public void onFail(ResponseBean result) {
                    Integer status = result.getStatus();
                    ToastUtil.showToast(context,result.getInfo());
                }

            });
        }
    }

}
