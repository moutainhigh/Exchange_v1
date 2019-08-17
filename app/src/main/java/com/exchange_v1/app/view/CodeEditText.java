package com.exchange_v1.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.R;


/**
 * Created by Lenovo on 2018/7/10.
 * 自定义验证码输入框
 */

public class CodeEditText extends RelativeLayout {

    private AccountEditText etCodeInput;
    private TextView codeBtn;
    private View bottomLineView;
    private final int HAND_COUNT_DOWN = 100;
    //获取验证码的倒计时，没有设置默认60s
    private int times;
    //倒计时状态量
    boolean istime = true;

    private final String mAuthCodeString = "获取验证码";

    private boolean isSendSms = false;//判断是否正在发送验证码(正在发送验证码 获取验证码按钮状态不能改变)


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HAND_COUNT_DOWN:
                    Integer time = (Integer) msg.obj;
                    if (time == 0) {
                        //验证码发送完成
                        isSendSms = false;

                        codeBtn.setEnabled(true);
                        istime = false;
                        codeBtn.setText(mAuthCodeString);

                        codeBtn.setBackgroundResource(R.drawable.shape_item_yellow);
                        codeBtn.setTextColor(getResources().getColor(R.color.while_1));

                        if (onCountDownListener != null) {
                            onCountDownListener.countDownEnd();
                        }
                    } else {
                        if (istime) {
                            //正在发送验证码
                            isSendSms = true;
                            codeBtn.setText(time + "秒后重新发送");

                            codeBtn.setBackgroundResource(R.color.while_1);
                            codeBtn.setTextColor(getResources().getColor(R.color.common_orange_text));
                            time--;
                            handler.sendMessageDelayed(handler.obtainMessage(HAND_COUNT_DOWN, time), 1000);
                        }
                    }

                    break;
                default:
                    break;
            }
            return true;
        }
    });

    public CodeEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View contentView = View.inflate(context, R.layout.d_code_edit_text, null);
        etCodeInput = (AccountEditText) contentView.findViewById(R.id.et_code_input);
        codeBtn = (TextView) contentView.findViewById(R.id.code_btn);
        bottomLineView = (View) contentView.findViewById(R.id.bottom_line_view);

        //暂时只提供倒计时属性，其余的后面需求要的时候再添加
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.C2bCodeEditView);
        times = typedArray.getInteger(R.styleable.C2bCodeEditView_time, 60);

        //提示文字
        etCodeInput.setHintText(getTypeArrayText(typedArray, R.styleable.C2bCodeEditView_hintInputText));
        etCodeInput.setInputStype(InputType.TYPE_CLASS_NUMBER);//验证码只能输入数字

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        addView(contentView, params);
        typedArray.recycle();

        codeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCode != null) {
                    //点击后的回调
                    onClickCode.onclick(v);
                }

                istime = true;
                codeBtn.setEnabled(false);
                handler.sendMessage(handler.obtainMessage(HAND_COUNT_DOWN, times));

            }
        });

        bottomLineView.setVisibility(VISIBLE);
        etCodeInput.setOnFocusChangeListener(new AccountEditText.OnFocusChangeListener() {
            @Override
            public void hasFocus(View v) {
                bottomLineView.setBackgroundColor(getResources().getColor(R.color.line_background));
            }

            @Override
            public void lostFocus(View v) {
                bottomLineView.setBackgroundColor(getResources().getColor(R.color.gray_line));
            }
        });

        etCodeInput.setOnTextChangeListener(new AccountEditText.OnTextChangeListener() {
            @Override
            public void beforeChange(CharSequence s, int start, int count, int after) {
                if (onTextChangeListener != null) {
                    onTextChangeListener.beforeChange(s, start, count, after);
                }
            }

            @Override
            public void afterChange(Editable s) {
                if (onTextChangeListener != null) {
                    onTextChangeListener.afterChange(s);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (onTextChangeListener != null) {
                    onTextChangeListener.onTextChanged(s, start, count, after);
                }
            }
        });
    }

    public void setCodeBtnZero() {
        handler.sendMessage(handler.obtainMessage(HAND_COUNT_DOWN, 0));
    }

    public void setGrayUnableBtn() {
        if (!isSendSms) {
            codeBtn.setBackgroundResource(R.drawable.shape_item_gray);
            codeBtn.setTextColor(getResources().getColor(R.color.while_1));
            codeBtn.setEnabled(false);
        }
    }

    public void setOrangeRightBtn() {
        if (!isSendSms) {
            codeBtn.setBackgroundResource(R.drawable.shape_item_yellow);
            codeBtn.setTextColor(getResources().getColor(R.color.while_1));
            codeBtn.setEnabled(true);
        }
    }

    private onClickCode onClickCode;

    public void setOnClickCodeListener(onClickCode onClickCode) {
        this.onClickCode = onClickCode;
    }

    public interface onClickCode {
        void onclick(View v);
    }

    public String getText() {
        return etCodeInput.getText();
    }

    public AccountEditText getEtCodeInput() {
        return etCodeInput;
    }

    private String getTypeArrayText(TypedArray a, int index) {
        String txt = a.getString(index);
        if (txt == null) {
            return "";
        }
        return txt;
    }

    /**
     * 文本监听
     */
    private OnTextChangeListener onTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    public interface OnTextChangeListener {
        void beforeChange(CharSequence s, int start, int count, int after);

        void afterChange(Editable s);

        void onTextChanged(CharSequence s, int start, int count, int after);
    }

    /**
     * 验证码倒计时回调
     */
    private OnCountDownListener onCountDownListener;

    public void setOnCountDownListener(OnCountDownListener onCountDownListener) {
        this.onCountDownListener = onCountDownListener;
    }

    public interface OnCountDownListener {
        void countDownEnd();
    }

}
