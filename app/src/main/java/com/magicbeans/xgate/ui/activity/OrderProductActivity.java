package com.magicbeans.xgate.ui.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.StrUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.data.db.manager.HistoryTableManager;
import com.magicbeans.xgate.databinding.ActivityHistoryBinding;
import com.magicbeans.xgate.databinding.ActivityOrderProductBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.helper.SpringViewHelper;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHistory;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterOrderProduct;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.List;

public class OrderProductActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private ActivityOrderProductBinding binding;
    private RecycleAdapterOrderProduct adapter;
    private List<ShopCart> goods;

    public static void start(Context context, List<ShopCart> goods) {
        if (AppHelper.User.isLogin()) {
            Intent intent = new Intent(context, OrderProductActivity.class);
            intent.putExtra("goods", ListUtil.transArrayList(goods));
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_product);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        goods = (List<ShopCart>) getIntent().getSerializableExtra("goods");
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterOrderProduct(this);
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
        SpringViewHelper.initSpringViewForTest(binding.spring);
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void initData() {
        adapter.getResults().clear();
        adapter.getResults().addAll(goods);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        ShopCart shopCart = adapter.getResults().get(viewHolder.getLayoutPosition());
        ProductDetailActivity.start(this, shopCart.getProdID());
    }
}
