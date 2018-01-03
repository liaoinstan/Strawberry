package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.bean.category.Cate1Wrap;
import com.magicbeans.xgate.bean.category.Cate2;
import com.magicbeans.xgate.bean.category.Cate2Wrap;
import com.magicbeans.xgate.bean.category.Cate3;
import com.magicbeans.xgate.bean.common.TestBean;
import com.magicbeans.xgate.databinding.FragmentCateBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterCateTab;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.ui.controller.ToolbarHomeController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by liaoinstan
 */
public class CateFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private FragmentCateBinding binding;
    private RecycleAdapterCateTab adapter;
    private CateInFragment cateInFragment;
    private BrandFragment brandFragment;

    private ToolbarHomeController toolbarHomeController;

    public static Fragment newInstance(int position) {
        CateFragment fragment = new CateFragment();
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
//            if (getActivity() != null) StatusBarTextUtil.StatusBarDarkMode(getActivity());
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cate, container, false);
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
    }

    private void initBase() {
        toolbarHomeController = new ToolbarHomeController(binding.includeToobarHome);

        cateInFragment = CateInFragment.newInstance(0);
        brandFragment = BrandFragment.newInstance(1);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (!cateInFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, cateInFragment, "cateInFragment");
        }
        if (!brandFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, brandFragment, "brandFragment");
            fragmentTransaction.hide(brandFragment);
        }
        fragmentTransaction.commit();
    }

    private void initView() {
    }

    private void initData() {
        adapter.netMainCategory();
    }

    private void initCtrl() {
        adapter = new RecycleAdapterCateTab(getActivity());
        adapter.setOnItemClickListener(this);
        binding.recycleTab.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycleTab.addItemDecoration(new ItemDecorationDivider(getActivity()));
        binding.recycleTab.setAdapter(adapter);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Cate1 cate1 = adapter.getResults().get(viewHolder.getLayoutPosition());
        if (position == 0) {
            showFragment(brandFragment);
        } else {
            showFragment(cateInFragment);
            cateInFragment.freshData(cate1.getCatgId());
        }
    }

    //控制2个fragment的显示隐藏
    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (fragment instanceof CateInFragment) {
            fragmentTransaction.show(cateInFragment);
            fragmentTransaction.hide(brandFragment);
        } else {
            fragmentTransaction.show(brandFragment);
            fragmentTransaction.hide(cateInFragment);
        }
        fragmentTransaction.commit();
    }
}
