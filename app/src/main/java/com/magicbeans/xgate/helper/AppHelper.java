package com.magicbeans.xgate.helper;

import com.magicbeans.xgate.common.AppData;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by SEELE on 2017/10/12.
 */

public class AppHelper {
    public static String getPriceSymbol(String CurSymbol) {
        return "¥";
    }

    public static class User {
        //当前是否已经登录
        public static boolean isLogin() {
            return AppData.App.getUser() != null;
        }
    }
}
