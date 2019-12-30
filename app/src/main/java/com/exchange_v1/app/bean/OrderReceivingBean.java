package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderReceivingBean extends BaseBean{

//    id：主键
//    state：状态，1-待确认
//    systemNo：系统订单号
//    paymentMoney：支付金额
//    paymentType：支付类型

    private String id;
    private String state;
    private String systemNo;
    private double paymentMoney;
    private String paymentType;
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    public double getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(double paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }
}
