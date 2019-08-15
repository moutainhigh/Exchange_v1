package com.exchange_v1.app.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.EditText;
import android.widget.TextView;


import com.exchange_v1.R;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.config.FileConfig;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @author 罗文忠
 * @version 1.0
 * @date 2013-7-27
 */
public class StringUtil {

    /**
     * 转换成千进制.
     *
     * @param number
     * 原数.
     * @return
     * @version 1.0
     * @createTime 2014年1月20日, 下午5:44:05
     * @updateTime 2014年1月20日, 下午5:44:05
     * @createAuthor paladin
     * @updateAuthor paladin
     * @updateInfo
     */

    public static DecimalFormat num2Format = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.CHINA));

    public static String getThousandSystem(String number) {
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        return df.format(Integer.valueOf(number));
    }

    /**
     * 转换成千进制.
     *
     * @param number 原数.
     * @return
     * @version 1.0
     * @createTime 2014年1月20日, 下午5:44:05
     * @updateTime 2014年1月20日, 下午5:44:05
     * @createAuthor paladin
     * @updateAuthor paladin
     * @updateInfo
     */
    public static String getThousandSystem(int number) {
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        return df.format(number);
    }

    /**
     * 要考虑到 带负号的千分位处理
     *
     * @param number
     * @return
     */
    public static String getThousand(String number) {
        String symbol = number.substring(0, 1);
        String complete_number;
        if ("-".equals(symbol)) {
            String thousand_number = DecimalFormat.getCurrencyInstance().format(Double.parseDouble(number.substring(1))).substring(1);
            complete_number = symbol + thousand_number;
        } else {
            complete_number = DecimalFormat.getCurrencyInstance().format(Double.parseDouble(number)).substring(1);
        }
        return complete_number;
    }

    /**
     * 生成带颜色字体
     *
     * @param text  需要处理的文本
     * @param color 文本颜色 rgb #ffffff
     * @return 处理后的html格式文本
     * @version 1.0
     * @createTime 2013-8-1,下午5:45:05
     * @updateTime 2013-8-1,下午5:45:05
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String makeColorText(String text, String color) {

        return "<font color=" + color + ">" + text + "</font>";
    }

    /**
     * 粗体到颜色字体
     *
     * @param text
     * @param color
     * @return
     * @version 1.0
     * @createTime 2015年1月12日, 下午5:42:05
     * @updateTime 2015年1月12日, 下午5:42:05
     * @createAuthor WangYuWen
     * @updateAuthor WangYuWen
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String makeBoldColorText(String text, String color) {

        return "<font color=" + color + "><b>" + text + "</b></font>";
        /**
         * 支持以下html元素： <b>文本加粗bold。 <i>文本变斜体italic。 <u>文本加下划线underline
         */
    }

    /**
     * 生成大号字体
     *
     * @param text 需要处理的文本
     * @return 处理后的html格式文本
     * @version 1.0
     * @createTime 2013-8-1,下午5:45:05
     * @updateTime 2013-8-1,下午5:45:05
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String makeBigText(String text, int size) {
        String htmlStart = "<font >";
        String htmlEnd = "</font >";
        for (int i = 0; i < size; i++) {
            htmlStart += "<big>";
            htmlEnd = "</big>" + htmlEnd;
        }
        return htmlStart + text + htmlEnd;
    }

    /**
     * 生成大号带颜色字体
     *
     * @param text  需要处理的文本
     * @param color 文本颜色 rgb #ffffff
     * @return 处理后的html格式文本
     * @version 1.0
     * @createTime 2013-8-1,下午5:45:05
     * @updateTime 2013-8-1,下午5:45:05
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String makeBigColorText(String text, String color) {

        return "<font color=" + color + "><big>" + text + "</big></font>";
    }

    /**
     * 根据网络图片路径,获取本地图片路径.
     *
     * @param netPath 网络图片路径
     * @author 刘艺谋
     * @version 1.0, 2013-4-5
     */
    public static String getLocalImagePath(String netPath) {
        if (TextUtils.isEmpty(netPath)) {
            return "";
        }
        return FileConfig.PATH_IMAGES
                + netPath.substring(netPath.lastIndexOf("/") + 1);
    }

    /**
     * 获取本地私有文件路径
     *
     * @param netPath
     * @return
     * @version 1.0
     * @createTime 2013-11-16,下午9:50:49
     * @updateTime 2013-11-16,下午9:50:49
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getUserLocalImagePath(String netPath) {
        if (TextUtils.isEmpty(netPath)) {
            return "";
        }
        return FileConfig.PATH_USER_IMAGE
                + netPath.substring(netPath.lastIndexOf("/") + 1);
    }

    /**
     * 获取本地私有文件路径
     *
     * @param parent  父文件夹
     * @param netPath 图片网络路径
     * @return 本地文件存储绝对路径
     * @version 1.0
     * @createTime 2013-11-16,下午9:50:49
     * @updateTime 2013-11-16,下午9:50:49
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getUserLocalImagePath(String parent, String netPath) {
        if (TextUtils.isEmpty(netPath)) {
            return "";
        }
        return parent + netPath.substring(netPath.lastIndexOf("/") + 1);
    }

    /**
     * 获取用户缩略图
     *
     * @param netPath
     * @return
     * @version 1.0
     * @createTime 2013-11-16,下午9:51:48
     * @updateTime 2013-11-16,下午9:51:48
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getUserLocalThumPath(String netPath) {
        if (TextUtils.isEmpty(netPath)) {
            return "";
        }
        return FileConfig.PATH_USER_THUMBNAIL
                + netPath.substring(netPath.lastIndexOf("/") + 1);
    }

    /**
     * 获取网络文件在本地缓存的路径
     *
     * @param netPath         网络文件路径
     * @param localParentPath 本地文件缓存目录
     * @return 本地缓存文件的绝对路径
     * @version 1.0
     * @createTime 2013-11-3,下午1:57:32
     * @updateTime 2013-11-3,下午1:57:32
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getLocalCachePath(String netPath,
                                           String localParentPath) {
        return localParentPath
                + netPath.substring(netPath.lastIndexOf("/") + 1);
    }

    /**
     * 判断参数.
     *
     * @param params 需要判断的字符串.
     * @return 若为空则返回空字符串, 否则返回原字符串.
     * @version 1.0
     * @createTime 2013年11月1日, 下午12:01:50
     * @updateTime 2013年11月1日, 下午12:01:50
     * @createAuthor paladin
     * @updateAuthor paladin
     * @updateInfo
     */
    public static String getParamsForString(String params) {
        if (null == params) {
            return "";
        } else {
            return params;
        }
    }

    /**
     * 判断参数.
     *
     * @param params 需要判断的字符串.
     * @return 若为空则返回0字符串, 否则返回原字符串.
     * @version 1.0
     * @createTime 2013年11月1日, 下午12:01:50
     * @updateTime 2013年11月1日, 下午12:01:50
     * @createAuthor paladin
     * @updateAuthor paladin
     * @updateInfo
     */
    public static String getParamsForNumber(String params) {
        if (TextUtils.isEmpty(params)) {
            return "0";
        } else {
            return params;
        }
    }

    /**
     * 格式化文件大小
     *
     * @param size
     * @version 1.0
     * @createTime 2013-7-27,下午4:06:54
     * @updateTime 2013-7-27,下午4:06:54
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String formatFileSize(float size) {
        DecimalFormat format = new DecimalFormat("###,###,##0.00");
        if (size < 1024) {
            format.applyPattern("###,###,##0.00B");
        } else if (size >= 1024 && size < 1024 * 1024) {
            size /= 1024;
            format.applyPattern("###,###,##0.00KB");
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            size /= (1024 * 1024);
            format.applyPattern("###,###,##0.00MB");
        } else if (size >= 1024 * 1024 * 1024
                && size < 1024 * 1024 * 1024 * 1024) {
            size /= (1024 * 1024 * 1024);
            format.applyPattern("###,###,##0.00GB");
        } else if (size >= 1024 * 1024 * 1024 * 1024
                && size < 1024 * 1024 * 1024 * 1024 * 1024) {
            size /= (1024 * 1024 * 1024 * 1024);
            format.applyPattern("###,###,##0.00GB");
        }
        return format.format(size);
    }

    /**
     * 计算时间
     *
     * @param time 原始时间(yyyy-MM-dd hh:mm:ss)
     * @return 处理后的时间
     * @version 1.0
     * @createTime 2013-11-11,下午2:21:25
     * @updateTime 2013-11-11,下午2:21:25
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String caculateTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String newTime = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            long timeMillis = date.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long caculTime = ((currentTimeMillis - timeMillis) / 1000);
            if (caculTime < 1) {
                newTime = 1 + TApplication.context.getString(R.string.second);
            } else if (caculTime < 60 && caculTime >= 1) { // 秒
                newTime = caculTime
                        + TApplication.context.getString(R.string.second);
            } else if (caculTime >= 60 && caculTime < 60 * 60) { // 分
                caculTime /= 60;
                newTime = caculTime
                        + TApplication.context.getString(R.string.minute);
            } else if (caculTime >= 60 * 60 && caculTime < 60 * 60 * 24) { // 时
                caculTime /= 60 * 60;
                newTime = caculTime
                        + TApplication.context.getString(R.string.hour);
            } else if (caculTime >= 60 * 60 * 24
                    && caculTime < 60 * 60 * 24 * 4) { // 天
                caculTime /= 60 * 60 * 24;
                newTime = caculTime
                        + TApplication.context.getString(R.string.day);
            } else {
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
                newTime = df.format(date);
            }

        } catch (ParseException e) {
            newTime = time;
            e.printStackTrace();
        }

        return newTime;
    }

    /**
     * 计算时间
     *
     * @param time 原始时间(yyyy-MM-dd hh:mm:ss)
     * @return 处理后的时间
     * @version 1.0
     * @createTime 2013-11-11,下午2:21:25
     * @updateTime 2013-11-11,下午2:21:25
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String caculateData(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String newTime = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            long timeMillis = date.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long caculTime = ((currentTimeMillis - timeMillis) / 1000);
            if (caculTime < 1) {
                newTime = 1 + TApplication.context.getString(R.string.second);
            } else if (caculTime < 60 && caculTime >= 1) { // 秒
                newTime = caculTime
                        + TApplication.context.getString(R.string.second);
            } else if (caculTime >= 60 && caculTime < 60 * 60) { // 分
                caculTime /= 60;
                newTime = caculTime
                        + TApplication.context.getString(R.string.minute);
            } else if (caculTime >= 60 * 60 && caculTime < 60 * 60 * 24) { // 时
                caculTime /= 60 * 60;
                newTime = caculTime
                        + TApplication.context.getString(R.string.hour);
            } else if (caculTime >= 60 * 60 * 24
                    && caculTime < 60 * 60 * 24 * 4) { // 天
                caculTime /= 60 * 60 * 24;
                newTime = caculTime
                        + TApplication.context.getString(R.string.day);
            } else {
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
                newTime = df.format(date);
            }

        } catch (ParseException e) {
            newTime = time;
            e.printStackTrace();
        }

        return newTime;
    }

    /**
     * 计算声音时长
     *
     * @param time 需要计算的时间长度long格式
     * @return
     * @version 1.0
     * @createTime 2014年7月22日, 下午4:35:30
     * @updateTime 2014年7月22日, 下午4:35:30
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String caculateSoundTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        String timeLengthStr = "";

        long timeLength = Long.parseLong(time) / 1000;
        if (timeLength > 0) {
            // 如果录音时间大于1s则设置语音长度
            if (timeLength / 60 > 0) {
                // 大于一分钟
                timeLengthStr += (Long.parseLong(time) / 1000 / 60) + "'";
            }

            // 如果录音时间小于1s则设置语音长度为1s
            timeLengthStr += (Long.parseLong(time) / 1000 % 60) + "\"";

        } else {
            timeLengthStr = "1" + "\"";
        }

        return timeLengthStr;
    }

    /**
     * 计算消息发送时间
     *
     * @param date 目标时间
     * @return
     * @version 1.0
     * @createTime 2013-12-6,下午8:41:04
     * @updateTime 2013-12-6,下午8:41:04
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    @SuppressWarnings("deprecation")
    public static String caculateMessageTime(String date) {
        String newTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat();

        // 当前日期
        Date currentDate = new Date(System.currentTimeMillis());
        // 源日期
        Date sourceDate;

        Calendar calendar = Calendar.getInstance();// 获取Calendar实例

        // 当前星期
        int weekPosition = 0;
        // 星期
        String[] weeks = TApplication.context.getResources().getStringArray(
                R.array.weeks);

        try {
            sourceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(date);

            // 当前周在当年的第几周
            calendar.setTime(currentDate);
            int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

            // 源日期在当年的第几周
            calendar.setTime(sourceDate);
            int sourceWeek = calendar.get(Calendar.WEEK_OF_YEAR);

            // 星期下标
            weekPosition = sourceDate.getDay();

            sdf.applyPattern("yyyy-MM-dd");
            if (sdf.format(currentDate).equals(sdf.format(sourceDate))) {// 当天，显示小时
                sdf.applyPattern("HH:mm");
                newTime = sdf.format(sourceDate);
            } else if (currentWeek == sourceWeek) {// 本周之内
                newTime = weeks[weekPosition];
            } else {// 显示日期
                sdf.applyPattern("yy-MM-dd");
                newTime = sdf.format(sourceDate);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newTime;
    }

    /**
     * 格式化时间
     *
     * @param time    原始时间啊， 格式 yyyy-MM-dd hh:mm:ss
     * @param pattern 新时间格式
     * @return
     * @version 1.0
     * @createTime 2013-11-18,下午9:06:13
     * @updateTime 2013-11-18,下午9:06:13
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String formatTime(String time, String pattern) {

        if (TextUtils.isEmpty(time)) {
            return "1970-01-01";
        }
        String newTime = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            long timeMillis = date.getTime();
            newTime = new SimpleDateFormat(pattern).format(timeMillis);

        } catch (ParseException e) {
            newTime = time;
            e.printStackTrace();
        }

        return newTime;
    }

    /**
     * 格式化时间
     *
     * @param time    原始时间啊， 格式 yyyy-MM-dd
     * @param pattern 新时间格式
     * @return
     * @version 1.0
     * @createTime 2013-11-18,下午9:06:13
     * @updateTime 2013-11-18,下午9:06:13
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String formatTimeAppointment(String time, String pattern) {

        if (TextUtils.isEmpty(time)) {
            return "1970-01-01";
        }
        String newTime = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            long timeMillis = date.getTime();
            newTime = new SimpleDateFormat(pattern).format(timeMillis);

        } catch (ParseException e) {
            newTime = time;
            e.printStackTrace();
        }

        return newTime;
    }

    /**
     * 比较时间
     *
     * @param time1 时间1（格式 yyyy-MM-dd HH:mm:ss）
     * @param time2 时间2（格式 yyyy-MM-dd HH:mm:ss）
     * @return 1(time1 > time2) 、 0(time1 == time2) 、 -1(除1，0的情况，都会返回-1)
     * @version 1.0
     * @createTime 2013-11-22,下午5:23:41
     * @updateTime 2013-11-22,下午5:23:41
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static int compareTime(String time1, String time2) {
        int status = -1;
        Date date1;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(time2);
            long timeMillis1 = date1.getTime();
            long timeMillis2 = date2.getTime();

            if (timeMillis1 == timeMillis2) {
                status = 0;
            } else if (timeMillis1 > timeMillis2) {
                status = 1;
            } else {
                status = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            status = -1;
        }

        return status;
    }

    /**
     * 格式化时间
     *
     * @param timeMillis 时间戳
     * @param pattern    时间正则
     * @return 返回格式后的时间
     * @version 1.0
     * @createTime 2013-11-15,下午11:23:18
     * @updateTime 2013-11-15,下午11:23:18
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String formatTime(long timeMillis, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(new Date(timeMillis));
    }

    /**
     * 更改时间格式
     *
     * @param time        原时间
     * @param fromPattern 原始格式
     * @param toPattern   目标格式
     * @return 格式化之后的
     * @version 1.0
     * @createTime 2014年1月3日, 下午2:08:33
     * @updateTime 2014年1月3日, 下午2:08:33
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    @SuppressWarnings("deprecation")
    public static String changeTimeFormat(String time, String fromPattern,
                                          String toPattern, boolean isMonth) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(toPattern);
        try {
            date = new SimpleDateFormat(fromPattern).parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isMonth) {
            if (SystemUtil.IsSystemLanguage()) {
                return sdf.format(date) + "";
            } else {
                if (date.getMonth() + 1 == 1) {
                    return "Jan";
                } else if (date.getMonth() + 1 == 2) {
                    return "Feb";
                } else if (date.getMonth() + 1 == 3) {
                    return "Mar";
                } else if (date.getMonth() + 1 == 4) {
                    return "Apr";
                } else if (date.getMonth() + 1 == 5) {
                    return "May";
                } else if (date.getMonth() + 1 == 6) {
                    return "Jun";
                } else if (date.getMonth() + 1 == 7) {
                    return "Jul";
                } else if (date.getMonth() + 1 == 8) {
                    return "Aug";
                } else if (date.getMonth() + 1 == 9) {
                    return "Sep";
                } else if (date.getMonth() + 1 == 10) {
                    return "Oct";
                } else if (date.getMonth() + 1 == 11) {
                    return "Nov";
                } else if (date.getMonth() + 1 == 12) {
                    return "Dec";
                } else {
                    return sdf.format(date) + "";
                }
            }
        } else {
            return sdf.format(date) + "";
        }
    }

    /**
     * 获取指定时间格式的时间戳
     *
     * @param time    时间
     * @param pattern 格式
     * @return
     * @version 1.0
     * @createTime 2014年1月3日, 下午1:55:49
     * @updateTime 2014年1月3日, 下午1:55:49
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static long getTimeMillis(String time, String pattern) {

        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
        return date.getTime();
    }

    /**
     * 判断字符串是否是标准时间格式
     *
     * @param time        判断的字符串
     * @param fromPattern 时间格式
     * @return 是返回true，不是返回false
     * @version 1.0
     * @createTime 2014-3-25,下午7:46:25
     * @updateTime 2014-3-25,下午7:46:25
     * @createAuthor liujingguo
     * @updateAuthor liujingguo
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static boolean valueTime(String time, String fromPattern) {

        try {
            new SimpleDateFormat(fromPattern).parse(time);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 描述：获取EditText控件所输入的文字 并去除空字符
     *
     * @param editTextInput 要获取文本内容的edittext控件
     * @version 1.0
     * @createTime 2014-4-21 上午10:50:04
     * @updateTime 2014-4-21 上午10:50:04
     * @createAuthor XinGo
     * @updateAuthor
     * @updateInfo (修改内容描述)
     */
    public static String trimEditTextInput(EditText editTextInput) {
        return editTextInput.getText().toString().trim();
    }

    /**
     * 字符串转换整形,
     *
     * @param str 字符串
     * @return 空返回0
     * @version 1.0
     * @createTime 2014年9月2日, 下午6:16:12
     * @updateTime 2014年9月2日, 下午6:16:12
     * @createAuthor WangYuWen
     * @updateAuthor WangYuWen
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static int StringToInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

    /**
     * 获取手机的mac地址
     *
     * @param context
     * @return
     * @version 1.0
     * @createTime 2014年11月13日, 下午9:29:34
     * @updateTime 2014年11月13日, 下午9:29:34
     * @createAuthor WangYuWen
     * @updateAuthor WangYuWen
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress().replace(":", "");
                else
                    return macAddress;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }

    /**
     * 更改时间格式
     *
     * @param time        原时间
     * @param fromPattern 原始格式
     * @param toPattern   目标格式
     * @return 格式化之后的
     */
    @SuppressWarnings("deprecation")
    public static String changeTimeFormat(String time, String fromPattern,
                                          String toPattern) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(toPattern);
        try {
            date = new SimpleDateFormat(fromPattern).parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (SystemUtil.IsSystemLanguage()) {
            return sdf.format(date) + "";
        } else {
            String returnStr = "";
            if (date.getMonth() + 1 == 1) {
                returnStr = "Jan";
            } else if (date.getMonth() + 1 == 2) {
                returnStr = "Feb";
            } else if (date.getMonth() + 1 == 3) {
                returnStr = "Mar";
            } else if (date.getMonth() + 1 == 4) {
                returnStr = "Apr";
            } else if (date.getMonth() + 1 == 5) {
                returnStr = "May";
            } else if (date.getMonth() + 1 == 6) {
                returnStr = "Jun";
            } else if (date.getMonth() + 1 == 7) {
                returnStr = "Jul";
            } else if (date.getMonth() + 1 == 8) {
                returnStr = "Aug";
            } else if (date.getMonth() + 1 == 9) {
                returnStr = "Sep";
            } else if (date.getMonth() + 1 == 10) {
                returnStr = "Oct";
            } else if (date.getMonth() + 1 == 11) {
                returnStr = "Nov";
            } else if (date.getMonth() + 1 == 12) {
                returnStr = "Dec";
            }
            returnStr = returnStr + ". " + (date.getDay() + 1) + ", "
                    + (date.getYear() + 1900);
            return returnStr;
        }
    }

    /**
     * 更改时间格式
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String changeTimeFormat(Date date) {
        String returnStr = "";
        if (date != null) {
            if (date.getMonth() + 1 == 1) {
                returnStr = "Jan";
            } else if (date.getMonth() + 1 == 2) {
                returnStr = "Feb";
            } else if (date.getMonth() + 1 == 3) {
                returnStr = "Mar";
            } else if (date.getMonth() + 1 == 4) {
                returnStr = "Apr";
            } else if (date.getMonth() + 1 == 5) {
                returnStr = "May";
            } else if (date.getMonth() + 1 == 6) {
                returnStr = "Jun";
            } else if (date.getMonth() + 1 == 7) {
                returnStr = "Jul";
            } else if (date.getMonth() + 1 == 8) {
                returnStr = "Aug";
            } else if (date.getMonth() + 1 == 9) {
                returnStr = "Sep";
            } else if (date.getMonth() + 1 == 10) {
                returnStr = "Oct";
            } else if (date.getMonth() + 1 == 11) {
                returnStr = "Nov";
            } else if (date.getMonth() + 1 == 12) {
                returnStr = "Dec";
            }
        }
        return returnStr;
    }

    /**
     * byte数组截取，并将数组转成String返回
     *
     * @param b   byte数组
     * @param len 截取的位置
     * @return
     * @version 1.0
     * @createTime 2015-7-1,下午4:48:24
     * @updateTime 2015-7-1,下午4:48:24
     * @createAuthor yeqing
     * @updateAuthor yeqing
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getHexString(byte[] b, int len) {
        String s = "";
        if (b.length > len) {
            for (int i = 0; i < len; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                s += hex;
            }
        }
        return s.toUpperCase();
    }

    /**
     * 截取数字
     *
     * @param content
     * @return
     * @version 1.0
     * @createTime 2015年10月29日, 上午10:12:41
     * @updateTime 2015年10月29日, 上午10:12:41
     * @createAuthor Geoff
     * @updateAuthor
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("[\\.\\d]+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static String replaceNumber(String content) {
        if (content != null) {
            return content.replace("元", "").replace("升", "").replace("期", "")
                    .replace("天", "");
        }
        return "";
    }


    // 设置字符串中一部分字体颜色的方法
    public static SpannableStringBuilder getTextSizeSpannBuilder(final Context context, int size,
                                                                 String allStr, String... num) {
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(
                builder);
        if (num != null && num.length > 0) {
            for (int i = 0; i < num.length; i++) {
                int index = allStr.indexOf(num[i], 0);
                while (index != -1) {
                    builderSpannable.setSpan(new AbsoluteSizeSpan(DisplayUtil.dip2px(
                            context, size)),
                            index, index + num[i].length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    StyleSpan span = new StyleSpan(Typeface.NORMAL);
                    builderSpannable.setSpan(span, index, index + num[i].length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    index = index + num[i].length();
                    index = allStr.indexOf(num[i], index);
                }
            }
        }
        return builderSpannable;
    }


    // 设置字符串中一部分字体颜色的方法
    public static SpannableStringBuilder getColorSpannBuilder(int color,
                                                              String allStr, String... num) {
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(
                builder);
        if (num != null && num.length > 0) {
            int index = 0 - num[0].length();
            String last = "";
            /*
             * for(String str : num) { index = builder.indexOf(str, index +
			 * str.length()); if(index!=-1) { builderSpannable.setSpan(new
			 * ForegroundColorSpan(color), index, index+str.length(),
			 * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); } }
			 */
            for (int i = 0; i < num.length; i++) {
                if (i == 0) {
                    index = builder.indexOf(num[i], 0);
                } else {
                    index = builder.indexOf(num[i], index + last.length());
                }
                if (index != -1) {
                    int nextIndex = index + num[i].length();
                    builderSpannable.setSpan(new ForegroundColorSpan(color),
                            index, nextIndex,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                last = num[i];
            }
        }
        return builderSpannable;
    }

    /**
     * 设置字符串中一部分字体大小的方法
     *
     * @param size   字体大小  单位 sp
     * @param allStr
     * @param num
     * @return
     */
    public static SpannableStringBuilder getFontSpannBuilder(int size,
                                                             String allStr, String... num) {
        if(allStr == null)
            allStr = "";
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(
                builder);
        if (num != null && num.length > 0) {
            int index = 0 - num[0].length();
            String last = "";
                /*
                 * for(String str : num) { index = builder.indexOf(str, index +
				 * str.length()); if(index!=-1) { builderSpannable.setSpan(new
				 * ForegroundColorSpan(color), index, index+str.length(),
				 * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); } }
				 */

            final float scale = TApplication.context.getResources().getDisplayMetrics().density;
            int pixel = (int) (size * scale + 0.5f);
            for (int i = 0; i < num.length; i++) {
                if (i == 0) {
                    index = builder.indexOf(num[i], 0);
                } else {
                    index = builder.indexOf(num[i], index + last.length());
                }
                if (index != -1) {
                    builderSpannable.setSpan(new AbsoluteSizeSpan(pixel),
                            index, index + num[i].length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                last = num[i];
            }
        }
        return builderSpannable;
    }

    /**
     * 设置字符串中最后一位字体大小和粗细与前面不一样的方法的方法(前面粗体后面正常)
     *
     * @param size   字体大小  单位 sp
     * @param allStr
     * @return
     */
    public static SpannableStringBuilder getFontSpannTypeBuilder(int size, String allStr) {
        final float scale = TApplication.context.getResources().getDisplayMetrics().density;
        int pixel = (int) (size * scale + 0.5f);
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(
                builder);
        builderSpannable.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, pixel, null, null), 0, allStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builderSpannable;
    }


    /**
     * 设置字符串中最后一位字体大小和粗细与前面不一样的方法的方法(前面粗体后面正常)
     *
     * @param size   字体大小  单位 sp
     * @param allStr
     * @return
     */
    public static SpannableStringBuilder getFontEndSpannTypeBuilder(int size, String allStr) {
        final float scale = TApplication.context.getResources().getDisplayMetrics().density;
        int pixel = (int) (size * scale + 0.5f);
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(
                builder);
        builderSpannable.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, pixel, null, null), allStr.length() - 1, allStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builderSpannable;
    }


    /**
     * 设置字符串中number之前字体大小和粗细与后面不一样的方法的方法(前面粗体后面正常)
     *
     * @param size   字体大小  单位 sp  number大小分隔的位置
     * @param allStr
     * @return
     */
    public static SpannableStringBuilder getFontSpannTypeBuilderForSize(int size, int number, String allStr) {
        final float scale = TApplication.context.getResources().getDisplayMetrics().density;
        int pixel = (int) (size * scale + 0.5f);
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(
                builder);
        builderSpannable.setSpan(new TextAppearanceSpan(null, Typeface.BOLD, pixel, null, null), 0, number, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builderSpannable;
    }

    /**
     * 设置字符串中最后两位字体大小和粗细与前面不一样的方法的方法(前面粗体后面正常)
     *
     * @param size   字体大小  单位 sp
     * @param allStr
     * @return
     */
    public static SpannableStringBuilder getTwoFontSpannTypeBuilder(int size, String allStr) {
        final float scale = TApplication.context.getResources().getDisplayMetrics().density;
        int pixel = (int) (size * scale + 0.5f);
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(
                builder);
        builderSpannable.setSpan(new TextAppearanceSpan(null, Typeface.BOLD, pixel, null, null), 0, allStr.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builderSpannable;
    }

    /**
     * @param edt 编辑文本框，让用户只能输入两位小数 edt 编辑文本框 flotNumber 限制输入的小数位数
     */
    public static void subEdtTwo(final EditText edt, final int flotNumber) {
        if (flotNumber < 1) {
            return;
        }
        edt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String result_str = edt.getText().toString();
                if (result_str.contains(".")) {
                    String sub_str = result_str.substring(
                            result_str.indexOf(".") + 1, result_str.length());
                    if (sub_str.length() > flotNumber) {

                        result_str = result_str.substring(0,
                                result_str.indexOf(".") + flotNumber + 1);
                        edt.setText(result_str);
                        edt.setSelection(result_str.length());
                    }

                }

            }
        });

    }

    /**
     * @param edt 设置edittext只能输入数字
     */
    public static void edtNumber(final EditText edt) {
        edt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result_str = edt.getText().toString();
                if (!RegularUtil.IsNumber(result_str)) {
                    edt.setText(StringUtil.getNumbers(result_str));
                    edt.setSelection(StringUtil.getNumbers(result_str).length());
                }

            }
        });
    }

    /**
     * 显示数字后两位
     *
     * @param number
     * @return
     */
    public static String getTwoDecimal(String number) {
        String result_str = number;
        String head_str = "";
        String end_str = "";

        if (number.contains(".")) {
            head_str = number.substring(0, number.indexOf("."));
            end_str = number.substring(number.indexOf("."), number.length());
            head_str = head_str;
            if (end_str.length() == 2) {
                end_str = end_str.concat("0");
            }
            result_str = head_str + end_str;
        } else {

            result_str = result_str + ".00";
        }
        return result_str;
    }

    /**
     * 将用户输入的数字字符串 按逗号隔开
     *
     * @param number 用户传进来要隔开的数字字符串
     *               用户按几位一隔
     * @return
     */
    public static String commaPartString(String number) {
        String result_str = number;
        String head_str = "";
        String end_str = "";

        if (number.contains(".")) {
            head_str = number.substring(0, number.indexOf("."));
            end_str = number.substring(number.indexOf("."), number.length());
            head_str = getNumKb(head_str);
            if (end_str.length() == 2) {
                end_str = end_str.concat("0");
            }
            result_str = head_str + end_str;
        } else {

            result_str = getNumKb(result_str) + ".00";
        }
        return result_str;
    }

    public static String getNumKb(String s) {

        BigDecimal b = new BigDecimal(s);
        // ,代表分隔符
        // .后面的##代表位数 如果换成0 效果就是位数不足0补齐
        DecimalFormat d1 = new DecimalFormat("#,##0.######");
        // 设置舍入模式

        // d1.setRoundingMode(RoundingMode.FLOOR);
        // d1.setRoundingMode();
        return d1.format(b);
    }

    /**
     * 前面字体变大  . 后面变小
     *
     * @param content
     * @param big     单位是dp
     * @param small   单位是dp
     * @return
     */
    public static SpannableString getSpannableString(String content, int big, int small) {
        if (!content.contains(".")) {
            return new SpannableString(content);
        }
        String str = content;
        int start = str.indexOf(".");
        SpannableString spannableString = new SpannableString(str);
        //前面字体变大
        spannableString.setSpan(new AbsoluteSizeSpan(big, true), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //后面字体变小
        spannableString.setSpan(new AbsoluteSizeSpan(small, true), start, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 前面字体变大 (传入的字符) 后面变小
     *
     * @param content
     * @param big     单位是dp
     * @param small   单位是dp
     * @return
     */
    public static SpannableString getSpannableString(String content, String t, int big, int small) {
        if (!content.contains(t)) {
            return new SpannableString(content);
        }
        String str = content;
        int start = str.indexOf(t);
        SpannableString spannableString = new SpannableString(str);
        //前面字体变大
        spannableString.setSpan(new AbsoluteSizeSpan(big, true), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //后面字体变小
        spannableString.setSpan(new AbsoluteSizeSpan(small, true), start, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 前面字体变大  % 后面变小
     *
     * @param content
     * @param big     单位是dp
     * @param small   单位是dp
     * @return
     */
    public static SpannableString getSpannabletoString(String content, int big, int small) {
        if (!content.contains("%")) {
            return new SpannableString(content);
        }
        String str = content;
        int start = str.indexOf("%");
        SpannableString spannableString = new SpannableString(str);
        //前面字体变大
        spannableString.setSpan(new AbsoluteSizeSpan(big, true), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //后面字体变小
        spannableString.setSpan(new AbsoluteSizeSpan(small, true), start, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 最后 一位变小 其它变大
     *
     * @param content
     * @param big     单位是dp
     * @param small   单位是dp
     * @return
     */
    public static SpannableString getSpannableforString(String content, int big, int small) {

        String str = content;
        SpannableString spannableString = new SpannableString(str);
        //前面字体变大
        spannableString.setSpan(new AbsoluteSizeSpan(big, true), 0, content.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //后面字体变小
        spannableString.setSpan(new AbsoluteSizeSpan(small, true), content.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 设置EditText hint的大小
     *
     * @param content  hint内容
     * @param hintSize 大小
     * @return
     */
    public static SpannableString getSpanEditTextHint(String content, int hintSize) {
        SpannableString spanHint = new SpannableString(content);
        AbsoluteSizeSpan textSize = new AbsoluteSizeSpan(hintSize, true);
        spanHint.setSpan(textSize, 0, spanHint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanHint;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }


    /**
     * 星号替换部分字符
     *
     * @param type 类型 0姓名 1证件 2手机号码
     * @param str
     * @return
     */
    public static String hideString(int type, String str) {

        if (TextUtils.isEmpty(str) || "null".equals(str)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        if (0 == type) {
            if (str.length() == 1) {
                sb.append(str);
            } else if (str.length() == 2) {
                sb.append("*");
                sb.append(str.subSequence(1, 2));
            } else if (str.length() >= 3) {
                sb.append(str.subSequence(0, 1));
                for (int i = 0; i < str.length() - 2; i++) {
                    sb.append("*");
                }
                sb.append(str.subSequence(str.length() - 1, str.length()));
            }
        } else if (1 == type) {
            if (str.length() > 8) {
                sb.append(str.subSequence(0, 4));
                for (int i = 0; i < str.length() - 8; i++) {
                    sb.append("*");
                }
                sb.append(str.subSequence(str.length() - 4, str.length()));
            } else {
                sb.append(str);
            }

        } else if (2 == type) {

            if (str.length() == 11) {
                sb.append(str.substring(0, 3));
                for (int i = 0; i < str.length() - 7; i++) {
                    sb.append("*");
                }
                sb.append(str.substring(str.length() - 4, str.length()));
            } else if (str.length() < 11 && str.length() > 5) {
                sb.append(str.substring(0, 3));
                for (int i = 0; i < str.length() - 5; i++) {
                    sb.append("*");
                }
                sb.append(str.charAt(str.length() - 1));
            } else {
                sb.append(str);
            }

//			if (str.length() >4) {
//				sb.append(str.substring(0, 3));
//				for (int i = 0; i < str.length() - 5; i++) {
//					sb.append("*");
//				}
//				sb.append(str.charAt(str.length()-1));
//			}else{
//				sb.append(str);
//			}
        }
        return sb.toString();
    }

    /**
     * 判断密码长度
     *
     * @param context
     * @param str
     * @return
     * @version 1.0
     * @createTime 2015年6月11日, 下午2:52:36
     * @updateTime 2015年6月11日, 下午2:52:36
     * @createAuthor Xiaohuan
     * @updateAuthor Xiaohuan
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static Boolean pwdIsLength(Context context, String str) {
        if (str.equals("") || str == null) {
            ToastUtil.showToast(context,
                    context.getResources().getString(R.string.hint_plase_pwd));
            return false;
        } else if (str.length() >= 6) {
            if (str.length() > 20) {
                ToastUtil.showToast(
                        context,
                        context.getResources().getString(
                                R.string.activity_loginPasEdit_hint2));
                return false;
            } else {
                return true;
            }
        } else {
            ToastUtil.showToast(
                    context,
                    context.getResources().getString(
                            R.string.activity_loginPasEdit_hint2));
        }
        return false;
    }


    /**
     * @param @param context
     * @param @param replyText
     * @param @param count
     * @param @param replyUnit    设定文件
     * @return void    返回类型
     * @throws
     * @Title: getReplyNumColor
     * @Description: TODO(字符串标橙)
     */
    public static void getReplyNumColor(Context context, TextView replyText, String count, String replyUnit) {
        String strQuotedPriceCount = String.format(replyUnit, count);
        SpannableStringBuilder builder = new SpannableStringBuilder(strQuotedPriceCount);
        int replyCountIndex = strQuotedPriceCount.indexOf(count);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.common_main_orange_color)), replyCountIndex, replyCountIndex + count.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        replyText.setText(builder);
    }


    /**
     * @param @param  time
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: times
     * @Description: TODO(时间戳转年月日)
     */
    public static String changetimes(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 整型转像素 sp
     *
     * @param size
     * @return
     */
    public static float intToSp(int size) {
        final float scale = TApplication.context.getResources().getDisplayMetrics().density;
        float size1 = size * scale + 0.5f;
        return size1;

    }


    public static void getAutoTextSize(Context context, TextView text, String content) {

        if (content.length() < 7) {
            text.setTextSize(16);
        } else {
            if (content.length() < 13) {
                text.setTextSize(14);
            } else {
                text.setTextSize(5);
            }

        }
    }

    /**
     * @param @param  price
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getFormatPriceMoney2
     * @Description: TODO(保留2位小数点)
     */
    public static String getFormatPriceMoney(String price) {
        double d = 0;
        if (StringUtils.isEmpty(price)) {
            d = 0;
        } else {
            Double temp = Double.parseDouble(price);
            if (temp.isNaN()) {
                d = 0;
            } else {
                d = temp;
            }
        }
        return num2Format.format(d);
    }

    public static String getFormatPriceMoney(double price) {

        DecimalFormat df = new DecimalFormat("#.00");
        String st = df.format(price);
        return st;
    }


    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /***
     * EditText仅能输入2位小数
     */
    public static void twoDot(Editable s) {
        String temp = s.toString();
        int dotIndex = temp.indexOf(".");
        if (dotIndex <= 0) {//没有小数
            return;
        }
        if (temp.length() - dotIndex - 1 > 2) {//超过两位小数
            s.delete(dotIndex + 3, dotIndex + 4);
        }
    }

    /***
     * 将URI转换成
     */
    public static String uriToRealPath(Context context, Uri uri) {
        String path;
        Cursor cursor = context.getContentResolver().query(uri,
                new String[]{MediaStore.Images.Media.DATA},
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            cursor.close();
        } else {
            path = uri.getPath();
        }
        return path;
    }
}
