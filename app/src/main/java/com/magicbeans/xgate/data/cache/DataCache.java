package com.magicbeans.xgate.data.cache;

import android.annotation.SuppressLint;
import android.content.Context;

import com.ins.common.utils.ListUtil;
import com.ins.common.utils.MapUtil;
import com.ins.common.utils.others.ACache;
import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.bean.category.Cate2Wrap;
import com.magicbeans.xgate.bean.search.SearchHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 * 运行时数据缓存，存放于内存中，退出APP后清空
 */

public class DataCache {

    public static void init(Context context) {
        DataCache.context = context.getApplicationContext();
    }

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    //##############单例##############//

    private static DataCache INSTANCE;

    private DataCache() {
        aCache = ACache.get(context);
    }

    public static DataCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataCache();
        }
        return INSTANCE;
    }

    //##############单例##############//

    private ACache aCache;

    private static final String KEY_SEARCH_HISTORY = "seachHistorys";
    private static final String KEY_HOT_BRANDS = "hot_brands";
    private static final String KEY_CATE1 = "cate1";
    private static final String KEY_CATE2_MAP = "cate2_map";

    //############################
    //############################

    public Cate2Wrap getCate2Cache(String CatgId) {
        Map<String, Cate2Wrap> map = (Map<String, Cate2Wrap>) aCache.getAsJSONObject(KEY_CATE2_MAP);
        if (map == null) map = new HashMap<>();
        return map.get(CatgId);
    }

    public void putCate2Cache(String CatgId, Cate2Wrap data) {
        Map<String, Cate2Wrap> map = (Map<String, Cate2Wrap>) aCache.getAsJSONObject(KEY_CATE2_MAP);
        if (map == null) map = new HashMap<>();
        map.put(CatgId, data);
        aCache.put(KEY_CATE2_MAP, MapUtil.transHashMap(map));
    }

    //############################
    //############################

    public List<Brand> getHotBrandsCache() {
        return (List<Brand>) aCache.getAsObject(KEY_HOT_BRANDS);
    }

    public void putHotBrandsCache(List<Brand> hotBrands) {
        aCache.put(KEY_HOT_BRANDS, ListUtil.transArrayList(hotBrands));
    }

    //############################
    //############################

    public List<Cate1> getCate1Cache() {
        return (List<Cate1>) aCache.getAsObject(KEY_CATE1);
    }

    public void putCate1Cache(List<Cate1> cate1List) {
        aCache.put(KEY_CATE1, ListUtil.transArrayList(cate1List));
    }

    //############################
    //############################

    public void putSeachHistory(SearchHistory seachHistory) {
        List<SearchHistory> seachHistorys = getSeachHistory();
        SearchHistory.removeExistHistory(seachHistorys, seachHistory);
        seachHistorys.add(0, seachHistory);
        aCache.put(KEY_SEARCH_HISTORY, ListUtil.transArrayList(seachHistorys));
    }

    public List<SearchHistory> getSeachHistory() {
        List<SearchHistory> searchHisorys = (List<SearchHistory>) aCache.getAsObject(KEY_SEARCH_HISTORY);
        if (searchHisorys == null) {
            return new ArrayList<>();
        } else {
            return (List<SearchHistory>) aCache.getAsObject(KEY_SEARCH_HISTORY);
        }
    }

    public void removeSeachHistory() {
        aCache.remove(KEY_SEARCH_HISTORY);
    }

}
