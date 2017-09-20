package com.ins.common.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 维护一个activity集合，包含app运行过程中所有的活动状态的Activity
 */
public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<Activity>();

    public static synchronized void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static synchronized void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    public static synchronized void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    public synchronized void clear() {
        for (int i = activities.size() - 1; i > -1; i--) {
            Activity activity = activities.get(i);
            finishActivity(activity);
            removeActivity(activity);
            //i = activities.size();
        }
    }
}
