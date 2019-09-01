package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class UpLoadBean extends BaseBean{


    /**
     * id : 0019137951816482816
     * state : 0
     * domain : http://pwhfnj6fq.bkt.clouddn.com
     * name : 1566473242242.png
     * hash : FnFMOOT2qLDJZ5qXwLfZzEu55Zdt
     * path : http://pwhfnj6fq.bkt.clouddn.com/1566473242242.png
     * createTime : 2019-08-22 19:27
     * tails : {}
     */

    private String id;
    private int state;
    private String domain;
    private String name;
    private String hash;
    private String path;
    private String createTime;
    private TailsBean tails;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public TailsBean getTails() {
        return tails;
    }

    public void setTails(TailsBean tails) {
        this.tails = tails;
    }

    public static class TailsBean {


    }
}
