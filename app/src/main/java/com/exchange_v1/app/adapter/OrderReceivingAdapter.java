package com.exchange_v1.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.activity.OrderReceivingDetailActivity;
import com.exchange_v1.app.bean.OrderReceivingBean;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.IntentUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;

public class OrderReceivingAdapter extends BaseAdapter {
    private List<OrderReceivingBean> list;
    private Context context;

    public OrderReceivingAdapter(Context context, List<OrderReceivingBean> list) {
        //去重
        Lists.newArrayList(Sets.newHashSet(list));
        this.context = context;
        this.list = list;
    }

    public List<OrderReceivingBean> getList() {
        return list;
    }

    public void setList(List<OrderReceivingBean> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderReceivingAdapter.itemHolder holder;
        OrderReceivingBean bean = list.get(position);
        if (convertView == null) {
            holder = new OrderReceivingAdapter.itemHolder();
            convertView = View.inflate(context, R.layout.item_jpush_order_ing, null);

            holder.tvOrderId = (TextView) convertView.findViewById(R.id.tv_order_id);
            holder.tvOrderTime = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.tvOrderAmount = (TextView) convertView.findViewById(R.id.tv_order_amount);
//            holder.tvAffirm = (TextView) convertView.findViewById(R.id.tv_affirm);
            convertView.setTag(holder);
        } else {
            holder = (OrderReceivingAdapter.itemHolder) convertView.getTag();
        }

        //数据填充
        holder.tvOrderId.setText("订单号："+bean.getSystemNo());
        holder.tvOrderTime.setText(bean.getCreateTime());
        holder.tvOrderAmount.setText("￥ "+bean.getPaymentMoney()+"");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(FieldConfig.intent_bean,bean);
                IntentUtil.gotoActivity(context, OrderReceivingDetailActivity.class,bundle);

//                LayoutInflater LayoutInflater =
//                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = LayoutInflater.inflate(R.layout.order_ing_item_dialog, null);
//                TextView account = view.findViewById(R.id.tv_account);
//                TextView orderNo = view.findViewById(R.id.tv_orderNo);
//                TextView tvSubmit = view.findViewById(R.id.tv_submit);
//                TextView tvOrder = view.findViewById(R.id.tv_order);
//                TextView tvTime = view.findViewById(R.id.tv_time);
//                TextView tvName = view.findViewById(R.id.tv_name);
//                TextView orderId = view.findViewById(R.id.tv_orderId);
//                TextView receptiveId = view.findViewById(R.id.tv_receptive_id);
//                TextView tvType = view.findViewById(R.id.tv_type);
//
//                account.setText(TApplication.getMineUserInfo().getAccount());
//                orderNo.setText(bean.getId()+"/"+bean.getSystemNo());
//                tvOrder.setText(bean.getId());
//                tvTime.setText(bean.getCreateTime());
//                tvName.setText(TApplication.getMineUserInfo().getCardName());
//                orderId.setText(bean.getId());
//                receptiveId.setText(bean.getSystemNo());
//                String codeType = bean.getPaymentType();
//                if ("ALI_BY".equals(codeType)){//支付宝
//                    tvType.setText("支付宝");
//                }else if ("WX_BY".equals(codeType)){
//                    tvType.setText("微信");
//                }else if ("JH_BY".equals(codeType)){
//                    tvType.setText("QQ");
//                }
//
//                MyDialog mMyDialog = new MyDialog(context, 0, 0, view, R.style.DialogTheme);
//                mMyDialog.setCancelable(true);
//                mMyDialog.show();
//
//                tvSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        LayoutInflater LayoutInflater2 =
//                                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        View view2= LayoutInflater2.inflate(R.layout.order_ing_confirm_dialog, null);
//                        MyDialog mMyDialog2 = new MyDialog(context, 0, 0, view2, R.style.DialogTheme);
//
//                        EditText amount = view2.findViewById(R.id.et_amout);
//                        TextView cancelBtn = view2.findViewById(R.id.cancel_btn);
//                        TextView confirmBtn = view2.findViewById(R.id.confirm_btn);
//
//                        confirmBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String trimAmount = amount.getText().toString().trim();
//                                double aDouble = 0;
//                                if (!StringUtils.isEmpty(trimAmount)){
//                                    aDouble = Double.parseDouble(trimAmount);
//                                }
//
//                                if (aDouble == bean.getPaymentMoney()){
//                                    Logger.i("进行中订单确认："+bean.getId());
//                                    //进行中确认
//                                    grabOrdercComfirm(context, bean.getId(),mMyDialog2);
//                                }else {
//                                    ToastUtil.showToast(context,"输入的金额与订单金额不相同");
//                                }
//                            }
//                        });
//
//                        cancelBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mMyDialog2.dismiss();
//                            }
//                        });
//
//
//                        mMyDialog2.setCancelable(true);
//                        mMyDialog2.show();
//
//                        mMyDialog.dismiss();
//                    }
//                });
            }
        });


//        holder.tvAffirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logger.i("进行中订单确认："+bean.getSystemNo());
//                    //进行中确认
//                grabOrdercComfirm(context, bean.getSystemNo());
//
//            }
//        });

        return convertView;
    }

//    private void grabOrdercComfirm(Context context, String systemNo, MyDialog mMyDialog2) {
//        if (!StringUtil.isEmpty(systemNo)){
//            OrderBiz.GrabOrderConfirm(context, systemNo, new RequestHandle() {
//                @Override
//                public void onSuccess(ResponseBean result) {
//                    ToastUtil.showToast(context,"已确认");
//                    mMyDialog2.dismiss();
//
//                    //刷新
//                    Intent intent = new Intent();
//                    intent.setAction(BroadcastFilters.ACTION_ORDER_ING_REFRESH);
//                    context.sendBroadcast(intent);
//                }
//
//                @Override
//                public void onFail(ResponseBean result) {
//                    Integer status = result.getStatus();
//                    ToastUtil.showToast(context,result.getInfo());
//                }
//
//            });
//        }
//    }

    private class itemHolder {

        private TextView tvOrderId;
        private TextView tvAffirm;
        private TextView tvOrderTime;
        private TextView tvOrderAmount;

    }
}
