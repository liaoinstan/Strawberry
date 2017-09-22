package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.utils.DensityUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeSale;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
import com.magicbeans.xgate.ui.base.BaseFragment;


/**
 * Created by liaoinstan
 */
public class ShopBagFragment extends BaseFragment {

    private int position;
    private View rootView;

    private RecyclerView recycle;
    private RecycleAdapterHomeShopbag adapter;

    public static Fragment newInstance(int position) {
        ShopBagFragment fragment = new ShopBagFragment();
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
        rootView = inflater.inflate(R.layout.fragment_shopbag, container, false);
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
        recycle = (RecyclerView) rootView.findViewById(R.id.recycle);
    }

    private void initData() {

        //限时促销列表假数据
        adapter.getResults().clear();
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.notifyDataSetChanged();

    }

    private void initCtrl() {
        adapter = new RecycleAdapterHomeShopbag(getContext());
        recycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycle.addItemDecoration(new ItemDecorationDivider(getContext(), LinearLayoutManager.VERTICAL));
        recycle.setAdapter(adapter);
    }
}
