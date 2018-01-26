package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioGroup;

import com.ins.common.helper.ToobarTansColorHelper;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.ScrollViewUtil;
import com.ins.common.view.ObservableNestedScrollView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.data.db.manager.HistoryTableManager;
import com.magicbeans.xgate.databinding.ActivityProductdetailBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.CommonRecommendController;
import com.magicbeans.xgate.ui.controller.ProductDetailAttrController;
import com.magicbeans.xgate.ui.controller.ProductDetailBottombarController;
import com.magicbeans.xgate.ui.controller.ProductDetailDescribeController;
import com.magicbeans.xgate.ui.controller.ProductDetailEvaController;
import com.magicbeans.xgate.ui.controller.ProductDetailNameBoradController;
import com.magicbeans.xgate.ui.controller.ToolbarProdDetailController;
import com.magicbeans.xgate.ui.dialog.DialogBottomProductAttr;

import java.util.Map;

public class ProductDetailActivity extends BaseAppCompatActivity {

    private ActivityProductdetailBinding binding;

    private ToolbarProdDetailController toolbarProdDetailController;
    private ProductDetailNameBoradController productDetailNameBoradController;
    private ProductDetailAttrController productDetailAttrController;
    private ProductDetailEvaController productDetailEvaController;
    private ProductDetailDescribeController productDetailDescribeController;
    private CommonRecommendController commonRecommendController;
    private ProductDetailBottombarController productDetailBottombarController;

    private String prodId;
    private ProductDetail productDetail;

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
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_REFRESH_SHOPCART:
                //收到刷新购物车的消息
                productDetailBottombarController.refreshShopCount();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productdetail);
        StatusBarTextUtil.transparencyBar(this);
        StatusBarTextUtil.StatusBarLightMode(this);
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        prodId = getIntent().getStringExtra("prodId");

        //初始化各部分控制器
        toolbarProdDetailController = new ToolbarProdDetailController(binding.includeToobbarProductdetail);
        productDetailNameBoradController = new ProductDetailNameBoradController(binding.includeNameboard);
        productDetailAttrController = new ProductDetailAttrController(binding.includeAttr);
        productDetailEvaController = new ProductDetailEvaController(binding.includeEva, prodId);
        productDetailDescribeController = new ProductDetailDescribeController(binding.includeDescribe);
        commonRecommendController = new CommonRecommendController(binding.includeRecomend, 6);
        productDetailBottombarController = new ProductDetailBottombarController(binding.includeBottombar, binding.getRoot());
        //设置商品品类选择监听
        productDetailAttrController.setOnSelectListenner(new DialogBottomProductAttr.OnSelectListenner() {
            @Override
            public void onSelect(Product2 product2) {
                productDetail.setProdID(product2.getProdID());
                setData(productDetail);
            }
        });
        //设置tab点击事件
        toolbarProdDetailController.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!radioGroup.findViewById(i).isPressed()) return;
                switch (i) {
                    case R.id.radio_product:
                        ScrollViewUtil.scrollToTop(binding.scrollView);
                        break;
                    case R.id.radio_recommend:
                        ScrollViewUtil.scrollTo(binding.scrollView, getTopHightInScroll(productDetailEvaController.getRootView()));
                        break;
                }
            }
        });
    }

    private void initView() {
    }

    private void initCtrl() {
        //设置scrollView滚动监听
        binding.scrollView.setOnScrollChangedListener(new ObservableNestedScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                //banner动态位置偏移
                binding.includeNameboard.banner.setTranslationY(y / 2);
                //toolbar动态透明渐变
                ToobarTansColorHelper.with(binding.includeToobbarProductdetail.toolbar)
                        .initMaxHeight(DensityUtil.dp2px(300))
                        .initColorStart(Color.parseColor("#00ffffff"))
                        .initColorEnd(ContextCompat.getColor(ProductDetailActivity.this, R.color.white))
                        .start(y);
                //根据滚动位置反向设置tab切换
                int hightRecommend = getTopHightInScroll(productDetailEvaController.getRootView());
                toolbarProdDetailController.setTabByScrollHeight(hightRecommend, y, oldy);
            }
        });
    }

    private void initData() {
        netProductDetail();
    }

    private void setData(ProductDetail productDetail) {
        productDetailNameBoradController.setData(productDetail);
        productDetailAttrController.setData(productDetail);
        productDetailDescribeController.setData(productDetail);
        productDetailBottombarController.setData(productDetail);
    }

    //获取某个在scrollView内部的View距离顶部的距离，用于切换tab进行滚动跳转
    private int getTopHightInScroll(View viewin) {
        return ScrollViewUtil.cacuHightByView(binding.scrollView, viewin) - toolbarProdDetailController.getHeight();
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
                ProductDetailActivity.this.productDetail = productDetail;
                setData(productDetail);
                //获取到商品详情，即表示该商品已经浏览过了，加入浏览记录中
                HistoryTableManager.getInstance().insert(productDetail.trans2Product());
                dismissLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog();
            }
        });
    }
}
