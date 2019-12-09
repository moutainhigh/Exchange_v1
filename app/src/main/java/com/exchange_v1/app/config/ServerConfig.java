package com.exchange_v1.app.config;


/**
 * 服务端配置类
 *
 */
public class ServerConfig {

    /**
     * 内网切换，取消注释：“服务器地址_内网”、“API接口方法模块_内网”、“SERVER_TYPE_VALUE”
     */
    // *****************************网请求消息状态码 ******************************//
    /**
     * 请求接口数据成功状态码
     */
    public static final String RESPONSE_STATUS_SUCCESS = "200";
    /**
     * 本地上传失败
     */
    public static final String EXCEPTION_UPLOAD_ERROR_STATUS = "805";

    // **************************失败状态码*****************************//
    /**
     * 注册需要手机号码
     */
    public static final String STATUS_REGISTER_NEED_PHONE = "60001";
    /**
     * 登录需要手机号码
     */
    public static final String STATUS_LOGIN_NEED_PHONE = "60003";
    /**
     * Tickey 过期失效
     */
    public static final String STATUS_TICKEY_UNVALID = "60004";

    // ***************************接口请求配置 ****************************//
    /**
     * 服务器连接方法key
     */
    public static final String SERVER_METHOD_KEY = "token";
    /**
     * 服务器连接类型key
     */
    public static final String SERVER_TYPE_KEY = "key";
    /**
     * 服务器连接类型数据
     */
    public static final String SERVER_TYPE_VALUE = "testb2c";
    /**
     * 服务器连接版本key
     */
    public static final String SERVER_VESRTION_KEY = "v";
    /**
     * 服务器连接版本 数据
     */
    public static final String SERVER_VESRTION_VAULE = "1.0";
    /**
     * 客户端平台
     */
    public static final String SERVER_PLATFORM_VAULE = "platform";

    /**
     * 服务器升级版本key
     */
    public static final String SERVER_UPDATE_KEY = "app_v";
    /**
     * 服务器升级版本 数据
     */
    public static final String SERVER_UPDATE_VAULE = "2.0";
    /**
     * 服务器超时时间
     */
    public static final int SERVER_CONNECT_TIMEOUT = 60000;
    /**
     * 请求数据条数
     */
    public static final String PAGE_COUNT = "10";
    /**
     * 请求数据条数
     */
    public static final String PAGE_LIST_COUNT = "12";
    /**
     * 请求数据条数
     */
    public static final String PAGE_COUNT_COLPENT_PRODUCT = "15";
    /**
     * 请求数据条数
     */
    public static final int PAGE_COUNT_INT = 10;
    /**
     * 管理后台分配给此系统的连接ID的key
     */
    public static final String SERVER_CAS_KEY = "res_key";
    /**
     * 管理后台分配给此系统的连接ID
     */
    public static final String SERVER_CAS_VALUE = "100";


    /**
     * build.gradle配置的SERVER_API_URL
     */
    public static String SERVER_API_URL = "";
    /**
     * 文件服务器地址
     */
    public final static String SERVER_FILE_URL = "http://buddyfile.t.com";
    /**
     * 版本更新服务器
     */
    public static final String SERVER_VERSION_URL = "http://buddy.t.com/Api/version/typeid/0";

    //URL前缀
    public static String SERVER_URL = "http://api.meilishenghuo.cn";

    //注册
    public static String REGISTER_API = SERVER_URL+"/agent/register";
    //发送短信
    public static String SEND_MSG_API = SERVER_URL+"/sms/send";
    //登录
    public static String LOGIN_API = SERVER_URL+"/auth/login";
    //银行卡四要素
    public static String BANKVALID_API = SERVER_URL+"/agent/bankValid";
    //用户激活/缴纳押金
    public static String ACTIVE_API = SERVER_URL+"/agent/active";
    //修改密码
    public static String RESET_PWD_API = SERVER_URL+"/agent/retrieve";
    //用户信息
    public static String USER_INFO_API = SERVER_URL+"/agent/info";
    //用户打开接单
    public static String ON_RECEPTIVE_API = SERVER_URL+"/agent/receptiveOn";
    //用户关闭接单
    public static String OFF_RECEPTIVE_API = SERVER_URL+"/agent/receptiveOff";
    //停用/移除银行卡
    public static String BANK_REMOVE_API = SERVER_URL+"/agent/bankRemove";

    //文件上传接口
    public static String UPLOAD_API = SERVER_URL+"/file/upload";

    //二维码绑定
    public static String BIND_QRCODE_API = SERVER_URL+"/qrCode/upload";
    //用户二维码列表
    public static String QRCODE_LIST_API = SERVER_URL+"/qrCode/list";

    //佣金提现
    public static String BROKERAGE_API = SERVER_URL+"/withdraw/brokerage";
    //跑分币提现
    public static String INTEGRAL_API = SERVER_URL+"/withdraw/integral";
    //提现列表
    public static String WITHDRAW_PAGE_API = SERVER_URL+"/withdraw/page";
    //钱包变动记录
    public static String WALLETHISTORY_API = SERVER_URL+"/walletHistory/page";
    //提现费率接口
    public static String WITH_CHARGE_API = SERVER_URL+"/withdraw/fee";


    //省市地区
    public static String DISTRICT_API = SERVER_URL+"/district/tree";
    //设置地区信息
    public static String SET_DISTRICT_API = SERVER_URL+"/agent/bindDistrict";

    //预充值接口
    public static String RECHARGE_REQUEST_API = SERVER_URL+"/recharge/request";
    //确认充值接口
    public static String RECHARGE_CONFIRM_API = SERVER_URL+"/recharge/confirm";
    //取消充值接口
    public static String RECHARGE_CANCEL_API = SERVER_URL+"/recharge/cancel";
    //充值列表接口
    public static String RECHARGE_LIST_API = SERVER_URL+"/recharge/list";

    //支付订单查询
    public static String ORDER_LIST_API = SERVER_URL+"/paymentOrder/page";
    //抢单
    public static String ORDER_GRAB_API = SERVER_URL+"/paymentOrder/grab";
    //进行中确认
    public static String ORDER_GRAB_CONFIRM_API = SERVER_URL+"/paymentOrder/confirm";
    //进行中列表
    public static String ORDER_GRAB_LIST_API = SERVER_URL+"/paymentOrder/list";

    //极光绑定
    public static String JPUSH_BIND_API = SERVER_URL+"/agentDevice/bind";
    //极光解绑
    public static String JPUSH_UNBIND_API = SERVER_URL+"/agentDevice/unbind";

    //版本升级
    public static String VERSION_UPDATE_API = SERVER_URL+"/upgrade/latest";
    //申请服务商
    public static String SERVICES_REQUEST_API = SERVER_URL+"/services/request";

}
