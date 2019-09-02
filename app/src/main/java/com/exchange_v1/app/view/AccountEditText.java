package com.exchange_v1.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.exchange_v1.app.R;


/**
 * Created by zhuwd on 2018/7/7.
 * 组合自定义 账号输入框
 */

public class AccountEditText extends RelativeLayout implements TextWatcher,View.OnFocusChangeListener {


    private ImageView leftIconIv;
    private EditText editInputEt;
    private ImageView rightCloseIv;
    private View lineBottom;
    //是否使用一键清除功能
    private Boolean closeEnable;
    //是否为手机号，默认为是
    private Boolean isPhone;
    //是否需要下划线
    private Boolean isBottomLine;
    //下划线颜色
    private int default_line_c;
    /**
     * 文本监听
     */
    private OnTextChangeListener onTextChangeListener;
    /** 焦点的监听 */
    private OnFocusChangeListener onFocusChangeListener;

    public AccountEditText(Context context) {
        super(context);
        init(context, null);
    }

    public AccountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AccountEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 构造方法，初始化xml的属性设置
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        View contentView = View.inflate(context, R.layout.d_account_edit_text, null);
        leftIconIv = (ImageView) contentView.findViewById(R.id.left_icon_iv);
        editInputEt = (EditText) contentView.findViewById(R.id.edit_input_et);
        rightCloseIv = (ImageView) contentView.findViewById(R.id.right_close_iv);
        lineBottom = (View) contentView.findViewById(R.id.line_bottom);

        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.C2bAccountEditView);

        //下划线的颜色
        default_line_c = tArray.getColor(R.styleable.C2bAccountEditView_bottomLineColor, getResources().getColor(R.color.line_background));
        isBottomLine = tArray.getBoolean(R.styleable.C2bAccountEditView_isBottomLine, true);
        if (isBottomLine){
            lineBottom.setVisibility(VISIBLE);
            lineBottom.setBackgroundColor(getResources().getColor(R.color.gray_line));
        }else {
            lineBottom.setVisibility(GONE);
        }

        Drawable leftIConDrawable = tArray.getDrawable(R.styleable.C2bAccountEditView_leftIconSrc);
        if (leftIConDrawable != null) {
            leftIconIv.setImageDrawable(leftIConDrawable);
        }
        //edittext提示文字
        editInputEt.setHint(getTypeArrayText(tArray, R.styleable.C2bAccountEditView_hintInputText));

        Drawable closeIConDrawable = tArray.getDrawable(R.styleable.C2bAccountEditView_closeIconSrc);
        if (closeIConDrawable != null) {
            rightCloseIv.setImageDrawable(closeIConDrawable);
        }
        rightCloseIv.setVisibility(INVISIBLE);
        closeEnable = tArray.getBoolean(R.styleable.C2bAccountEditView_clearEnable, true);
        if (!closeEnable) {
            rightCloseIv.setVisibility(GONE);
        }
        //限制输入的为手机号
        isPhone = tArray.getBoolean(R.styleable.C2bAccountEditView_isPhone, true);
        if (isPhone){
            //设置只能输入11位
            editInputEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });
            //输入类型为数字
            editInputEt.setInputType(InputType.TYPE_CLASS_PHONE);
        }

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        addView(contentView, params);
        tArray.recycle();

        rightCloseIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editInputEt.getText().clear();
            }
        });

        editInputEt.addTextChangedListener(this);

        editInputEt.setOnFocusChangeListener(this);
    }

    public String getText() {
        return editInputEt.getText().toString();
    }

    public void setInputStype(int stype){
        editInputEt.setInputType(stype);
    }

    public void setText(String value) {
        editInputEt.setText(value);
        editInputEt.setSelection(editInputEt.getText().toString().length());//光标放在最后
    }

    public EditText getEditText() {
        return editInputEt;
    }

    public void setHintText(String text){
        editInputEt.setHint(text);
    }

    public void setLeftIconVisibility(){
        leftIconIv.setVisibility(GONE);
    }

    /**设置是否使用一键清除
     * @param enable
     */
    public void setClearEnable(Boolean enable){
        this.closeEnable = enable;
    }

    public Boolean getClearEnable(){
        return closeEnable;
    }

    private String getTypeArrayText(TypedArray a, int index) {
        String txt = a.getString(index);
        if (txt == null) {
            return "";
        }
        return txt;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (onTextChangeListener != null) {
            onTextChangeListener.beforeChange(s, start, count, after);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (onTextChangeListener != null) {
            onTextChangeListener.onTextChanged(s, start, count, after);
        }

        //一键清除输入框按钮
        if (closeEnable) {
            rightCloseIv.setVisibility(s.length() > 0 ? VISIBLE : INVISIBLE);
        } else {
            rightCloseIv.setVisibility(GONE);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (onTextChangeListener != null) {
            onTextChangeListener.afterChange(s);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if(null!=onFocusChangeListener){
                onFocusChangeListener.hasFocus(v);
            }

            lineBottom.setBackgroundColor(default_line_c);
            rightCloseIv.setVisibility(editInputEt.getText().length() > 0 ? VISIBLE : GONE);
        } else {
            if(null!=onFocusChangeListener){
                onFocusChangeListener.lostFocus(v);
            }

            lineBottom.setBackgroundColor(getResources().getColor(R.color.gray_line));
            rightCloseIv.setVisibility(GONE);
        }
    }

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    public interface OnTextChangeListener {
        void beforeChange(CharSequence s, int start, int count, int after);

        void afterChange(Editable s);

        void onTextChanged(CharSequence s, int start, int count, int after);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    public interface OnFocusChangeListener {
        void hasFocus(View v);

        void lostFocus(View v);
    }

    public EditText getEditInputEt() {
        return editInputEt;
    }
}
