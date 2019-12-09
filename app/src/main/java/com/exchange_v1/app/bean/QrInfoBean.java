package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class QrInfoBean extends BaseBean{


    /**
     * path : http://pn5kv.cn/1575861356464.jpg
     * name : 邱金来
     * id : 0033219969588133888
     * account : qiujinlai@qq.com
     * paymentType : ALI_BY
     */

    private String path;//二维码图片路径
    private String name;//姓名
    private String id;
    private String account;//账号
    private String paymentType;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
