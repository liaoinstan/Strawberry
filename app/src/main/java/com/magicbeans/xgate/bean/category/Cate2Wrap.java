package com.magicbeans.xgate.bean.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class Cate2Wrap implements Serializable {
    private String ProdCatgId;
    private String ProdCatgName;
    private List<Cate2> Groups;

    //################### 业务方法 ##################
    public List<Cate3> formatToCate3List() {
        //数据模型转换
        ArrayList<Cate3> cate3List = new ArrayList<>();
        for (Cate2 cate2 : Groups) {
            Cate3 header = new Cate3(true, cate2.getProdGroupName());
            cate3List.add(header);
            cate3List.addAll(cate2.getProdTypes());
        }
        return cate3List;
    }

    public String getProdCatgId() {
        return ProdCatgId;
    }

    public void setProdCatgId(String prodCatgId) {
        ProdCatgId = prodCatgId;
    }

    public String getProdCatgName() {
        return ProdCatgName;
    }

    public void setProdCatgName(String prodCatgName) {
        ProdCatgName = prodCatgName;
    }

    public List<Cate2> getGroups() {
        return Groups;
    }

    public void setGroups(List<Cate2> groups) {
        Groups = groups;
    }
}
