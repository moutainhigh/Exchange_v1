package com.exchange_v1.app.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.TintContextWrapper;
import android.view.ContextThemeWrapper;
import android.view.View;

/**
 * Created by huanghh on 2017/2/10.
 */
public class ContextUtils {

    public static final Class thisC = ContextUtils.class;
    public static final String TAG = thisC.getSimpleName();

    /**
     * 当实现Activity继承不是Activity时，而是AppCompatActivity,
     * 使用view.getContext()有机会获取到的TintContextWrapper，而不是Context，
     * 所以在某些情况要把Context强转为某个类Activity的实现接口，需要经过这样转化
     * @param v
     * @return
     */
    public static Context getRealContext(View v) {
        if (v==null) {
            return null;
        }

        Context context = getRealContext(v.getContext());
        return context;
    }

    public static Context getRealContext(Context c) {
        Context context = c;
        if (context instanceof TintContextWrapper) {
            context = ((TintContextWrapper)context).getBaseContext();
        }else if (context instanceof ContextThemeWrapper) {
            if (!(context instanceof Activity)) {
                context = ((ContextThemeWrapper)context).getBaseContext();
            }

        }
        return context;
    }
}
