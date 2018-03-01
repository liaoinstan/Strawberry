package com.magicbeans.xgate.bean.search;

import com.google.gson.Gson;
import com.magicbeans.xgate.bean.category.Cate1;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SearchHistory implements Serializable {

    public static final int TYPE_STRING = 0;
    public static final int TYPE_CATE1 = 1;

    private int historyType;

    private String searchKey;
    private Cate1 cate1;
    private String tag;

    public SearchHistory(Cate1 cate1) {
        this.cate1 = cate1;
        historyType = TYPE_CATE1;
        tag = "热门搜索";
    }

    public SearchHistory(String searchKey) {
        this.searchKey = searchKey;
        historyType = TYPE_STRING;
    }

    //#############################

    public static void removeExistHistory(List<SearchHistory> searchHistoryList, SearchHistory searchHistory) {
        Iterator<SearchHistory> iterator = searchHistoryList.iterator();
        while (iterator.hasNext()) {
            SearchHistory next = iterator.next();
            if (next.equals(searchHistory)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(this).equals(gson.toJson(obj));
    }

    //#############################

    public int getHistoryType() {
        return historyType;
    }

    public void setHistoryType(int historyType) {
        this.historyType = historyType;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Cate1 getCate1() {
        return cate1;
    }

    public void setCate1(Cate1 cate1) {
        this.cate1 = cate1;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
