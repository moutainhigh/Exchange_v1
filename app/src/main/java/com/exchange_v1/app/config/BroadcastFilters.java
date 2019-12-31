package com.exchange_v1.app.config;

/**
 * 广播事件
 * 
 */
public class BroadcastFilters {
	// ***************************注册广播接收动作:包名+字段名 ***************************//
	/** 关闭修改密码一界面 */
	public static final String ACTION_FORGET_PWD_CLOSE = "com_exchange_app_action_forget_pwd_close";

	/** 更新用户信息 */
	public static final String ACTION_UPDATE_USER_INFO = "com_exchange_app_action_update_user_info";

	/** 登录后更新我的UI */
	public static final String ACTION_UPDATE_LOGIN_USER = "com_exchange_app_action_update_login_user";

	/** 主动关闭mainActivity */
	public static final String ACTION_CLOSE_MAIN = "com_exchange_app_action_close_main";

	/** 收到订单推送 */
	public static final String ACTION_ORDER = "com_exchange_app_action_order";
	/** 收到订单被抢走推送 */
	public static final String ACTION_ORDER_CANCLE = "com_exchange_app_action_order_cancle";
	/** 收到订单进行中 */
	public static final String ACTION_ORDER_ING = "com_exchange_app_action_order_ing";
	/** 收到订单进行中刷新 */
	public static final String ACTION_ORDER_ING_REFRESH = "com_exchange_app_action_order_ing_refresh";

}
