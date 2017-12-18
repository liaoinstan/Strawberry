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

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.IndexBar;
import com.ins.common.view.LoadingLayout;
import com.ins.common.view.SideBar;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.bean.brand.BrandHotWrap;
import com.magicbeans.xgate.bean.brand.BrandIndex;
import com.magicbeans.xgate.bean.brand.BrandWrap;
import com.magicbeans.xgate.helper.SpringViewHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ProductActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterSortBrand;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by liaoinstan
 */
public class BrandFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;
    private SpringView spring;

    private IndexBar index_bar;
    private RecyclerView recycler;
    private RecycleAdapterSortBrand adapter;
    private LinearLayoutManager layoutManager;

    public static BrandFragment newInstance(int position) {
        BrandFragment fragment = new BrandFragment();
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
        rootView = inflater.inflate(R.layout.fragment_brand, container, false);
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
        spring = (SpringView) rootView.findViewById(R.id.spring);
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        index_bar = (IndexBar) rootView.findViewById(R.id.index_bar);
        recycler = (RecyclerView) rootView.findViewById(R.id.rl_recycle_view);
        FocusUtil.focusToTop(recycler);
    }

    public void initCtrl() {
        adapter = new RecycleAdapterSortBrand(getContext());
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(layoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        index_bar.setColors(ColorUtil.colors);
        index_bar.setIndexStr("热ABCDEFGHIJKLMNOPQRSTUVWXY#");
        index_bar.addOnIndexChangeListener(new SideBar.OnIndexChangeListener() {
            @Override
            public void onIndexChanged(float centerY, String tag, int position) {
                int pos = getPosByTag(adapter.getResults(), tag);
                if (pos != -1) layoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });
        SpringViewHelper.initSpringViewForTest(spring);
    }

    private void initData() {
        netGetBrandList();
        netGetBrand();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Brand brand = adapter.getResults().get(position);
        ProductActivity.startBrand(getContext(), brand.getBrandID());
    }

    //从联系人中获取tag标记的位置，如果未获取到，返回-1
    public static int getPosByTag(List<Brand> results, String tag) {
        if (!StrUtil.isEmpty(results) || !StrUtil.isEmpty(tag)) {
            for (int i = 0; i < results.size(); i++) {
                if (tag.equals(results.get(i).getIndexName())) {
                    return i;
                }
            }
        }
        return -1;
    }

    //结构化数据模型
    private static List<Brand> format(List<BrandIndex> brandIndexs) {
        ArrayList<Brand> brandsAll = new ArrayList<>();
        if (!StrUtil.isEmpty(brandIndexs)) {
            for (BrandIndex brandIndex : brandIndexs) {
                String indexName = brandIndex.getIndexName();
                List<Brand> brands = brandIndex.getBrandList();
                Brand header = new Brand(indexName, indexName, true);
                brandsAll.add(header);
                brandsAll.addAll(brands);
            }
        }
        return brandsAll;
    }

    private void setBrandData(List<Brand> brands) {
        adapter.getResults().addAll(brands);
        adapter.notifyDataSetChanged();
    }

    private void setBrandHotData(List<Brand> brandHots) {
        brandHots.add(0, new Brand("热", "热门品牌", true));
        adapter.getResults().addAll(0, brandHots);
        adapter.notifyDataSetChanged();
    }

    //API获取品牌列表
    private void netGetBrandList() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netBrandList(param).enqueue(new STCallback<BrandWrap>(BrandWrap.class) {
            @Override
            public void onSuccess(int status, BrandWrap bean, String msg) {
                List<Brand> brands = format(bean.getBrandIndexList());
                if (!StrUtil.isEmpty(brands)) {
                    setBrandData(brands);
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    //API获取热门品牌列表
    private void netGetBrand() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netHomeSelectBrand(param).enqueue(new STCallback<BrandHotWrap>(BrandHotWrap.class) {
            @Override
            public void onSuccess(int status, BrandHotWrap bean, String msg) {
                List<Brand> brandHots = bean.getBrand();
                if (!StrUtil.isEmpty(brandHots)) {
                    setBrandHotData(brandHots);
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
