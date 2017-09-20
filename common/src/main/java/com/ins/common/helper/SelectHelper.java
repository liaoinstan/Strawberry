package com.ins.common.helper;

import com.ins.common.entity.BaseSelectBean;
import com.ins.common.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan on 2017/6/13.
 *
 * 开发帮助：
 * 什么是"选择实体"（BaseSelectBean）？
 * BaseSelectBean是一个实体类，里面只有一个字段：isSelect （是否被选择）
 * APP有很多类型需求：列表网格等，支持单选多选，我们的列表每个item对应的实体对象都应该有一个字段保存该item是否被选择，这些实体都应该继承BaseSelectBean来实现统一处理
 * 这样我们就可以使用泛型来封装一系列处理选中状态的方法
 *
 * 注意：
 * 1：如果你的实体对象需要继承别的对象，别这样，直接让BaseSelectBean继承它，或者它继承BaseSelectBean就好了，反正BaseSelectBean很简单
 * 2：如果非要只继承其中一个，可以考虑把BaseSelectBean抽象成一个接口，提供isSelect和setSelect的虚方法就成，目前没有发现有这种需求的场景
 * 3：有些实现单选多选不用实体存储选择状态，而是在Adapter中保存被选中的id，这样做很不好，为什么要让数据实体的异构性体现在adapter中
 */

public class SelectHelper {

    //全选、撤销全选选择实体列表
    public static <T extends BaseSelectBean> void selectAllSelectBeans(List<T> selectBeans, boolean select) {
        if (StrUtil.isEmpty(selectBeans)) {
            return;
        }
        for (BaseSelectBean selectBean : selectBeans) {
            selectBean.setSelect(select);
        }
    }

    //检查选择实体列表是否已经全选
    public static <T extends BaseSelectBean> boolean isSelectAll(List<T> selectBeans) {
        if (StrUtil.isEmpty(selectBeans)) {
            return false;
        }
        for (BaseSelectBean selectBean : selectBeans) {
            if (!selectBean.isSelect()) {
                return false;
            }
        }
        return true;
    }

    //获取已被选择的选择实体（单选）
    public static <T extends BaseSelectBean> T getSelectBean(List<T> selectBeans) {
        if (StrUtil.isEmpty(selectBeans)) {
            return null;
        }
        for (T selectBean : selectBeans) {
            if (selectBean.isSelect()) {
                return selectBean;
            }
        }
        return null;
    }

    //获取已被选择的选择实体（多选）
    public static <T extends BaseSelectBean> List<T> getSelectBeans(List<T> selectBeans) {
        ArrayList<T> ts = new ArrayList<>();
        if (!StrUtil.isEmpty(selectBeans)) {
            for (T selectBean : selectBeans) {
                if (selectBean.isSelect()) {
                    ts.add(selectBean);
                }
            }
        }
        return ts;
    }
}
