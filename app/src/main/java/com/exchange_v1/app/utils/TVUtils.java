package com.exchange_v1.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.bean.Discountrate;


/**
 * Created by huanghh on 2016/9/13.
 */
public class TVUtils {

    public static final Class thisC = TVUtils.class;
    public static final String TAG = thisC.getSimpleName();

    public static String getText(TextView tv) {
        if (tv==null)return "";


        return tv.getText().toString().trim();
    }

    public static void setTextColor(TextView tv, int color) {
        if (tv==null)return;
        if (color<0)return;

        tv.setTextColor(tv.getResources().getColor(color));
    }

    public static void setTextColor(TextView tv, String color) {
        if (tv==null)return;
        if (color==null) return;
        if (!color.startsWith("#")) return;

        tv.setTextColor(Color.parseColor(color));
    }

    public static void setText(TextView tv, CharSequence s) {
        if (tv==null)return;
        if (TextUtils.isEmpty(s))return;

        tv.setText(s);

    }

    public static void clearText(TextView tv) {
        if (tv==null)return;
        tv.setText("");
    }
    public static void setText(TextView tv, String s) {
        if (tv==null)return;
        if (TextUtils.isEmpty(s)){
            clearText(tv);
            return;
        }
        tv.setText(s);
        if (tv instanceof EditText) {
            ((EditText) tv).setSelection(s.length());
        }
    }

    public static void setTextInt(TextView tv, int intV) {
        if (tv==null)return;
        setText(tv,String.valueOf(intV));
    }

    @SuppressLint("ResourceType")
    public static void setText(TextView tv, @StringRes int resid) {
        if (tv==null)return;
        if (resid<0)return;

        setText(tv,tv.getContext().getString(resid));
    }

    public static void setTextHtml(TextView tv,String s) {
        if (tv==null)return;
        if (TextUtils.isEmpty(s))return;

        tv.setText(Html.fromHtml(s));
    }

    public static void setBackgroundResource(TextView tv, int resId) {
        if (tv==null)return;
        if (resId<0)return;

        tv.setBackgroundResource(resId);
    }

    public static String getDiscountrateStr(Discountrate data) {
        String discountrateStr = discountrateStr = "无折扣";
        //10折相当于无折扣
        if (data.getDiscountrate() < 10) {
            discountrateStr = data.getDiscountrate() + "折";
        }
        return discountrateStr;
    }

    public static void setTextColor(TextView tv, boolean isSelect) {
        int colorI = R.color.common_color_black_text_title;

        if (isSelect) {
            colorI = R.color.white;
        }
        TVUtils.setTextColor(tv, colorI);
    }
    public static void setMainTextColor(TextView tv, boolean isSelect) {
        int colorI = R.color.common_color_black_text_title;

        if (isSelect) {
            colorI = R.color.common_main_orange_color;
        }
        TVUtils.setTextColor(tv, colorI);
    }
    public static void setViewBackground(View v, boolean isSelect) {
        int itemViewBackground = R.drawable.shape_frame_gray_rb;
        if (isSelect) {
            itemViewBackground = R.drawable.shape_frame_red_f248;
        }
        ViewUtils.setBackgroundResource(v, itemViewBackground);
    }
    public static void setMainViewBackground(View v, boolean isSelect) {
        int itemViewBackground = R.mipmap.main_card_unselect_background;
        if (isSelect) {
            itemViewBackground = R.mipmap.main_card_select_background;
        }
        ViewUtils.setBackgroundResource(v, itemViewBackground);
    }
    public static void disableSoldOutBtn(TextView soldOutBtn , int strRes, Context context) {
        disableSoldOutBtn(soldOutBtn,context.getResources().getString(strRes));
    }
    public static void disableSoldOutBtn(TextView soldOutBtn , String str) {
        TVUtils.setText(soldOutBtn, str);
        ViewUtils.setEnabledFalse(soldOutBtn);
    }
    public static void disableOutBtn(TextView soldOutBtn , String str) {
        TVUtils.setText(soldOutBtn, str);
        ViewUtils.setEnabledTrue(soldOutBtn);
    }
}
