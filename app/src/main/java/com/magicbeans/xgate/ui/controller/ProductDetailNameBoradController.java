package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.widget.ImageView;

import com.ins.common.entity.Image;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.LayProductdetailNameboardBinding;
import com.magicbeans.xgate.helper.AppHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailNameBoradController extends BaseController<LayProductdetailNameboardBinding> {

    public ProductDetailNameBoradController(LayProductdetailNameboardBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.banner.setOnLoadImgListener(new BannerView2.OnLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, R.drawable.shape_rect_white, imgurl);
            }
        });
        FontUtils.boldText(binding.textPrice);
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        //设置banner数据
        List<Image> imgs = productDetail.getImgs();
        binding.banner.setDatas(imgs);
        Product2 product = productDetail.getSelectProduct(productDetail.getProdID());
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
