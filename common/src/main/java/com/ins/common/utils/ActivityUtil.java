package com.ins.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by liaoinstan on 2016/11/10.
 * Activity工具类
 */

public class ActivityUtil {
//    public static boolean isForeground(Context context) {
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.processName.equals(context.getPackageName())) {
//                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
//                    Log.i("后台", appProcess.processName);
//                    return false;
//                }else{
//                    Log.i("前台", appProcess.processName);
//                    return true;
//                }
//            }
//        }
//        return true;
//    }

    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }
}
