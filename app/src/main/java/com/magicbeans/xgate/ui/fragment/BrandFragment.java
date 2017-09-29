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

import com.ins.common.utils.FocusUtil;
import com.ins.common.view.IndexBar;
import com.ins.common.view.LoadingLayout;
import com.ins.common.view.SideBar;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Brand;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterSortBrand;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.utils.ColorUtil;
import com.magicbeans.xgate.utils.SortUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by liaoinstan
 */
public class BrandFragment extends BaseFragment {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;

    private IndexBar index_bar;
    private RecyclerView recycler;
    private RecycleAdapterSortBrand adapter;
    private LinearLayoutManager layoutManager;

    private List<Brand> brands = new ArrayList<>();

    public static Fragment newInstance(int position) {
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
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        index_bar = (IndexBar) rootView.findViewById(R.id.index_bar);
        recycler = (RecyclerView) rootView.findViewById(R.id.rl_recycle_view);
        FocusUtil.focusToTop(recycler);
    }

    public void initCtrl() {
        adapter = new RecycleAdapterSortBrand(getContext());
//        recycler.setLayoutManager(layoutManager = new LinearLayoutManager(getContext()));
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
                int pos = SortUtil.getPosByTag(adapter.getResults(), tag);
                if (pos != -1) layoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });
    }

    private void initData() {
        brands.clear();
        brands.add(new Brand("阿达"));
        brands.add(new Brand("阿萨德"));
        brands.add(new Brand("地方"));
        brands.add(new Brand("让他一人"));
        brands.add(new Brand("从v"));
        brands.add(new Brand("ui"));
        brands.add(new Brand("键盘"));
        brands.add(new Brand("天啊撸"));
        brands.add(new Brand("阿萨德"));
        brands.add(new Brand("在v"));
        brands.add(new Brand("太容易人体宴"));
        brands.add(new Brand("驱蚊器"));
        freshData(brands);
    }

    private void freshData(List<Brand> results) {
        //对数据进行排序
        SortUtil.sortData(results);
        addSortHeaders(results);
        addHots(results);
        adapter.getResults().clear();
        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }

    //添加
    private static void addSortHeaders(List<Brand> results) {
        String temp = "";
        ListIterator<Brand> iterator = results.listIterator();
        while (iterator.hasNext()) {
            Brand brand = iterator.next();
            if (!temp.equals(brand.getSortTag())) {
                temp = brand.getSortTag();
                iterator.previous();
                iterator.add(new Brand(temp, temp, true));
            }
        }
    }

    private static void addHots(List<Brand> results) {
        List<Brand> hots = new ArrayList<Brand>() {{
            add(new Brand("热门品牌", "热", true));
            add(new Brand("阿迪"));
            add(new Brand("香奈儿"));
            add(new Brand("资生堂"));
            add(new Brand("美肌"));
        }};
        results.addAll(0, hots);
    }
}
