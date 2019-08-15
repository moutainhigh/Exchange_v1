package com.exchange_v1.app.utils;

import android.database.Cursor;

import com.exchange_v1.app.bean.UserInfoBean;
import com.exchange_v1.app.config.DataBaseFields;
import com.exchange_v1.app.utils.database.DataBaseManage;
import com.exchange_v1.app.utils.database.OperationDataBaseUtil;


/**
 * 数据库操作.
 * 
 */
public class DataBaseUtil {

	/**
	 * 根据类型插入json
	 * 
	 * @version 1.0
	 * @createTime 2015-4-28,下午6:38:11
	 * @updateTime 2015-4-28,下午6:38:11
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param json json
	 * @param type 类型
	 */
//	public static void updateJSON(String json, String type) {
//		OperationDataBaseUtil helper = DataBaseManage
//				.getOperationDataBaseUtil(DataBaseManage.DATA_BASE_NAME);
//		helper.delete(false, DataBaseFields.TB_JSON, DataBaseFields.FIELD_TYPE + " = ? ",
//				new String[] { type });
//		helper.insert(true, DataBaseFields.TB_JSON, new String[] { DataBaseFields.FIELD_TYPE,
//				DataBaseFields.FIELD_JSON }, new String[] { type, json });
//		helper.close();
//	}

	/**
	 * 根据类型获取json
	 * 
	 * @version 1.0
	 * @createTime 2015-4-28,下午6:38:38
	 * @updateTime 2015-4-28,下午6:38:38
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param type 类型
	 * @return
	 */
//	public static String getJSON(String type) {
//		OperationDataBaseUtil helper = DataBaseManage
//				.getOperationDataBaseUtil(DataBaseManage.DATA_BASE_NAME);
//		Cursor cursor = helper.select(DataBaseFields.TB_JSON,
//				new String[] { DataBaseFields.FIELD_JSON }, DataBaseFields.FIELD_TYPE + " = ? ",
//				new String[] { type }, null, null, null, null);
//		String json = null;
//		if (cursor.getCount() > 0) {
//			cursor.moveToNext();
//			json = cursor.getString(cursor.getColumnIndex(DataBaseFields.FIELD_JSON));
//		}
//		cursor.close();
//		helper.close();
//		return json;
//	}

	/**
	 * 获取指定登录用户信息
	 * 
	 * @version 1.0
	 * @createTime 2013-10-22,上午11:04:50
	 * @updateTime 2013-10-22,上午11:04:50
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return 指定的用户数据信息(-1 游客)
	 */
	public UserInfoBean getUserBean(String userNo) {
		String table = DataBaseFields.TB_USER;// 表名
		OperationDataBaseUtil helper = DataBaseManage
				.getOperationDataBaseUtil(DataBaseManage.DATA_BASE_NAME);// 数据库操作对象
		Cursor cursor = helper.select(table, new String[] { "*" }, DataBaseFields.USERNO + " =? ",
				new String[] { userNo }, null, null, "id asc", null);
		UserInfoBean bean = new UserInfoBean();

		if (cursor.moveToNext()) {
			bean.setId(cursor.getString(cursor.getColumnIndex(DataBaseFields.ID)));
			bean.setUserId(cursor.getString(cursor.getColumnIndex(DataBaseFields.USER_ID)));
			// userNO 必须优先保证解析，用于后边的数据加解密
			bean.setUserNo(cursor.getString(cursor.getColumnIndex(DataBaseFields.USERNO)));
		}

		cursor.close();
		helper.close();
		return bean;
	}

	// **************************用户数据表******************************//
	/**
	 * 插入一条登录用户记录
	 * 
	 * @version 1.0
	 * @createTime 2013-10-22,上午10:29:18
	 * @updateTime 2013-10-22,上午10:29:18
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param bean 登录用户属性
	 */
	public void insterUserInfo(UserInfoBean bean) {
		Cursor cursor = null;
		OperationDataBaseUtil helper = null;
		try {
			String table = DataBaseFields.TB_USER;// 表名
			helper = DataBaseManage.getOperationDataBaseUtil(DataBaseManage.DATA_BASE_NAME);
			String[] titles = bean.getDataBaseTitles();
			String[] values = bean.getDataBaseValues();
			// 从本地数据库查找是否存在该用户记录
			cursor = helper.select(table, new String[] { "*" }, DataBaseFields.USERNO + " =? ",
					new String[] { bean.getUserNo() }, null, null, null, null);

			// 如果本地数据库存在该用户，则更新该记录，否则插入一条新的记录
			if (cursor.getCount() <= 0) {
				helper.insert(true, table, titles, values);
			} else {
				helper.update(true, table, titles, values, DataBaseFields.USERNO + " =? ",
						new String[] { bean.getUserNo() });
			}

		} catch (Exception e) {

		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (helper != null) {
				helper.close();
			}
		}
	}

}
