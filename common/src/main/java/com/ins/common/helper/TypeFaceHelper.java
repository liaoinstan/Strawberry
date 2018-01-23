package com.ins.common.helper;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/23.
 * 管理字体集合，字体对象在APP中只应该存在一份单例，使用map来管理这些字体对象
 */

public class TypeFaceHelper {

    private static TypeFaceHelper instance;
    private Context context;

    private TypeFaceHelper(Context context) {
        this.context = context;
    }

    public static synchronized TypeFaceHelper with(Context context) {
        if (instance == null) instance = new TypeFaceHelper(context);
        return instance;
    }

    private Map<String, Typeface> map = new HashMap<>();

    /////////////////////////////////////

    public Typeface getTypeFace(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), name);
            map.put(name, typeface);
            return typeface;
        }
    }
}
