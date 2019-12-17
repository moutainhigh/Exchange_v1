package com.exchange_v1.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.activity.RechargeSecondActivity;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.PrepareRechargeBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.RechargeBiz;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;

//充值item页面
public class RechargeFragment extends BaseFragment implements View.OnClickListener {

    private EditText etMoney;
    private TextView tvSubmit;
    private PrepareRechargeBean prepareBean;

    private TextView tvLeftBtn;
    private TextView tvRightBtn;
    private TextView tvBankName;

    private Spinner accountSpinner;
    private ArrayAdapter<String> accountAdapter;

    private boolean leftClick = true;
    private Handler handler = new Handler();
    private String editString;

    private Boolean inputCheck = true;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void findViews() {
        etMoney = findViewById(R.id.et_money);
        tvSubmit = findViewById(R.id.tv_submit);

        tvLeftBtn = findViewById(R.id.tv_left_btn);
        tvRightBtn = findViewById(R.id.tv_right_btn);

        tvBankName = findViewById(R.id.tv_bank_name);
        accountSpinner = findViewById(R.id.sp_account);

    }

    @Override
    protected void initGetData() {


    }

    private void setSpinner() {
        String[] bankArr = new String[1];

        UserBiz.userInfo(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                MineUserInfoBean userBean = (MineUserInfoBean) result.getObject();
                //保存用户登录信息
                TApplication.setMineUserInfo(userBean);

                bankArr[0] = userBean.getBankNo();

                //适配器
                accountAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,bankArr);
                //设置样式
                accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                accountSpinner.setAdapter(accountAdapter);
//        accountAdapter.add(TApplication.getMineUserInfo().getBankNo());
                //默认选择第一个
                accountSpinner.setSelection(0,true);


                tvBankName.setText(userBean.getBankName());
            }

            @Override
            public void onFail(ResponseBean result) {
            }
        });


    }

    @Override
    protected void widgetListener() {
        tvSubmit.setOnClickListener(this);

        tvLeftBtn.setOnClickListener(clickListener);
        tvRightBtn.setOnClickListener(clickListener);

        etMoney.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (inputCheck){
                    if(delayRun!=null){
                        //每次editText有变化的时候，则移除上次发出的延迟线程
                        handler.removeCallbacks(delayRun);
                    }
                    editString = s.toString();

                    //延迟800ms，如果不再输入字符，则执行该线程的run方法
                    handler.postDelayed(delayRun, 800);
                }else {
                    inputCheck = true;
                }

            }
        });
    }

    @Override
    protected void init() {
        setSpinner();

    }

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            getPrepareRechargeInfo(editString);
        }
    };

    private void commit() {

        if (prepareBean!=null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(FieldConfig.intent_bean,prepareBean);
            IntentUtil.gotoActivity(context,RechargeSecondActivity.class,bundle);
        }else {
            ToastUtil.showToast(context,"没有获取到订单详情数据，请重新输入金额");
        }

    }

    private void getPrepareRechargeInfo(String editString) {

//        String money = etMoney.getText().toString().trim();
        if (!StringUtil.isEmpty(editString)){
            RechargeBiz.prepare(context, editString,leftClick?"0":"1", new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    inputCheck = false;
                    prepareBean = (PrepareRechargeBean) result.getObject();
                    etMoney.setText(prepareBean.getAmount()+"");

                }

                @Override
                public void onFail(ResponseBean result) {
                    inputCheck = true;
                    ToastUtil.showToast(context,result.getInfo());

                }
            });
        }else {
            inputCheck = true;
            ToastUtil.showToast(context,"金额不能为空");

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                //提交
                commit();
                break;
            default:
                break;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_left_btn:
                    tvLeftBtn.setBackgroundResource(R.drawable.shape_blue_stroke_btn);
                    tvLeftBtn.setTextColor(getResources().getColor(R.color.blue));
                    tvRightBtn.setBackgroundResource(R.drawable.shape_gray_stroke_btn);
                    tvRightBtn.setTextColor(getResources().getColor(R.color.text_gray));
                    leftClick = true;
                    break;
                case R.id.tv_right_btn:
                    tvLeftBtn.setBackgroundResource(R.drawable.shape_gray_stroke_btn);
                    tvLeftBtn.setTextColor(getResources().getColor(R.color.text_gray));
                    tvRightBtn.setBackgroundResource(R.drawable.shape_blue_stroke_btn);
                    tvRightBtn.setTextColor(getResources().getColor(R.color.blue));
                    leftClick = false;
                    break;
                default:
                    break;
            }
        }
    };
}
