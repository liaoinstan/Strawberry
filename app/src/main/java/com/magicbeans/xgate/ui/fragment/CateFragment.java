package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.utils.viewutils.TabLayoutUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.adapter.PagerAdapterCate;
import com.magicbeans.xgate.ui.base.BaseFragment;


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
        adapterPager = new PagerAdapterCate(getChildFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
        TabLayoutUtil.reflex(tab);
        //
    }
}
