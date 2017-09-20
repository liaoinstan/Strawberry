package com.ins.common.utils;

import android.util.Log;

import java.io.PrintStream;

/**
 * LOG工具类
 */
public class L {

    private static String TAG = "LOG";

    private volatile static boolean DEBUG = true;

    private L() {
    }

    public static void v(Object message) {
        v(TAG, message);
    }

    public static void v(String tag, Object message) {
        if (!DEBUG) {
            return;
        }
        try {
            Log.v(tag, message.toString());
        } catch (NullPointerException e) {
            printError(e);
        }
    }

    public static void i(Object message) {
        i(TAG, message);
    }

    public static void i(String tag, Object message) {
        if (!DEBUG) {
            return;
        }

        try {
            Log.i(tag, message.toString());
        } catch (NullPointerException e) {
            printError(e);
        }
    }

    public static void e(Object message) {
        e(TAG, message);
    }

    public static void e(String tag, Object message) {
        if (!DEBUG) {
            return;
        }

        try {
            Log.e(tag, message.toString());
        } catch (NullPointerException e) {
            printError(e);
        }
    }

    public static void d(Object message) {
        d(TAG, message);
    }

    public static void d(String tag, Object message) {
        if (!DEBUG) {
            return;
        }

        try {
            Log.d(tag, message.toString());
        } catch (NullPointerException e) {
            printError(e);
        }
    }

    public static void printError(Throwable e) {
        PrintStream err = System.err;
        StackTraceElement[] stackTrace = e.getStackTrace();
        err.println("L.e(Object message),object is null:\n" + e.getMessage());
        for (StackTraceElement stackTraceElement : stackTrace) {
            err.println("\tat " + stackTraceElement);
        }
    }

    /////////////get & set

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        L.TAG = TAG;
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        L.DEBUG = DEBUG;
    }
}
