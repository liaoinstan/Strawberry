package com.magicbeans.xgate.common;

import com.ins.common.utils.SharedPrefUtilV2;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;

/**
 * Created by Administrator on 2017/7/17.
 */

public class AppData {

    public static class App {

        private static final String SHARENAME = "app_config";
        private static final String KEY_TOKEN = "token";
        private static final String KEY_OPENID = "openid";
        private static final String KEY_USER = "user";
        private static final String KEY_USERAGENT = "user_agent";
        private static final String KEY_LANGUAGE = "language";
        private static final String KEY_CART = "cart";

        public static void saveToken(Token token) {
            SharedPrefUtilV2.open(SHARENAME).put(KEY_TOKEN, token);
        }

        public static Token getToken() {
            return (Token) SharedPrefUtilV2.open(SHARENAME).get(KEY_TOKEN);
        }

        public static void removeToken() {
            SharedPrefUtilV2.open(SHARENAME).remove(KEY_TOKEN);
        }

        ////////////////////////////////////////////////////////////////////////////////////////

        public static String getOpenId() {
            return SharedPrefUtilV2.open(SHARENAME).getString(KEY_OPENID);
        }

        public static void saveOpenId(String openId) {
            SharedPrefUtilV2.open(SHARENAME).putString(KEY_OPENID, openId);
        }

        public static void removeOpenId() {
            SharedPrefUtilV2.open(SHARENAME).remove(KEY_OPENID);
        }


        ////////////////////////////////////////////////////////////////////////////////////////

        public static boolean getIsDoingCart() {
            return SharedPrefUtilV2.open(SHARENAME).getBoolean(KEY_CART);
        }

        public static void saveIsDoingCart(boolean isDoing){
            SharedPrefUtilV2.open(SHARENAME).putBoolean(KEY_CART, isDoing);
        }

        //////////////////////////////////////////////////////////////////////////////////////

        public static void saveUser(User user) {
            SharedPrefUtilV2.open(SHARENAME).put(KEY_USER, user);
        }

        public static User getUser() {
            return (User) SharedPrefUtilV2.open(SHARENAME).get(KEY_USER);
        }

        public static void removeUser() {
            SharedPrefUtilV2.open(SHARENAME).remove(KEY_USER);
        }

        ////////////////////////////////////////////////////////////////////////////////////////

        public static void saveUserAgent(String userAgent) {
            SharedPrefUtilV2.open(SHARENAME).putString(KEY_USERAGENT, userAgent);
        }

        public static String getUserAgent() {
            return SharedPrefUtilV2.open(SHARENAME).getString(KEY_USERAGENT);
        }

        public static void removeUserAgent() {
            SharedPrefUtilV2.open(SHARENAME).remove(KEY_USERAGENT);
        }

        ////////////////////////////////////////////////////////////////////////////////////////

        public static String getLanguage() {
            return SharedPrefUtilV2.open(SHARENAME).getString(KEY_LANGUAGE);
        }

        public static void saveLanguage(String language) {
            SharedPrefUtilV2.open(SHARENAME).putString(KEY_LANGUAGE, language);
        }


    }

    /**
     * 记录了app中所有全局控制常量
     */
    public static class Config {
        public static boolean showVali = false;                 //显示验证码（仅测试）
        public static boolean showTestToast = false;            //打印测试信息到窗口（仅测试）
    }

    public static class Constant {
    }

    /**
     * 记录了app中的请求连接地址
     */
    public static class Url {

        /**
         * 资源服务器地址
         */
        public static String domainRes;

        /**
         * 接口请求地址
         */
        public static String version = "http://7xnfyf.com1.z0.glb.clouddn.com/version.json";                                   //客户端检查更新

    }
}
