package com.magicbeans.xgate.bean.checkout;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class Notice implements Serializable{
    private List<String> Msg;

    public List<String> getMsg() {
        return Msg;
    }

    public void setMsg(List<String> msg) {
        Msg = msg;
    }
}
