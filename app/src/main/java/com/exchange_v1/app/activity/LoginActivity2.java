package com.exchange_v1.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.LoginBean;
import com.exchange_v1.app.bean.UserInfoBean;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.config.RequestCode;
import com.exchange_v1.app.utils.DataBaseUtil;
import com.exchange_v1.app.utils.ISDoubleClickUtils;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.SpUtil;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.exchange_v1.app.utils.UserInfoUtil;
import com.exchange_v1.app.view.CodeEditText;
import com.exchange_v1.app.view.DeleteEditText;
import com.exchange_v1.app.view.UnderLineEditText;


/**
 * 登录界面
 *
 */
//@MLinkRouter(keys = "havemLinkContent3")
public class LoginActivity2 extends BaseActivity implements OnClickListener {
    public static final String KEY_FROM_ACTIVITY = "KEY_FROM_ACTIVITY";
    public static final String KEY_FROM_ACTIVITY_BUNDLE = "KEY_FROM_ACTIVITY_BUNDLE";
    public static final String KEY_FROM_ACTIVITY_REQUEST_CODE = "KEY_FROM_ACTIVITY_REQUEST_CODE";
    public static final int KEY_LOGIN_ACTIVITY_REQUEST_CODE = 666;
    public static final int LOGIN_PASSWORD_REQUEST_CODE = 256;
    /**
     * 账号
     */
    private UnderLineEditText edit_phone;
    /**
     * 密码
     */
    private CodeEditText edit_password;
    /**
     * 登录
     */
    private TextView btn_login;
    /**
     * 登录实体类
     */
    private LoginBean mLoginBean;
    /**
     * 用户信息实体类
     */
    private UserInfoBean mUserInfoBean;
    /**
     * 1:注册
     */
    private final String C_TYPE_REG = "1";
    /**
     * 2、找回登录密码
     */
    private final String C_TYPE_PWD = "2";

    /**
     * 4、H5页面进入的注册
     */
    private final String C_TYPE_H5 = "4";

    int c_login = 0;

    private String mFromActivity = "";
    private String type_str = "";//摩窗代码，如果是0跳转到产品详情页
    private String oilid_str = "";
    private String jump_type;
    private String jump;
    private String product_id;
    private String type_bn;
    private String period;//新增 期数
    private String share_title;
    private String share_icon;
    private String share_url;
    private String share_text;
    private String event;
    private String prodcutName_str;
    private String productType_str;
    private Bundle bundle;
    private String mMemberId = "null";
    private String gesture;

    private TextView a_login2_password_tv;//账号密码登录
    private TextView a_login2_vip_content_tv;//会员协议
    private TextView login_under_message_send_tv;//短信发送提示
    private TextView mLoginVipAgreement;
    private String mAuthCodeString = "获取验证码";
    private String mPhone;//手机号
    private String statecode;
    private final int HAND_COUNT_DOWN = 10;//倒计时
    private int times = 120;//有效时间2分钟
    private DeleteEditText mPhoneInputEdt;//手机输入框
    //    private DeleteEditText mPasswordInputEdt;//验证码输入框

    private boolean isPhonecheck = false;//手机号是否有数字
    private boolean isPasswordcheck = false;//密码是否有数字
    private boolean isSendSms = false;//判断是否正在发送验证码(正在发送验证码 获取验证码按钮状态不能改变)

    //    private Handler handler = new Handler(new Handler.Callback() {
    //        @Override
    //        public boolean handleMessage(Message msg) {
    //            switch (msg.what) {
    //                case HAND_COUNT_DOWN:
    //                    Integer time = (Integer) msg.obj;
    //                    if (time == 0) {
    //                        //验证码发送完成
    //                        isSendSms = false;
    //
    //                        mEditPasswordRightTv.setEnabled(true);
    //                        istime = false;
    //                        mEditPasswordRightTv.setText(mAuthCodeString);
    //                        //判断是否是手机号  获取验证码状态改变
    //                        if (edit_phone.getText().trim().length() >= 11) {
    //                            setOrangeRightText();
    //                        } else {
    //                            setGrayRightText();
    //                        }
    //
    //                    } else {
    //                        if (istime) {
    //                            //正在发送验证码
    //                            isSendSms = true;
    //                            mEditPasswordRightTv.setText(time + "秒后重新发送");
    //                            //更改背景和字体颜色
    //                            mUnder_line_right_ll.setVisibility(View.VISIBLE);
    //                            mUnder_line_right_ll.setBackgroundResource(R.color.while_1);
    //                            mEditPasswordRightTv.setBackgroundResource(R.color.while_1);
    //                            mEditPasswordRightTv.setTextColor(thisA.getResources().getColor(R.color.common_orange_text));
    //                            time--;
    //                            handler.sendMessageDelayed(handler.obtainMessage(HAND_COUNT_DOWN, time), 1000);
    //                        }
    //                    }
    //
    //                    break;
    //                default:
    //                    break;
    //            }
    //            return true;
    //        }
    //    });

    @Override
    protected int getContentViewId() {
        return R.layout.a_login2;
    }

    @Override
    protected void findViews() {

        edit_phone = (UnderLineEditText) findViewById(R.id.login_under_line_edit_phone);
        edit_phone.setLeftIvIcon(thisA.getResources().getDrawable(R.drawable.setting_phone_icon));//设置图片
        mPhoneInputEdt = edit_phone.getInput_edt();
        mPhoneInputEdt.setInputType(InputType.TYPE_CLASS_PHONE);
        //设置只能输入11位
        mPhoneInputEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        edit_password = (CodeEditText) findViewById(R.id.login_under_line_edit_password);
        //        mPasswordInputEdt = edit_password.getInput_edt();

        btn_login = (TextView) findViewById(R.id.login_under_line_btn_login);
        a_login2_password_tv = (TextView) findViewById(R.id.a_login2_password_tv);
        a_login2_vip_content_tv = (TextView) findViewById(R.id.a_login2_vip_content_tv);
        login_under_message_send_tv = (TextView) findViewById(R.id.login_under_message_send_tv);
        mLoginVipAgreement = (TextView) findViewById(R.id.login_vip_agreement);
    }

    @Override
    protected void initGetData() {
        mLoginBean = UserInfoUtil.getLoginBean();
        if (mLoginBean != null) {
            if (mLoginBean.getAccount() != null) {
                edit_phone.setText(mLoginBean.getAccount());
            }
        }

        //		initDebugAccount();
        bundle = getBundle();
        if (bundle != null) {
            mFromActivity = bundle.getString(KEY_FROM_ACTIVITY);
            /**摩窗代码*/
            type_str = bundle.getString("type");

            /**摩窗代码*/
            oilid_str = bundle.getString("oilid");
            prodcutName_str = bundle.getString("productName");
            productType_str = bundle.getString("productType");
            jump_type = bundle.getString(getString(R.string.intent_key_jump_type));
            jump = bundle.getString(getString(R.string.intent_key_jump));
            product_id = bundle.getString(getString(R.string.intent_key_product_id));
            type_bn = bundle.getString(getString(R.string.intent_key_type_bn));
            period = bundle.getString("period");
            share_title = bundle.getString(getString(R.string.intent_key_title));
            share_icon = bundle.getString(getString(R.string.intent_key_ico));
            share_url = bundle.getString(getString(R.string.intent_key_url));
            share_text = bundle.getString(getString(R.string.intent_key_text));
            event = bundle.getString(getString(R.string.intent_key_event));
            gesture = bundle.getString("gesture");
        }

        //判断是否是手机号  获取验证码状态改变
        if (edit_phone.getText().trim().length() >= 11) {
            setOrangeRightText();
        } else {
            setGrayRightText();
        }

        //判断手机号一开始进来是否有值
        if (edit_phone.getText().length() > 0) {
            isPhonecheck = true;
        }
        SpannableString string = new SpannableString("《会员服务协议》");
        string.setSpan(new UnderlineSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mLoginVipAgreement.setText(string);
        //开始按钮状态的改变
        showLoginView();
    }


    //	/**
    //	 * 默认一个帐号密码，方便登录调试
    //	 */
    //	private void initDebugAccount() {
    //		if(Constant.DEBUG_SWITCH){
    //			edit_phone.setText(Constant.DEBUG_COUNT);
    //			edit_password.setText(Constant.DEBUG_PWD);
    //		}
    //	}

    @Override
    protected void widgetListener() {
        titleView.setBackBtn();
        btn_login.setOnClickListener(this);
        a_login2_password_tv.setOnClickListener(this);
        a_login2_vip_content_tv.setOnClickListener(this);
        mLoginVipAgreement.setOnClickListener(this);
        //手机文本监听
        mPhoneInputEdt.setOnTextChangeListener(new DeleteEditText.OnTextChangeListener() {
            @Override
            public void beforeChange(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterChange(Editable s) {
                //手机文本监听(获取验证码变状态)
                if (s.toString().length() >= 11) {
                    setOrangeRightText();
                } else {
                    setGrayRightText();
                }

                //判断是否有数字(开始按钮的状态设置)
                if (s.toString().length() > 0) {
                    isPhonecheck = true;
                } else {
                    isPhonecheck = false;
                }

                //开始按钮状态的改变
                showLoginView();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        //验证码文本监听
        edit_password.setOnTextChangeListener(new CodeEditText.OnTextChangeListener() {
            @Override
            public void beforeChange(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterChange(Editable s) {
                //判断是否有数字(开始按钮的状态设置)
                if (s.toString().length() > 0) {
                    isPasswordcheck = true;
                } else {
                    isPasswordcheck = false;
                }

                //开始按钮状态的改变
                showLoginView();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        edit_password.setOnCountDownListener(new CodeEditText.OnCountDownListener() {
            @Override
            public void countDownEnd() {
                //倒计时结束时判断是否是手机号  改变按钮状态
                if (edit_phone.getText().trim().length() >= 11) {
                    setOrangeRightText();
                } else {
                    setGrayRightText();
                }
            }
        });

        edit_password.setOnClickCodeListener(new CodeEditText.onClickCode() {
            @Override
            public void onclick(View v) {
                getCode();
            }
        });

    }

    //开始按钮状态的改变
    private void showLoginView() {
        if (isPhonecheck && isPasswordcheck) {
            //开始按钮变灰
            btn_login.setEnabled(true);
        } else {
            //开始按钮变灰
            btn_login.setEnabled(false);
        }
    }

    @Override
    protected void init() {
        titleView.setTitle("手机验证");
        mLoginBean = new LoginBean();

    }

    //	@Override
    //	protected void doTest() {
    ////		super.doTest();
    //		String phone = "";
    //		String password = "";
    //		if (BuildConfig.SERVER_API_URL.equals("http://192.168.101.53/branch_v2.3_balance/index.php/b2cphone/")) {
    //			//53环境
    ////		phone = "13631617535";//测试的测试账号，53环境
    ////		password = "a123456";//支付密码000000
    ////		phone = "15578207241";//测试的测试账号，53环境
    ////		password = "a11111";//支付密码000000
    ////		phone = "13200000013";//测试的测试账号，53环境,油箱有油
    ////		password = "m000000";
    //			phone = "13200000115";//测试的测试账号，李亮
    //			password = "m000000";//支付密码000000
    //		}else if(BuildConfig.SERVER_API_URL.equals("https://democ2b.brightoilonline.com/index.php/b2cphone/")){
    //		//预生产环境
    ////			phone = "15089199422";//测试的测试账号，在预生产,有加油卡
    ////			password = "456963sa";
    ////		phone = "13200000111";//测试的测试账号，在预生产
    ////		password = "m000000";
    ////		phone = "18098992353";//测试的测试账号，在预生产,没有现金券
    ////		password = "123123a";
    ////		phone = "13200000117";//测试的测试账号，在预生产,油箱有油
    ////		password = "m000000";
    // 		phone = "13200000115";//zwd测试帐号
    //		password = "m000000";
    ////		phone = "13066960656";//老魏的测试账号，在预生产
    ////		password = "abc123456";
    //		}
    //
    //		if (
    //				(!StringUtil.isEmpty(phone))
    //						&& (!StringUtil.isEmpty(password))
    //				) {
    //			TVUtils.setText(edit_phone.getInput_edt(), phone);
    //			TVUtils.setText(edit_password.getInput_edt(), password);
    //		}
    //
    //	}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_under_line_btn_login: // 开始按钮(登录或者注册)
                if (check()) {
                    // 三星手机需要先隐藏键盘，否则会有花屏的情况
                    titleView.showInput(false);
                    if (!ISDoubleClickUtils.isDoubleClick()) {//防止多次点击
                        login(edit_phone.getText().toString().trim(), edit_password.getText().toString().trim());
                    }
                }
                break;

            case R.id.a_login2_password_tv: //账号密码登录
//                Bundle bundle = new Bundle();
//                sendString(bundle, getString(R.string.intent_key_jump_type), jump_type);
//                sendString(bundle, context.getString(R.string.intent_key_jump), jump);
//                sendString(bundle, context.getString(R.string.intent_key_event), event);
//                sendString(bundle, context.getString(R.string.intent_key_url), share_url);
//                IntentUtil.gotoActivityForResult(thisA, PasswordLoginActivity.class,bundle, LOGIN_PASSWORD_REQUEST_CODE);
                break;

            case R.id.login_vip_agreement: //会员协议
//                //友盟、百度统计埋点--注册页面-会员注册协议
//                CountUtil.sendDataForStatistics(this, CountIdUtil.RegisterPage_protocol);
//                IntentUtil.gotoActivity(this, RegisterProtocol.class);
                break;
            default:
                break;
        }
    }

    private void sendString(Bundle bundle, String key, String string) {
        if (!StringUtil.isEmpty(string)) {
            bundle.putString(key, string);
        }
    }

    /**
     * 检查必填项
     *
     * @return
     * @version 1.0
     * @createTime 2015年8月19日, 下午3:45:20
     * @updateTime 2015年8月19日, 下午3:45:20
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private boolean check() {
        // 账号
        String phone = edit_phone.getText().trim();
        // 验证码
        String password = edit_password.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast(LoginActivity2.this,
                    getString(R.string.hint_place_login_phone));
            edit_phone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(LoginActivity2.this, TApplication.context.getResources().getString(R.string.hint_place_login_pwd));
            edit_password.requestFocus();
            return false;
        }

        // if (!RegularUtil.isCrrectPassword(password)) {
        // ToastUtil.showToast(context,
        // getResources().getString(R.string.activity_forget_mimarule));
        // return false;
        // }

        mLoginBean.setAccount(phone);
        return true;
    }

    /**
     * 登录
     *
     * @param account 手机号
     * @param vcode   手机验证码
     * @version 1.0
     * @createTime 2015年8月19日, 下午4:12:45
     * @updateTime 2015年8月19日, 下午4:12:45
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private void login(final String account, final String vcode) {
//        MineBiz.quickLogin(thisA, getString(R.string.process_dialog_wait), account, vcode, new MyRequestHandle() {
//
//            @Override
//            public void onSuccess(ResponseBean responseBean) {
//
//                c_login = 0;
//                mUserInfoBean = (UserInfoBean) responseBean.getObject();
//                mUserInfoBean.setUserName(account);
//
//                updateAccountInfo();
//                //先保存账号--保存用户信息时候要用
//                saveUserData();
//                //保存用户信息
//                TApplication.setUserInfoBean(mUserInfoBean);
//                TApplication.setSecretKey(mUserInfoBean.getEncryptPass());
//
//                // 构建该用户的专用数据库
//                DataBaseManage.createDataBase(mUserInfoBean.getUserNo());
//                // 构建该用户的专用文件目录
//                FileUtil.createAllFile();
//                EventBus.getDefault().post(new LoginEvent(true, account));
//                /** 通知首页更新 */
//                EventBus.getDefault().post(new UpdateHomeEvent());
//                EventBus.getDefault().post(new CloudRefulshBean());
//                //登录成功  自定义统计事件
//                String phoneNumber = mLoginBean.getAccount();
//                if (TApplication.userInfoBean != null) {
//                    mMemberId = TApplication.userInfoBean.getMember_id();
//                }
//                if (mUserInfoBean != null) {
//                    if ("login".equalsIgnoreCase(mUserInfoBean.getOp_type())) {
//                        CountUtil.sendC2bLoginSuccess(LoginActivity2.this, mMemberId, phoneNumber);
//                    } else if ("signup".equalsIgnoreCase(mUserInfoBean.getOp_type())) {
//                        CountUtil.sendC2bRegisterSuccess(LoginActivity2.this, mMemberId, phoneNumber);
//                    }
//                }
//                //友盟、百度统计埋点--登录
//                CountUtil.sendDataForStatistics(LoginActivity2.this, CountIdUtil.LoginPage_login);
//                //自定义统计事件 页面跳转统计
//                ListUtils.getListUtils().setStatisticsBeanData(thisA, getEndTime() + "", "LoginActivity2", getNowTime() + "");
//
//
//            }
//
//            @Override
//            public void onRequestCache(ResponseBean result) {
//
//            }
//
//            @Override
//            public void onFail(ResponseBean responseBean) {
//                // TODO Auto-generated method stub
//                ToastUtil.showToast(context, responseBean.getInfo());
//                //登录失败 自定义统计事件
//                CountUtil.sendC2bLoginFail(LoginActivity2.this, responseBean.getInfo());
//
//            }
//
//            @Override
//            public void onCancel() {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public ResponseBean getRequestCache() {
//                // TODO Auto-generated method stub
//                return null;
//            }
//        });


    }

    private void updateAccountInfo() {
//        MineBiz.updateAccountInfo(thisA, getString(R.string.process_dialog_wait), new MyRequestHandle() {
//
//            @Override
//            public void onSuccess(ResponseBean result) {
//                // TODO Auto-generated method stub
//                UserAccountBean userBean = (UserAccountBean) result.getObject();
//                TApplication.setUserAccountBean(userBean);
//                if (!StringUtil.isEmpty(userBean.getLatest_oilid())
//                        && (!userBean.getLatest_oilid().equals(SpUtil.getSpTankDeductionId(userBean.getMoblie()))) && "0".equals(userBean.getOil_used())) {
//                    TApplication.setOilTankChange(true);
//                    TApplication.setShowMineRed(true);
//                }
//                if ("1".equals(userBean.getOil_used())) {
//                    TApplication.setOilTankChange(false);
//                }
//                if (!StringUtil.isEmpty(userBean.getLatest_cpnid())
//                        && (!userBean.getLatest_cpnid().equals(SpUtil.getSpCouponId(userBean.getMoblie()))) && "0".equals(userBean.getCpn_used())) {
//                    TApplication.setCpnChange(true);
//                    TApplication.setShowMineRed(true);
//                }
//                if ("1".equals(userBean.getCpn_used())) {
//                    TApplication.setCpnChange(false);
//                }
//                SpUtil.setSpCouponId(userBean.getLatest_cpnid(), userBean.getMoblie());
//                SpUtil.setSpTankDeductionId(userBean.getLatest_oilid(), userBean.getMoblie());
//                //setResult(RequestCode.GOODSDETAIL_LOGINSUCCESS);
//                Bundle bundle = getIntent().getExtras();
//                if (bundle != null && !TextUtils.isEmpty(bundle.getString(getString(R.string.intent_key_from)))) {
//                    Intent backIntent = new Intent();
//                    backIntent.putExtras(bundle);
//                    setResult(RESULT_OK, backIntent);
//                } else {
//                    setResult(RequestCode.GOODSDETAIL_LOGINSUCCESS);
//                }
//                Intent intent = new Intent();
//                intent.setAction(BroadcastFilters.ACTION_LGOIN_LOGOUT);
//                //TApplication.context.sendBroadcast(intent);
//                Util.sendBroadcast(context, intent);
//
//                //				// 更新购物车未读数量
//                //				intent = new Intent();
//                //				intent.setAction(BroadcastFilters.ACTION_HOME_NUMBER);
//                //				TApplication.context.sendBroadcast(intent);
//
//                //				Intent result = new Intent();
//                //				result.putExtras(getBundle());
//                setResult(RESULT_OK, getIntent());
//                goNextAction();
//                //如果是外部h5摩窗跳过来的 就让它跳产品详情页
//                if (type_str != null && type_str.equalsIgnoreCase("recommend")) { //跳转到推荐有奖页面
//                    SpecialH5Activity.openActivity(context, "", "", true);
//                } else if (type_str != null && type_str.equalsIgnoreCase("goil")) {//跳转到我的邮箱
//                    //                    Bundle bundle1 = new Bundle();
//                    //                    bundle.putSerializable("user_accountinfo",TApplication.getUserAccountBean());
//                    //                    IntentUtil.gotoActivity(context, LayOilActivity.class, bundle1);
//                    IntentUtil.gotoActivity(context, OilTankActivity.class);
//
//                } else if (type_str != null && type_str.equalsIgnoreCase("cashcoupon")) {//跳转到我的优惠券
//                    IntentUtil.gotoActivity(context, EVoucherActivity2_7_3.class);
//                } else if (type_str != null && type_str.equalsIgnoreCase("recommend")) {//跳转到推荐有奖界面
//                    SpecialH5Activity.openActivity(context, "", "", true);
//                }
//
//                //主动登录，把手势密码清理掉
//                //2018-8-22 银联过检 放开手势密码
//                GestureUtil.clearCode(mUserInfoBean.getUserNo());
//
//                if (mUserInfoBean != null) {
//                    if ("login".equalsIgnoreCase(mUserInfoBean.getOp_type())) {
//                        finish();
//                        IntentUtil.gotoActivity(context, GestureActivity.class);
//                    } else if ("signup".equalsIgnoreCase(mUserInfoBean.getOp_type())) {
//                        Bundle bundle2 = new Bundle();
//                        bundle2.putString("from", "login");
//                        IntentUtil.gotoActivityAndFinish(thisA, LoginSkipActivity.class, bundle2);
//                    }
//                } else {
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFail(ResponseBean result) {
//                ToastUtil.showToast(thisA, result.getInfo());
//
//            }
//
//            @Override
//            public void onCancel() {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public ResponseBean getRequestCache() {
//                // TODO Auto-generated method stub
//                return null;
//            }
//
//            @Override
//            public void onRequestCache(ResponseBean result) {
//                // TODO Auto-generated method stub
//
//            }
//
//        });
    }

    /**
     * 从H5 登录后的跳转逻辑
     */
    private void goNextAction() {
//        if (!StringUtil.isEmpty(event)) {
//            if (event.equals("login")) {//登录
//
//                if (!StringUtil.isEmpty(jump_type) && !StringUtil.isEmpty(jump)) {
//                    if (jump_type.equals("function")) {
//                        if (jump.equals("home")) {//首页
//                            SpecialH5Activity.goHome(context);
//                        } else if (jump.equals("productList")) {//产品列表
//                            SpecialH5Activity.goProductListActivity(context);
//                        } else if (jump.equals("product")) {//产品详情
//                            SpecialH5Activity.goProductDeatilActivity(context, product_id, type_bn, period);
//                            LogUtil.out("-----产品详情-->" + "product_id:" + product_id + " __type_bn:" + type_bn);
//                        } else if (jump.equals("recommend")) {//推荐有奖详情页
//                            SpecialH5Activity.goRecommendActivity(context);
//                        } else if (jump.equals("cashcoupon")) {//优惠卷列表
//                            SpecialH5Activity.goCashActivity(context);
//                        } else if (jump.equals("scan")) {//扫码加油
//                            SpecialH5Activity.goScanActivity(context);
//                        } else if (jump.equals("recharge")) {//加油卡充值
//                            SpecialH5Activity.goOilsPayActivity(context);
//                        } else if (jump.equals("goil")) {//我的油箱
//                            SpecialH5Activity.goMyOilsActivity(context);
//                        } else if (jump.equals("share")) {//分享
//                            //分享只能在onActivityResult中去处理
//                            //                            SpecialH5Activity.goShareWindow(context,share_title,share_text,share_url,share_icon);
//
//                            //                            Bundle extras = getIntent().getExtras();
//                            //                            extras.putString(getString(R.string.intent_key_share),"1");
//                            //                            Intent backIntent = new Intent();
//                            //                            backIntent.putExtras(extras);
//                            //                            setResult(RESULT_OK, backIntent);
//                        }
//                    } else if (jump_type.equals("url")) {
//                        SpecialH5Activity.openActivity(context, "", jump);
//                    }
//                }
//            } else if (event.equals("home")) {//首页
//                SpecialH5Activity.goHome(context);
//            } else if (event.equals("productList")) {//产品列表
//                SpecialH5Activity.goProductListActivity(context);
//            } else if (event.equals("product")) {//产品详情
//                SpecialH5Activity.goProductDeatilActivity(context, product_id, type_bn, period);
//            } else if (event.equals("recommend")) {//推荐有奖详情页
//                SpecialH5Activity.goRecommendActivity(context);
//            } else if (event.equals("cashcoupon")) {//优惠卷列表
//                SpecialH5Activity.goCashActivity(context);
//            } else if (event.equals("scan")) {//扫码加油
//                SpecialH5Activity.goScanActivity(context);
//            } else if (event.equals("recharge")) {//加油卡充值
//                SpecialH5Activity.goOilsPayActivity(context);
//            } else if (event.equals("goil")) {//我的油箱
//                SpecialH5Activity.goMyOilsActivity(context);
//            }
//
//            return;//结束返回
//        }

    }

    /**
     * 保存用户数据
     *
     * @version 1.0
     * @createTime 2015年8月19日, 下午4:40:25
     * @updateTime 2015年8月19日, 下午4:40:25
     * @createAuthor gushiyong
     * @updateAuthor gushiyong
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    protected void saveUserData() {

        mLoginBean.setPortrait(mUserInfoBean.getPortrait());
        mLoginBean.setUserNo(mUserInfoBean.getUserNo());
        // 保存登录信息
        UserInfoUtil.saveLoginInfo(mLoginBean);

        // 将新用户数据更新到数据库
        new DataBaseUtil().insterUserInfo(mUserInfoBean);

        SpUtil spUtil = SpUtil.getSpUtil(
                getString(R.string.spkey_file_userinfo), MODE_PRIVATE);
        spUtil.putSPValue(getString(R.string.spkey_value_islogin), true);
        spUtil.putSPValue(getString(R.string.spkey_value_isautologin), true);
    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BroadcastFilters.ACTION_CLOSE_REGISTER)) {
            finish();
        }
        super.onReceive(context, intent);
    }

    public static void startLoginAfterStartTarget(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;

            startLoginAfterStartTarget(context, activity.getClass().getName(), activity.getIntent().getExtras());
        }
    }

    /**
     * 直接关闭，因为需要重新刷新目的页面
     *
     * @param context
     * @param activityName
     * @param fromActivityBundle
     */
    public static void startLoginAfterStartTarget(Context context, String activityName, Bundle fromActivityBundle) {
        doGoToLogin(context, activityName, fromActivityBundle, 0, false);
        //		ToastUtil.showToast(context,"请先登录");//应测试（王康）所要求，不能跳转登陆页面
    }


    public static void loginCallback(Context thisA, Intent data) {

    }

    /**
     * 跳转到Login页面并携带被跳转的页面的参数
     *
     * @param context
     * @param activityName
     * @param fromActivityBundle
     */
    private static void doGoToLogin(Context context, String activityName, Bundle fromActivityBundle, int requestCode, boolean isNeedFinish) {
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.intent_key_from), "");
        bundle.putString(LoginActivity2.KEY_FROM_ACTIVITY, activityName);
        bundle.putInt(context.getString(R.string.intent_key_operation), 666);
        bundle.putInt(LoginActivity2.KEY_FROM_ACTIVITY_REQUEST_CODE, requestCode);

        bundle.putBundle(LoginActivity2.KEY_FROM_ACTIVITY_BUNDLE, fromActivityBundle);

        if (isNeedFinish) {
            IntentUtil.gotoActivityAndFinish(context, LoginActivity2.class, bundle);
        } else {
            IntentUtil.gotoActivityForResult(context, LoginActivity2.class, bundle, KEY_LOGIN_ACTIVITY_REQUEST_CODE);
        }

    }

    /**
     * 不用关闭"源页面"，因为需要回调"源页面"的onActivityForResult
     *
     * @param thisA
     * @param activityName
     * @param fromActivityBundle
     * @param requestCode
     */
    public static void startLoginAfterStartTargetNoFinish(Context thisA, String activityName, Bundle fromActivityBundle, int requestCode) {
        doGoToLogin(thisA, activityName, fromActivityBundle, requestCode, false);
    }

    public static void h5CallDoLogin(Context context) {
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.intent_key_from), "");
        bundle.putInt(context.getString(R.string.intent_key_operation), 0x88);
        Intent intent = new Intent(context, LoginActivity2.class);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, RequestCode.REQUEST_CODE_LOGIN);
    }

    public static void onActivityResult(Activity thisA, int requestCode, int resultCode, Intent data, Runnable toDo) {
        if (data == null) {
            return;
        }
        if (thisA == null) {
            return;
        }
        if (!(thisA instanceof Activity)) {
            return;
        }
        switch (requestCode) {
            case KEY_LOGIN_ACTIVITY_REQUEST_CODE:
            case RequestCode.REQUEST_CODE_LOGIN:
                if (resultCode == RESULT_OK) {
                    if (toDo != null) {
                        toDo.run();
                    }
                }
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //如果密码登录成功回调
        if (requestCode == LOGIN_PASSWORD_REQUEST_CODE && resultCode == RESULT_OK) {
            //启动这个页面需要数据回传
            setResult(RESULT_OK, data);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //设置右边的黄色获取验证码
    private void setOrangeRightText() {
        //不是正在发送验证码状态才改变按钮的背景
        //        if (!isSendSms) {
        //            mUnder_line_right_ll.setVisibility(View.VISIBLE);
        //            mUnder_line_right_ll.setBackgroundResource(R.drawable.shape_item_yellow);
        //            mEditPasswordRightTv.setBackgroundResource(R.drawable.shape_item_yellow);
        //            mEditPasswordRightTv.setTextColor(thisA.getResources().getColor(R.color.while_1));
        //            mEditPasswordRightTv.setEnabled(true);
        //        }
        edit_password.setOrangeRightBtn();
    }

    //设置右边的灰色获取验证码
    private void setGrayRightText() {
        //不是正在发送验证码状态才改变按钮的背景
        //        if (!isSendSms) {
        //            mUnder_line_right_ll.setVisibility(View.VISIBLE);
        //            mUnder_line_right_ll.setBackgroundResource(R.drawable.shape_item_gray);
        //            mEditPasswordRightTv.setBackgroundResource(R.drawable.shape_item_gray);
        //            mEditPasswordRightTv.setTextColor(thisA.getResources().getColor(R.color.while_1));
        //            mEditPasswordRightTv.setEnabled(false);
        //        }
        edit_password.setGrayUnableBtn();
    }


    /**
     * 获取校验码
     */
    private void getCode() {
        mPhone = edit_phone.getText().toString().trim();
        getIdentifyingCode(mPhone, statecode);
    }

    /**
     * 获取取验证码
     *
     * @param phone
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private void getIdentifyingCode(final String phone, final String contry_code) {
//        MineBiz.getQuickIdentifyingCode(context, "", phone, "quick_login", "", contry_code, new RequestHandle() {
//
//            @Override
//            public void onSuccess(ResponseBean result) {
//                login_under_message_send_tv.setVisibility(View.VISIBLE);
//                login_under_message_send_tv.setText("短信验证码已发送至" + mPhone + "，请查收");
//                ToastUtil.showToast(thisA, getString(R.string.result_send_sms_ok));
//            }
//
//            @Override
//            public void onFail(ResponseBean result) {
//                //				edit_identifyingcode.recoveryBtn();
//                login_under_message_send_tv.setVisibility(View.GONE);
//                ToastUtil.showToast(thisA,
//                        result.getInfo());
//            }
//        });

    }
}