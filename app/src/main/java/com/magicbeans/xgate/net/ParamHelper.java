package com.magicbeans.xgate.net;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ParamHelper {
    /**
     * 获取OpenId类型标志
     * 1: WeChat
     * 2: Weibo
     * 3: QQ
     */
    public static int getOpenIDType(String platform) {
        if (platform.equals(Wechat.NAME)) {
            return 1;
        } else if (platform.equals(SinaWeibo.NAME)) {
            return 2;
        } else if (platform.equals(QQ.NAME)) {
            return 3;
        } else {
            return 0;
        }
    }

    public static Map<String, Object> getDeviceTypeMap() {
        return new LinkedHashMap<String, Object>() {{
            put("deviceType", getDeviceType());
        }};
    }

    public static String getDeviceType() {
        return "android";
    }
    public static String getLanguage() {
        return "zh_CN";
    }
}
