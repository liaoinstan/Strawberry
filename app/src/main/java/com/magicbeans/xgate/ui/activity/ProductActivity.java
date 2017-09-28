package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.PopBean;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.databinding.ActivityProductBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProduct;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.dialog.MyGridPopupWindow;

import java.util.ArrayList;

public class ProductActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private ActivityProductBinding binding;
    private RecycleAdapterProduct adapter;
    private RecyclerView.ItemDecoration decorationList;
    private MyGridPopupWindow pop_brand;
    private MyGridPopupWindow pop_cate;
    private MyGridPopupWindow pop_skin;

    public static void start(Context context) {
        Intent intent = new Intent(context, ProductActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        setToolbar(false);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        decorationList = new ItemDecorationDivider(this, ItemDecorationDivider.VERTICAL_LIST);
        pop_brand = new MyGridPopupWindow(this);
        pop_cate = new MyGridPopupWindow(this);
        pop_skin = new MyGridPopupWindow(this);
        pop_brand.setShadowView(binding.shadow);
        pop_cate.setShadowView(binding.shadow);
        pop_skin.setShadowView(binding.shadow);
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterProduct(this);
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.recycler.addItemDecoration(decorationList);
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initData() {
        //设置列表测试数据
        adapter.getResults().clear();
        for (int i = 0; i < 35; i++) {
            adapter.getResults().add(new TestBean());
        }
        adapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        ProductDetailActivity.start(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                if (binding.recycler.getLayoutManager() instanceof GridLayoutManager) {
                    //设置为列表样式
                    binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    binding.recycler.addItemDecoration(decorationList);
                    adapter.setGridMode(false);
                    adapter.notifyDataSetChanged();
                } else {
                    //设置为网格样式
                    binding.recycler.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
                    binding.recycler.removeItemDecoration(decorationList);
                    adapter.setGridMode(true);
                    adapter.notifyDataSetChanged();
                }
                break;
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
