package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录属性类
 *
 */
public class LoginBean extends BaseBean {

    /**
     * @author yeqing
     * @version 1.0
     * @date 2015-5-8
     */
    private static final long serialVersionUID = -7913164740774384304L;
    /**
     * 帐号
     */
    private String account;
    /**
     * 头像
     */
    private String portrait;
    /**
     * 用户编号
     */
    private String userNo;
    /**
     * 验证码
     */
    private String identifyingCode;

    /**
     * 是否自动登录
     */
    private boolean isautologin = false;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public boolean isIsautologin() {
        return isautologin;
    }

    public void setIsautologin(boolean isautologin) {
        this.isautologin = isautologin;
    }

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "LoginBean [account=" + account + ", portrait=" + portrait + ", userNo=" + userNo + ", isautologin="
                + isautologin + "]";
    }

}
