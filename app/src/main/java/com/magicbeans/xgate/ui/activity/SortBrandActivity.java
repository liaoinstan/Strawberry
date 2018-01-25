package com.magicbeans.xgate.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

//FIXME：弃用的类
public class SortBrandActivity extends BaseAppCompatActivity {

//    private LoadingLayout loadingLayout;
//
//    private IndexBar index_bar;
//    private RecyclerView recycler;
//    private RecycleAdapterSortBrand adapter;
//    private ItemDecorationSortStickTop decoration;
//    private LinearLayoutManager layoutManager;
//
//    private List<Brand> brands = new ArrayList<>();
//
//    public static void start(Context context, int orderId) {
//        Intent intent = new Intent(context, SortBrandActivity.class);
//        intent.putExtra("orderId", orderId);
//        context.startActivity(intent);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_brand);
//        setToolbar();
//        initBase();
//        initView();
//        initCtrl();
//        initData();
    }
//
//    private void initBase() {
//    }
//
//    public void initView() {
//        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
//        index_bar = (IndexBar) findViewById(R.id.index_bar);
//        recycler = (RecyclerView) findViewById(R.id.rl_recycle_view);
//        FocusUtil.focusToTop(recycler);
//    }
//
//    public void initCtrl() {
//        adapter = new RecycleAdapterSortBrand(this);
//        recycler.setLayoutManager(layoutManager = new LinearLayoutManager(this));
//        recycler.addItemDecoration(decoration = new ItemDecorationSortStickTop(this, ColorUtil.colors));
//        recycler.setAdapter(adapter);
//        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initData();
//            }
//        });
//
//        index_bar.setColors(ColorUtil.colors);
//        index_bar.addOnIndexChangeListener(new SideBar.OnIndexChangeListener() {
//            @Override
//            public void onIndexChanged(float centerY, String tag, int position) {
//                int pos = SortUtil.getPosByTag(adapter.getResults(), tag);
//                if (pos != -1) layoutManager.scrollToPositionWithOffset(pos, 0);
//            }
//        });
//    }
//
//    private void initData() {
//        brands.clear();
//        brands.add(new Brand("阿达"));
//        brands.add(new Brand("阿萨德"));
//        brands.add(new Brand("地方"));
//        brands.add(new Brand("让他一人"));
//        brands.add(new Brand("从v"));
//        brands.add(new Brand("ui"));
//        brands.add(new Brand("键盘"));
//        freshData(brands);
//    }
//
//    private void freshData(List<Brand> results) {
//        SortUtil.sortData(results);
//        String tagsStr = SortUtil.getTags(results);
//        List<String> tagsArr = SortUtil.getTagsArr(results);
//        index_bar.setIndexStr(tagsStr);
//        decoration.setTags(tagsArr);
//        adapter.getResults().clear();
//        adapter.getResults().addAll(results);
//        adapter.notifyDataSetChanged();
//    }
}
