package com.magicbeans.xgate.ui.controller;

import android.view.View;
import android.widget.ImageView;

import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.databinding.LayOrderaddAddressBinding;
import com.magicbeans.xgate.databinding.LayOrderaddProductsBinding;
import com.magicbeans.xgate.net.nethelper.NetAddressHelper;
import com.magicbeans.xgate.ui.activity.AddressActivity;
import com.magicbeans.xgate.ui.activity.AddressAddActivity;
import com.magicbeans.xgate.ui.activity.OrderProductActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class OrderAddProductsController extends BaseController<LayOrderaddProductsBinding> implements View.OnClickListener {

    private List<ShopCart> goods;

    public OrderAddProductsController(LayOrderaddProductsBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.layBundle.setOnClickListener(this);
        binding.bundleview.setOnBundleLoadImgListener(new BundleImgView.OnBundleLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, imgurl);
            }
        });

    }

    private void initData() {
    }

    public void setGoodsData(List<ShopCart> goods) {
        this.goods = goods;
        //设置商品图片列表数据
        ArrayList<BundleImgEntity> bundles = new ArrayList<>();
        for (ShopCart good : goods) {
            bundles.add(new BundleImgEntity(good.getHeaderImg()));
        }
        binding.bundleview.setPhotos(bundles);
        binding.textCount.setText("总计：" + ShopCartContentController.calcuCount(goods));
    }

    public List<ShopCart> getGoods() {
        return goods;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_bundle:
                OrderProductActivity.start(context, goods);
                break;
        }
    }
}
