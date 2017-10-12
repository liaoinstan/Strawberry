package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.LayHomeBannerboardBinding;
import com.magicbeans.xgate.databinding.LayHomeSelectBinding;
import com.magicbeans.xgate.ui.activity.SearchActivity;
import com.magicbeans.xgate.ui.activity.SectionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeSelectController implements View.OnClickListener{

    private Context context;
    private LayHomeSelectBinding binding;

    public HomeSelectController(LayHomeSelectBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void initCtrl() {
        binding.banner.setOnLoadImgListener(onLoadImgListener);
        binding.btnMore.setOnClickListener(this);
    }

    public void initData() {
        //banner初始化假数据
        List<Image> banners = new ArrayList<Image>() {{
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900665723&di=b12643bfd81b25ba211d427533544b61&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0101945774f35c0000018c1b113714.png"));
            add(new Image("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3995594144,3504136179&fm=27&gp=0.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900773698&di=0bcf274f7b9d284e79f90ce2dca79c73&imgtype=0&src=http%3A%2F%2Fwww.289.com%2Fup%2Farticle%2F2015%2F0928%2F141259_64120628.gif"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900712345&di=c61a3609656fc809917dfcd1c1fda075&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e00f56d56e336ac7252ce61858a3.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506495445&di=4f316c2a5465fed43af0120773d2f8dc&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F16%2F70%2F07%2F40n58PIC2eb_1024.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506495515&di=1f7990b9071035dbd5751f1d93c029a6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010c87584941bba801219c7702c875.jpg%40900w_1l_2o_100sh.jpg"));
        }};
        binding.banner.setDatas(banners);
    }

    private BannerView2.OnLoadImgListener onLoadImgListener = new BannerView2.OnLoadImgListener() {
        @Override
        public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
            GlideUtil.loadImg(imageView, R.drawable.default_bk_img, imgurl);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_more:
                SectionActivity.start(context);
                break;
        }
    }
}
