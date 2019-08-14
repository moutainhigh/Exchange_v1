package com.exchange_v1.app.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数学处理
 *
 * @author qw
 * @Description
 * @date 2015-9-21
 * @Copyright: Copyright (c) 2015 深圳光汇云油电商有限公司
 */
public class NumUtil {
    /**
     * 截取2位小数
     */
    public static String getDecimal(String num) {
        try {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(Double.valueOf(num));
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 截取2位小数,并含有逗号
     */
    public static String getDecimalHasComma(String num) {
        try {
            DecimalFormat df = new DecimalFormat("#,###,###.00");
            return df.format(Double.valueOf(num));
        } catch (Exception e) {
            return "0.00";
        }
    }

    public static String getDecimalWithInt(String num) {
        try {
            DecimalFormat df = new DecimalFormat("#0");
            return df.format(Double.valueOf(num));
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 不四舍五入获取两位小数
     */
    public static String getDecimalNotFive(String num) {
        String getnum_str = num;
        String noPoit;
        if (getnum_str.contains(".")) {
            noPoit = getnum_str.substring(0, getnum_str.indexOf("."));
            String float_str = getnum_str.substring(
                    getnum_str.indexOf(".") + 1, getnum_str.length());

            if (float_str.length() > 2) {
                float_str = float_str.substring(0, 2);
                getnum_str = noPoit + "." + float_str;

            }
        }

        return getnum_str;
    }

    public static String getDecimalFormat(String num) {
        try {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(Double.valueOf(num));
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 小数点后两位
     *
     * @param num
     * @return
     */
    public static String getDecimalFormatBackTwo(double num) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(num);
    }

    /**
     * 获取四舍五入的2位小数
     */
    public static String getDecimal(double num) {
        try {

            DecimalFormat df = new DecimalFormat("#0.00");
            return getDecimalFormat(df.format(num));
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 四舍五入保留两位
     * 使用double有精度问题
     */
    public static String getRoundTwo(double num) {
        return getRoundTwo(String.valueOf(num));
    }

    /**
     * 四舍五入保留两位
     */
    public static String getRoundTwo(String num) {
        try {
            BigDecimal bigDec = new BigDecimal(num);
            double total = bigDec.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(total);
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 获取第三位小数非零 则向上进位
     */
    public static String getNumberThr(double num) {
        try {
            double Data = Double.valueOf(num);
            String result;
            DecimalFormat df = new DecimalFormat("#.00");
            StringBuffer sb = new StringBuffer();
            int TNum = (int) (Data * 1000);
            if (1 <= TNum && TNum < 10) {
                return "0.01";
            } else if (TNum < 1) {
                return "0.00";
            } else {
                if (TNum % 10 == 0) {
                    int endNum = TNum / 10;
                    result = String.valueOf(endNum);
                    if (result.length() == 2) {
                        result = "0" + result;
                    } else if (result.length() == 1) {
                        result = "00" + result;
                    }
                    sb = sb.append(result).insert(result.length() - 2, ".");
                } else {
                    int endNum = TNum / 10;
                    result = String.valueOf(endNum + 1);
                    if (result.length() == 2) {
                        result = "0" + result;
                    } else if (result.length() == 1) {
                        result = "00" + result;
                    }
                    sb = sb.append(result).insert(result.length() - 2, ".");
                }
                return sb.toString();
            }
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 获取四舍五入的1位小数
     */
    public static String getDecimalWihtOne(double num) {
        try {
            DecimalFormat df = new DecimalFormat("#0.0");
            return df.format(Double.valueOf(num));
        } catch (Exception e) {
            return "0.0";
        }
    }

    /**
     * 获取四舍五入的2位小数
     */
    public static String getDecimal(float num) {
        try {
            DecimalFormat df = new DecimalFormat("#0.00");
            return getDecimalFormat(df.format(num));
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 获取保留两位小数，第三位小数非零则向上进位
     */
    public static String getUpDoubleString(double num) {
        try {

            BigDecimal df = new BigDecimal(num);
            String bigDecimal = df.setScale(2, BigDecimal.ROUND_UP).toString();
            return bigDecimal;
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 获取四舍五入的4位小数
     */
    public static String getOilDecimal(String num) {
        DecimalFormat df = new DecimalFormat("#.###");
        return getDecimalFormat(df.format(Double.parseDouble(num)));
    }

    public static int compare(String v1, String v2) {
        BigDecimal data1 = new BigDecimal(Double.parseDouble(getDecimal(v1)));
        BigDecimal data2 = new BigDecimal(Double.parseDouble(getDecimal(v2)));
        int compartValue = data1.compareTo(data2);
        return compartValue;
    }

    /**
     * + 加
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String add(String v1, String v2) {
        BigDecimal data1 = new BigDecimal(pasreThousands(v1));
        BigDecimal data2 = new BigDecimal(pasreThousands(v2));
        BigDecimal value = data1;
        if (data2.doubleValue() > 0) {
            value = data1.add(data2);
        }
        return getDecimal(value.doubleValue());
    }

    /**
     * - 减
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double subtract(double v1, double v2) {
        BigDecimal data1 = new BigDecimal(v1);
        BigDecimal data2 = new BigDecimal(v2);
        BigDecimal value = data1;
        if (data2.doubleValue() > 0) {
            value = data1.subtract(data2);
        }
        return value.doubleValue();
    }

    public static String subtract(String v1, String v2) {
        BigDecimal data1 = new BigDecimal(pasreThousands(v1));
        BigDecimal data2 = new BigDecimal(pasreThousands(v2));
        BigDecimal value = data1;
        if (data2.doubleValue() > 0) {
            value = data1.subtract(data2);
        }
        return getDecimal(value.doubleValue());
    }

    /**
     * * 乘
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String multiply(String v1, String v2) {
        BigDecimal data1 = new BigDecimal(pasreThousands(v1));
        BigDecimal data2 = new BigDecimal(pasreThousands(v2));
        BigDecimal value = data1;
        if (data2.doubleValue() > 0) {
            value = data1.multiply(data2);
        }
        return getDecimal(value.doubleValue());
    }

    /**
     * / 除以
     *
     * @param v1
     * @param v2
     * @return
     */
    public static String divide(String v1, String v2) {
        BigDecimal data1 = new BigDecimal(pasreThousands(v1));
        BigDecimal data2 = new BigDecimal(pasreThousands(v2));
        BigDecimal value = data1;
        if (data2.doubleValue() > 0) {
            value = data1.divide(data2);
        }
        return getDecimal(value.doubleValue());
    }

    /**
     * 解释千分位字符
     */
    public static double pasreThousands(String numStr) {
        Number num = null;
        try {
            DecimalFormat df = new DecimalFormat("###,###,###.00");
            num = df.parse(numStr);
            return num.doubleValue();
        } catch (Exception e) {
            return 0.00;
        }
    }

    /**
     * 去除数字字符中开始为0的部分
     *
     * @param moneyText
     * @return noZeroStartNum 没有0开头
     */
    @NonNull
    public static String getNoZeroStartNum(String moneyText) {
        if (TextUtils.isEmpty(moneyText))
            return "0";

        String noZeroStartNum = moneyText.replaceFirst("^0+", "");
        if (noZeroStartNum.startsWith(".")) {
            noZeroStartNum = "0".concat(noZeroStartNum);
        }
        return noZeroStartNum;
    }

    /**
     * 整数金额
     * 将每三个数字加上逗号处理
     */
    public static String getIntAmount(String num) {
        DecimalFormat df = new DecimalFormat(",###.##");
        return df.format(Double.parseDouble(num));
    }
}
