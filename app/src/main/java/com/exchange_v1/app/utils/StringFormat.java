package com.exchange_v1.app.utils;

/**
 * Created by zhuwd on 2017/5/24.
 * 字符串截取
 */

public class StringFormat {

    /**截取指定位置前的字符串
     * @param str 截取的string
     * @param index 截取位置
     * @return      返回截取后的字符串
     */
    public static String subFrontString(String str, int index){
        if (!StringUtil.isEmpty(str)){
            return str.substring(0,index);
        }
        return null;
    }

    /**截取指定位置后的字符串
     * @param str 截取的string
     * @param index 截取位置
     * @return      返回截取后的字符串
     */
    public static String subBehindString(String str, int index){
        if (!StringUtil.isEmpty(str)){
            return str.substring(index,str.length());
        }
        return null;
    }

    /**在指定位置插入字符串
     * @param index
     * @param str
     * @param inSearchStr
     * @return
     */
    public static String insertStrAtIndex(int index, String str, String inSearchStr){
        if (StringUtil.isEmpty(str)){
            return "";
        }
        StringBuilder sb = new StringBuilder(str);//构造一个StringBuilder对象
        sb.insert(index, inSearchStr);//在指定的位置1，插入指定的字符串
        return sb.toString();
    }

    /**截取字符串后N位数
     * @param str 截取的string
     * @param index 截取位置
     * @return      返回截取后的字符串
     */
    public static String subEndString(String str, int index){
        if (!StringUtil.isEmpty(str)){
            if (str.length()>=index){
                return str.substring(str.length()-index);
            }
        }
        return null;
    }

}
