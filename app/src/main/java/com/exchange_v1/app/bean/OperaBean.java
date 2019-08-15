package com.exchange_v1.app.bean;


import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.utils.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 数据统计bean
 *
 * @author WangYuWen
 * @version 1.0
 * @date 2014年8月16日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 */
public class OperaBean extends BaseBean {

	public OperaBean() {
		setTime(DateUtil.getDate());// 设置操作时间
		// if (TApplication.getUserInfoBean() != null) {
		// if (!TextUtils.isEmpty(TApplication.getUserInfoBean().getUserNo())) {
		// setUser_Id(TApplication.getUserInfoBean().getUserNo());// 设置用户Id
		// }
		// }
		setType(android.os.Build.MODEL);// 设置机型
		setSystem_verson(android.os.Build.VERSION.RELEASE);// 设置系统版本
		setSystem_model("Android");// 设置系统类型
		setApply_verson(TApplication.VERSION_NAME);// 设置应用版本
	}

	/**
	 *
	 * @author WangYuWen
	 * @version 1.0
	 * @date 2014年8月16日
	 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd.
	 *             All rights reserved.
	 */
	private static final long serialVersionUID = -8829950916372045248L;
	/** 用户Id */
	private String user_Id;
	/** 操作时间 */
	private String time;
	/** 机型 */
	private String type;
	/** 系统版本 */
	private String system_verson;
	/** 应用版本 */
	private String apply_verson;
	/** 系统类型 */
	private String system_model;

	/** 操作模块 */
	private String opera_module;
	/** 操作类型 */
	private String opera_model;
	/** 操作描述（备注） */
	private String opera_remark;

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		// TODO Auto-generated method stub

	}

	public String getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOpera_remark() {
		return opera_remark;
	}

	public void setOpera_remark(String opera_remark) {
		this.opera_remark = opera_remark;
	}

	public String getOpera_module() {
		return opera_module;
	}

	public void setOpera_module(String opera_module) {
		this.opera_module = opera_module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSystem_verson() {
		return system_verson;
	}

	public void setSystem_verson(String system_verson) {
		this.system_verson = system_verson;
	}

	public String getApply_verson() {
		return apply_verson;
	}

	public void setApply_verson(String apply_verson) {
		this.apply_verson = apply_verson;
	}

	public String getSystem_model() {
		return system_model;
	}

	public void setSystem_model(String system_model) {
		this.system_model = system_model;
	}

	public String getOpera_model() {
		return opera_model;
	}

	public void setOpera_model(String opera_model) {
		this.opera_model = opera_model;
	}
}
