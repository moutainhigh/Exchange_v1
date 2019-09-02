package com.exchange_v1.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.app.R;


/**
 * 自定义输入框 Created by liangxg on 15/12/7.
 */
public class UnderLineEditText extends RelativeLayout {

    /** 输入文本 */
    DeleteEditText input_edt;
    /** 左边图标*/
    ImageView left_iv;
    TextView right_tv;//右边文字
    LinearLayout under_line_right_ll;//右边文字布局
    /** 提示文本 */
    View view_line;
    /** 默认横线背景 */
    int default_line_c;
    /** 改变背景 */
    int change_line_c;
    /** 是否密码控件 */
    boolean isPwd;

    public UnderLineEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs);
    }

    public UnderLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }



    private void init(Context context, AttributeSet attrs) {

        View contentView = View.inflate(context, R.layout.d_under_line_edit_text,
                null);
        input_edt = (DeleteEditText) contentView.findViewById(R.id.input_edt);
        left_iv = (ImageView) contentView.findViewById(R.id.left_iv);
        right_tv = (TextView) contentView.findViewById(R.id.under_line_right_tv);
        under_line_right_ll = (LinearLayout) contentView.findViewById(R.id.under_line_right_ll);
        view_line = contentView.findViewById(R.id.line);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyInputEditView);
//        left_tv.setText(getTypeArrayText(a,
//                R.styleable.MyInputEditView_leftText));
        input_edt.setHint(getTypeArrayText(a,
                R.styleable.MyInputEditView_rightHintText));
        default_line_c = a.getColor(R.styleable.MyInputEditView_lineColor1,
                getResources().getColor(R.color.gray_line));
        change_line_c = a.getColor(R.styleable.MyInputEditView_lineColor1,
                getResources().getColor(R.color.line_background));
        view_line.setBackgroundColor(default_line_c);
        isPwd = a.getBoolean(R.styleable.MyInputEditView_isPwd, false);
        if (isPwd) {
            input_edt.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        addView(contentView, params);
        a.recycle();
        input_edt
                .setLineChangeListener(new DeleteEditText.LineChangeListener() {
                    @Override
                    public void change() {
                        view_line.setBackgroundColor(change_line_c);
                    }

                    @Override
                    public void back() {
                        view_line.setBackgroundColor(default_line_c);
                    }
                });

    }

    /**
     * 获取Edt控件
     *
     * @return
     */
    public DeleteEditText getInput_edt() {
        return input_edt;
    }

    private String getTypeArrayText(TypedArray a, int index) {
        String txt = a.getString(index);
        if (txt == null) {
            return "";
        }
        return txt;
    }

    public ImageView getLeftIV(){
        return left_iv;
    }

    public void setLeftIvIcon(Drawable drawable){
        left_iv.setImageDrawable(drawable);
    }

    public String getText() {
        return input_edt.getText().toString();
    }

    public void setText(String value) {
        input_edt.setText(value);
    }

    public TextView getRight_tv() {
        return right_tv;
    }

    public void setRight_tv(TextView right_tv) {
        this.right_tv = right_tv;
    }

    public LinearLayout getUnder_line_right_ll() {
        return under_line_right_ll;
    }

    public void setUnder_line_right_ll(LinearLayout under_line_right_ll) {
        this.under_line_right_ll = under_line_right_ll;
    }
    public View getView_line() {
        return view_line;
    }

    public void setView_line(View view_line) {
        this.view_line = view_line;
    }
}
