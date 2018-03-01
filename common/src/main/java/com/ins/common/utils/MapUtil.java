package com.ins.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liaoinstan on 2017/8/11.
 */

public class MapUtil {

    //把一个Map集合转换为HashMap
    public static <K, T> HashMap<K, T> transHashMap(Map<K, T> fromMap) {
        if (fromMap instanceof HashMap) {
            return (HashMap<K, T>) fromMap;
        } else {
            HashMap<K, T> toMap = new HashMap<>();
            for (Map.Entry<K, T> entry : fromMap.entrySet()) {
                toMap.put(entry.getKey(), entry.getValue());
            }
            return toMap;
        }
    }
}
