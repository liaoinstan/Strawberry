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
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.ui.activity.ProductActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterCateIn;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
import com.magicbeans.xgate.ui.base.BaseFragment;


/**
 * Created by liaoinstan
 */
public class CateInFragment extends BaseFragment implements OnRecycleItemClickListener{

    private int position;
    private View rootView;

    private RecyclerView recycle;
    private RecycleAdapterCateIn adapter;

    public static Fragment newInstance(int position) {
        CateInFragment fragment = new CateInFragment();
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
        rootView = inflater.inflate(R.layout.fragment_catein, container, false);
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
        adapter.getResults().clear();
        for (int i = 0; i < 100; i++) {
            adapter.getResults().add(new TestBean());
        }
        adapter.notifyDataSetChanged();
    }

    private void initCtrl() {
        adapter = new RecycleAdapterCateIn(getContext());
        adapter.setOnItemClickListener(this);
        recycle.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false));
        recycle.setAdapter(adapter);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        ProductActivity.start(getActivity());
    }
}
