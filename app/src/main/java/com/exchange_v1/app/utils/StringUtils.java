package com.exchange_v1.app.utils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;


public class StringUtils {

	public static DecimalFormat numFormat = new DecimalFormat(",###.#", new DecimalFormatSymbols(Locale.CHINA));
	public static DecimalFormat numFormat1 = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.CHINA));
	public static DecimalFormat num2Format = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.CHINA));
	public static DecimalFormat numFormat2 = new DecimalFormat("#", new DecimalFormatSymbols(Locale.CHINA));
	public static DecimalFormat numFormat3 = new DecimalFormat(",###", new DecimalFormatSymbols(Locale.CHINA));
	public static DecimalFormat numFormat4 = new DecimalFormat(",###.##", new DecimalFormatSymbols(Locale.CHINA));
	/**
	 * 字符转换
	 * 
	 * @param num
	 * @return
	 */
	public static String parsenDouble(double num) {

		if (((Double) num).isNaN()) {
			return "0";
		}
		return numFormat1.format(num);
	}

	/**
	 * 
	 * @Title: isStringEmpty
	 * @Description: TODO(判断字符串是否为空)
	 * @param: @param value
	 * @param: @return
	 * @return: boolean
	 */
	public static boolean isEmpty(String value) {
		if (value != null && !"".equals(value.trim()) && !"null".equals(value.trim())) {
			return false;
		} else {
			return true;
		}
	}

	public String strToMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return s;
		}
	}

	public String strToMD5ToUpperCase(String s) {
		return strToMD5(s).toUpperCase();
	}

	public boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public String insertCharToStr(String str, int averageIndex, String charStr) {
		double valueLength = 0;
		StringBuffer sb = new StringBuffer(str);
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 1;
			} else {
				// 其他字符长度为0.5
				valueLength += 0.5;
			}
			if (Math.ceil(valueLength) >= averageIndex) {
				sb.insert((int) Math.ceil(valueLength), charStr);
				valueLength = 0;
			}
		}

		return sb.toString();
	}

	/**
	 * 获取字符串的长度，中文占一个字符,英文数字占半个字符
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static double length(String value) {
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 1;
			} else {
				// 其他字符长度为0.5
				valueLength += 0.5;
			}
		}
		// 进位取整
		return Math.ceil(valueLength);
	}

	/**
	 * 将textview中的字符全角化,解决排版问题
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 
	 * @Title: getPingYin
	 * @Description: TODO(将汉字转化成拼音)
	 * @param: inputString
	 * @return: String
	 * @author yangnaibo
	 */
	public static String getPingYin(String inputString) {
//		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//		format.setVCharType(HanyuPinyinVCharType.WITH_V);
//		StringBuilder builder = new StringBuilder();
//		char[] array = inputString.toCharArray();
//		for (char c : array) {
//			if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
//				try {
//					String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
//					builder.append(pinyin[0]);
//				} catch (Exception e) {
//					e.printStackTrace();
//					builder.append("#");
//				}
//			} else {
//				builder.append(c);
//			}
//		}
//		if (!String.valueOf(builder.charAt(0)).matches("[a-zA-Z]+")) {
//			builder.insert(0, "#");
//		}
		return null;//builder.toString();
	}

	/**
	 * 
	 * @Title: hasSpecialCharacter
	 * @Description: TODO(是否包含特殊字符)
	 * @param: @param str
	 * @param: @return
	 * @return: boolean
	 * @throws
	 */
	public static boolean hasSpecialCharacter(String str) {
		Pattern p = Pattern.compile("[\\p{Alpha}]*[\\p{Punct}][\\p{Alpha}]*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 
	 * @Title: getPriceWithMoneySymbol   
	 * @Description: 由于很多地方在代码中将 
	 * @param:  price
	 * @return: String     
	 */
	public static String getPriceWithMoneySymbol(String price){
		return "";
//		return String.format(ApplicationConfig.context.getString(com.cnlaunch.general.lib.R.string.money_symbol), price);
	}

	/**
	 * 价格保留2位小数点
	 * @param price
	 * @return
	 */
	public static String getFormatPriceMoney(String price){
		double d=0;
		if(StringUtils.isEmpty(price)){
			d= 0;
		}else{
			Double temp=Double.parseDouble(price);
			if(temp.isNaN()){
				d= 0;
			}else{
				d=temp;
			}
		}
		return getPriceWithMoneySymbol(num2Format.format(d));
	}
	public static  boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
	/**
	 * @param to_str
	 * @return
	 * 根据传进来的字符串判断是否返回空字符传,如果为空 比如为null 都将返回 "" 否则按传进来的字符串返回
	 */
	public static String ifReturnEmptyStr(String to_str){
		String this_str=to_str;
		if(TextUtils.isEmpty(to_str)){
			this_str="";
		}else if (to_str.equalsIgnoreCase("null")){
			this_str="";
		}
		return this_str;
		
	}

}
