package com.exchange_v1.app.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.brightoilonline.c2b_phone.R;
import com.brightoilonline.c2b_phone.activity.mine.AuthenticationActivity;
import com.brightoilonline.c2b_phone.activity.mine.BankCardEditActivity;
import com.brightoilonline.c2b_phone.activity.mine.SetTransactionPasswordActivity;
import com.brightoilonline.c2b_phone.activity.mine.VerificationPhonePasActivity;
import com.brightoilonline.c2b_phone.activity2.home.LoginActivity2;
import com.brightoilonline.c2b_phone.activity2.home.TakePasValidateActivity2;
import com.brightoilonline.c2b_phone.activity2.means.ExtractActivity2;
import com.brightoilonline.c2b_phone.activity2.means.RechargeActivity2;
import com.brightoilonline.c2b_phone.activity2.mine.UpLoadingIdentityActivity;
import com.brightoilonline.c2b_phone.activity2.oil.ShowBillPicActivity;
import com.brightoilonline.c2b_phone.bean.GoodsBean;
import com.brightoilonline.c2b_phone.bean.OilStationInfoBean;
import com.brightoilonline.c2b_phone.bean.RemainderNotEnoughBean;
import com.brightoilonline.c2b_phone.bean.ResponseBean;
import com.brightoilonline.c2b_phone.bean.SpeciaH5WebInterfaceBean;
import com.brightoilonline.c2b_phone.bean.TinyBankRealBean;
import com.brightoilonline.c2b_phone.bean.UserAccountBean;
import com.brightoilonline.c2b_phone.biz.CloudOilBiz;
import com.brightoilonline.c2b_phone.biz.MineBiz;
import com.brightoilonline.c2b_phone.config.Constant;
import com.brightoilonline.c2b_phone.config.FieldConfig;
import com.brightoilonline.c2b_phone.config.RequestCode;
import com.brightoilonline.c2b_phone.config.TApplication;
import com.brightoilonline.c2b_phone.interf.MyRequestHandle;
import com.brightoilonline.c2b_phone.interf.OnBindingBank;
import com.brightoilonline.c2b_phone.interf.OnGetAccountListener;
import com.brightoilonline.c2b_phone.interf.OnLoginPasListener;
import com.brightoilonline.c2b_phone.interf.OnPasMaxListener;
import com.brightoilonline.c2b_phone.interf.PassWrongListenler;
import com.brightoilonline.c2b_phone.network.RequestHandle;
import com.brightoilonline.c2b_phone.view.GridPasView;
import com.brightoilonline.c2b_phone.view.GridPasView.OnPasChangeListener;
import com.brightoilonline.c2b_phone.view.TinyOpenDialog;
import com.brightoilonline.c2b_phone.view.TwoButtonDialog;
import com.brightoilonline.c2b_phone.widget.HorizontalCustomDialog;
import com.brightoilonline.c2b_phone.widget.PasDialog;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 一些公用方法
 *
 * @Description
 * @date 2015-8-31
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司
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
    public static boolean isLogin(Context context, String fragmentTag, int do_action) {
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.intent_key_from), fragmentTag);
        bundle.putInt(context.getString(R.string.intent_key_operation), do_action);
        if (null == TApplication.getUserInfoBean()) {
            Intent intent = new Intent(context, LoginActivity2.class);
            intent.putExtras(bundle);
            ((Activity) context).startActivityForResult(intent, RequestCode.REQUEST_CODE_LOGIN);
            return false;
        }
        return true;
    }

    public static boolean isLogin(Fragment context, String fragmentTag, int do_action) {
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.intent_key_from), fragmentTag);
        bundle.putInt(context.getString(R.string.intent_key_operation), do_action);
        if (null == TApplication.getUserInfoBean()) {
            Intent intent = new Intent(context.getActivity(), LoginActivity2.class);
            intent.putExtras(bundle);
            context.startActivityForResult(intent, RequestCode.REQUEST_CODE_LOGIN);
            return false;
        }
        return true;
    }

    /**
     * 新H5登录后的跳转逻辑
     * 判断是否登录
     */
    public static boolean isLogin(Context context, SpeciaH5WebInterfaceBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.intent_key_from), "H5");
        bundle.putInt(context.getString(R.string.intent_key_operation), 0);

        if (null == TApplication.getUserInfoBean()) {
            String event = bean.getEvent();
            String jump_type = bean.getJump_type();
            String jump = bean.getJump();
            String product_id = bean.getProduct_id();
            String title = bean.getPromotion_title();
            String icon = bean.getPromotion_ico();
            String text = bean.getPromotion_text();
            String url = bean.getPromotion_url();
            String type_bn = bean.getType_bn();

            bundle.putString(context.getString(R.string.intent_key_jump_type), jump_type);
            bundle.putString(context.getString(R.string.intent_key_jump), jump);
            bundle.putString(context.getString(R.string.intent_key_product_id), product_id);
            bundle.putString(context.getString(R.string.intent_key_type_bn), type_bn);

            bundle.putString(context.getString(R.string.intent_key_event), event);
            bundle.putString(context.getString(R.string.intent_key_title), title);
            bundle.putString(context.getString(R.string.intent_key_ico), icon);
            bundle.putString(context.getString(R.string.intent_key_text), text);
            bundle.putString(context.getString(R.string.intent_key_url), url);

            Intent intent = new Intent(context, LoginActivity2.class);
            intent.putExtras(bundle);
            ((Activity) context).startActivityForResult(intent, RequestCode.REQUEST_CODE_H5_LOGIN);
            return false;
        }
        return true;
    }

    /**
     * 判断是否登录
     */
    public static boolean isLogin(Context context) {
        return isLogin();
    }

    public static boolean isLogin() {
        if (null == TApplication.getUserInfoBean()) {
            return false;
        }
        return true;
    }

    public static void getMyAccount(final Context context,
                                    final OnGetAccountListener listener, final boolean isRefresh) {
        getMyAccount(context, listener, isRefresh, "");
    }

    /**
     * 获取我的帐号
     */
    public static void getMyAccount(final Context context,
                                    final OnGetAccountListener listener, final boolean isRefresh, String msg) {
        if (null == listener || context == null) {
            return;
        }


        if (TApplication.getUserAccountBean() != null) {
            listener.onCacheAccount(TApplication.getUserAccountBean());
        }
        if (isRefresh || TApplication.getUserAccountBean() == null) {

            MineBiz.updateAccountInfo(context, msg, new RequestHandle() {

                @Override
                public void onSuccess(ResponseBean result) {
                    UserAccountBean bean = (UserAccountBean) result.getObject();
                    TApplication.setUserAccountBean(bean);
                    if (isRefresh) {
                        listener.onHttpsGetAccount(bean);
                    } else {
                        listener.onCacheAccount(bean);
                    }
                }

                @Override
                public void onFail(ResponseBean result) {

                }
            });
        }
    }

    /**
     * 判断是否获取某些权限
     *
     * @param context
     * @param permiss
     * @return
     */
    public static boolean isPermissions(Context context, String permiss) {
        PackageManager pm = context.getPackageManager();

        boolean flag = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(permiss, context.getPackageName()));
        if (flag) {
            return true;
            //有这个权限，做相应处理
        } else {              //没有权限
            return false;
        }


    }

    public static void sendBroadcast(Context context, Intent intent) {
        String permission = com.brightoilonline.c2b_phone.Manifest.permission.bwoilpermiss;
        context.sendBroadcast(intent, permission);
    }


    /**
     * 判定实名认证，是否绑定银行卡方法
     *
     * @param context
     * @param msg
     * @param onBindingBank
     * @param type          type为normal：只判断新认证用户 或者  老用户 即可通过判断执行相应操作
     *                      主要用于“转出”，“充值”等操作 不需要设置交易密码的操作
     *                      <p>
     *                      <p>
     *                      type为buy：情况1 没有通过新认证，但设置了实名认证以及绑定了银行卡 跳转设置交易密码
     *                      清空2  通过新认或者 或者 是老用户 设置交易密码，设置实名认证，绑定银行卡 可以执行相应操作
     *                      情况3  其他执行 跳转新实名认证流程
     *                      主要用于“支付订单”，“领取”等操作 需要使用交易密码等操作
     */
    public static void isBindingBank(final Context context, final String title, final String msg, final OnBindingBank onBindingBank, final String type) {

        getMyAccount(context, new OnGetAccountListener() {
            @Override
            public void onCacheAccount(UserAccountBean userAccountBean) {
                doBindingBank(context, title, msg, onBindingBank, type);
            }

            @Override
            public void onHttpsGetAccount(UserAccountBean userAccountBean) {
                doBindingBank(context, title, msg, onBindingBank, type);
            }
        }, false, context.getString(R.string.process_dialog_wait));

    }


    public static void setTradePwd(final Context context) {
        UserAccountBean userAccountBean = TApplication.getUserAccountBean();
        if (userAccountBean == null) {
            return;
        }
        if ("1".equals(userAccountBean.getCertified()) && "false".equals(userAccountBean.getTrade_pwd_auth()) && "true".equalsIgnoreCase(userAccountBean.getBand_card())) {
            /**
             * 未通过新实名认证，但已经绑卡的用户,要去设置交易密码
             * 跳转设置交易密码界面
             */
            Bundle bundle = new Bundle();
            bundle.putString("come_from", "0");
            IntentUtil.gotoActivityForResult(context, SetTransactionPasswordActivity.class, bundle,
                    RequestCode.REQUEST_CODE_EDIT_BANK_CARD);

        } else if ("1".equals(userAccountBean.getCertified()) || ("true".equals(userAccountBean.getTrade_pwd_auth()) && "true".equals(userAccountBean.getRealname_auth()) && "true".equals(userAccountBean.getBand_card()))) {
            /**
             * 通过新实名认证
             * 或者老用户 设置交易密码，设置实名认证，绑定银行卡
             *  执行响应操作
             */
            IntentUtil.gotoActivity(context, AuthenticationActivity.class);

        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(FieldConfig.intent_type, 1);
            IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                    RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
        }
    }

    public static void doBindingBank(final Context context, String title, String msg, OnBindingBank onBindingBank, String type) {
        UserAccountBean userAccountBean = TApplication.getUserAccountBean();
        if (userAccountBean == null) {
            return;
        }
        if (OnBindingBank.TYPE_NORMAL.equals(type)) {
            /**
             * normal的情况
             * 主要用于“转出”，“充值”等操作 不需要设置交易密码的操作
             */
            if ("1".equals(userAccountBean.getCertified()) || "true".equals(userAccountBean.getIs_old_user())) {
                onBindingBank.bindingBank();
            } else {
                DialogUtil.showTwoBtnDialog(context, title, msg, "取消", "确定", new TwoButtonDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TwoButtonDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TwoButtonDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putInt(FieldConfig.intent_type, 1);
                        IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            }
        } else if (OnBindingBank.TYPE_NEED_CARD.equals(type)) {
            if (!"1".equals(userAccountBean.getCertified()) && "false".equals(userAccountBean.getTrade_pwd_auth()) && userAccountBean.getBand_card().equalsIgnoreCase("true")) {
                /**
                 * 未通过新实名认证，但已经绑卡的用户,要去设置交易密码
                 * 跳转设置交易密码界面
                 */
                DialogUtil.showTwoBtnDialog(context, "交易密码", msg, "取消", "确定", new TwoButtonDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TwoButtonDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TwoButtonDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("come_from", "0");
                        IntentUtil.gotoActivityForResult(context, SetTransactionPasswordActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            } else if (("1".equals(userAccountBean.getCertified()) && "true".equals(userAccountBean.getBand_card())) || ("true".equals(userAccountBean.getTrade_pwd_auth()) && "true".equals(userAccountBean.getRealname_auth()) && "true".equals(userAccountBean.getBand_card()))) {
                /**
                 * 通过新实名认证且绑卡
                 * 或者老用户 设置交易密码，设置实名认证，绑定银行卡
                 *  执行响应操作
                 */
                onBindingBank.bindingBank();
            } else {
                DialogUtil.showTwoBtnDialog(context, title, msg, "取消", "确定", new TwoButtonDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TwoButtonDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TwoButtonDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putInt(FieldConfig.intent_type, 1);
                        IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            }
        } else {
            if (!"1".equals(userAccountBean.getCertified()) && "false".equals(userAccountBean.getTrade_pwd_auth()) && userAccountBean.getBand_card().equalsIgnoreCase("true")) {
                /**
                 * 未通过新实名认证，但已经绑卡的用户,要去设置交易密码
                 * 跳转设置交易密码界面
                 */
                DialogUtil.showTwoBtnDialog(context, "交易密码", msg, "取消", "确定", new TwoButtonDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TwoButtonDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TwoButtonDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("come_from", "0");
                        IntentUtil.gotoActivityForResult(context, SetTransactionPasswordActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            } else if ("1".equals(userAccountBean.getCertified()) || ("true".equals(userAccountBean.getTrade_pwd_auth()) && "true".equals(userAccountBean.getRealname_auth()) && "true".equals(userAccountBean.getBand_card()))) {
                /**
                 * 通过新实名认证且绑卡
                 * 或者老用户 设置交易密码，设置实名认证，绑定银行卡
                 *  执行响应操作
                 */
                onBindingBank.bindingBank();
            } else {
                DialogUtil.showTwoBtnDialog(context, title, msg, "取消", "确定", new TwoButtonDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TwoButtonDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TwoButtonDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putInt(FieldConfig.intent_type, 1);
                        IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            }
        }
    }

    /**
     * 验证可用余额不足，弹出提示框
     * bean 此实体主要是传给 购买加油卡的参数
     */
    public static void showMoneyNotEnoughDialog(final Context context, final RemainderNotEnoughBean bean, final OilStationInfoBean.AddOilInfo oilInfo) {
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        builder.setMessage("可用加油金额不足，请购买加油卡后再提交");
        builder.setTitle("余额不足");
        builder.setPositiveButton("购买加油卡",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // TODO Auto-generated method stub
                        Bundle build = new Bundle();
                        build.putSerializable("data", bean);
                        // IntentUtil.gotoActivity(context,);
                        //BuyRefulActivity.openActivity((AddOilActivity2)context);
                        //						IntentUtil.gotoActivity(context, OilServiceBuyOilCardActivity.class, build);//// 2017.06.07

                        GoodsBean.goToProductDetail(context, oilInfo.product_id, oilInfo.product_name, oilInfo.product_type, oilInfo.period);

                    }
                });
        builder.create().show();
    }


    /**
     * 转出操作
     *
     * @param context
     * @param bean
     */
    public static void withdrawCash(final Context context,
                                    final UserAccountBean bean, final boolean isFinish) {

        isBindingBank(context, "关联银行卡", "请先完成关联银行卡，才能进行转出操作", new OnBindingBank() {
            @Override
            public void bindingBank() {
                final Bundle bundle = new Bundle();
                if (isFinish) {
                    bundle.putString("come_from", "2");
                    IntentUtil.gotoActivityAndFinish(context,
                            ExtractActivity2.class, bundle);
                } else {
                    IntentUtil
                            .gotoActivity(context, ExtractActivity2.class, bundle);
                }
            }
        }, OnBindingBank.TYPE_BUY);

        //		HorizontalCustomDialog dialog;
        //		HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
        //				context);
        //		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        //
        //			@Override
        //			public void onClick(DialogInterface dialog, int which) {
        //				// TODO Auto-generated method stub
        //				dialog.dismiss();
        //			}
        //		});
        //
        //		final Bundle bundle = new Bundle();
        //
        //		if (!bean.getBand_card().equalsIgnoreCase("true")) {
        //			/*
        //			 * 未关联银行卡
        //			 */
        //			builder.setTitle("关联银行卡");
        //			builder.setMessage("请先完成关联银行卡，才能进行转出操作");
        //			builder.setPositiveButton("确定",
        //					new DialogInterface.OnClickListener() {
        //
        //						@Override
        //						public void onClick(DialogInterface dialog, int which) {
        //							dialog.dismiss();
        //							// TODO Auto-generated method stub
        //							bundle.putString("come_from", "2");
        //							IntentUtil.gotoActivity(context,
        //									BankCardEditActivity.class, bundle);
        //						}
        //					});
        //			dialog = builder.create();
        //			dialog.show();
        //
        //		} else {
        //
        //			if (isFinish) {
        //				IntentUtil.gotoActivityAndFinish(context,
        //						ExtractActivity2.class, bundle);
        //			} else {
        //				IntentUtil
        //						.gotoActivity(context, ExtractActivity2.class, bundle);
        //			}
        //
        //		}

    }


    /**
     * 充值操作
     *
     * @param context
     * @param bean
     */
    public static void rechargeMoney(final Context context,
                                     final UserAccountBean bean, boolean isFinish) {

        if (bean == null) {
            return;
        }

        HorizontalCustomDialog dialog;
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        final Bundle bundle = new Bundle();

        if (!bean.getBand_card().equalsIgnoreCase("true")) {
            /*
             * 未关联银行卡
			 */
            builder.setTitle("关联银行卡");
            builder.setMessage("请先完成关联银行卡，才能进行充值操作");
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // TODO Auto-generated method stub
                            Bundle bundle = new Bundle();
                            bundle.putInt(FieldConfig.intent_type, 1);
                            IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                                    RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                        }
                    });
            dialog = builder.create();
            dialog.show();

        } else {

            if (isFinish) {
                IntentUtil.gotoActivityAndFinish(context,
                        RechargeActivity2.class, bundle);
            } else {
                IntentUtil
                        .gotoActivity(context, RechargeActivity2.class, bundle);
            }

        }

    }


    /**
     * 新认证（找回交易密码）
     *
     * @param context
     * @param bean
     */
    public static void getDealPas(final Context context,
                                  final UserAccountBean bean, boolean isFinish) {

        if (bean == null) {
            return;
        }

        HorizontalCustomDialog dialog;
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        final Bundle bundle = new Bundle();

        if (!bean.getBand_card().equalsIgnoreCase("true")) {
			/*
			 * 未关联银行卡
			 */
            builder.setTitle("关联银行卡");
            builder.setMessage("请先完成关联银行卡，才能进行找回交易密码操作");
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // TODO Auto-generated method stub
                            bundle.putString("come_from", "2");
                            IntentUtil.gotoActivity(context,
                                    BankCardEditActivity.class, bundle);
                        }
                    });
            dialog = builder.create();
            dialog.show();

        } else {

            if (isFinish) {
                IntentUtil.gotoActivityAndFinish(context,
                        VerificationPhonePasActivity.class, bundle);
            } else {
                IntentUtil
                        .gotoActivity(context, VerificationPhonePasActivity.class, bundle);
            }

        }

    }


    /**
     * 新认证（设置交易密码）
     *
     * @param context
     * @param bean
     */
    public static void setDealPas(final Context context,
                                  final UserAccountBean bean) {

        if (bean == null) {
            return;
        }

        HorizontalCustomDialog dialog;
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        final Bundle bundle = new Bundle();

        if (!bean.getBand_card().equalsIgnoreCase("true")) {
			/*
			 * 未关联银行卡
			 */
            builder.setTitle("关联银行卡");
            builder.setMessage("请先完成关联银行卡，才能进行交易密码操作");
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putString("come_from", "0");
                            IntentUtil.gotoActivity(context, SetTransactionPasswordActivity.class, bundle);
                        }
                    });
            dialog = builder.create();
            dialog.show();

        }

    }


    public interface AgainListener {
        public void execut();

        public void onFail();
    }

    public static void doAgain(int countTime, AgainListener listener) {
        if (countTime <= 3) {
            listener.execut();
        } else {
            listener.onFail();
        }
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || obj.toString().equals("")
                || obj.toString().equals("null");
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
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
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 使用md5的算法进行加密
     *
     * @param plainText
     * @return
     * @updateTime 2015-6-22,下午2:44:29
     * @updateAuthor qw
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * 获取密码输入框
     *
     * @param context
     * @return
     */
    public static PasDialog getGridPasDialog(final Context context) {
        PasDialog pasDialog = new PasDialog(context);
        return pasDialog;
    }


    /**
     * 显示格子密码输入框
     *
     * @param context  上下文
     * @param title    标题
     * @param money    金额文本(可传null)
     * @param listener 监听
     * @version 1.0
     * @createTime 2015-8-28,下午12:46:11
     * @updateTime 2015-8-28,下午12:46:11
     * @createAuthor 綦巍
     * @updateAuthor 綦巍
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static Dialog showGridPasDialog(final Context context, String title,
                                           String money, final OnPasMaxListener listener) {
        final Dialog dialog = new Dialog(context, R.style.NobackDialog);
        View view = View.inflate(context, R.layout.dialog_grid_pas, null);
        final GridPasView gridPasView = (GridPasView) view
                .findViewById(R.id.gridPas_gridPasView);
        TextView txt_title = (TextView) view
                .findViewById(R.id.gridPas_txt_title);
        TextView txt_hint = (TextView) view.findViewById(R.id.gridPas_txt_hint);
        TextView txt_money = (TextView) view
                .findViewById(R.id.gridPas_txt_money);

        TextView forget_pwd_btn = (TextView) view
                .findViewById(R.id.forget_pwd_btn);
		/*
		 * 忘记密码事件
		 */
        forget_pwd_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                //				Bundle bundle = new Bundle();
                //				// bundle.putString("come_from", "4");
                //
                //				// IntentUtil.gotoActivity(context, DealPasSetActivity.class,
                //				// bundle);
                //				bundle.putString(context.getString(R.string.intent_key_sourse),
                //						"3");
                //				IntentUtil.gotoActivity(context, WritePhoneActivity2.class,
                //						bundle);
                //
                //				// Bundle bundle = new Bundle();
                //				// bundle.putString(context.getString(R.string.intent_key_sourse),
                //				// "2");
                //				// IntentUtil.gotoActivity(context, WritePhoneActivity2.class,
                //				// bundle);
                //				dialog.dismiss();


                IntentUtil.gotoActivity(context, VerificationPhonePasActivity.class);
                //checkAccountByRealPas(context,TApplication.getUserInfoBean().getUserNo());

            }
        });

        if (title != null) {
            txt_title.setText(title);
        }
        if (money != null) {
            txt_hint.setVisibility(View.VISIBLE);
            txt_money.setVisibility(View.VISIBLE);
            txt_money.setText(money);
        }
        view.findViewById(R.id.gridPas_img_close).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        gridPasView.hideInputMethod();
                    }
                });
        gridPasView.setOnPasChangeListener(new OnPasChangeListener() {
            @Override
            public void onPasChange(String pas) {
                if (pas.length() == 6) {
                    dialog.dismiss();
                    listener.onPasMax(pas);
                }
            }
        });
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();

        return dialog;
    }

    /**
     * 扫码加油-输入登录密码完场支付弹出窗
     */
    public static Dialog showLoginPasDialog(final Context context, String title, final OnLoginPasListener listener) {
        final Dialog dialog = new Dialog(context, R.style.NobackDialog);
        View view = View.inflate(context, R.layout.dialog_loginpas_pas, null);
        TextView txt_title = (TextView) view.findViewById(R.id.loginPas_txt_title);
        txt_title.setText(title);
        final EditText enterPas_edt = (EditText) view.findViewById(R.id.loginpas_enter_edt);
        TextView cancel_btn = (TextView) view.findViewById(R.id.loginpas_cancel_btn_tv);
        cancel_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView confirm_btn = (TextView) view.findViewById(R.id.loginpas_confirm_btn_tv);
        confirm_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(enterPas_edt.getText().toString())) {
                    listener.OnLoginPas(enterPas_edt.getText().toString());
                    dialog.dismiss();
                } else {
                    ToastUtil.showToast(context, "请输入登录密码");
                }
            }
        });
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
        return dialog;

    }

    /**
     * 扫码加油 弹出密码输入框
     */
    public static Dialog showGridPasDialog2(final Context context, String title,
                                            String money, final OnPasMaxListener listener) {

        final Dialog dialog = new Dialog(context, R.style.NobackDialog);
        View view = View.inflate(context, R.layout.dialog_gridoil_pas, null);
        GridPasView gridPasView = (GridPasView) view
                .findViewById(R.id.gridPas_gridPasView);
        TextView txt_title = (TextView) view
                .findViewById(R.id.gridPas_txt_title);

        TextView txt_money = (TextView) view
                .findViewById(R.id.gridPas_txt_money);

        TextView forget_pwd_btn = (TextView) view
                .findViewById(R.id.forget_pwd_btn);
        pasWrong_tv = (TextView) view
                .findViewById(R.id.gridPas_Paswrong_tv);
		/*
		 * 忘记密码事件
		 */
        forget_pwd_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                //				Bundle bundle = new Bundle();
                //
                //				bundle.putString(context.getString(R.string.intent_key_sourse),
                //						"3");
                //				IntentUtil.gotoActivity(context, WritePhoneActivity2.class,
                //						bundle);
                IntentUtil.gotoActivity(context, VerificationPhonePasActivity.class);

                dialog.dismiss();

            }
        });

        if (title != null) {
            txt_title.setText(title);
        }
        if (money != null) {

            txt_money.setVisibility(View.VISIBLE);
            txt_money.setText(money);
        }
        view.findViewById(R.id.gridPas_img_close).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        gridPasView.setOnPasChangeListener(new OnPasChangeListener() {
            @Override
            public void onPasChange(String pas) {
                if (pas.length() == 6) {

                    listener.onPasMax(pas);
                }
            }
        });
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();

        return dialog;

    }


    /**
     * 给ListView添加头部空白
     *
     * @param listView
     * @param context
     * @updateTime 2015-8-31,下午1:42:50
     * @updateAuthor 綦巍
     */
    public static void addHeadSpace(ListView listView, Context context,
                                    int height) {
        View headSpaceView = new View(context);
        AbsListView.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, height);
        headSpaceView.setLayoutParams(params);
        headSpaceView.setBackgroundResource(R.drawable.item_default_trans);
        listView.addHeaderView(headSpaceView);
    }

    /**
     * 给ListView添加线条
     *
     * @param listView
     * @param context
     * @param isHead   是否是头部
     * @updateTime 2015-8-31,下午1:43:10
     * @updateAuthor 綦巍
     */
    public static void addLine(ListView listView, Context context,
                               boolean isHead) {
        View lineView = new View(context);
        lineView.setBackgroundColor(context.getResources().getColor(
                R.color.view_line));
        AbsListView.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 0.5f));
        lineView.setLayoutParams(params);
        if (isHead) {
            listView.addHeaderView(lineView);
        } else {
            listView.addFooterView(lineView);
        }
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

    /**
     * 判断密码是否正确(6-20个字符，必须包含字母和数字)
     *
     * @param value
     * @return
     * @version 1.0
     * @createTime 2015年10月8日, 上午11:31:36
     * @updateTime 2015年10月8日, 上午11:31:36
     * @createAuthor Geoff
     * @updateAuthor
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static Boolean checkTrue(String value) {
        if (value.length() < 6) {
            return false;
        }
        if (!value.matches(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]")) {
            return false;
        }
        return true;
    }

    /**
     * 计算ListView高度
     *
     * @param listView
     * @param adapter
     * @version 1.0
     * @createTime 2015-10-12,下午5:57:40
     * @updateTime 2015-10-12,下午5:57:40
     * @createAuthor qw
     * @updateAuthor qw
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void setListViewHeightBasedOnChildren(ListView listView,
                                                        BaseAdapter adapter) {
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 判断当前APP是否在运行
     *
     * @param context
     * @return
     * @version 1.0
     * @createTime 2015年11月12日, 下午3:11:32
     * @updateTime 2015年11月12日, 下午3:11:32
     * @createAuthor Geoff
     * @updateAuthor
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static boolean isAppRunning(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(Constant.MY_PKG_NAME)
                    && info.baseActivity.getPackageName().equals(
                    Constant.MY_PKG_NAME)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 格式化银行卡号
     *
     * @param brankNo 银行卡号
     * @return
     * @version 1.0
     * @createTime 2015年11月12日, 下午3:57:31
     * @updateTime 2015年11月12日, 下午3:57:31
     * @createAuthor Geoff
     * @updateAuthor
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String formatBrank(String brankNo) {
        try {
            if (!TextUtils.isEmpty(brankNo) && brankNo.length() > 8) {
                String head = brankNo.substring(0, 4);
                String end = brankNo.substring(brankNo.length() - 4,
                        brankNo.length());
                return head + "********" + end;
            }
            return brankNo;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 格式化手机号
     *
     * @param phone
     * @version 1.0
     * @createTime 2015年11月12日, 下午4:07:12
     * @updateTime 2015年11月12日, 下午4:07:12
     * @createAuthor Geoff
     * @updateAuthor
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String formatPhone(String phone) {
        try {
            if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
                String head = phone.substring(0, 3);
                String end = phone
                        .substring(phone.length() - 4, phone.length());
                return head + "****" + end;
            }
            return phone;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 格式化姓名
     *
     * @param name 姓名
     * @return
     */
    public static String formatNameStr(String name) {
        try {
            if (!TextUtils.isEmpty(name) && name.length() == 3) {
                String head = name.substring(0, 1);
                String end = name
                        .substring(name.length() - 1, name.length());
                return head + "*" + end;
            } else if (!TextUtils.isEmpty(name) && name.length() == 2) {
                String end = name
                        .substring(name.length() - 1, name.length());
                return "*" + end;
            }
            return name;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 正则校验手机号码
     *
     * @param mobileNum 手机号码
     * @return true 正确手机号码；false 非法手机号码
     * @version 1.0
     * @createTime 2013-11-9,下午9:34:24
     * @updateTime 2013-11-9,下午9:34:24
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static boolean isValidMobilePhone(String mobileNum) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(mobileNum);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * @throws
     * @Title: 进入拨号页面
     * @Description: TODO(点击号码时，进入拨号盘)
     * @param: @param activity
     * @param: @param number
     * @return: void
     */
    public static void actionDial(final Activity aty, String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            aty.startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id) {
        if (id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 找回交易密码
     *
     * @version 1.0
     * @createTime 2015-9-21,下午2:33:27
     * @updateTime 2015-9-21,下午2:33:27
     * @createAuthor qw
     * @updateAuthor qw
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private static void checkAccountByRealPas(final Context context, final String phone) {


        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.intent_key_sourse),
                "3");
        bundle.putString(context.getString(R.string.intent_key_phone),
                phone);
        IntentUtil.gotoActivity(context,
                TakePasValidateActivity2.class, bundle);

    }

    /**
     * 上传发票专用弹出框
     */
    public static void uploadBillBank(final Context context, String button1_str, String title, String msg, final PassWrongListenler listenler) {
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton(button1_str, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                //				Bundle bundle = new Bundle();
                //				bundle.putInt(FieldConfig.intent_type, 1);
                //				IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                //						RequestCode.REQUEST_CODE_EDIT_BANK_CARD);

                listenler.forGetPassword();

            }
        });
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton("重试",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // TODO Auto-generated method stub

                        listenler.retry();
                    }
                });
        builder.create().show();

    }

    /**
     * 上传发票取消图片弹出框
     */
    public static void uploadBillCancelPic(final Context context, String title, String msg) {
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();


            }
        });
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // TODO Auto-generated method stub

                        ((ShowBillPicActivity) context).setResult(300);
                        ((ShowBillPicActivity) context).finish();
                    }
                });
        builder.create().show();

    }

    public static void setUserPassword(final Context context, String title, String msg) {

        DialogUtil.showTwoBtnDialog(context, title, msg, "取消", "确定", new TwoButtonDialog.OnClickListener() {
            @Override
            public void onCancelClcik(TwoButtonDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onComfirmClick(TwoButtonDialog dialog) {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("come_from", "0");
                IntentUtil.gotoActivityForResult(context, SetTransactionPasswordActivity.class, bundle,
                        RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
            }
        });
    }

    /**
     * 格式化身份证号
     *
     * @param identityNo
     * @return
     */
    public static String formatIdentityNum(String identityNo) {
        try {
            if (!TextUtils.isEmpty(identityNo) && identityNo.length() > 8) {
                String head = identityNo.substring(0, 2);
                String end = identityNo.substring(identityNo.length() - 4,
                        identityNo.length());
                return head + "************" + end;
            }
            return identityNo;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 格式化姓名(只显示姓)
     *
     * @param name 姓名
     * @return
     */
    public static String formatNameOnlyHeadStr(String name) {
        try {
            if (!TextUtils.isEmpty(name) && name.length() == 3) {
                String head = name.substring(0, 1);
                return head + "**";
            } else if (!TextUtils.isEmpty(name) && name.length() == 2) {
                String head = name.substring(0, 1);
                return head + "*";
            }
            return name;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 云油加油卡的特殊认证
     * 判定实名认证，是否绑定银行卡方法
     *
     * @param context
     * @param msg
     * @param onBindingBank
     * @param type          type为normal：只判断新认证用户 或者  老用户 即可通过判断执行相应操作
     *                      主要用于“转出”，“充值”等操作 不需要设置交易密码的操作
     *                      type为buy：情况1 没有通过新认证，但设置了实名认证以及绑定了银行卡 跳转设置交易密码
     *                      清空2  通过新认或者 或者 是老用户 设置交易密码，设置实名认证，绑定银行卡 可以执行相应操作
     *                      情况3  其他执行 跳转新实名认证流程
     */
    public static void isWeBankProve(final Context context, final String title, final String msg, final OnBindingBank onBindingBank, final String type) {

        getMyAccount(context, new OnGetAccountListener() {
            @Override
            public void onCacheAccount(UserAccountBean userAccountBean) {
                doWeBankProve(context, title, msg, onBindingBank, type);
            }

            @Override
            public void onHttpsGetAccount(UserAccountBean userAccountBean) {
                doWeBankProve(context, title, msg, onBindingBank, type);
            }
        }, false, context.getString(R.string.process_dialog_wait));

    }

    public static void doWeBankProve(final Context context, String title, String msg, OnBindingBank onBindingBank, String type) {
        UserAccountBean userAccountBean = TApplication.getUserAccountBean();
        if (userAccountBean == null) {
            return;
        }
        if (OnBindingBank.TYPE_NORMAL.equals(type)) {
            /**
             * normal的情况
             * 主要用于“转出”，“充值”等操作 不需要设置交易密码的操作
             */
            if ("1".equals(userAccountBean.getCertified()) || "true".equals(userAccountBean.getIs_old_user())) {
                onBindingBank.bindingBank();
            } else {
                DialogUtil.showTinyOpenDialog(context, "", "", "立刻开通", new TinyOpenDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TinyOpenDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TinyOpenDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putInt(FieldConfig.intent_type, 1);
                        bundle.putBoolean("is_webank_card", true);//判断是不是云油加油卡
                        IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            }
        } else if (OnBindingBank.TYPE_NEED_CARD.equals(type)) {
            if (!"1".equals(userAccountBean.getCertified()) && "false".equals(userAccountBean.getTrade_pwd_auth()) && userAccountBean.getBand_card().equalsIgnoreCase("true")) {
                /**
                 * 未通过新实名认证，但已经绑卡的用户,要去设置交易密码
                 * 跳转设置交易密码界面
                 */
                DialogUtil.showTinyOpenDialog(context, "", "", "立刻开通", new TinyOpenDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TinyOpenDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TinyOpenDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("come_from", "0");
                        IntentUtil.gotoActivityForResult(context, SetTransactionPasswordActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            } else if (("1".equals(userAccountBean.getCertified()) && "true".equals(userAccountBean.getBand_card())) || ("true".equals(userAccountBean.getTrade_pwd_auth()) && "true".equals(userAccountBean.getRealname_auth()) && "true".equals(userAccountBean.getBand_card()))) {
                /**
                 * 通过新实名认证且绑卡
                 * 或者老用户 设置交易密码，设置实名认证，绑定银行卡
                 *  执行响应操作
                 */
                onBindingBank.bindingBank();
            } else {
                DialogUtil.showTinyOpenDialog(context, "", "", "立刻开通", new TinyOpenDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TinyOpenDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TinyOpenDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putInt(FieldConfig.intent_type, 1);
                        bundle.putBoolean("is_webank_card", true);//判断是不是云油加油卡
                        IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            }
        } else {
            if (!"1".equals(userAccountBean.getCertified()) && "false".equals(userAccountBean.getTrade_pwd_auth()) && userAccountBean.getBand_card().equalsIgnoreCase("true")) {
                /**
                 * 未通过新实名认证，但已经绑卡的用户,要去设置交易密码
                 * 跳转设置交易密码界面
                 */
                DialogUtil.showTinyOpenDialog(context, "", "", "立刻开通", new TinyOpenDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TinyOpenDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TinyOpenDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("come_from", "0");
                        IntentUtil.gotoActivityForResult(context, SetTransactionPasswordActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            } else if ("1".equals(userAccountBean.getCertified()) || ("true".equals(userAccountBean.getTrade_pwd_auth()) && "true".equals(userAccountBean.getRealname_auth()) && "true".equals(userAccountBean.getBand_card()))) {
                /**
                 * 通过新实名认证且绑卡
                 * 或者老用户 设置交易密码，设置实名认证，绑定银行卡
                 *  执行响应操作
                 */
                onBindingBank.bindingBank();
            } else {
                DialogUtil.showTinyOpenDialog(context, "", "", "立刻开通", new TinyOpenDialog.OnClickListener() {
                    @Override
                    public void onCancelClcik(TinyOpenDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComfirmClick(TinyOpenDialog dialog) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putInt(FieldConfig.intent_type, 1);
                        bundle.putBoolean("is_webank_card", true);//判断是不是云油加油卡
                        IntentUtil.gotoActivityForResult(context, BankCardEditActivity.class, bundle,
                                RequestCode.REQUEST_CODE_EDIT_BANK_CARD);
                    }
                });
            }
        }
    }


    /**
     * 在跳到微众上传身份前需要调用上传四要素接口
     * @param context
     * @param msg
     */
    public static void loadPhotoAdress(final Context context, String msg) {
        CloudOilBiz.getWebankReal(context, msg, new MyRequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                if (result != null) {
                    TinyBankRealBean tinyBankRealBean = (TinyBankRealBean) result.getObject();
                    String istrue = tinyBankRealBean.getIs_true();
                    if ("0".equals(istrue)) {
                        //未实名
                        gotoRealName(context);
                    } else if ("2".equals(istrue)) {
                        //已实名未绑定银行卡  跳转到实名认证
                        gotoBindBank(context);
                    } else {
                        //已实名未上传
                        IntentUtil.gotoActivity(context, UpLoadingIdentityActivity.class);
                    }
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context, result.toString());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public ResponseBean getRequestCache() {
                return null;
            }

            @Override
            public void onRequestCache(ResponseBean result) {

            }
        });
    }

    public static void gotoRealName(final Context context) {
        //未完成实名 认证  跳转到实名谁界面
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.setMessage("您还未进行实名认证请先实名认证后再申请开通银行电子账户");
        builder.setTitle("提示");
        builder.setPositiveButton("立即实名认证",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "tinyMain");
                        IntentUtil.gotoActivity(context, BankCardEditActivity.class, bundle);
                    }
                });
        builder.create().show();
    }

    public static void gotoBindBank(final Context context) {
        HorizontalCustomDialog.Builder builder = new HorizontalCustomDialog.Builder(
                context);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.setMessage("您还未绑定银行卡请先绑定银行卡后再申请开通银行电子账户");
        builder.setTitle("提示");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        IntentUtil.gotoActivity(context, BankCardEditActivity.class);
                    }
                });
        builder.create().show();
    }

}