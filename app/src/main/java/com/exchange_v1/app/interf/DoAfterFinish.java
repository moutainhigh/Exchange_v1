package com.exchange_v1.app.interf;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by liangxg on 2017/2/10.
 */
public interface DoAfterFinish extends Serializable {

    //执行相关操作
    public void execute(Context context);

}
