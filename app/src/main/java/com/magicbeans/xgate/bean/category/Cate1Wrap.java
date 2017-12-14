package com.magicbeans.xgate.bean.category;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class Cate1Wrap implements Serializable{
    private List<Cate1> MenuList;

    public List<Cate1> getMenuList() {
        return MenuList;
    }

    public void setMenuList(List<Cate1> menuList) {
        MenuList = menuList;
    }
}
