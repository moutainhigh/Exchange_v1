package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by penglian on 2016/7/25.
 */
public class AddLoadBean extends BaseBean{

    /**启动页面图片停留时间*/
    private String launchTime;
    /**启动页面图片*/
    private String launchImage;
    /**是否显示新增启动页面*/
    private String launchTimeIsOn;

    private String linkType; //none 不跳转;link M站跳转;product APP产品详情页; oillist APP油价分析列表
    private String productId;
    private String typeBn;
    private String period;
    private String linkUrl;


    public String getLaunchTimeIsOn() {
        return launchTimeIsOn;
    }

    public void setLaunchTimeIsOn(String launchTimeIsOn) {
        this.launchTimeIsOn = launchTimeIsOn;
    }

    public String getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime;
    }

    public String getLaunchImage() {
        return launchImage;
    }

    public void setLaunchImage(String launchImage) {
        this.launchImage = launchImage;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTypeBn() {
        return typeBn;
    }

    public void setTypeBn(String typeBn) {
        this.typeBn = typeBn;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setLaunchTime(jSon.optString("launchTime"));
        setLaunchImage(jSon.optString("launchImage"));
        setLaunchTimeIsOn(jSon.optString("launchTimeIsOn"));

        setLinkType(jSon.optString("link_type"));
        setProductId(jSon.optString("product_id"));
        setTypeBn(jSon.optString("type_bn"));
        setPeriod(jSon.optString("period"));
        setLinkUrl(jSon.optString("link_url"));
    }
}
