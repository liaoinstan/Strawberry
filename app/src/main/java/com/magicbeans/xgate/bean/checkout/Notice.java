package com.magicbeans.xgate.bean.checkout;

import android.text.Html;

import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/7.
 */

public class Notice implements Serializable {
    private List<String> Msg;

    public List<String> getMsg() {
        return Msg;
    }

    public void setMsg(List<String> msg) {
        Msg = msg;
    }

    //################## 业务方法 ####################

    public String getNoticeStr() {
        if (StrUtil.isEmpty(Msg)) return "";
        String msgAll = "";
        for (String str : Msg) {
            msgAll += Html.fromHtml(str).toString() + "\n";
        }
        return StrUtil.subLastChart(msgAll, "\n");
    }
}
