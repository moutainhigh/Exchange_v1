package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class CashingBean extends BaseBean {
    /**
     * amount : 100.0
     * createTime : 2019-08-23 14:37:01
     * bankNo : 6823878231231390
     * bankName : 建设银行
     * id : 0019427265691516928
     * state : 0
     * type : 0
     */

    private double amount;
    private String createTime;
    private String bankNo;
    private String bankName;
    private String id;
    private int state;
    private int type;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
