package com.ins.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liaoinstan on 15-6-14.
 * 字符串处理工具，验证null，转换数字，检查中文等
 */
public class StrUtil {

    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断对象是否为空<br>
     * 1,字符串(null或者"")都返回true<br>
     * 2,数字类型(null或者0)都返回true<br>
     * 3,集合类型(null或者不包含元素都返回true)<br>
     * 4,数组类型不包含元素返回true(包含null元素返回false)<br>
     * 5,其他对象仅null返回true
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Number) {
            Number num = (Number) obj;
            if (num.intValue() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof String) {
            String str = (String) obj;
            if ((str == null) || str.equals("")) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof Collection<?>) {
            Collection<?> c = (Collection<?>) obj;
            return c.isEmpty();
        } else if (obj instanceof Map<?, ?>) {
            Map<?, ?> m = (Map<?, ?>) obj;
            return m.isEmpty();
        } else if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            return length == 0 ? true : false;
        } else {
            return false;
        }
    }

    //移除集合中的null项目，[null]
    public static void removeNull(List list) {
        if (StrUtil.isEmpty(list)) return;
        for (Object object : list) {
            if (object == null)
                list.remove(object);
        }
    }

    public static boolean isUrl(String str) {
        if (str == null) return false;
        if (str.startsWith("http")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取首行缩进（直接使用空格间距不一致，使用下面的方法）
     */
    public static String getSpace() {
        return "\u3000\u3000";
    }

    /*-----------------------------------

    笨方法：String s = "你要去除的字符串";

            1.去除空格：s = s.replace('\\s','');

            2.去除回车：s = s.replace('\n','');

    这样也可以把空格和回车去掉，其他也可以照这样做。

    注：\n 回车(\u000a)
    \t 水平制表符(\u0009)
    \s 空格(\u0008)
    \r 换行(\u000d)*/
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 去除字符串最后一个特点字符
     */
    public static String subLastChart(String str, String c) {
        if (isEmpty(str)) {
            return "";
        }
        if (isEmpty(c)) {
            return str;
        }
        if (str.length() <= c.length()) {
            if (str.equals(c)) {
                return "";
            } else {
                return str;
            }
        }
        String lastc = str.substring(str.length() - c.length());
        if (lastc.equals(c)) {
            return str.substring(0, str.length() - c.length());
        } else {
            return str;
        }
    }

    /**
     * 获取集合长度，为null返回0
     */
    public static int getSize(List list) {
        return list != null ? list.size() : 0;
    }

    /**
     * 去除字符串第一个特定字符
     */
    public static String subFirstChart(String str, String c) {
        if (isEmpty(str)) {
            return "";
        }
        if (isEmpty(c)) {
            return str;
        }
        if (str.startsWith(c)) {
            return str.substring(c.length());
        } else {
            return str;
        }
    }

    public static boolean isChineseChar(String str) {
        if (isEmpty(str)) {
            return false;
        }
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    //把字符串转为double型，失败返回0
    public static double str2double(String str) {
        if (StrUtil.isEmpty(str)) {
            return 0;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //把字符串转为float型，失败返回0
    public static float str2float(String str) {
        if (StrUtil.isEmpty(str)) {
            return 0;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //把字符串转为int型，失败返回0
    public static int str2int(String str) {
        return str2int(str, 0);
    }

    //把字符串转为int型，失败返回def
    public static int str2int(String str, int def) {
        if (StrUtil.isEmpty(str)) {
            return def;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return def;
        }
    }

    //把字符串转为Double型，失败返回null
    public static Double str2doubleEnull(String str) {
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
