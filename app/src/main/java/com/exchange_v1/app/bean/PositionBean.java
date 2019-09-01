package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PositionBean extends BaseBean {

    /**
     * name : 北京市
     * id : 0021401352567521280
     * childs : [{"c_serialNo":1,"c_id":"0021401446264078336","c_name":"市辖区"}]
     * serialNo : 1
     */

    private String name;
    private String id;
    private int serialNo;
    private List<ChildsBean> childs;

    @Override
    protected void init(JSONObject jSon) throws JSONException {

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

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public List<ChildsBean> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildsBean> childs) {
        this.childs = childs;
    }

    public static class ChildsBean {
        /**
         * c_serialNo : 1
         * c_id : 0021401446264078336
         * c_name : 市辖区
         */

        private int c_serialNo;
        private String c_id;
        private String c_name;

        public int getC_serialNo() {
            return c_serialNo;
        }

        public void setC_serialNo(int c_serialNo) {
            this.c_serialNo = c_serialNo;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }
    }
}
