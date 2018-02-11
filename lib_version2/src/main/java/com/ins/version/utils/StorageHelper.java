package com.ins.version.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 利用SharedPreferences存储数据的简单工具
 */

public class StorageHelper {

    private Context context;
    private static final String SPACENAME = "debugConfig";
    private static final String NAME_IGNORE_VIERSIONCODE = "ignore_versioncode";

    private StorageHelper(Context context) {
        this.context = context;
    }

    public static StorageHelper with(Context context) {
        return new StorageHelper(context);
    }

    public void putIgnoreVersionCode(int ignore_versioncode) {
        putInt(NAME_IGNORE_VIERSIONCODE, ignore_versioncode);
    }

    public int getIgnoreVersionCode() {
        return getInt(NAME_IGNORE_VIERSIONCODE);
    }

    public void removeIgnoreVersionCode() {
        remove(NAME_IGNORE_VIERSIONCODE);
    }

    private void putInt(String key, int value) {
        SharedPreferences prefrence = context.getSharedPreferences(SPACENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private int getInt(String key) {
        SharedPreferences prefrence = context.getSharedPreferences(SPACENAME, Context.MODE_PRIVATE);
        return prefrence.getInt(key, 0);
    }

    private void remove(String key) {
        SharedPreferences prefrence = context.getSharedPreferences(SPACENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.remove(key);
        editor.apply();
    }
}
