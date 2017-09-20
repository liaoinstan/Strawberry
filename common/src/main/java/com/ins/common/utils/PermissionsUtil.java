package com.ins.common.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * by liaoinstan
 * <p>
 * 前言：
 * 目前6.0已经发布很久了，运行时权限管理已经是每个APP必须要处理的标配，但是！
 * 目前国产厂商的手机系统定制水平参差不齐，不同品牌手机在权限管理的修改上逻辑各不相同、相互不兼容，很难定制出一套复合所有机型的解决方案
 * 不同厂商的机型SDK API返回不同结果，这里严正谴责各无良手机厂商，给开发带来了很大不便。
 * 这里以google官方机型为标准，给出一个官方标准的权限申请流程，其余杂牌手机room的不兼容问题恕不处理，这些兼容性问题由厂商制造由厂商买单，开发要紧跟谷歌的步伐，不乱主次
 * 按谷歌的官方文档进行开发，这些厂商的兼容会在将来逐渐向谷歌靠拢
 * 这里总结一下各厂商的坑：
 * 小米：对权限申请进行归类，合并处理，对部分谷歌标准的危险权限进行放水，不需请求即可获取（摄像头权限关闭了仍然可以进行拍照）
 * 魅族：对部分谷歌标准的危险权限进行放水（摄像头，定位等），另外在5.0系统上有大坑：5.0是没有权限运行时管理的，但是魅族自己开发了一套权限管理嵌入到官方的API中，导致调用SDK检查权限始终返回已授予权限
 * *     但实际魅族可以关闭权限，导致崩溃问题发生，开发人员不得不对魅族进行特殊处理；对部分权限在申请时不授予，在调用时才授予
 * OPPO&VIVO：对部分谷歌标准的危险权限进行放水，部分SDK在其设备上返回结果与实际情况不一致
 * 华为：对部分谷歌标准的危险权限进行放水
 * <p>
 * 权限控制工具类：
 * 为了适配API23，即Android M 在清单文件中配置use permissions后，还要在程序运行的时候进行申请。
 * <p/>
 * ***整个权限的申请与处理的过程是这样的：
 * *****1.进入主Activity，首先申请所有的权限；
 * *****2.用户对权限进行授权，有2种情况：
 * ********1).用户Allow了权限，则表示该权限已经被授权，无须其它操作；
 * ********2).用户Deny了权限，则下次启动Activity会再次弹出系统的Permisssions申请授权对话框。
 * *****3.如果用户Deny了权限，那么下次再次进入Activity，会再次申请权限，这次的权限对话框上，会有一个选项“dont ask me again”：
 * ********1).如果用户勾选了“dont ask me again”的checkbox，下次启动时就必须自己写Dialog或者Snackbar引导用户到应用设置里面去手动授予权限；
 * ********2).如果用户未勾选上面的选项，若选择了Allow，则表示该权限已经被授权，无须其它操作；
 * ********3).如果用户未勾选上面的选项，若选择了Deny，则下次启动Activity会再次弹出系统的Permisssions申请授权对话框。
 * <p>
 * shouldShowRequestPermissionRationale()
 * 如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
 * 如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
 * 如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
 * <p>
 * 使用帮助：
 * {@link #checkAndRequestPermissions(Activity)} 检查并申请APP所声明的所有权限（一般在MainActivity中调用）
 * {@link #checkAndRequestPermissions(Activity, String[])} 检查并申请一个权限集合并引导用户前往设置（一般在使用时调用）
 * {@link #checkAndRequestPermissions(Activity, String[], boolean)} 上面方法都是调用的这个方法
 */
public class PermissionsUtil {

    // 状态码、标志位
    public static final int REQUEST_STATUS_CODE = 0x001;

    public static boolean checkAndRequestPermissions(Activity activity) {
        return checkAndRequestPermissions(activity, getAppPermissions(activity), false);
    }

    public static boolean checkAndRequestPermissions(Activity activity, boolean needLead) {
        return checkAndRequestPermissions(activity, getAppPermissions(activity), needLead);
    }

    public static boolean checkAndRequestPermissions(Activity activity, String[] pemissions) {
        return checkAndRequestPermissions(activity, pemissions, true);
    }

    /**
     * 检查一个权限集合并尝试申请其中的权限
     * needLead：如果权限并未全部授予是否展示引导视图引导用户前往设置页面进行授权
     */
    public static boolean checkAndRequestPermissions(Activity activity, String[] pemissions, boolean needLead) {
        //检查并获取未授权的权限
        String[] deniedPermissions = checkAndGetDeniedArr(activity, pemissions);
        //全部授权，则返回成功
        if (StrUtil.isEmpty(deniedPermissions)) return true;
        //对没授权的权限进行申请
        requestPermissions(activity, deniedPermissions);
        //还有没有授权的权限，展示引导视图，让用户前往手动设置权限
        if (needLead) {
            showLeadView(activity, pemissions);
        }
        return false;
    }

    //展示引导视图，让用户前往手动设置权限
    private static void showLeadView(final Activity activity, String[] pemissions) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(getDescribeByPermission(activity, pemissions))
                .setNegativeButton("我已授权", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("前往设置", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startPermissionSettingActivity(activity);
                    }
                }).create();
        dialog.show();
    }

    //检查权限集合，把没有授权的权限作为集合返回
    private static String[] checkAndGetDeniedArr(Context context, String[] pemissions) {
        ArrayList<String> denidArray = new ArrayList<>();
        for (String permission : pemissions) {
            int grantCode = PermissionChecker.checkSelfPermission(context, permission);
            if (grantCode != PackageManager.PERMISSION_GRANTED) {
                denidArray.add(permission);
            }
        }
        return denidArray.toArray(new String[denidArray.size()]);
    }

    //申请权限
    public static void requestPermissions(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_STATUS_CODE);
    }

    //启调设置页面
    private static void startPermissionSettingActivity(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String packageName = info.applicationInfo.packageName;

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取APP的所有声明的权限
    private static String[] getAppPermissions(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    //通过权限列表 获取权限的描述(用于向用户展示，说明申请权限的类型和理由)
    private static String getDescribeByPermission(Context context, String[] permissions) {
        Map<String, String> map = getDescribeMapByPermission(context, permissions);
        String infoStr = "";
        if (!StrUtil.isEmpty(map)) {
            for (Map.Entry<String, String> keyValue : map.entrySet()) {
                infoStr += keyValue.getKey() + " : " + keyValue.getValue() + "\n";
            }
        }
        String ret = "需要您授予如下权限："
                + infoStr
                + "";
        return ret;
    }

    //通过权限列表 获取权限的描述Map集合
    private static Map<String, String> getDescribeMapByPermission(Context context, String[] permissions) {
        try {
            PackageManager pm = context.getPackageManager();
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            for (int i = 0; i < permissions.length; i++) {
                //通过usesPermissionName获取该权限的详细信息
                PermissionInfo permissionInfo = pm.getPermissionInfo(permissions[i], PackageManager.GET_META_DATA);
                //获得该权限属于哪个权限组
                if (!StrUtil.isEmpty(permissionInfo.group)) {
                    PermissionGroupInfo permissionGroupInfo = pm.getPermissionGroupInfo(permissionInfo.group, PackageManager.GET_META_DATA);
                    map.put(permissionGroupInfo.loadLabel(pm).toString(), permissionGroupInfo.loadDescription(pm).toString());
                }
            }
            return map;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //################## 通用方法 #######################

    public static boolean checkCamera(Activity activity){
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        return checkAndRequestPermissions(activity,permissions);

    }
}
