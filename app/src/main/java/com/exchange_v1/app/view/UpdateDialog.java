package com.exchange_v1.app.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exchange_v1.app.R;


/**
 * Created by huangtuo on 2018/10/30.
 */

public class UpdateDialog extends Dialog {

    private Context mContext;
    private ImageView mImgClose;
    private TextView mTvContent;
    private TextView mBtnUpdate;

    OnUpdateListener onUpdateListener;
    /**
     * 是否点击后关闭对话框
     */
    private boolean mIsNeedDismiss =true;

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public UpdateDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        init(context);
    }

    public UpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected UpdateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.dialog_update);

        mImgClose = findViewById(R.id.img_update_close);
        mTvContent = findViewById(R.id.tv_update_content);
        mBtnUpdate = findViewById(R.id.btn_update);

        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        widgetListener();
    }

    private void widgetListener() {
        mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onUpdateListener != null){
                    onUpdateListener.onUpdateClick(UpdateDialog.this);
                }
                if (isIsNeedDismiss()) {
                    dismiss();
                }
            }
        });

    }

    public boolean isIsNeedDismiss() {
        return mIsNeedDismiss;
    }

    public void setIsNeedDismiss(boolean isNeedDismiss) {
        this.mIsNeedDismiss = isNeedDismiss;
    }


    public void setContentStr(String contentStr) {
        if(contentStr != null){
            mTvContent.setText(Html.fromHtml(contentStr));
        }
    }

    public void setCloseVis(int vis){
        mImgClose.setVisibility(vis);
    }

    public void setUpdateBtnText(String text){
        if(text != null)
            mBtnUpdate.setText(text);
    }

    public TextView getUpdateBtn(){
        return mBtnUpdate;
    }

    public interface OnUpdateListener{
        void onUpdateClick(UpdateDialog updateDialog);
    }
}
