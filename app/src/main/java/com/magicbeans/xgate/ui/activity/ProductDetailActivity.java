package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.entity.Image;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.WebViewUtil;
import com.ins.common.view.BannerView;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Eva;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.bean.common.KeyValue;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeSale;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.dialog.DialogBottomProductAttr;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private BannerView banner;
    private WebView webview;
    private TextView btn_selectattr;

    private RecyclerView recycle_attr;
    private RecycleAdapterProductAttr adapterAttr;
    private RecyclerView recycle_eva;
    private RecycleAdapterEva adapterEva;

    private DialogBottomProductAttr dialogBottomProductAttr;

    public static void start(Context context) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        context.startActivity(intent);
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, SuggestActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
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
        dialogBottomProductAttr = new DialogBottomProductAttr(this);
    }

    private void initView() {
        banner = (BannerView) findViewById(R.id.banner);
        webview = (WebView) findViewById(R.id.webview);
        btn_selectattr = (TextView) findViewById(R.id.btn_selectattr);
        recycle_attr = (RecyclerView) findViewById(R.id.recycle_attr);
        recycle_eva = (RecyclerView) findViewById(R.id.recycle_eva);
        btn_selectattr.setOnClickListener(this);
    }

    private void initCtrl() {
        banner.showTitle(false);
        banner.setOnLoadImgListener(new BannerView.OnLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, R.drawable.default_bk_img, imgurl);
            }
        });

        adapterAttr = new RecycleAdapterProductAttr(this);
        recycle_attr.setNestedScrollingEnabled(false);
        recycle_attr.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycle_attr.addItemDecoration(new ItemDecorationDivider(this));
        recycle_attr.setAdapter(adapterAttr);

        adapterEva = new RecycleAdapterEva(this);
        adapterEva.setNeedRecomment(false);
        recycle_eva.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycle_eva.addItemDecoration(new ItemDecorationDivider(this));
        recycle_eva.setNestedScrollingEnabled(false);
        recycle_eva.setAdapter(adapterEva);

        WebViewUtil.initWebSetting(webview);
        webview.loadUrl("https://www.bing.com");
    }

    private void initData() {
        //banner初始化假数据
        List<Image> banners = new ArrayList<Image>() {{
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970104&di=3dca564f29823ca3acc9f3d49912999e&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F09%2F17%2F11b58PICZY4_1024.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970810&di=acc3f88cf0c20cce56bece96d2caf1e0&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F09%2F96%2F71h58PICpki_1024.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970810&di=e38e7c05a91bd90e19ffcc3a9efbbbe5&imgtype=0&src=http%3A%2F%2Ffile06.16sucai.com%2F2016%2F0329%2Fcf5937b18b18e57b41452565c2f7d70f.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970809&di=0b3bee4de28f75003abda588f188436e&imgtype=0&src=http%3A%2F%2Fpic4.nipic.com%2F20090904%2F2476235_221737303068_2.jpg"));
        }};
        banner.setDatas(banners);

        adapterAttr.getResults().clear();
        adapterAttr.getResults().add(new KeyValue("功效", "抗氧化 保湿 净化排毒 舒缓 清爽"));
        adapterAttr.getResults().add(new KeyValue("适合肤质", "干性肌肤 混合肤质"));
        adapterAttr.getResults().add(new KeyValue("品牌", "Aesop/伊索"));
        adapterAttr.notifyDataSetChanged();

        adapterEva.getResults().clear();
        adapterEva.getResults().add(new Eva(new ArrayList<BundleImgEntity>() {{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapterEva.getResults().add(new Eva(new ArrayList<BundleImgEntity>() {{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapterEva.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectattr:
                dialogBottomProductAttr.show();
                break;
        }
    }
}
