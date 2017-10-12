package com.magicbeans.xgate.bean.brand;

import com.magicbeans.xgate.bean.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class BrandIndex implements Serializable {
    private String IndexName;
    private List<Brand> BrandList;

    public String getIndexName() {
        return IndexName;
    }

    public void setIndexName(String indexName) {
        IndexName = indexName;
    }

    public List<Brand> getBrandList() {
        return BrandList;
    }

    public void setBrandList(List<Brand> brandList) {
        BrandList = brandList;
    }
}
