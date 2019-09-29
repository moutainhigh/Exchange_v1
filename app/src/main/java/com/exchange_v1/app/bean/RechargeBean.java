package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class RechargeBean extends BaseBean {
    /**
     * amount : 100.87
     * orderNo : 27298157
     * createTime : 2019-09-02 21:01:18
     * bankNo : 6823878231231390
     * cardType : 0
     * branchName :
     * bankName : 建设银行
     * id : 0023147851827642368
     * state : 3
     */

    private String amount;
    private String orderNo;
    private String createTime;
    private String bankNo;
    private int cardType;
    private String branchName;
    private String bankName;
    private String id;
    private int state;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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
}
