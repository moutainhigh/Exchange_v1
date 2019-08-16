package com.exchange_v1.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by huanghh on 2016/12/30.
 */
public class BundleUtils {

    public static final Class thisC = BundleUtils.class;
    public static final String TAG = thisC.getSimpleName();

    public static final String KEY_IDTO = "KEY_IDTO";
    public static final String KEY_ODTO = "KEY_ODTO";

    /**
     * @return 直接返回 "输出数据传输对象"
     */
    public static final <ODTO> ODTO getODTO (Intent activityResultData) {
        if (activityResultData == null) {
            return null;
        }
        Bundle bundle = activityResultData.getExtras();
        if (bundle == null) return null;
        try {
            return (ODTO) bundle.getSerializable(KEY_ODTO);
        } catch (Exception ex) {
            System.err.println("unable get dto."+ ex);
            throw ex;
        }
    }

    /**
     * @return 直接返回 "输入数据传输对象"
     */
    public static final <IDTO> IDTO getIDTO (Activity activity) {
        Bundle bundle = getBundle(activity);
        if (bundle == null) return null;
        try {
            return (IDTO) bundle.getSerializable(KEY_IDTO);
        } catch (Exception ex) {
            System.err.println("unable get dto."+ ex);
            throw ex;
        }
    }

    public static Bundle getBundleByHasIDTO(Serializable idto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_IDTO,idto);
        return bundle;
    }

    public static Bundle getBundleByHasODTO(Serializable odto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ODTO,odto);
        return bundle;
    }

    public static void setResultOKHasOdto(Activity activity, Serializable odto) {
        Intent data = new Intent();
        data.putExtras(getBundleByHasODTO(odto));
        activity.setResult(Activity.RESULT_OK, data);
    }

    /**
     * 为了省多一句
     * @return
     */
    protected static Bundle getBundle(Activity activity) {
        return activity.getIntent().getExtras();
    }
}
