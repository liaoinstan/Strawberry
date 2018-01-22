package com.magicbeans.xgate.common;

import com.ins.common.utils.SharedPrefUtilV2;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;

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
     * 记录了app中所有的请求连接地址
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
        public static String upload = "images/res/upload";                                                 //上传文件

        public static String bannerInfo = "api/page/app/bannerInfo";                                            //banner详情?bannerId=1
        public static String newsInfo = "api/page/app/newsInfo";                                                //资讯详情?newsId=2

        public static String quelity = "api/page/aptitude";                     //资质
        public static String about = "api/page/aboutUs";                        //关于我们
        public static String netpoint = "api/page/branch";                      //网点
        public static String safe = "api/page/polling";                         //安全巡检
        public static String clause = "api/page/app/agreement";                //使用条款

    }
}
