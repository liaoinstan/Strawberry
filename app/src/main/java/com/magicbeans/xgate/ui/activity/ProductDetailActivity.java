package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.WebViewUtil;
import com.ins.common.view.BannerView;
import com.ins.common.view.BannerView2;
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
import com.magicbeans.xgate.ui.controller.ProductDetailAttrController;
import com.magicbeans.xgate.ui.controller.ProductDetailEvaController;
import com.magicbeans.xgate.ui.controller.ProductDetailNameBoradController;
import com.magicbeans.xgate.ui.dialog.DialogBottomProductAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityProductdetailBinding binding;

    private ProductDetailNameBoradController productDetailNameBoradController;
    private ProductDetailAttrController productDetailAttrController;
    private ProductDetailEvaController productDetailEvaController;

    private DialogBottomProductAttr dialogBottomProductAttr;

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
        setToolbar();
        toolbar.bringToFront();
        StatusBarTextUtil.transparencyBar(this);
        StatusBarTextUtil.StatusBarLightMode(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        prodId = getIntent().getStringExtra("prodId");
        productDetailNameBoradController = new ProductDetailNameBoradController(this);
        productDetailAttrController = new ProductDetailAttrController(binding.includeAttr);
        productDetailEvaController = new ProductDetailEvaController(binding.includeEva);
        dialogBottomProductAttr = new DialogBottomProductAttr(this);
    }

    private void initView() {
    }

    private void initCtrl() {
        productDetailNameBoradController.initCtrl();
        productDetailAttrController.initCtrl();
        productDetailEvaController.initCtrl();

        WebViewUtil.initWebSetting(binding.webview);
        binding.webview.loadUrl("https://www.baidu.com");
    }

    private void initData() {
        productDetailNameBoradController.initData();
        productDetailAttrController.initData();
        productDetailEvaController.initData(prodId);
        netProductDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectattr:
                dialogBottomProductAttr.show();
                break;
        }
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
                productDetailNameBoradController.setData(productDetail, prodId);
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
