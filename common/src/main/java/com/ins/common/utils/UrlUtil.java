package com.ins.common.utils;

import android.net.Uri;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * url 工具类
 * 处理 url 字符串，提供添加、获取、修改参数，拼接键值对等方法
 *
 * 常用：
 * getParam 获取参数
 * addParam & addParams 添加或修改单个参数或参数集合
 * matchUrl 比较2个链接是否一样
 */

public class UrlUtil {

    /**
     * 获取一个url链接中的参数值
     * 如果该参数没有值返回""，如果没有该参数返回null
     */
    public static String getParam(String url, String key) {
        return Uri.parse(url).getQueryParameter(key);
    }

    /**
     * 删除url中的链接，只留下参数部分
     * http://www.baidu.com/api/map/getloc?id=1 --> id=1
     */
    public static String getParamStr(String url) {
        int index = url.indexOf("?");
        if (index >= 0) {
            return url.substring(index + 1);
        } else {
            return "";
        }
    }

    /**
     * 解析出url参数中的键值对，无则返回size为0 的map
     * index.jsp?id=123&name=ins  -->  { id: 123 , name: ins }
     */
    public static Map<String, String> getParamMap(String url) {
        Map<String, String> map = new LinkedHashMap();
        String paramStr = getParamStr(url);
        if (StrUtil.isEmpty(paramStr)) return map;
        String[] keyValues = paramStr.split("[&]");
        for (String keyValue : keyValues) {
            String[] keyValueArr = keyValue.split("[=]");
            map.put(keyValueArr[0], keyValueArr.length > 1 ? keyValueArr[1] : "");
        }
        return map;
    }

    /**
     * 给url添加一个参数，如参数已存在那么修改参数的值为添加后的值，如果不存在则新增一个参数
     */
    public static String addParam(String url, String key, String value) {
        String urlNo = cutParam(url);
        Map<String, String> map = getParamMap(url);
        map.put(key, value);
        String urlnew = buildParamStr(urlNo, map);
        return urlnew;
    }

    /**
     * 上面方法的批处理{@link #addParam(String, String, String)}
     */
    public static String addParams(String url, Map<String, String> paramMap) {
        if (StrUtil.isEmpty(paramMap) || StrUtil.isEmpty(url)) return url;
        String urlNo = cutParam(url);
        Map<String, String> map = getParamMap(url);
        for (Map.Entry<String, String> keyValue : paramMap.entrySet()) {
            map.put(keyValue.getKey(), keyValue.getValue());
        }
        String urlnew = buildParamStr(urlNo, map);
        return urlnew;
    }

    /**
     * 把键值对map集合拼接成参数字符串
     * { id: 123 , name: ins }  -->  id=123&name=ins
     */
    public static String buildParamStr(Map<String, String> map) {
        if (StrUtil.isEmpty(map)) return "";
        String ret = "";
        for (Map.Entry<String, String> keyValue : map.entrySet()) {
            ret += keyValue.getKey() + "=" + keyValue.getValue() + "&";
        }
        ret = StrUtil.subLastChart(ret, "&");
        return ret;
    }

    /**
     * 把键值对map集合拼接成参数字符串，拼接到一个没有参数的URL中
     */
    public static String buildParamStr(String urlNo, Map<String, String> map) {
        String paramStr = buildParamStr(map);
        String url = !StrUtil.isEmpty(paramStr) ? urlNo + "?" + paramStr : urlNo;
        return url;
    }

    /**
     * 删除url中的服务器域名
     * http://www.baidu.com/api/map/getloc?id=1 --> api/map/getloc?id=1
     */
    public static String cutDomain(String url) {
        int index = url.indexOf("/", 10);
        if (index >= 0) {
            return url.substring(index);
        } else {
            return url;
        }
    }

    /**
     * 删除url中的所有参数，只留下请求链接
     * http://www.baidu.com/api/map/getloc?id=1 --> http://www.baidu.com/api/map/getloc
     */
    public static String cutParam(String url) {
        int index = url.indexOf("?");
        if (index >= 0) {
            return url.substring(0, index);
        } else {
            return url;
        }
    }

    /**
     * 判断两个url是否相同（排除domain头和参数的影响）
     * http://192.168.1.129/api/map/getloc?id=1  ==  http://domain.magicbeans.net/api/map/getloc?id=2
     * 上诉2个链接判断为相同：只比较 api/map/getloc 部分
     */
    public static boolean matchUrl(String url1, String url2) {
        String nourl1 = cutParam(cutDomain(url1));
        String nourl2 = cutParam(cutDomain(url2));
        if (nourl1.equals(nourl2)) {
            return true;
        } else {
            return false;
        }
    }
}
