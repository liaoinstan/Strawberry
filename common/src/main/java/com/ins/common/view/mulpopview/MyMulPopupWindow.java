package com.ins.common.view.mulpopview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.ins.common.R;
import com.ins.common.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyMulPopupWindow extends PopupWindow {

    private Context context;

    private ListView firstListView;
    private ListView subListView;
    private ListView thirdListView;
    private MyAdapter firstAdapter;
    private MyAdapter subAdapter;
    private MyAdapter thirdAdapter;

    private View shadowView;
    private boolean needanim = true;

    private Map<String, Map<String, List<String>>> resutls;

    //    int images[] = new int[]{R.drawable.ic_category_0, R.drawable.ic_category_10, R.drawable.ic_category_30, R.drawable.ic_category_20
//            , R.drawable.ic_category_60, R.drawable.ic_category_50, R.drawable.ic_category_45, R.drawable.ic_category_50, R.drawable.ic_category_70,
//            R.drawable.ic_category_65, R.drawable.ic_category_80};
    String strs[] = new String[]{"全部频道", "美食", "休闲娱乐", "购物"};

    int images[] = null;

    public MyMulPopupWindow(final Context context) {
        this.context = context;

        //加载布局
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(inflater.inflate(R.layout.pop_mul, null));

        //获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int w = outMetrics.widthPixels;
        int h = outMetrics.heightPixels;

        // 设置SelectPicPopupWindow的View
        this.setContentView(getContentView());
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setHeight(DensityUtil.dp2px(context, 200));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);

        resutls = initTestData();
        initBase();
    }

    public void setResutls(Map<String, Map<String, List<String>>> resutls) {
        this.resutls = resutls;
        firstAdapter.setResults(getFirstResults());
        firstAdapter.notifyDataSetInvalidated();
    }

    private void initBase() {
        firstListView = (ListView) getContentView().findViewById(R.id.listView);
        subListView = (ListView) getContentView().findViewById(R.id.subListView);
        thirdListView = (ListView) getContentView().findViewById(R.id.thirdListView);

        //一级列表初始化数据
        firstAdapter = new MyAdapter(context, 1);
        firstAdapter.setResults(getFirstResults());
        firstAdapter.setSelectedPosition(-1);
        firstAdapter.notifyDataSetInvalidated();
        firstListView.setAdapter(firstAdapter);

        //二级列表初始化数据
        subAdapter = new MyAdapter(context, 2);
        subListView.setAdapter(subAdapter);

        //二级列表初始化数据
        thirdAdapter = new MyAdapter(context, 3);
        thirdListView.setAdapter(thirdAdapter);

        firstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                firstAdapter.setSelectedPosition(position);
                firstAdapter.notifyDataSetInvalidated();

                subAdapter.setSelectedPosition(-1);
                subAdapter.setResults(getSubResults(firstAdapter.getSelectedValue()));
                subAdapter.notifyDataSetChanged();

                thirdAdapter.getResults().clear();
                thirdAdapter.notifyDataSetChanged();

                //回调借口
                if (position == 0 && onPopClickListenner != null) {
                    onPopClickListenner.OnPopClick(firstAdapter.getSelectedValue(), subAdapter.getSelectedValue(), thirdAdapter.getSelectedValue());
                }
            }
        });

        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                subAdapter.setSelectedPosition(position);
                subAdapter.notifyDataSetInvalidated();

                thirdAdapter.setSelectedPosition(-1);
                thirdAdapter.setResults(getThirdResults(firstAdapter.getSelectedValue(), subAdapter.getSelectedValue()));
                thirdAdapter.notifyDataSetChanged();

                //回调借口
                if (position == 0 && onPopClickListenner != null) {
                    onPopClickListenner.OnPopClick(firstAdapter.getSelectedValue(), subAdapter.getSelectedValue(), thirdAdapter.getSelectedValue());
                }
            }
        });

        thirdListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                thirdAdapter.setSelectedPosition(position);
                thirdAdapter.notifyDataSetInvalidated();

                //回调借口
                if (onPopClickListenner != null) {
                    onPopClickListenner.OnPopClick(firstAdapter.getSelectedValue(), subAdapter.getSelectedValue(), thirdAdapter.getSelectedValue());
                }
            }
        });
    }

    /**
     * 显示popupWindow
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 0);
            if (needanim) turnBackgroundDark();
        } else {
            this.dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (needanim) resetBackground();
    }


    private Map<String, Map<String, List<String>>> initTestData() {
        Map<String, Map<String, List<String>>> resultMap = new TreeMap<>();

//        Map<String, List<String>> submap1 = new TreeMap<>();
//        submap1.put("成都市", Arrays.asList(new String[]{"武侯区", "锦江区", "青羊区", "金牛区", "成华区", "龙泉驿区", "新都区", "温江区", "青白江区"}));
//        submap1.put("绵阳市", Arrays.asList(new String[]{"涪城区", "游仙区", "高新区", "南郊经开区", "农科区", "科教创业园区"}));
//        submap1.put("自贡市", Arrays.asList(new String[]{"飞机区", "大象区"}));
//        submap1.put("南充市", Arrays.asList(new String[]{"顺庆区", "高坪区", "嘉陵区"}));
//
//        Map<String, List<String>> submap2 = new TreeMap<>();
//        submap2.put("广州市", Arrays.asList(new String[]{"荔湾区", "越秀区", "海珠区", "天河区", "白云区", "黄埔区", "增城区", "花都区", "从化区", "番禺区", "南沙区"}));
//        submap2.put("汕头市", Arrays.asList(new String[]{"潮阳区", "潮南区", "金平区", "龙湖区", "澄海区", "濠江区"}));
//        submap2.put("佛山市", Arrays.asList(new String[]{"顺德区", "禅城区", "南海区", "高明区", "三水区"}));
//        submap2.put("东莞市", Arrays.asList(new String[]{"东城区", "南城区", "莞城区", "万江区"}));
//
//        Map<String, List<String>> submap3 = new TreeMap<>();
//        submap3.put("重庆市", Arrays.asList(new String[]{"万州区", "涪陵区", "渝中区", "大渡口区", "江北区", "沙坪坝区", "九龙坡区", "南岸区", "北碚区", "万盛区", "双桥区", "渝北区", "巴南区", "黔江区", "长寿区"}));
//
//        resultMap.put("四川", submap1);
//        resultMap.put("广东", submap2);
//        resultMap.put("重庆", submap3);


        String json = "{'四川':{'成都市':['青羊区','成华区'],'绵阳市':['南街区','邵鑫区']},'重庆':{'重庆市':['渝中区','城北区']}}";
//        String json = "{'全部':{'全部':['全部']}}";
        resultMap = new Gson().fromJson(json, resultMap.getClass());

        return resultMap;
    }

    private List<String> getFirstResults() {
        Set<String> keys = resutls.keySet();
        ArrayList<String> resutlts = new ArrayList<>(keys);
        resutlts.add(0, "全部");
        return resutlts;
    }

    private List<String> getSubResults(String key) {
        Map<String, List<String>> submap = resutls.get(key);
        if (submap != null) {
            Set<String> keys = submap.keySet();
            ArrayList<String> resutlts = new ArrayList<>(keys);
            resutlts.add(0, "全部");
            return resutlts;
        } else {
            return new ArrayList<>();
        }
    }

    private List<String> getThirdResults(String firstkey, String subkey) {
        Map<String, List<String>> submap = resutls.get(firstkey);
        List<String> list = submap.get(subkey);
        if (list != null) {
            ArrayList<String> resutlts = new ArrayList<>(list);
            resutlts.add(0, "全部");
            return resutlts;
        } else {
            return new ArrayList<>();
        }
    }

    ////////////////////
    /////对外接口
    ////////////////////

    public static String getSelectCity(String province, String city, String disrict) {
        String ret = "中国";
        if (!"全部".equals(province)) {
            ret += "," + province;
            if (!"全部".equals(city)) {
                ret += "," + city;
                if (!"全部".equals(disrict)) {
                    ret += "," + disrict;
                }
            }
        }
        return ret;
    }

    private OnPopClickListenner onPopClickListenner;

    public void setOnPopClickListenner(OnPopClickListenner onPopClickListenner) {
        this.onPopClickListenner = onPopClickListenner;
    }

    public interface OnPopClickListenner {
        void OnPopClick(String province, String city, String district);
    }

    public void setShadowView(View shadowView) {
        this.shadowView = shadowView;
    }

    //////////////////////////
    ////////背景变暗
    //////////////////////////

    private void turnBackgroundDark() {
        if (shadowView!=null) {
            shadowView.setVisibility(View.VISIBLE);
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(300).start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (Float) animation.getAnimatedValue();
                    shadowView.setAlpha(alpha);
                }
            });
        }else {
            final Activity activity = (Activity) context;
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.7f);
            animator.setDuration(300).start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                    params.alpha = (Float) animation.getAnimatedValue();
                    activity.getWindow().setAttributes(params);
                }
            });
        }
    }

    private void resetBackground() {
        if (shadowView!=null) {
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
            animator.setDuration(200).start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (Float) animation.getAnimatedValue();
                    shadowView.setAlpha(alpha);
                }
            });
        }else {
            final Activity activity = (Activity) context;
            ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1f);
            animator.setDuration(200).start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                    params.alpha = (Float) animation.getAnimatedValue();
                    activity.getWindow().setAttributes(params);
                }
            });
        }
    }
}
