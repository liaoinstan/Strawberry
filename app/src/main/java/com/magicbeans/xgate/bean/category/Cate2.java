package com.magicbeans.xgate.bean.category;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class Cate2 implements Serializable {

    private String ProdGroupId;
    private String ProdGroupName;
    private List<Cate3> ProdTypes;

    public String getProdGroupId() {
        return ProdGroupId;
    }

    public void setProdGroupId(String prodGroupId) {
        ProdGroupId = prodGroupId;
    }

    public String getProdGroupName() {
        return ProdGroupName;
    }

    public void setProdGroupName(String prodGroupName) {
        ProdGroupName = prodGroupName;
    }

    public List<Cate3> getProdTypes() {
        return ProdTypes;
    }

    public void setProdTypes(List<Cate3> prodTypes) {
        ProdTypes = prodTypes;
    }
}
