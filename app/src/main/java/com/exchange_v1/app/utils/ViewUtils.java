package com.exchange_v1.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by huanghh on 2016/9/13.
 */
public class ViewUtils {

    public static final Class thisC = ViewUtils.class;
    public static final String TAG = thisC.getSimpleName();

    /**
     *
     * @param id View的Id
     * @param <V> 不用管
     * @return 直接返回泛型的View
     */
    public static final <V extends View> V getView (View v, int id) {
        if (v == null) return null;
        if (id <= 0 ) return null;
        try {
            return (V) v.findViewById(id);
        } catch (ClassCastException ex) {
            System.err.println("Could not cast View to concrete class."+ ex);
            throw ex;
        }
    }

    /**
     * 设置背景，预防为空
     * @param v
     * @param resid
     */
    public static final void setBackgroundResource (View v, int resid) {
        if (v == null) return ;
        if (resid <= 0 ) return ;

        v.setBackgroundResource(resid);
    }
    /**
     * 设置背景，预防为空
     * @param v
     */
    public static final void setBackgroundColor(View v, int color) {
        if (v == null) return ;
        if (color <= 0 ) return ;

        v.setBackgroundColor(color);
    }

    /**
     * 设置背景，预防为空
     * @param v
     */
    public static final void setBackgroundColor(View v, String color) {
        if (v == null) return ;
        if (TextUtils.isEmpty(color)) return ;
        if (!color.startsWith("#")) return;

        v.setBackgroundColor(Color.parseColor(color));
    }

    /**
     * 设置不可用，预防为空
     * @param v
     */
    public static final void setEnabledFalse(View v) {
        if (v == null) return ;

        v.setEnabled(false);
    }

    /**
     * 设置不可用，预防为空
     * @param v
     */
    public static final void setEnabledTrue(View v) {
        if (v == null) return ;

        v.setEnabled(true);
    }

    /**
     * 设置是否不可用
     * @param v
     */
    public static final void setEnabledView(View v, boolean enabled) {
        if (v == null) return ;

        v.setEnabled(enabled);
    }

    /**
     * 是否不可见
     * @return
     */
    public static boolean isGone(View v) {
        if (v == null) return false;

        return v.getVisibility() == View.GONE;
    }
    /**
     * 是否可见
     * @return
     */
    public static boolean isVisible(View v) {
        if (v == null) return false;

        return v.getVisibility() == View.VISIBLE;
    }

    /**
     * 是否显示控件
     * @param v
     * @param isShow
     */
    public static void setIsShowView(View v, boolean isShow) {
        if (v == null) return ;
        if (isShow) {
            setVisible(v);
        }else{
            setGone(v);
        }
    }
    /**
     * 是否显示控件
     * @param v
     * @param isShow
     */
    public static void isShowViewByHideIsInvisible(View v, boolean isShow) {
        if (v == null) return ;
        if (isShow) {
            setVisible(v);
        }else{
            setInvisible(v);
        }
    }

    public static void setVisible(View v) {
        if (v == null) return ;
        v.setVisibility(View.VISIBLE);
    }

    public static void setInvisible(View v) {
        if (v == null) return ;
        v.setVisibility(View.INVISIBLE);
    }

    public static void setGone(View v) {
        if (v == null) return ;
        v.setVisibility(View.GONE);
    }

    /**
     * 设置不可用，预防为空
     * @param v
     * 设置字体
     */
    public static final void setFontText(TextView v, Context context) {
        if (v == null) return ;

        v.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/DIN-Regular.otf"));
    }


    //Dp to Px
    public static float dp2px(float dp,Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }

    //获取view的宽度
    public static int getViewWidth(View view) {

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }
}
