package com.exchange_v1.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.utils.ToastUtil;


public class HorizontalCustomDialog extends Dialog {

    public HorizontalCustomDialog(Context context) {
        super(context);
    }

    public HorizontalCustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private int type;//0默认样式1自定义输入金额样式
        private String balance;//预存款余额
        private OnClickable onClickable;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        private int positiveBgId;
        private int negativeBgId;

        private int positiveTextColor;
        private int negativeTextColor;

        private EditText et_custom;
        /**金钱提示*/
        private TextView hint_tv;





        public int getNegativeTextColor() {
            return negativeTextColor;
        }

        public Builder setNegativeTextColor(int negativeTextColor) {
            this.negativeTextColor = negativeTextColor;
            return this;
        }

        public String getPositiveButtonText() {
            return positiveButtonText;
        }

        public Builder setPositiveButtonText(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }

        public int getPositiveBgId() {
            return positiveBgId;
        }

        public Builder setPositiveBgId(int positiveBgId) {
            this.positiveBgId = positiveBgId;
            return this;
        }

        public int getNegativeBgId() {
            return negativeBgId;
        }

        public Builder setNegativeBgId(int negativeBgId) {
            this.negativeBgId = negativeBgId;
            return this;
        }

        public int getPositiveTextColor() {
            return positiveTextColor;
        }

        public Builder setPositiveTextColor(int positiveTextColor) {
            this.positiveTextColor = positiveTextColor;
            return this;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, int type, String balance, OnClickable onClickable) {
            this.context = context;
            this.type = type;
            this.balance = balance;
            this.onClickable = onClickable;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public HorizontalCustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final HorizontalCustomDialog dialog = new HorizontalCustomDialog(
                    context, R.style.HorizontalDialog);
            final View layout = inflater.inflate(
                    type == 1 ? R.layout.view_custom_cash_dialog : R.layout.view_horizontalcommon_dialog2, null);
            if (type == 1) {
                et_custom = (EditText) layout.findViewById(R.id.et_custom);
                et_custom.addTextChangedListener(textWatcher);
                hint_tv=(TextView) layout.findViewById(R.id.hint_tv);
            }
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // set the dialog title
            // ((TextView) layout.findViewById(R.id.title)).setTextInt(title);
            // set the confirm button
            positiveButtonText = type == 1 ? "确定" : positiveButtonText;
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.confirm_btn))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.confirm_btn)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
                if (onClickable != null) {
                    layout.findViewById(R.id.confirm_btn)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    String money = (et_custom).getText().toString();


                                    if (TextUtils.isEmpty(money)||".".equals(money)|| Double.parseDouble(money) <= 0) {
                                        ToastUtil.showToast(context, "请输入金额");
                                    } else {
                                        dialog.dismiss();
                                        onClickable.comfirm(Double.parseDouble(money)+"");
                                    }
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.confirm_btn).setVisibility(View.GONE);
            }

            // set the cancel button
            negativeButtonText = type == 1 ? "取消" : negativeButtonText;
            if (negativeButtonText != null) {
                ((TextView) layout.findViewById(R.id.cancel_btn))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    (layout.findViewById(R.id.cancel_btn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
                if (onClickable != null) {
                    layout.findViewById(R.id.cancel_btn)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    onClickable.cancel();
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.cancel_btn).setVisibility(View.GONE);
            }

            if (title != null) {
                ((TextView) layout.findViewById(R.id.title_tv)).setText(title);
            } else {
                layout.findViewById(R.id.title_tv).setVisibility(View.GONE);
                layout.findViewById(R.id.line_view).setVisibility(View.GONE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.msg_tv)).setText(message);
            }

			/*
             * 设置背景
			 */
            if (negativeBgId != 0) {
                (layout.findViewById(R.id.cancel_btn))
                        .setBackgroundDrawable(context.getResources()
                                .getDrawable(negativeBgId));

            }
            if (positiveBgId != 0) {
                (layout.findViewById(R.id.confirm_btn))
                        .setBackgroundDrawable(context.getResources()
                                .getDrawable(positiveBgId));

            }


            if (negativeTextColor != 0) {
                ((TextView) layout.findViewById(R.id.cancel_btn))
                        .setTextColor(context.getResources().getColor(
                                negativeTextColor));
            }
            if (positiveTextColor != 0) {
                ((TextView) layout.findViewById(R.id.confirm_btn))
                        .setTextColor(context.getResources().getColor(
                                positiveTextColor));
            }

            dialog.setContentView(layout);
            return dialog;
        }

        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String txt = s.toString();
                if (!TextUtils.isEmpty(txt)) {
                    double temp1 = Double.parseDouble(txt);
                    double temp2 = Double.parseDouble(balance);
                    if (temp1 > temp2) {
//                        if (balance.contains(".")) {
//                            String str = balance.substring(0, balance.indexOf("."));
//                            et_custom.setTextInt(str);
//                            et_custom.setSelection(str.length());
//                        } else {
//                            et_custom.setTextInt(balance);
//                            et_custom.setSelection(balance.length());
//
//                        }
                        hint_tv.setText(context.getString(R.string.beyond_balance));
                        hint_tv.setVisibility(View.VISIBLE);
                    }else{
                        hint_tv.setVisibility(View.INVISIBLE);
                        hint_tv.setText("");
                    }
//                    else{
//
//                    }
                }
            }
        };
    }

    public interface OnClickable {
        void comfirm(String text);

        void cancel();
    }
}
