package com.exchange_v1.app.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.exchange_v1.R;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.LoginBean;
import com.exchange_v1.app.bean.UserInfoBean;
import com.exchange_v1.app.executor.DataBaseExecutor;


/**
 * 用户属性工具类
 *
 */
public class UserInfoUtil {
    /**
     * 获取当前登录用户数据
     *
     * @return 当登录的用户信息
     * @version 1.0
     * @createTime 2013-10-22,下午3:37:15
     * @updateTime 2013-10-22,下午3:37:15
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static UserInfoBean getUserInfo() {
        SpUtil spUtil = SpUtil.getSpUtil(TApplication.context.getString(R.string.spkey_file_userinfo), Activity.MODE_PRIVATE);
        if (isLogin()) { // 如果已经登录，返回用户数据
            String userNo = spUtil.getSPValue(TApplication.context.getString(R.string.spkey_value_userno), "");
            return new DataBaseUtil().getUserBean(userNo);
        } else { // 否则返回空数据
            return TApplication.userInfoBean;
        }
    }

    /**
     * 保存当前登录的用户信息
     *
     * @param bean
     * @version 1.0
     * @createTime 2013-10-22,下午3:38:21
     * @updateTime 2013-10-22,下午3:38:21
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void saveUserInfo(UserInfoBean bean) {
        new DataBaseUtil().insterUserInfo(bean);
    }

    /**
     * 在后台保存用户数据
     *
     * @param bean
     * @version 1.0
     * @createTime 2014年5月30日, 下午4:55:24
     * @updateTime 2014年5月30日, 下午4:55:24
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void saveUserInfoOnBack(final UserInfoBean bean) {
        DataBaseExecutor.addTask(new Runnable() {

            @Override
            public void run() {
                new DataBaseUtil().insterUserInfo(bean);
            }
        });
    }

    /**
     * 注销登录
     *
     * @version 1.0
     * @createTime 2013-10-22,下午3:38:55
     * @updateTime 2013-10-22,下午3:38:55
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void logout() {
        // 以登录用户的帐号生成Md5串作为用户私有文件夹
        SpUtil spUtil = SpUtil.getSpUtil(TApplication.context.getString(R.string.spkey_file_userinfo), Activity.MODE_PRIVATE);
        spUtil.putSPValue(TApplication.context.getString(R.string.spkey_value_isautologin), false);
        spUtil.putSPValue(TApplication.context.getString(R.string.spkey_value_islogin), false);
    }

    /**
     * 是否已经登录
     *
     * @return true已经登录， false未登录
     * @version 1.0
     * @createTime 2013-10-22,下午3:34:26
     * @updateTime 2013-10-22,下午3:34:26
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static boolean isLogin() {
        SpUtil spUtil = SpUtil.getSpUtil(TApplication.context.getString(R.string.spkey_file_userinfo), Activity.MODE_PRIVATE);
        return spUtil.getSPValue(TApplication.context.getString(R.string.spkey_value_islogin), false);
    }

    public static void checkIsUserLoginTodo(Fragment fragment, Runnable toDo) {
//        checkIsUserLoginTodo(fragment, toDo, LoginActivity2.class);
    }

    public static void checkIsUserLoginTodo(Context context, Runnable toDo) {
//        checkIsUserLoginTodo(context, toDo, LoginActivity2.class);
    }

    public static void checkIsUserLoginTodo(Context context, Runnable toDo, Class clazz) {
        // 判断是否登录
        if (Util.isLogin(context)) {
            toDo.run();
        } else {
//            IntentUtil.gotoActivityForResult(context, clazz, RequestCode.REQUEST_CODE_LOGIN);
        }
    }

    public static void checkIsUserLoginTodo(Fragment context, Runnable toDo, Class clazz) {
        // 判断是否登录
        if (TApplication.getUserInfoBean() != null && UserInfoUtil.isLogin()) {
            toDo.run();
        } else {
//            IntentUtil.gotoActivityForResult(context, clazz, RequestCode.REQUEST_CODE_LOGIN);
        }
    }

    /**
     * 设置登录状态
     *
     * @param flag true 已经登陆并且自动登录 false 未登录，并不自动登录
     * @version 1.0
     * @createTime 2014年4月19日, 下午6:14:32
     * @updateTime 2014年4月19日, 下午6:14:32
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void setLoginStatus(boolean flag) {
        // 以登录用户的帐号生成Md5串作为用户私有文件夹
        SpUtil spUtil = SpUtil.getSpUtil(TApplication.context.getString(R.string.spkey_file_userinfo), Activity.MODE_PRIVATE);
        spUtil.putSPValue(TApplication.context.getString(R.string.spkey_value_isautologin), flag);
        spUtil.putSPValue(TApplication.context.getString(R.string.spkey_value_islogin), flag);
        if (flag) {
        }
    }

    /**
     * 获取登录信息
     *
     * @return
     * @version 1.0
     * @createTime 2014年4月19日, 下午5:48:21
     * @updateTime 2014年4月19日, 下午5:48:21
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static LoginBean getLoginBean() {
        SpUtil spUtil = SpUtil.getSpUtil(TApplication.context.getString(R.string.spkey_file_userinfo), Activity.MODE_PRIVATE);
        String account = spUtil.getSPValue(TApplication.context.getString(R.string.spkey_value_account), "");
        String portrait = spUtil.getSPValue(TApplication.context.getString(R.string.spkey_value_portrait), "");
        LoginBean bean = new LoginBean();
        bean.setAccount(account);
        bean.setPortrait(portrait);
        return bean;
    }


    /**
     * 保存登录信息
     *
     * @param bean 登录信息属性
     * @version 1.0
     * @createTime 2014年4月19日, 下午6:11:58
     * @updateTime 2014年4月19日, 下午6:11:58
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void saveLoginInfo(LoginBean bean) {
        SpUtil spUtil = SpUtil.getSpUtil(TApplication.context.getString(R.string.spkey_file_userinfo), Activity.MODE_PRIVATE);
        spUtil.putSPValue(TApplication.context.getString(R.string.spkey_value_account), bean.getAccount());
        spUtil.putSPValue(TApplication.context.getString(R.string.spkey_value_portrait), bean.getPortrait());
        spUtil.putSPValue(TApplication.context.getString(R.string.spkey_value_userno), bean.getUserNo());
    }
}
