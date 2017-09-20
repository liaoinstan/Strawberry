package com.ins.common.utils;

import android.app.Application;
import android.content.Context;

/**
 * 保存ApplicationContext的引用，一个App只存在一个ApplicationContext，伴随整个应用生命周期，
 * ApplicationContext生命周期和静态类生命周期一致，可以使用静态成员保存ApplicationContext引用，需要的时候通过该类获取
 * App.getContext()
 * 在Application的onCreate方法中调用saveApplication方法
 */
public class App {

    private App() {
    }

    private static Application application;

    public static void saveApplication(Application application) {
        App.application = application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    public static boolean hasContext() {
        return application != null;
    }
}
