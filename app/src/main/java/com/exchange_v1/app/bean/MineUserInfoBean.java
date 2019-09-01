package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class MineUserInfoBean extends BaseBean {

    /**
     * cardName : 朱伟达
     * bankName : 招商银行
     * cityId :
     * cardNo : 441302199204151058
     * balance : 0
     * isAgent : 0
     * bankNo : 6214837841911211
     * stock :
     * bankAuth : 1
     * mobile : 18302071900
     * avatar :
     * provinceId :
     * qqNo :
     * wxNo :
     * freezeBalance : 0
     * account : zhuwd
     */

    private String cardName;
    private String bankName;
    private String cityId;
    private String cardNo;
    private int balance;
    private int isAgent;
    private String bankNo;
    private String stock;
    private int bankAuth;
    private String mobile;
    private String avatar;
    private String provinceId;
    private String qqNo;
    private String wxNo;
    private int freezeBalance;
    private String account;
    private String id;
    private String receptive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceptive() {
        return receptive;
    }

    public void setReceptive(String receptive) {
        this.receptive = receptive;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getBankAuth() {
        return bankAuth;
    }

    public void setBankAuth(int bankAuth) {
        this.bankAuth = bankAuth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    public String getWxNo() {
        return wxNo;
    }

    public void setWxNo(String wxNo) {
        this.wxNo = wxNo;
    }

    public int getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(int freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
