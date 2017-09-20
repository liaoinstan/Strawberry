package com.ins.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SharePreferences工具类
 *
 * 使用：
 * SharedPrefUtil.open("prefrenceName").get( name );
 * SharedPrefUtil.open("prefrenceName").putXXX( key , value );
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SharedPrefUtilV2 {

    public static void init(Context context) {
        SharedPrefUtilV2.context = context.getApplicationContext();
    }

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private SharedPreferences prefrence;

    private SharedPrefUtilV2(String prefrenceName) {
        prefrence = context.getSharedPreferences(prefrenceName, Context.MODE_PRIVATE);
    }

    /**
     * 打开一个SharedPreferences文件
     *
     * @param name SharedPreferences文件名
     * @return SPUtil的实例
     */
    public static SharedPrefUtilV2 open(String name) {
        if (context == null) {
            throw new NullPointerException("Must call init(Context context) at first");
        }
        return new SharedPrefUtilV2(name);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return this.prefrence.getString(key, "");
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return this.prefrence.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.prefrence.getBoolean(key, defaultValue);
    }

    public void putLong(String key, Long value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return this.prefrence.getLong(key, 0L);
    }

    public void putInt(String key, Integer value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * @param key 键
     * @return 默认返回0
     */
    public int getInt(String key) {
        return this.prefrence.getInt(key, 0);
    }

    public void putFloat(String key, Float value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * @param key 键
     * @return 默认返回0.0F
     */
    public float getFloat(String key) {
        return this.prefrence.getFloat(key, 0.0F);
    }


    /**
     * 存放对象
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, Object value) {
        if (value != null) {
            try {
                ByteArrayOutputStream t = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(t);
                oos.writeObject(value);
                oos.flush();
                oos.close();
                byte[] data = t.toByteArray();
                String base64 = Base64.encodeToString(data, 2);
                this.putString(key, base64);
            } catch (Throwable var7) {
                var7.printStackTrace();
            }
        }
    }

    /**
     * 获得对象
     *
     * @param key 键
     * @return 返回Object
     */
    public Object get(String key) {
        try {
            String t = this.getString(key);
            if (TextUtils.isEmpty(t)) {
                return null;
            } else {
                byte[] data = Base64.decode(t, 2);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                Object value = ois.readObject();
                ois.close();
                return value;
            }
        } catch (Throwable var7) {
            var7.printStackTrace();
            return null;
        }
    }

    /**
     * 根据键移除对应的值
     *
     * @param key 键值
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.remove(key);
        editor.apply();
    }
}
