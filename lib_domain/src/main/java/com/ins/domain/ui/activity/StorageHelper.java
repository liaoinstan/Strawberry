package com.ins.domain.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.ins.domain.bean.Domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 利用SharedPreferences存储数据的简单工具
 */

class StorageHelper {

    private Context context;
    private static final String SPACENAME = "debugConfig";
    private static final String NAME_DOMAIN = "domains";
    private static final String NAME_EDITSTR = "editStr";
    private static final String NAME_DOMAIN_RES = "domain_res";

    private StorageHelper(Context context) {
        this.context = context;
    }

    public static StorageHelper with(Context context) {
        return new StorageHelper(context);
    }

    public void putDomains(ArrayList<Domain> domains) {
        put(NAME_DOMAIN, domains);
    }

    public ArrayList<Domain> getDomains() {
        return (ArrayList<Domain>) get(NAME_DOMAIN);
    }

    public void putEditStr(String editStr) {
        putString(NAME_EDITSTR, editStr);
    }

    public String getEditStr() {
        return getString(NAME_EDITSTR);
    }

    public void putDomainRes(String editStr) {
        putString(NAME_DOMAIN_RES, editStr);
    }

    public String getDomainRes() {
        return getString(NAME_DOMAIN_RES);
    }

    public void putString(String key, String value) {
        SharedPreferences prefrence = context.getSharedPreferences(SPACENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences prefrence = context.getSharedPreferences(SPACENAME, Context.MODE_PRIVATE);
        return prefrence.getString(key, "");
    }

    /**
     * 存放对象
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
}
