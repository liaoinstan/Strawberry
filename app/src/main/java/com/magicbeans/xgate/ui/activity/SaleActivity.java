package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.ActivitySaleBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterSale;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class SaleActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private ActivitySaleBinding binding;
    private RecycleAdapterSale adapter;

    public static final int TYPE_TODAY = 0;     //今日秒杀
    public static final int TYPE_SALE = 1;      //特卖专场
    public static final int TYPE_SINGLE = 2;    //王牌单品
    public static final int TYPE_NEW = 3;       //新品上市
    public static final int TYPE_RECOMMED = 5;  //精品推荐
    public static final int TYPE_CLEAR = 6;     //清仓优惠
    public static final int TYPE_SELECT = 7;    //每日精选

    private Map<Integer, String> titles = new HashMap() {{
        put(TYPE_TODAY, "今日秒杀");
        put(TYPE_SALE, "特卖专场");
        put(TYPE_SINGLE, "王牌单品");
        put(TYPE_NEW, "新品上市");
        put(TYPE_RECOMMED, "精品推荐");
        put(TYPE_CLEAR, "清仓优惠");
        put(TYPE_SELECT, "每日精选");
    }};

    public int type;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_TODAY, TYPE_SALE, TYPE_SINGLE, TYPE_NEW, TYPE_RECOMMED, TYPE_CLEAR, TYPE_SELECT})
    @interface Type {
    }

    private int page = 1;

    //测试启动
    public static void start(Context context, @Type int type) {
        Intent intent = new Intent(context, SaleActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        type = getIntent().getIntExtra("type", TYPE_TODAY);
        setToolbar(titles.get(type));
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterSale(this);
        adapter.setOnItemClickListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netGetProductList(true);
            }
        });
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netGetProductList(false);
            }

            @Override
            public void onLoadmore() {
                netLoadmore();
            }
        });
    }

    private void initData() {
        netGetProductList(true);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Product product = adapter.getResults().get(position);
        ProductDetailActivity.start(this, product.getProdID());
    }

    public void netGetProductList(final boolean showLoading) {
        page = 1;
        Map<String, Object> param = new NetParam()
                .put("page", page)
                .build();
        if (showLoading) showLoadingDialog();
        Call<ResponseBody> call;
        switch (type) {
            case TYPE_TODAY:
                call = NetApi.NI().netHomeTodayList(param);     //今日秒杀
                break;
            case TYPE_SALE:
                call = NetApi.NI().netHomeSaleList(param);      //特卖专场
                break;
            case TYPE_SINGLE:
                call = NetApi.NI().netHomeSingleList(param);    //王牌单品
                break;
            case TYPE_NEW:
                call = NetApi.NI().netHomeNewList(param);       //新品上市
                break;
            case TYPE_RECOMMED:
                call = NetApi.NI().netHomeRecommendList(param); //精品推荐
                break;
            case TYPE_CLEAR:
                call = NetApi.NI().netHomeClearList(param);     //清仓优惠
                break;
            case TYPE_SELECT:
                call = NetApi.NI().netHomeSelectList(param);    //每日精选
                break;
            default:
                call = NetApi.NI().netHomeTodayList(param);     //默认：今日秒杀
                break;
        }
        call.enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                List<Product> products = bean.getProductList();
                if (!StrUtil.isEmpty(products)) {
                    adapter.getResults().clear();
                    adapter.getResults().addAll(products);
                    adapter.notifyDataSetChanged();
                } else {
                }
                if (showLoading) dismissLoadingDialog();
                binding.spring.onFinishFreshAndLoad();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                if (showLoading) dismissLoadingDialog();
                binding.spring.onFinishFreshAndLoad();
            }
        });
    }

    public void netLoadmore() {
        Map<String, Object> param = new NetParam()
                .put("page", page + 1)
                .build();
        NetApi.NI().netHomeRecommendList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                List<Product> products = bean.getProductList();
                if (!StrUtil.isEmpty(products)) {
                    page++;
                    adapter.getResults().addAll(products);
                    adapter.notifyDataSetChanged();
                    binding.spring.onFinishFreshAndLoad();
                } else {
                    binding.spring.onFinishFreshAndLoad();
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                binding.spring.onFinishFreshAndLoad();
            }
        });
    }
}
