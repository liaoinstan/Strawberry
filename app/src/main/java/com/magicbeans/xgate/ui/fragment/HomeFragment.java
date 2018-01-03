package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.FragmentHomeBinding;
import com.magicbeans.xgate.helper.SpringViewHelper;
import com.magicbeans.xgate.ui.activity.ScanActivity;
import com.magicbeans.xgate.ui.activity.SearchActivity;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.ui.controller.HomeBannerBoardController;
import com.magicbeans.xgate.ui.controller.HomeBrandController;
import com.magicbeans.xgate.ui.controller.HomeClearController;
import com.magicbeans.xgate.ui.controller.HomeNewProductController;
import com.magicbeans.xgate.ui.controller.HomeRecommendController;
import com.magicbeans.xgate.ui.controller.HomeSingleController;
import com.magicbeans.xgate.ui.controller.ToolbarHomeController;


/**
 * Created by liaoinstan
 */
public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding binding;

    private int position;
    private View rootView;

    //各个子模块控制器，各控制器相互独立，各自处理自己模块的业务逻辑
    private ToolbarHomeController toolbarHomeController;
    private HomeBannerBoardController homeBannerBoardController;
    private HomeSingleController homeSingleController;
    private HomeNewProductController homeNewProductController;
    private HomeBrandController homeBrandController;
    private HomeRecommendController homeRecommendController;
    private HomeClearController homeClearController;

    public static Fragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
////            if (getActivity() != null) StatusBarTextUtil.transBarBackground(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
////            if (getActivity() != null) StatusBarTextUtil.StatusBarDarkMode(getActivity());
//        }
//    }

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
        toolbarHomeController = new ToolbarHomeController(binding.includeToobarHome);
        homeBannerBoardController = new HomeBannerBoardController(binding.includeHomeBannerboard);
        homeSingleController = new HomeSingleController(binding.includeHomeSingle);
        homeNewProductController = new HomeNewProductController(binding.includeHomeNewproduct);
        homeBrandController = new HomeBrandController(binding.includeHomeBrand);
        homeRecommendController = new HomeRecommendController(binding.includeHomeRecommend);
        homeClearController = new HomeClearController(binding.includeHomeClear);
    }

    private void initView() {
    }

    private void initData() {
    }

    private void initCtrl() {
        SpringViewHelper.initSpringViewForTest(binding.spring);
    }
}
