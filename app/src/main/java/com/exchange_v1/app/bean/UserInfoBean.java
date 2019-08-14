package com.exchange_v1.app.bean;


import com.exchange_v1.app.config.DataBaseFields;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户信息Bean
 *
 * @Description TODO
 * @author gushiyong
 * @date 2015年8月18日
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司.
 */
public class UserInfoBean extends BaseBean {
	/** 变量描述 */
	private static final long serialVersionUID = -6600529907119714390L;
	/** 注册id */
	private String member_id;
	/** 金币 */
	private String point;
	/** 余额 */
	private String advance;
	/** 冻结余额 */
	private String advance_freeze;

	/** 本地数据库记录id */
	private String id;
	/** 用户id（服务器上用户唯一Id） */
	private String userId;
	/** 用户编号（用户注册后生成的唯一编号） */
	private String userNo;
	/** 账户用户名 */
	private String userName;
		/** 头像 */
	private String portrait;
	/** 生日 */
	private String birthday;
	/** 个性签名 */
	private String signature;
	/** 昵称 */
	private String nickName;
	/** 真实姓名 */
	private String realName;
	/** 性别 */
	private String sex = "";
	/** 所在省份 */
	private String province = "";
	/** 所在城市 */
	private String city = "";
	/** 邮箱 */
	private String email;
	/** 手机号码 */
	private String phoneNum;
	/** 是否自动登录 1、true ; 0、false */
	private String isAutoLogin;
	/** 是否记住密码1、true ; 0、false */
	private String isRemember;
	/** 是否允许推送 1、true ; 0、false */
	private String isAllowPush;
	/** 注册时间 */
	private String registerTime;
	/** 最后登录时间 */
	private String lastLoginTime;
	/** 用户类型 */
	private String usertype;
	/** 身份证号 */
	private String cardNo;
	/** 收货地址 */
	private String userAddr;
	/** 收货电话 */
	private String userTel;
	/** 是否是默认地址 */
	private String isDefault;
	/** 是否选中 1、true 0、false */
	private String isSelect;
	/** 是否设置问题 */
	private String questionAuth;
	/** 是否设置交易密码 */
	private String trade_pwd_auth;
	/** 是否设置真实姓名 */
	private String realname_auth;
	/** pcode值，分销用 */
	private String shop_member_bn;
	private String op_type;//判断是登录还是注册(login 登录 signup注册)
	/**
	 * 加密key
	 */
	private String encryptPass;

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		setMember_id(jSon.optString("member_id"));
		setPoint(jSon.optString("point")); //
		setUserNo(jSon.optString("account"));
		advance = jSon.optString("advance"); // 可用余额
		advance_freeze = jSon.optString("advance_freeze"); // 冻结余额

		setUserTel(jSon.optString("mobile_phone"));
		setUserAddr(jSon.optString("addr"));
		setCardNo(jSon.optString("iden_number"));
		setUserName(jSon.optString("true_name"));

		setRealName(jSon.optString("true_name"));
		setQuestionAuth(jSon.optString("question_auth"));
		setTrade_pwd_auth(jSon.optString("trade_pwd_auth"));
		setRealname_auth(jSon.optString("realname_auth"));
		setShop_member_bn(jSon.optString("shop_member_bn"));
		setOp_type(jSon.optString("op_type"));
		setEncryptPass(jSon.optString("encryptPass"));
	}

	public String getOp_type() {
		return op_type;
	}

	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}

	public String getShop_member_bn() {
		return shop_member_bn;
	}

	public void setShop_member_bn(String shop_member_bn) {
		this.shop_member_bn = shop_member_bn;
	}

	public String getAdvance() {
		return advance;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getQuestionAuth() {
		return questionAuth;
	}

	public void setQuestionAuth(String questionAuth) {
		this.questionAuth = questionAuth;
	}

	public String getTrade_pwd_auth() {
		return trade_pwd_auth;
	}

	public void setTrade_pwd_auth(String trade_pwd_auth) {
		this.trade_pwd_auth = trade_pwd_auth;
	}

	public String getRealname_auth() {
		return realname_auth;
	}

	public void setRealname_auth(String realname_auth) {
		this.realname_auth = realname_auth;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public String getAdvance_freeze() {
		return advance_freeze;
	}

	public void setAdvance_freeze(String advance_freeze) {
		this.advance_freeze = advance_freeze;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIsAutoLogin() {
		return isAutoLogin;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public void setIsAutoLogin(String isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public String getIsRemember() {
		return isRemember;
	}

	public void setIsRemember(String isRemember) {
		this.isRemember = isRemember;
	}

	public String getIsAllowPush() {
		return isAllowPush;
	}

	public void setIsAllowPush(String isAllowPush) {
		this.isAllowPush = isAllowPush;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// ***************************加密的set和
	// get方法******************************

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public String getEmailEncrypt() {
		return EncryptUtil.Encrypt(userNo, email);
	}

	public void setEmailEncrypt(String email) {
		this.email = EncryptUtil.DEcrypt(userNo, email);
	}

	public String getUserNameEncrypt() {
		return EncryptUtil.Encrypt(userNo, userName);
	}

	public void setUserNameEncrypt(String userName) {
		this.userName = EncryptUtil.DEcrypt(userNo, userName);
	}

	public String getPhoneNumEncrypt() {
		return EncryptUtil.Encrypt(userNo, phoneNum);
	}

	public void setPhoneNumEncrypt(String phoneNum) {
		this.phoneNum = EncryptUtil.DEcrypt(userNo, phoneNum);
	}

	public String getProvinceEncrypt() {
		return EncryptUtil.Encrypt(userNo, province);
	}

	public void setProvinceEncrypt(String province) {
		this.province = EncryptUtil.DEcrypt(userNo, province);
	}

	public String getCityEncrypt() {
		return EncryptUtil.Encrypt(userNo, city);
	}

	public void setCityEncrypt(String city) {
		this.city = EncryptUtil.DEcrypt(userNo, city);
	}

	public String getPasswordEncrypt() {
		return "";
	}

	public String getBirthdayEncrypt() {
		return EncryptUtil.Encrypt(userNo, birthday);
	}

	public void setBirthdayEncrypt(String birthday) {
		this.birthday = EncryptUtil.DEcrypt(userNo, birthday);
	}

	public String getNickNameEncrypt() {
		return EncryptUtil.Encrypt(userNo, nickName);
	}

	public void setNickNameEncrypt(String nickName) {
		this.nickName = EncryptUtil.DEcrypt(userNo, nickName);
	}

	public String getSignatureEncrypt() {
		return EncryptUtil.Encrypt(userNo, signature);
	}

	public void setSignatureEncrypt(String signature) {
		this.signature = EncryptUtil.DEcrypt(userNo, signature);
	}

	public String getPortraitEncrypt() {
		return EncryptUtil.Encrypt(userNo, portrait);
	}

	public void setPortraitEncrypt(String portrait) {
		this.portrait = EncryptUtil.DEcrypt(userNo, portrait);
	}

	public String getRealNameEncrypt() {
		return EncryptUtil.Encrypt(userNo, realName);
	}

	public void setRealNameEncrypt(String realName) {
		this.realName = EncryptUtil.DEcrypt(userNo, realName);
	}

	public void setuserTypeEncrypt(String usertype) {
		this.usertype = EncryptUtil.DEcrypt(userNo, usertype);
	}

	public String getuserTypeEncrypt() {
		return EncryptUtil.Encrypt(userNo, usertype);
	}

	public String getEncryptPass() {
		return encryptPass;
	}

	public void setEncryptPass(String encryptPass) {
		this.encryptPass = encryptPass;
	}

	@Override
	public String toString() {
		return "UserInfoBean [id=" + id + ", userId=" + userId + ", userNo=" + userNo + ", userName=" + userName
				+ ", portrait=" + portrait + ", birthday=" + birthday + ", signature=" + signature + ", nickName=" + nickName + ", realName="
				+ realName + ", sex=" + sex + ", province=" + province + ", city=" + city + ", email=" + email + ", phoneNum=" + phoneNum
				+ ", isAutoLogin=" + isAutoLogin + ", isRemember=" + isRemember + ", isAllowPush=" + isAllowPush + ", registerTime=" + registerTime
				+ ", lastLoginTime=" + lastLoginTime + "]";
	}

	/** 获取数据库字段头 */
	public String[] getDataBaseTitles() {
		String[] titles = {
				// 列名
				DataBaseFields.USER_ID, // 用户id
				DataBaseFields.USERNO, // 用户唯一编号
				DataBaseFields.USERNAME, // 账户、用户名
				DataBaseFields.PASSWORD, // 密码
				DataBaseFields.NICKNAME, // 昵称
				DataBaseFields.REALNAME, // 真实姓名
				DataBaseFields.SEX, // 性别
				DataBaseFields.PORTRAIT, // 头像
				DataBaseFields.BIRTHDAY, // 生日
				DataBaseFields.SIGNATURE, // 个性签名
				DataBaseFields.PROVINCE, // 省份
				DataBaseFields.CITY, // 城市
				DataBaseFields.PHONE_NUM, // 手机号码
				DataBaseFields.IS_REMEMBER, // 是否记住密码
				DataBaseFields.IS_AUTO_LOGIN, // 是否自动登录
				DataBaseFields.IS_ALLOW_PUSH, // 是否接受推送消息
				DataBaseFields.IS_ALLOW_RECOMMEND, // 是否允许推荐消息
				DataBaseFields.REGISTER_TIME,// 注册时间
				DataBaseFields.EMAIL,// 邮箱
				DataBaseFields.LAST_LOGIN_TIME,// 最后登录时间
		};
		return titles;
	}

	/** 获取用于数据库存储的所有数据 */
	public String[] getDataBaseValues() {
		String[] values = {
				// 数据
				userId, // 用户id
				userNo, // 用户唯一编号
				getUserNameEncrypt(), // 账户、用户名
				getPasswordEncrypt(), // 密码
				getNickNameEncrypt(), // 昵称
				getRealNameEncrypt(), // 真实姓名
				sex, // 性别
				getPortraitEncrypt(), // 头像
				getBirthdayEncrypt(), // 生日
				getSignatureEncrypt(), // 个性签名
				getProvinceEncrypt(), // 省份
				getCityEncrypt(), // 城市
				getPhoneNumEncrypt(), // 手机号码
				isRemember, // 是否记住密码
				isAutoLogin, // 是否自动登录
				isAllowPush, // 是否接受推送消息
				registerTime,// 注册时间
				getEmailEncrypt(),// 邮箱
				lastLoginTime,// 最后登录时间
		};

		return values;
	}

}
