package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.view.BannerView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liaoinstan
 */
public class HomeFragment extends BaseFragment {

    private int position;
    private View rootView;

    private BannerView banner;

    public static Fragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        banner = (BannerView) rootView.findViewById(R.id.banner);
    }

    private void initData() {
        //banner初始化假数据
        List<Image> banners = new ArrayList<Image>() {{
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900665723&di=b12643bfd81b25ba211d427533544b61&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0101945774f35c0000018c1b113714.png"));
            add(new Image("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3995594144,3504136179&fm=27&gp=0.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900773698&di=0bcf274f7b9d284e79f90ce2dca79c73&imgtype=0&src=http%3A%2F%2Fwww.289.com%2Fup%2Farticle%2F2015%2F0928%2F141259_64120628.gif"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900712345&di=c61a3609656fc809917dfcd1c1fda075&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e00f56d56e336ac7252ce61858a3.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506495445&di=4f316c2a5465fed43af0120773d2f8dc&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F16%2F70%2F07%2F40n58PIC2eb_1024.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506495515&di=1f7990b9071035dbd5751f1d93c029a6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010c87584941bba801219c7702c875.jpg%40900w_1l_2o_100sh.jpg"));
        }};
        banner.setDatas(banners);
    }

    private void initCtrl() {
        banner.showTitle(false);
        banner.setOnLoadImgListener(new BannerView.OnLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, R.drawable.default_bk_img, imgurl);
            }
        });
    }
}
