package com.magicbeans.xgate.common;

import android.text.TextUtils;

import com.ins.common.utils.ValidateUtil;

/**
 * Created by Administrator on 2017/5/12.
 */

public class AppVali {

    public static String email(String email) {
        if (!ValidateUtil.Email(email)) {
            return "请输入正确的邮箱";
        } else {
            return null;
        }
    }

    public static String createAccount(String openId, String email, String mobile) {
        if (TextUtils.isEmpty(openId)) {
            return "没有openId，请退出重试";
        } else if (!ValidateUtil.Email(email)) {
            return "请输入正确的邮箱";
        } else if (!ValidateUtil.Mobile(mobile)) {
            return "请输入正确的手机号";
        } else {
            return null;
        }
    }

    public static String mergeAccount(String openId, String email, String psw) {
        if (TextUtils.isEmpty(openId)) {
            return "没有openId，请退出重试";
        } else if (!ValidateUtil.Email(email)) {
            return "请输入正确的邮箱";
        } else if (!length(psw, 6, 32)) {
            return "请输入6-32位密码";
        } else {
            return null;
        }
    }

    public static String addAddress(String addrNickname, String tel, String province, String city, String district, String address, String idcard) {
        if (TextUtils.isEmpty(addrNickname)) {
            return "请输入收货人";
        } else if (TextUtils.isEmpty(tel)) {
            return "请输入收货人手机号";
        } else if (!ValidateUtil.Mobile(tel)) {
            return "请输入正确的手机号";
        } else if (TextUtils.isEmpty(province)) {
            return "请选择省份";
        } else if (TextUtils.isEmpty(city)) {
            return "请选择城市";
        } else if (TextUtils.isEmpty(district)) {
            return "请选择城区";
        } else if (TextUtils.isEmpty(address)) {
            return "请输入详细地址";
        } else if (TextUtils.isEmpty(idcard)) {
            return "请输入申报资料";
        } else {
            return null;
        }
    }

    public static String checkOut(String idcard) {
        if (TextUtils.isEmpty(idcard)) {
            return "请填写申报资料";
        } else {
            return null;
        }
    }

    /////////////////////////////////////


    public static String login(String userName, String psw) {
        if (TextUtils.isEmpty(userName)) {
            return "请输入手机号";
        } else if (!ValidateUtil.Mobile(userName)) {
            return "请输入正确的手机号";
        } else if (TextUtils.isEmpty(psw)) {
            return "请输入密码";
        } else if (!length(psw, 6, 32)) {
            return "密码格式不正确";
        } else {
            return null;
        }
    }

    public static String modifyPsw(String oldPsw, String newPsw, String newPsw_repeat) {
        if (isEmpty(oldPsw)) {
            return "请输入旧密码";
        } else if (isEmpty(newPsw)) {
            return "请输入新密码";
        } else if (isEmpty(newPsw_repeat)) {
            return "请再次输入新密码";
        } else if (!length(oldPsw, 6, 32)) {
            return "旧密码格式不正确";
        } else if (!length(newPsw, 6, 32)) {
            return "新密码格式不正确";
        } else if (!newPsw.equals(newPsw_repeat)) {
            return "两次输入不一致";
        } else {
            return null;
        }
    }

    public static String forgetPsw(String newPsw, String newPsw_repeat) {
        if (isEmpty(newPsw)) {
            return "请输入新密码";
        } else if (isEmpty(newPsw_repeat)) {
            return "请再次输入新密码";
        } else if (!length(newPsw, 6, 32)) {
            return "新密码格式不正确";
        } else if (!newPsw.equals(newPsw_repeat)) {
            return "两次输入不一致";
        } else {
            return null;
        }
    }

    public static String phone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "请输入手机号";
        } else if (!ValidateUtil.Mobile(phone)) {
            return "请输入正确的手机号";
        } else {
            return null;
        }
    }

    public static String content(String content) {
        if (isEmpty(content)) {
            return "请输入内容";
        } else {
            return null;
        }
    }


    public static String regist_phone(String phone, String phone_old, String cold, String code_old) {
        if (TextUtils.isEmpty(phone)) {
            return "请输入手机号";
        } else if (!phone.equals(phone_old)) {
            return "您的手机号与验证码不匹配";
        } else if (!ValidateUtil.Mobile(phone_old)) {
            return "请输入正确的手机号";
        } else if (!cold.equals(code_old)) {
            return "验证码不正确";
        } else {
            return null;
        }
    }

    public static String regist_psw(String psw, String psw_repeat) {
        if (TextUtils.isEmpty(psw)) {
            return "请输入密码";
        } else if (!length(psw, 6, 32)) {
            return "密码格式不正确";
        } else if (!psw.equals(psw_repeat)) {
            return "两次输入号码不同";
        } else {
            return null;
        }
    }

    private static boolean length(String str, int min, int max) {
        return str.length() >= min && str.length() <= max;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    private static String trim(String str) {
        return str != null ? str.trim() : "";
    }
}
