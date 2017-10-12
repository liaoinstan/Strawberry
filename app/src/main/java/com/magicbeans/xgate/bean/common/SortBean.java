package com.magicbeans.xgate.bean.common;

import android.text.Html;
import android.text.TextUtils;

import com.ins.common.entity.BaseSelectBean;

/**
 * by liaoinstan
 * 排序列表实体基类，提供基础字段，这些字段必须存在，其余需求增加字段可以基础该类进行拓展
 */

public class SortBean extends BaseSelectBean {

    //根据name解析出的首字母，例如（'廖'->'l'）
    private String sortTag;
    //名称，也是选择实体的唯一选择依据
    private String sortName;
    //存储名称的有色html字符，（用户查找匹配的时候匹配部分文字要变为高亮，未匹配部分原色，为实现这种复杂颜色字符串，这里采用html文本添加color标签处理，也可以使用SpanableString）
    private String sortNameHtml;

    public SortBean(String sortName) {
        this.sortName = sortName;
    }

    public String getSortTag() {
        return sortTag;
    }

    public void setSortTag(String sortTag) {
        this.sortTag = sortTag;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortNameHtml() {
        return sortNameHtml;
    }

    public void setSortNameHtml(String sortNameHtml) {
        this.sortNameHtml = sortNameHtml;
    }

    public CharSequence getSortNameSmart() {
        if (!TextUtils.isEmpty(sortNameHtml)){
            return Html.fromHtml(sortNameHtml);
        }else {
            return sortName;
        }
    }
}
