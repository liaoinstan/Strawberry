package com.magicbeans.xgate.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.ins.common.utils.SharedPrefUtilV2;
import com.magicbeans.xgate.bean.category.Cate2Wrap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 * 运行时数据缓存，存放于内存中，退出APP后清空
 */

public class CateRuntimeCache {

    //##############单例##############//

    private static CateRuntimeCache INSTANCE = new CateRuntimeCache();

    private CateRuntimeCache() {
    }

    public static CateRuntimeCache getInstance() {
        return INSTANCE;
    }

    //############################//

    private Map<String, Cate2Wrap> cacheMap = new HashMap<>();

    public void putCache(String CatgId, Cate2Wrap data) {
        cacheMap.put(CatgId, data);
    }

    public Cate2Wrap getCache(String CatgId) {
        return cacheMap.get(CatgId);
    }
}
