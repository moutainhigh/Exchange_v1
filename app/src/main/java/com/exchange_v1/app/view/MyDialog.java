package com.exchange_v1.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MyDialog extends Dialog {

    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        window.setAttributes(params);
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }




}
