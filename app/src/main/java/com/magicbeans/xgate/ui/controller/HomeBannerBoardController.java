package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.banner.BannerWrap;
import com.magicbeans.xgate.databinding.LayHomeBannerboardBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.SaleActivity;
import com.magicbeans.xgate.ui.activity.WebActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeBannerBoardController implements View.OnClickListener {

    private Context context;
    private LayHomeBannerboardBinding binding;

    public HomeBannerBoardController(LayHomeBannerboardBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        binding.banner.setOnLoadImgListener(onLoadImgListener);
        binding.banner.setOnBannerClickListener(new BannerView2.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position, Image image) {
                WebActivity.start(context, "草莓网", image.getUrl());
            }
        });
        binding.btnBannerboardToday.setOnClickListener(this);
        binding.btnBannerboardSale.setOnClickListener(this);
        binding.btnBannerboardSingle.setOnClickListener(this);
        binding.btnBannerboardNew.setOnClickListener(this);
        binding.btnBannerboardBrandhot.setOnClickListener(this);
        binding.btnBannerboardRecommed.setOnClickListener(this);
        binding.btnBannerboardClear.setOnClickListener(this);
        binding.btnBannerboardSelection.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bannerboard_today:
                SaleActivity.start(context, SaleActivity.TYPE_SALE);
                break;
            case R.id.btn_bannerboard_sale:
                SaleActivity.start(context, SaleActivity.TYPE_TODAY);
                break;
            case R.id.btn_bannerboard_single:
                SaleActivity.start(context, SaleActivity.TYPE_NEW);
                break;
            case R.id.btn_bannerboard_new:
                SaleActivity.start(context, SaleActivity.TYPE_SINGLE);
                break;
            case R.id.btn_bannerboard_brandhot:
                SaleActivity.start(context, SaleActivity.TYPE_TOP);
                break;
            case R.id.btn_bannerboard_recommed:
                SaleActivity.start(context, SaleActivity.TYPE_POPU);
                break;
            case R.id.btn_bannerboard_clear:
                SaleActivity.start(context, SaleActivity.TYPE_SELECT);
                break;
            case R.id.btn_bannerboard_selection:
                SaleActivity.start(context, SaleActivity.TYPE_GOOD);
                break;
        }
    }

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
