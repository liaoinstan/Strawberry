package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.banner.BannerWrap;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayHomeBannerboardBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.WebActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeBannerBoardController {

    private Context context;
    private LayHomeBannerboardBinding binding;

    public HomeBannerBoardController(LayHomeBannerboardBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
        binding.banner.setOnLoadImgListener(onLoadImgListener);
        binding.banner.setOnBannerClickListener(new BannerView2.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position, Image image) {
                WebActivity.start(context, image.getTitle(), image.getUrl());
            }
        });
    }

    public void initData() {
        netHomeBanner();
    }

    private BannerView2.OnLoadImgListener onLoadImgListener = new BannerView2.OnLoadImgListener() {
        @Override
        public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
            GlideUtil.loadImg(imageView, R.drawable.default_bk_img, imgurl);
        }
    };

    private void netHomeBanner() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netHomeBanner(param).enqueue(new STCallback<BannerWrap>(BannerWrap.class) {
            @Override
            public void onSuccess(int status, BannerWrap bean, String msg) {
                List<Image> images = bean.getBanner();
                binding.banner.setDatas(images);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
