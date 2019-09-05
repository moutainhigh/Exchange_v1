package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 提现费率
 */
public class CashFree extends BaseBean {
    /**
     * total : 315
     * balance : 10000
     * fee : 15
     * feeRate : 5
     */

    private String total;
    private String balance;
    private String fee;
    private String feeRate;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }
}
