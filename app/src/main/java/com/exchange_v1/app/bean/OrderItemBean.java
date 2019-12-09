package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderItemBean extends BaseBean{
    /**
     * paymentNo : 5794557958
     * createTime : 2019-08-31 23:01:04
     * tenantNo : 5794557958
     * id : 0021259885794557959
     * paymentMoney : 100
     * state : 1
     * paymentTime : 2019-08-31 23:22:24
     * paymentType : ALI_BY
     */

    private String paymentNo;
    private String createTime;
    private String tenantNo;
    private String id;
    private String paymentMoney;
    private int state;
    private String paymentTime;
    private String confirmTime;

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    private String paymentType;


    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTenantNo() {
        return tenantNo;
    }

    public void setTenantNo(String tenantNo) {
        this.tenantNo = tenantNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(String paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
