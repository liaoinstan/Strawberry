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
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterProductAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailNameBoradController {

    private Activity activity;
    private BannerView2 banner;
    private TextView text_name;
    private TextView text_price;

    public ProductDetailNameBoradController(Activity activity) {
        this.activity = activity;
        banner = (BannerView2) activity.findViewById(R.id.banner);
        text_name = (TextView) activity.findViewById(R.id.text_name);
        text_price = (TextView) activity.findViewById(R.id.text_price);
    }

    public void initCtrl() {
        banner.setOnLoadImgListener(new BannerView2.OnLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, R.drawable.default_bk_img, imgurl);
            }
        });
    }

    public void initData() {
//        //banner初始化假数据
//        List<Image> banners = new ArrayList<Image>() {{
//            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970104&di=3dca564f29823ca3acc9f3d49912999e&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F09%2F17%2F11b58PICZY4_1024.jpg"));
//            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970810&di=acc3f88cf0c20cce56bece96d2caf1e0&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F09%2F96%2F71h58PICpki_1024.jpg"));
//            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970810&di=e38e7c05a91bd90e19ffcc3a9efbbbe5&imgtype=0&src=http%3A%2F%2Ffile06.16sucai.com%2F2016%2F0329%2Fcf5937b18b18e57b41452565c2f7d70f.jpg"));
//            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506593970809&di=0b3bee4de28f75003abda588f188436e&imgtype=0&src=http%3A%2F%2Fpic4.nipic.com%2F20090904%2F2476235_221737303068_2.jpg"));
//        }};
//        banner.setDatas(banners);
    }

    public void setData(ProductDetail productDetail, String prodId) {
        //设置banner数据
        List<Image> imgs = getImgs(productDetail);
        banner.setDatas(imgs);
        Product2 product = getSelectProduct(productDetail, prodId);
        //设置姓名版数据
        if (product!=null) {
            text_name.setText(productDetail.getBrandName() + " " + product.getProdName() + " " + product.getSize());
            text_price.setText(AppHelper.getPriceSymbol(productDetail.getCurSymbol()) + product.getShopPrice());
        }else {
            text_name.setText("未找到该商品");
            text_price.setText("");
        }
    }

    //################ 工具方法 ################

    private static Product2 getSelectProduct(ProductDetail productDetail, String prodId) {
        List<Product2> product2s = productDetail.getProds();
        if (StrUtil.isEmpty(product2s)) return null;
        for (Product2 product2 : product2s) {
            if (product2.getProdID().equals(prodId)) {
                return product2;
            }
        }
        return null;
    }

    private List<Image> getImgs(ProductDetail productDetail) {
        ArrayList<Image> imgs = new ArrayList<>();
        List<Product2> product2s = productDetail.getProds();
        if (!StrUtil.isEmpty(product2s)) {
            for (Product2 product2 : product2s) {
                List<ProductImages> productImages = product2.getProductImages();
                if (!StrUtil.isEmpty(productImages)) {
                    for (ProductImages productImage : productImages) {
                        imgs.add(new Image(productImage.getImg700Src()));
                    }
                }
            }
        }
        return imgs;
    }
}
