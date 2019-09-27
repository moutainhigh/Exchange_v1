package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class QrCodeBean extends BaseBean{
    /**
     * path : http://pwhfnj6fq.bkt.clouddn.com/1566468995180.png
     * id : 0019122792616165376
     * state : 1
     * paymentType : 1 //二维码类型 ALI_BY-支付宝,WX_BY-微信
     */

    private String path;
    private String id;
    private int state;
    private int paymentType;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }
}
