package com.magicbeans.xgate.utils;

import com.github.promeg.pinyinhelper.Pinyin;
import com.ins.common.common.CharacterParser;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.common.SortBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liaoinstan on 2017/5/3.
 */

public class SortUtil {

    /**
     * 对数据进行排序
     *
     * @param list 要进行排序的数据源
     */
    public static <T extends SortBean> void sortData(List<T> list) {
        if (list == null || list.size() == 0) return;
        for (int i = 0; i < list.size(); i++) {
            SortBean bean = list.get(i);
            String tag = Pinyin.toPinyin(bean.getSortName().substring(0, 1).charAt(0)).substring(0, 1);
            if (tag.matches("[A-Z]")) {
                bean.setSortTag(tag);
            } else {
                bean.setSortTag("#");
            }
        }
        Collections.sort(list, new Comparator<SortBean>() {
            @Override
            public int compare(SortBean o1, SortBean o2) {
                if ("#".equals(o1.getSortTag())) {
                    return 1;
                } else if ("#".equals(o2.getSortTag())) {
                    return -1;
                } else {
                    if (o1.getSortTag().equals(o2.getSortTag())) {
                        //如果2个对象的SortTag一样，那么名称和sortTag一至的对象小（排在前面，例如：A < 啊）
                        if (StrUtil.isEqualsNoCase(o1.getSortName(), o1.getSortTag())) {
                            return -1;
                        } else if (StrUtil.isEqualsNoCase(o2.getSortName(), o2.getSortTag())) {
                            return 1;
                        }
                    }
                    return o1.getSortTag().compareTo(o2.getSortTag());
                }
            }
        });
    }

    //从联系人中获取tag标记的位置，如果未获取到，返回-1
    public static <T extends SortBean> int getPosByTag(List<T> results, String tag) {
        if (!StrUtil.isEmpty(results) || !StrUtil.isEmpty(tag)) {
            for (int i = 0; i < results.size(); i++) {
                if (tag.equals(results.get(i).getSortTag())) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @param beans 数据源
     * @return tags 返回一个包含所有Tag字母在内的字符串
     */
    public static <T extends SortBean> String getTags(List<T> beans) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < beans.size(); i++) {
            if (!builder.toString().contains(beans.get(i).getSortTag())) {
                builder.append(beans.get(i).getSortTag());
            }
        }
        return builder.toString();
    }

    /**
     * 返回所有实体的tag集合
     */
    public static <T extends SortBean> List<String> getTagsArr(List<T> beans) {
        ArrayList<String> tagsArr = new ArrayList<>();
        if (!StrUtil.isEmpty(beans)) {
            for (SortBean bean : beans) {
                tagsArr.add(bean.getSortTag());
            }
        }
        return tagsArr;
    }

    /**
     * 匹配一个SortBean是否匹配，并设置其Html高亮文字
     */
    public static boolean match(SortBean sortbean, String filterStr) {
        boolean isMatch = false;
        String name = sortbean.getSortName();
        int sellingCount = matchText(name, filterStr);
        if (sellingCount != 0) {
            isMatch = true;
            sortbean.setSortNameHtml("<font color='#2f76b8'><b>" + name.substring(0, sellingCount) + "</b></font>" + name.substring(sellingCount));
        }
        int index = name.toLowerCase().indexOf(filterStr.toLowerCase().toString());
        int length = filterStr.length();
        if (index != -1) {
            isMatch = true;
            sortbean.setSortNameHtml(name.substring(0, index) + "<font color='#2f76b8'><b>" + filterStr + "</b></font>" + name.substring(index + length));
        }
        return isMatch;
    }

    /**
     * 按拼音匹配字符串，返回匹配的字符的个数
     * 例如：安其拉  --->   anqi  返回2
     */
    private static int matchText(String name, String filterStr) {
        int sellingcount = 0;
        String[] sellingArray = CharacterParser.getInstance().getSellingArray(name);
        for (String selling : sellingArray) {
            if ("".equals(filterStr)) {
                return sellingcount;
            }
            if (filterStr.startsWith(selling)) {
                sellingcount++;
                filterStr = filterStr.substring(selling.length(), filterStr.length());
            } else if (selling.startsWith(filterStr)) {
                sellingcount++;
                return sellingcount;
            } else {
                return 0;
            }
        }
        return sellingcount;
    }
}
