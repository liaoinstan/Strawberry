package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.PopBean;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayHomeClearBinding;
import com.magicbeans.xgate.databinding.LayProductlistSortBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ProductActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeSingle;
import com.magicbeans.xgate.ui.dialog.MyGridPopupWindow;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductListSortController implements View.OnClickListener{

    private Context context;
    private LayProductlistSortBinding binding;

    private MyGridPopupWindow pop_brand;
    private MyGridPopupWindow pop_cate;
    private MyGridPopupWindow pop_skin;

    public ProductListSortController(LayProductlistSortBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        pop_brand = new MyGridPopupWindow(context);
        pop_cate = new MyGridPopupWindow(context);
        pop_skin = new MyGridPopupWindow(context);

        binding.radioBrand.setOnClickListener(this);
        binding.radioCate.setOnClickListener(this);
        binding.radioSkin.setOnClickListener(this);
    }

    public void initData() {
        //设置品牌测试数据
        pop_brand.setResults(new ArrayList<PopBean>() {{
            add(new PopBean("雅诗兰黛"));
            add(new PopBean("百雀羚"));
            add(new PopBean("自然堂"));
            add(new PopBean("温碧泉"));
            add(new PopBean("珀莱雅"));
            add(new PopBean("欧诗漫"));
            add(new PopBean("大宝"));
            add(new PopBean("郁美净"));
            add(new PopBean("康婴健"));
            add(new PopBean("哈罗闪"));
            add(new PopBean("加菲猫"));
            add(new PopBean("爱护"));
            add(new PopBean("施巴"));
            add(new PopBean("法贝儿"));
            add(new PopBean("露安适"));
            add(new PopBean("御泥坊"));
            add(new PopBean("美加净"));
            add(new PopBean("贝得力"));
            add(new PopBean("努比"));
            add(new PopBean("保黛宝"));
            add(new PopBean("韩熙贞"));
            add(new PopBean("巴斯克林"));
            add(new PopBean("瓷肌"));
            add(new PopBean("小树苗"));
            add(new PopBean("品迈"));
            add(new PopBean("小白熊"));
            add(new PopBean("婴姿坊"));
            add(new PopBean("康贝"));
            add(new PopBean("牛尔"));
            add(new PopBean("添乐"));
            add(new PopBean("宝宝金水"));
        }});
        //设置分类测试数据
        pop_cate.setResults(new ArrayList<PopBean>() {{
            add(new PopBean("洁面"));
            add(new PopBean("精华"));
            add(new PopBean("妆前乳"));
            add(new PopBean("护理"));
            add(new PopBean("磨砂"));
            add(new PopBean("去角质"));
            add(new PopBean("面膜"));
            add(new PopBean("BB/CC"));
            add(new PopBean("爽肤/喷雾"));
            add(new PopBean("乳液"));
        }});
        //设置肤质测试数据
        pop_skin.setResults(new ArrayList<PopBean>() {{
            add(new PopBean("油性"));
            add(new PopBean("混合性"));
            add(new PopBean("干性"));
            add(new PopBean("任何肤质"));
            add(new PopBean("混合偏油"));
            add(new PopBean("中性偏干"));
            add(new PopBean("敏感性"));
            add(new PopBean("其他"));
        }});
    }

    public void setShadowView(View shadow){
        pop_brand.setShadowView(shadow);
        pop_cate.setShadowView(shadow);
        pop_skin.setShadowView(shadow);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_brand:
                pop_brand.showPopupWindow(v);
                break;
            case R.id.radio_cate:
                pop_cate.showPopupWindow(v);
                break;
            case R.id.radio_skin:
                pop_skin.showPopupWindow(v);
                break;
        }
    }
}
