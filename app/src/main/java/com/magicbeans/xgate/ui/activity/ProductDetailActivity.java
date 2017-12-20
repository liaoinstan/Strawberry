package com.magicbeans.xgate.ui.activity;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.entity.Image;
import com.ins.common.helper.ToobarTansColorHelper;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.ScrollViewUtil;
import com.ins.common.utils.viewutils.WebViewUtil;
import com.ins.common.view.BannerView;
import com.ins.common.view.BannerView2;
import com.ins.common.view.ObservableNestedScrollView;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.bean.common.KeyValue;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.ActivityProductdetailBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.CommonRecommendController;
import com.magicbeans.xgate.ui.controller.ProductDetailAttrController;
import com.magicbeans.xgate.ui.controller.ProductDetailDescribeController;
import com.magicbeans.xgate.ui.controller.ProductDetailEvaController;
import com.magicbeans.xgate.ui.controller.ProductDetailNameBoradController;
import com.magicbeans.xgate.ui.controller.ToolbarProdDetailController;
import com.magicbeans.xgate.ui.dialog.DialogBottomProductAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends BaseAppCompatActivity {

    private ActivityProductdetailBinding binding;

    private ToolbarProdDetailController toolbarProdDetailController;
    private ProductDetailNameBoradController productDetailNameBoradController;
    private ProductDetailAttrController productDetailAttrController;
    private ProductDetailEvaController productDetailEvaController;
    private ProductDetailDescribeController productDetailDescribeController;
    private CommonRecommendController commonRecommendController;

    private String prodId;

    //测试启动
    public static void start(Context context) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("prodId", "174755");
        context.startActivity(intent);
    }

    public static void start(Context context, String prodId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("prodId", prodId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productdetail);
        StatusBarTextUtil.transparencyBar(this);
        StatusBarTextUtil.StatusBarLightMode(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        prodId = getIntent().getStringExtra("prodId");

        toolbarProdDetailController = new ToolbarProdDetailController(binding.includeToobbarProductdetail);
        productDetailNameBoradController = new ProductDetailNameBoradController(binding.includeNameboard);
        productDetailAttrController = new ProductDetailAttrController(binding.includeAttr);
        productDetailEvaController = new ProductDetailEvaController(binding.includeEva, prodId);
        productDetailDescribeController = new ProductDetailDescribeController(binding.includeDescribe);
        commonRecommendController = new CommonRecommendController(binding.includeRecomend);


        toolbarProdDetailController.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_product:
                        ScrollViewUtil.scrollToTop(binding.scrollView);
                        break;
                    case R.id.radio_recommend:
                        ScrollViewUtil.scrollTo(binding.scrollView, productDetailEvaController.getRootView(), -toolbarProdDetailController.getHeight());
                        break;
                }
            }
        });
    }

    private void initView() {
    }

    private void initCtrl() {
        binding.scrollView.setOnScrollChangedListener(new ObservableNestedScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                //banner动态位置偏移
                binding.includeNameboard.banner.setTranslationY(y / 2);
                //toolbar动态透明渐变
                ToobarTansColorHelper.with(binding.includeToobbarProductdetail.toolbar)
                        .initMaxHeight(DensityUtil.dp2px(200))
                        .initColorStart(Color.parseColor("#00ffffff"))
                        .initColorEnd(ContextCompat.getColor(ProductDetailActivity.this, R.color.white))
                        .start(y);
            }
        });
    }

    private void initData() {
        netProductDetail();
    }

    private void netProductDetail() {
        Map<String, Object> param = new NetParam()
                .put("prodId", prodId)
                .put("currId", "CNY")
                .build();
        showLoadingDialog();
        NetApi.NI().netProductDetail(param).enqueue(new STCallback<ProductDetail>(ProductDetail.class) {
            @Override
            public void onSuccess(int status, ProductDetail productDetail, String msg) {
                productDetail.setProdID(prodId);
                productDetailNameBoradController.setData(productDetail);
                productDetailAttrController.setData(productDetail);
                productDetailDescribeController.setData(productDetail);
                hideLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
