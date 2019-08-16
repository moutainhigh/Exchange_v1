package com.exchange_v1.app.interf;

/**
 * Created by chenyang on 2017/2/20.
 */

public interface PassWrongListenler {
    public void  retry();//重试监听按钮事件
    public void  forGetPassword(); //忘记密码监听事件
}
