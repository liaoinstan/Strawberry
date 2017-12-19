package com.magicbeans.xgate.data.cache;

import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.bean.category.Cate2Wrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 * 运行时数据缓存，存放于内存中，退出APP后清空
 */

public class RuntimeCache {

    //##############单例##############//

    private static RuntimeCache INSTANCE = new RuntimeCache();

    private RuntimeCache() {
    }

    public static RuntimeCache getInstance() {
        return INSTANCE;
    }

    //############################
    //############################

    private Map<String, Cate2Wrap> cacheMap = new HashMap<>();

    public void putCate2Cache(String CatgId, Cate2Wrap data) {
        cacheMap.put(CatgId, data);
    }

    public Cate2Wrap getCate2Cache(String CatgId) {
        return cacheMap.get(CatgId);
    }

    //############################
    //############################

    private List<Brand> hotBrands = new ArrayList<>();

    public void putHotBrandsCache(List<Brand> hotBrands) {
        this.hotBrands.addAll(hotBrands);
    }

    public List<Brand> getHotBrandsCache() {
        return hotBrands;
    }

    //############################
    //############################

    private List<Cate1> cate1List = new ArrayList<>();

    public List<Cate1> getCate1Cache() {
        return cate1List;
    }

    public void putCate1Cache(List<Cate1> cate1List) {
        this.cate1List.addAll(cate1List);
    }
}
