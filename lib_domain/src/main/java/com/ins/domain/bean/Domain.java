package com.ins.domain.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Domain implements Serializable {
    private String ip;
    private String note;

    public Domain() {
    }

    public Domain(String ip, String name) {
        this.ip = ip;
        this.note = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}