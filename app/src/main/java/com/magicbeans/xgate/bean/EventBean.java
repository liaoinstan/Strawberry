package com.magicbeans.xgate.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class EventBean implements Serializable {

    //语言切换
    public static final int EVENT_LANGUAGE_CHANGE = 0xffa101;
    //未登录
    public static final int EVENT_NOLOGIN = 0xffa102;
    //首页跳转到热门品牌页面
    public static final int EVENT_JUMP_BRANDHOT = 0xffa103;
    //用户切换到购物车时，发送一条消息
    public static final int EVENT_IN_SHOPCART = 0xffa104;
    //商品有变动，刷新购物车
    public static final int EVENT_REFRESH_SHOPCART = 0xffa105;
    //登录
    public static final int EVENT_LOGIN = 0xffa106;
    //注销
    public static final int EVENT_LOGOUT = 0xffa107;
    //刷新地址管理列表
    public static final int EVENT_REFRESH_ADDRESSLIST = 0xffa108;
    //从地址管理列表获取返回地址对象
    public static final int EVENT_GET_ADDRESS = 0xffa109;
    //刷新个人中心历史记录总数
    public static final int EVENT_ME_HISTORY_COUNT = 0xffa109;
    //刷新订单新增的地址区域
    public static final int EVENT_REFRESH_ORDERADD_ADDRESS = 0xffa120;

    private int event;
    private Map<String, Object> map = new HashMap<>();

    public EventBean() {
    }

    public EventBean(int event) {
        this.event = event;
    }

    public EventBean put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Object get(String key) {
        return map.get(key);
    }

    //////////get & set///////////////

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
