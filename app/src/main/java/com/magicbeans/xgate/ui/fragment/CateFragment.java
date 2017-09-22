package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.TabLayoutUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.ui.adapter.PagerAdapterCate;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
import com.magicbeans.xgate.ui.base.BaseFragment;

import java.lang.reflect.Field;

import static com.liaoinstan.springview.utils.DensityUtil.dip2px;


/**
 * Created by liaoinstan
 */
public class CateFragment extends BaseFragment {

    private int position;
    private View rootView;

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterCate adapterPager;

    private String[] titles = new String[]{"类别", "品牌"};

    public static Fragment newInstance(int position) {
        CateFragment fragment = new CateFragment();
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
        rootView = inflater.inflate(R.layout.fragment_cate, container, false);
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
        tab = (TabLayout) rootView.findViewById(R.id.tab);
        pager = (ViewPager) rootView.findViewById(R.id.pager);
    }

    private void initData() {

    }

    private void initCtrl() {
        adapterPager = new PagerAdapterCate(getActivity().getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
        TabLayoutUtil.reflex(tab);
    }
}
