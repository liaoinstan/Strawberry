package com.magicbeans.xgate.bean.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/28.
 */

public class KeyValue implements Serializable{
    private String key;
    private String Value;

    public KeyValue(String key, String value) {
        this.key = key;
        Value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
