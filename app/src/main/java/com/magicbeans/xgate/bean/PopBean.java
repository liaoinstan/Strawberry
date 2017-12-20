package com.magicbeans.xgate.bean;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.data.cache.RuntimeCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class PopBean implements Serializable {

    private String id;
    private String name;

    //###############  业务方法  #################

    public static List<PopBean> transFromBrands(List<Brand> brands) {
        List<PopBean> popBrands = new ArrayList<>();
        if (!StrUtil.isEmpty(brands)) {
            for (Brand brand : brands) {
                popBrands.add(new PopBean(brand.getBrandID(), brand.getBrandLangName()));
            }
        }
        return popBrands;
    }

    public static List<PopBean> transFromCate1s(List<Cate1> cate1s) {
        List<PopBean> popCates = new ArrayList<>();
        if (!StrUtil.isEmpty(cate1s)) {
            for (Cate1 cate1 : cate1s) {
                popCates.add(new PopBean(cate1.getCatgId(), cate1.getTitle()));
            }
        }
        return popCates;
    }

    //###############  业务方法  #################


    public PopBean(String name) {
        this.name = name;
    }

    public PopBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
