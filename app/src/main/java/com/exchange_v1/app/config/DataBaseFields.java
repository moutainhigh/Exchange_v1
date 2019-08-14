package com.exchange_v1.app.config;

/**
 * 数据库操作字段
 * 
 * @Description
 * @author 綦巍
 * @date 2015-4-28
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
 */
public class DataBaseFields {
	// *******************************公用字段*****************************//
	/** 本地数据库自增长Id */
	public static final String ID = "id";
	/** 用户昵称 */
	public static final String NICKNAME = "nickname";
	/** 用户备注 */
	public static final String REMARK = "remark";
	/** 真实姓名 */
	public static final String REALNAME = "realname";
	/** 用户头像 */
	public static final String PORTRAIT = "portrait";
	/** 省份 */
	public static final String PROVINCE = "province";
	/** 城市 */
	public static final String CITY = "cn_city";
	/** 个性签名 */
	public static final String SIGNATURE = "signature";
	/** 性别 */
	public static final String SEX = "sex";
	/** 是否接受推送消息 */
	public static final String IS_ALLOW_PUSH = "is_allow_push";
	/** 是否允许推荐消息 */
	public static final String IS_ALLOW_RECOMMEND = "is_allow_recommend";
	/** 邮箱 */
	public static final String EMAIL = "email";

	// *************************************用户表*********************************//
	/**
	 * 用户数据表
	 */
	public static final String TB_USER = "tb_user";
	/** 用户Id */
	public static final String USER_ID = "user_id";
	/** 用户唯一编号 */
	public static final String USERNO = "userNo";
	/** 帐号、用户名 */
	public static final String USERNAME = "username";
	/** 用户密码 */
	public static final String PASSWORD = "password";
	/** 用户生日 */
	public static final String BIRTHDAY = "birthday";
	/** 是否记住密码 */
	public static final String IS_REMEMBER = "is_remember";
	/** 是否自动登录 */
	public static final String IS_AUTO_LOGIN = "is_auto_login";
	/** 注册时间 */
	public static final String REGISTER_TIME = "register_time";
	/** 手机号码 */
	public static final String PHONE_NUM = "phone_num";
	/** 最近一次登录时间 */
	public static final String LAST_LOGIN_TIME = "last_login_time";
	// *************************************用户表*********************************//
	/** json表名 */
	public static final String TB_JSON = "tb_json";
	/** json数据 */
	public static final String FIELD_JSON = "field_json";
	/** json类型 */
	public static final String FIELD_TYPE = "field_type";
	/** 被缓存的jsonType */
	public static final String JSON_CITY = "json_city";




}
