package com.magicbeans.xgate.utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017/5/18.
 */

public class ColorUtil {

    //预设颜色列表，可随意添加，颜色越多重复几率越小
    public static List<Integer> colors = new ArrayList<Integer>() {{
        add(Color.parseColor("#EC5745"));
        add(Color.parseColor("#377caf"));
        add(Color.parseColor("#4ebcd3"));
        add(Color.parseColor("#6fb30d"));
        add(Color.parseColor("#FFA500"));
        add(Color.parseColor("#bf9e5a"));
    }};

    /**
     * 使用种子获取一个伪随机颜色
     */
    public static int getRandomColorBySeed(int seed) {
//        Random random = new Random(seed);
//        int i = random.nextInt(colors.size());
//        return colors.get(i);
        return colors.get(seed % colors.size());
    }
}

