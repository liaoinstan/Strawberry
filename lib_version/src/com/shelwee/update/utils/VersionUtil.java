package com.shelwee.update.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class VersionUtil {

    //获取版本信息 静态接口
    public static PackageInfo getpaPackageInfo(Context context) {
        PackageInfo pinfo = null;
        if (context != null) {
            try {
                pinfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pinfo;
    }

    public static String getVersion(Context context) {
        PackageInfo pkinfo = getpaPackageInfo(context);
        if (pkinfo != null && pkinfo.versionName != null && !"".equals(pkinfo.versionName)) {
            String appName = pkinfo.applicationInfo.loadLabel(context.getPackageManager()).toString() + " " + pkinfo.versionName;
            return appName;
        }
        return null;
    }

    public static String getAppName(Context context) {
        PackageInfo pkinfo = getpaPackageInfo(context);
        if (pkinfo != null && pkinfo.versionName != null && !"".equals(pkinfo.versionName)) {
            String appName = pkinfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            return appName;
        }
        return null;
    }

    public static int getAppVersionCode(Context context) {
        PackageInfo pkinfo = getpaPackageInfo(context);
        return pkinfo.versionCode;
    }
}
