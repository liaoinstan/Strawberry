package com.magicbeans.xgate.common;

import com.ins.common.utils.SharedPrefUtilV2;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.net.NetApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class AppData {


    public static class App {

        private static final String SHARENAME = "app_config";
        private static final String KEY_TOKEN = "token";
        private static final String KEY_USER = "user";
        private static final String KEY_LANGUAGE = "language";

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

        public static String getLanguage() {
            return SharedPrefUtilV2.open(SHARENAME).getString(KEY_LANGUAGE);
        }

        public static void saveLanguage(String language) {
            SharedPrefUtilV2.open(SHARENAME).putString(KEY_LANGUAGE, language);
        }

        ////////////////////////////////////////////////////////////////////////////////////////

        public static String getTestOpenId() {
            return SharedPrefUtilV2.open(SHARENAME).getString("TEST_OPENID");
        }

        public static void saveTestOpenId(String testOpenId) {
            SharedPrefUtilV2.open(SHARENAME).putString("TEST_OPENID", testOpenId);
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

        public static String apiUserProfile() {
            return NetApi.getBaseUrl() + "app/apiUserProfile.aspx";
        }

        /**
         * 添加购物车
         * ProdId
         * token
         */
        public static String apiAddShopCart() {
            return NetApi.getBaseUrl() + "app/apiShopcart.aspx?ID=xGate&act=additem";
        }

        /**
         * 获取购物车
         * token
         */
        public static String apiGetShopCartList() {
            return NetApi.getBaseUrl() + "app/apiShopcart.aspx?ID=xGate";
        }
    }
}
