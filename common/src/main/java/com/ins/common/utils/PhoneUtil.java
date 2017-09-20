package com.ins.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * by liaoinstan
 * 拨打电话工具类
 *
 * 注意：
 * 拨打电话一般有两种途径，一种是调启系统拨号页面，二种APP直接发起拨号
 * 第一种方式比较安全，不需要额外权限即可调用
 * 第二种方式需要赋予拨号权限<uses-permission android:name="android.permission.CALL_PHONE" />
 * 同时拨号权限是危险权限，需要在代码中动态申请，安装时APP会出现安全提示“允许拨打电话”“可能产生费用”这样的敏感词，吃瓜用户可能会认为不安全拒绝权限甚至停止安装
 *
 * 使用建议：
 * 如果APP只需拨打电话并无特殊要求，推荐使用第一种方案，安全易于被用户接受
 * 如果APP需要定制拨号请求，比如钉钉滴滴这样的回拨等复杂需求，则使用第二种
 *
 * 下面代码报红请忽略，只要加上<uses-permission android:name="android.permission.CALL_PHONE" />权限即可，但是这个工具库不应该持有一个危险权限，我就是不加，如果使用第二种方式，请在主module加上该权限
 */
public class PhoneUtil {

    //调启系统拨号页面，由系统进行拨打电话
    public static void phone(Context context, String num) {
        if (!StrUtil.isEmpty(num)) {
            if (!num.startsWith("tel:")) {
                num = "tel:" + num;
            }
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse(num);
            intent.setData(data);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "电话号码为空", Toast.LENGTH_SHORT).show();
        }
    }

    //APP发起拨号（注意在主工程里添加权限<uses-permission android:name="android.permission.CALL_PHONE" />不要在common子工程里加，不是所有APP都需要这个权限，下面报红不用管）
    public static void call(Activity context, String num) {
        if (!StrUtil.isEmpty(num)) {
            if (!num.startsWith("tel:")) {
                num = "tel:" + num;
            }
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse(num);
            intent.setData(data);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                PermissionsUtil.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE});
                return;
            }
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "电话号码为空", Toast.LENGTH_SHORT).show();
        }
    }
}
