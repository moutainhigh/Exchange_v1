package com.exchange_v1.app.bean;

public class WebBean {


    /**
     * command : 101
     * data : {"orderId":"0032858419186630656"}
     * msg : 订单结单消息
     */

    private int command;//100抢单中，101订单已被人抢走，
    private DataBean data;
    private String msg;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * orderId : 0032858419186630656
         */

        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
