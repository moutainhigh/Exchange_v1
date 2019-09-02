package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class PrepareRechargeBean extends BaseBean {
    /**
     * amount : 100.4
     * orderNo : 81887974
     * cardName : 林5
     * bankNo : 6823878231231390
     * bankName : 建设银行
     * id : 0023171576077746176
     */

    private double amount;
    private String orderNo;
    private String cardName;
    private String bankNo;
    private String bankName;
    private String id;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
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

    //{"amount":100.87,"orderNo":"27298157","cardName":"林5","bankNo":"6823878231231390","bankName":"建设银行","id":"0023147851827642368"}
}
