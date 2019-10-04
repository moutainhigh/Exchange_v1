package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinDetailBean extends BaseBean{


    /**
     * amount : 300
     * walletChange : PLEDGE
     * createTime : 2019-09-02 23:55:03
     * id : 0023191576729616384
     * beforAmount : 10000
     * changeMsg : 缴纳押金
     * afterAmount : 9700
     */

    private int amount;
    private String walletChange;
    private String createTime;
    private String id;
    private int beforAmount;
    private String changeMsg;
    private int afterAmount;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getWalletChange() {
        return walletChange;
    }

    public void setWalletChange(String walletChange) {
        this.walletChange = walletChange;
    }

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

    public int getBeforAmount() {
        return beforAmount;
    }

    public void setBeforAmount(int beforAmount) {
        this.beforAmount = beforAmount;
    }

    public String getChangeMsg() {
        return changeMsg;
    }

    public void setChangeMsg(String changeMsg) {
        this.changeMsg = changeMsg;
    }

    public int getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(int afterAmount) {
        this.afterAmount = afterAmount;
    }
}
