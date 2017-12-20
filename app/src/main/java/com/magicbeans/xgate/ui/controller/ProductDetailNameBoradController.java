package com.magicbeans.xgate.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.BannerView;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.common.KeyValue;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.bean.product.ProductImages;
import com.magicbeans.xgate.databinding.LayProductdetailAttrBinding;
import com.magicbeans.xgate.databinding.LayProductdetailNameboardBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailNameBoradController {


    private LayProductdetailNameboardBinding binding;
    private Context context;

    public ProductDetailNameBoradController(LayProductdetailNameboardBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.banner.setOnLoadImgListener(new BannerView2.OnLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, R.drawable.default_bk_img, imgurl);
            }
        });
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        //设置banner数据
        List<Image> imgs = ProductDetail.getImgs(productDetail);
        binding.banner.setDatas(imgs);
        Product2 product = ProductDetail.getSelectProduct(productDetail, productDetail.getProdID());
        //设置姓名版数据
        if (product != null) {
            binding.textName.setText(productDetail.getBrandName() + " " + product.getProdName() + " " + product.getSize());
            binding.textPrice.setText(AppHelper.getPriceSymbol(productDetail.getCurSymbol()) + product.getShopPrice());
        } else {
            binding.textName.setText("未找到该商品");
            binding.textPrice.setText("");
        }
    }
}
