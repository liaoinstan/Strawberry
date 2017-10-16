package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.LoadingLayout;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by liaoinstan
 */
public class EvaFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;

    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterEva adapter;

    public static Fragment newInstance(int position) {
        EvaFragment fragment = new EvaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_eva, container, false);
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
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterEva(getContext());
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getResults().add(new Eva());
                        adapter.getResults().add(new Eva());
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    private void initData() {
        adapter.getResults().clear();
        adapter.getResults().add(new Eva(new ArrayList<BundleImgEntity>(){{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapter.getResults().add(new Eva(new ArrayList<BundleImgEntity>(){{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapter.getResults().add(new Eva(new ArrayList<BundleImgEntity>(){{
            add(new BundleImgEntity());
        }}));
        adapter.getResults().add(new Eva(new ArrayList<BundleImgEntity>(){{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapter.getResults().add(new Eva(new ArrayList<BundleImgEntity>(){{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }}));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
    }
}
