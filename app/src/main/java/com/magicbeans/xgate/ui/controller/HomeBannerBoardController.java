package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
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
import com.magicbeans.xgate.ui.activity.SaleActivity;
import com.magicbeans.xgate.ui.activity.WebActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeBannerBoardController implements View.OnClickListener{

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
                WebActivity.start(context, image.getTitle(), image.getUrl());
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
        switch (view.getId()){
            case R.id.btn_bannerboard_today:
                SaleActivity.start(context);
                break;
            case R.id.btn_bannerboard_sale:
                Platform plat = ShareSDK.getPlatform(QZone.NAME);
                plat.SSOSetting(true);  //设置false表示使用SSO授权方式
                plat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        ToastUtil.showToastShort("onComplete");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        ToastUtil.showToastShort("onError");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        ToastUtil.showToastShort("onCancel");
                    }
                }); // 设置分享事件回调

                plat.authorize();//单独授权
                plat.showUser(null);//授权并获取用户信息
                break;
            case R.id.btn_bannerboard_single:
                break;
            case R.id.btn_bannerboard_new:
                break;
            case R.id.btn_bannerboard_brandhot:
                break;
            case R.id.btn_bannerboard_recommed:
                break;
            case R.id.btn_bannerboard_clear:
                break;
            case R.id.btn_bannerboard_selection:
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
