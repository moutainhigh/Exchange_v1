package com.exchange_v1.app.config;

/**
 * 广播事件
 * 
 * @Description
 * @author qw
 * @date 2015-6-22
 */
public class BroadcastFilters {
	// ***************************注册广播接收动作:包名+字段名 ***************************//
	/** 基本注册动作 */
	public static final String ACTION_TEST = "com_tentinet_c2b_phone_action_test";
	/** 网络状态变化,网络可用 */
	public static final String ACTION_BROADCAST_NET_CHANGE_AVAILABLE = "com_tentinet_c2b_phone_net_change_available";
	/** 网络状态变化,网络不可用 */
	public static final String ACTION_BROADCAST_NET_CHANGE_AVAILABLE_NO = "com_tentinet_c2b_phone_net_change_available_no";
	/** Activity退出 */
	public static final String ACTION_ACTIVITY_EXIT = "com_tentinet_c2b_phone_action_activity_exit";
	/** 语言切换监听 */
	public static final String ACTION_CHANGE_LANGUAGE = "com.tentinet.c2b.activity.Setting.ChangeLanguage";
	/** 立即绑定完毕 */
	public static final String ACTION_BIND_ESSE_OVER = "com.tentinet.c2b.ACTION_BIND_ESSE_OVER";
	/** 回购刷新 */
	public static final String ACTION_BUY_BACK = "com.tentinet.c2b.ACTION_BUY_BACK";
	/** 转让刷新 */
	public static final String ACTION_ATTORN_CARD = "com.tentinet.c2b.ATTORN_CARD";
	/** 领取 */
	public static final String ACTION_ATTORN_CASH = "com.tentinet.c2b.ATTORN_CASH";
	/** 删除购物车数据 */
	public static final String ACTION_ATTORN_DELETE = "com.tentinet.c2b.ATTORN_DELETE";
	/** 注销 */
	public static final String ACTION_LGOIN_LOGOUT = "com.tentinet.c2b.ACTION_login_logout";
	/** 类型切换 */
	public static final String ACTION_TYPE_SWITCH = "com.tentinet.c2b.ACTION_TYPE_SWITCH";
	public static final String ACTION_TYPE_SWITCH1 = "com.tentinet.c2b.ACTION_TYPE_SWITCH1";
	/** 关闭注册界面 */
	public static final String ACTION_CLOSE_REGISTER = "com.tentinet.c2b.action_close_register";
	/** 刷新主页界面 */
	public static final String ACTION_HOME_REFURBISH = "com.tentinet.c2b.action_home_refurbish";
	/** 刷新订单界面 */
	public static final String ACTION_HOME_ORDER_REFURBISH = "com.tentinet.c2b.action_order_refurbish";
	/** 关闭界面 */
	public static final String ACTION_HOME_CLOSE = "com.tentinet.c2b.action_goods_close";
	/** 更新余额 */
	public static final String ACTION_HOME_ADVANCE = "com.tentinet.c2b.action_advance";
	/** 更新未读数量 */
	public static final String ACTION_HOME_NUMBER = "com.tentinet.c2b.action_NUMBER";
	
	/** 关闭详情页面 */
	public static final String ACTION_GOODSDETAILS_CLOSE = "com.tentinet.c2b.action_gooddetails_close";
	/** app留存时长的广播 */
	public static final String ACTION_C2B_TIMER = "com.tentinet.c2b.action_c2b_timer";
	/** 刷新个人信息 */
	public static final String ACTION_C2B_UPDATA_USER_DATA = "com.tentinet.c2b.action_c2b_updata_user_data ";
	/** 关闭快捷支付的绑卡界面 */
	public static final String ACTION_CLOSE_FAST_PAY = "com.tentinet.c2b.action_close_fast_pay";
	/** 关闭快捷支付的  支付订单页 */
	public static final String ACTION_CLOSE_FAST_PAY_PAYSELECT = "com.tentinet.c2b.action_close_fast_pay_payselect";
    /** 我的加油省钱看是否领取成功进行列表的刷新 */
	public static final String ACTION_CLOSE_OIL_CARD_LIST_SUCCESS = "com.tentinet.c2b.action_close_oil_card_list_success";
	/** 退出APP */
	public static final String ACTION_APP_CLOSE = "com.tentinet.c2b.action_app_close";
	/** 回到前台弹出手势密码页面 */
	public static final String ACTION_OPEN_GESTURE = "com.tentinet.c2b.action_open_gesture";
    /** 支付成功后返回购买页面 */
    public static final String ACTION_CLOSE_PAY_RESULT_SUCCESS = "com.tentinet.c2b.action_close_pay_result_success";

}
