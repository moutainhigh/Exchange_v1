package com.exchange_v1.app.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.exchange_v1.app.base.TApplication;

import java.lang.reflect.Field;
import java.util.List;


/**
 * 一些公用方法
 *
 */
public class Util {
    static int c_updateUserInfo = 0;
    public static TextView pasWrong_tv;
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 银行卡四位加空格
     *
     * @param mEditText
     */
    public static void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }


            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }
                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }


    /**
     * 判断是否登录
     */
    public static boolean isLogin(Context context) {
        return isLogin();
    }

    public static boolean isLogin() {
        if (null == TApplication.getMineUserInfo()) {
            return false;
        }
        return true;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @param context
     * @return
     * @updateTime 2015-6-22,下午2:44:41
     * @updateAuthor qw
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void sendBroadcast(Context context, Intent intent) {
        String permission = com.exchange_v1.app.Manifest.permission.bwoilpermiss;
        context.sendBroadcast(intent, permission);
    }

    /**
     * 递归遍历设置所有字体
     */
    public static void setFont(View view, Typeface face) {
        if (view instanceof ViewGroup) {
            View[] children = null;
            try {
                Field field = ViewGroup.class.getDeclaredField("mChildren");
                field.setAccessible(true);
                children = (View[]) field.get(view);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (children != null) {
                for (View child : children) {
                    setFont(child, face);
                }
            } else {
                int count = ((ViewGroup) view).getChildCount();
                for (int i = 0; i < count; ++i) {
                    View child = ((ViewGroup) view).getChildAt(i);
                    if (null != child) {
                        setFont(child, face);
                    }
                }
            }
        } else if (view instanceof TextView) {
            // ((TextView) view).setTypeface(face);
        }
    }
}