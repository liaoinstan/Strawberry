package com.shelwee.update.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ShelWee on 14-5-8.
 */
public class URLUtils {

	public static boolean isHttpUrl(String url) {
		return (null != url) && (url.length() > 6)
				&& url.substring(0, 7).equalsIgnoreCase("http://");
	}

	public static boolean isHttpsUrl(String url) {
		return (null != url) && (url.length() > 7)
				&& url.substring(0, 8).equalsIgnoreCase("https://");
	}

	public static boolean isNetworkUrl(String url) {
		if (url == null || url.length() == 0) {
			return false;
		}
		return isHttpUrl(url) || isHttpsUrl(url);
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
		if (str!=null) {
			Pattern p = Pattern.compile("\\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}