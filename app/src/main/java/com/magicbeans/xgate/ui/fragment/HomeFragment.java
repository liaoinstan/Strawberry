package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.utils.FocusUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.FragmentHomeBinding;
import com.magicbeans.xgate.ui.activity.ScanActivity;
import com.magicbeans.xgate.ui.activity.SearchActivity;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.ui.controller.HomeBannerBoardController;
import com.magicbeans.xgate.ui.controller.HomeBrandController;
import com.magicbeans.xgate.ui.controller.HomeClassController;
import com.magicbeans.xgate.ui.controller.HomeHotController;
import com.magicbeans.xgate.ui.controller.HomeRecommendController;
import com.magicbeans.xgate.ui.controller.HomeSaleController;
import com.magicbeans.xgate.ui.controller.HomeSelectController;
import com.magicbeans.xgate.ui.controller.HomeSingleController;


/**
 * Created by liaoinstan
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    private int position;
    private View rootView;

    //各个子模块控制器，各控制器相互独立，各种处理自己模块的业务逻辑
    private HomeBannerBoardController homeBannerBoardController;
    private HomeSaleController homeSaleController;
    private HomeBrandController homeBrandController;
    private HomeHotController homeHotController;
    private HomeSelectController homeSelectController;
    private HomeSingleController homeSingleController;
    private HomeClassController homeClassController;
    private HomeRecommendController homeRecommendController;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        rootView = binding.getRoot();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(binding.getRoot());
    }

    private void initBase() {
        homeBannerBoardController = new HomeBannerBoardController(binding.includeHomeBannerboard);
        homeSaleController = new HomeSaleController(binding.includeHomeSale);
        homeBrandController = new HomeBrandController(binding.includeHomeBrand);
        homeHotController = new HomeHotController(binding.includeHomeHot);
        homeSelectController = new HomeSelectController(binding.includeHomeSelect);
        homeSingleController = new HomeSingleController(binding.includeHomeSingle);
        homeClassController = new HomeClassController(binding.includeHomeclass);
        homeRecommendController = new HomeRecommendController(binding.includeHomeRecommend);
    }

    private void initView() {
        binding.textSearch.setOnClickListener(this);
        binding.btnLeft.setOnClickListener(this);
    }

    private void initData() {
        homeBannerBoardController.initData();
        homeSaleController.initData();
        homeBrandController.initData();
        homeHotController.initData();
        homeSelectController.initData();
        homeSingleController.initData();
        homeClassController.initData();
        homeRecommendController.initData();
    }

    private void initCtrl() {
        homeBannerBoardController.initCtrl();
        homeSaleController.initCtrl();
        homeBrandController.initCtrl();
        homeHotController.initCtrl();
        homeSelectController.initCtrl();
        homeSingleController.initCtrl();
        homeClassController.initCtrl();
        homeRecommendController.initCtrl();
        binding.spring.setHeader(new AliHeader(getActivity(),false));
        binding.spring.setFooter(new AliFooter(getActivity(),false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_search:
                SearchActivity.start(getActivity());
                break;
            case R.id.btn_left:
                ScanActivity.start(getActivity());
                break;
        }
    }
}
