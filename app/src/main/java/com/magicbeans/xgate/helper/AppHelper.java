package com.magicbeans.xgate.helper;

import com.magicbeans.xgate.common.AppData;

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
