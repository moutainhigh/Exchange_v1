package com.exchange_v1.app.interf;

/**
 * 绑定银行卡
 * Created by liangxg on 2016/7/18.
 */
public interface OnBindingBank {
    /**不需要弹出密码输入框的*/
    public static final String TYPE_NORMAL="normal";
    /**弹出密码输入框的*/
    public static final String TYPE_BUY="buy";
    /**
     * 实名制需要绑卡判断
     */
    public static final String TYPE_NEED_CARD="needCard";

    public void bindingBank();
}
