package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.category.Cate2;
import com.magicbeans.xgate.bean.category.Cate2Wrap;
import com.magicbeans.xgate.bean.category.Cate3;
import com.magicbeans.xgate.bean.common.TestBean;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.data.cache.CateRuntimeCache;
import com.magicbeans.xgate.databinding.FragmentCateBinding;
import com.magicbeans.xgate.databinding.FragmentCateinBinding;
import com.magicbeans.xgate.helper.SpringViewHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ProductActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterCateIn;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by liaoinstan
 */
public class CateInFragment extends BaseFragment implements OnRecycleItemClickListener, View.OnClickListener {

    private int position;
    private View rootView;

    private FragmentCateinBinding binding;
    private RecycleAdapterCateIn adapter;

    public static CateInFragment newInstance(int position) {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catein, container, false);
        rootView = binding.getRoot();
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
        GlideUtil.loadImg(binding.imgPop, R.drawable.default_bk_img, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513231176137&di=8962c6a63f455e0e7da0e092bc39377d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01016457a01a840000012e7e03ff55.jpg");
//        GlideUtil.loadImg(binding.imgPop, R.drawable.default_bk_img, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513845476&di=643fdc849eeb0ee85ee61f21739949a9&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a9e45846dae6a8012060c8aee515.jpg%40900w_1l_2o_100sh.jpg");
    }

    private void initData() {
        freshData("1");
    }

    //刷新数据并检查是否有缓存
    public void freshData(String CatgId) {
        Cate2Wrap cate2Wrap = CateRuntimeCache.getInstance().getCache(CatgId);
        if (cate2Wrap != null) {
            setData(cate2Wrap);
        } else {
            netSubCategory(CatgId);
        }
    }

    private void initCtrl() {
        adapter = new RecycleAdapterCateIn(getContext());
        adapter.setOnItemClickListener(this);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false));
        binding.recycle.setAdapter(adapter);

        binding.imgPop.setOnClickListener(this);
        SpringViewHelper.initSpringViewForTest(binding.spring);
    }

    private void setData(Cate2Wrap cate2Wrap) {
        adapter.getResults().clear();
        adapter.getResults().addAll(cate2Wrap.formatToCate3List());
        adapter.notifyDataSetChanged();
        binding.textPop.setText("所有" + cate2Wrap.getProdCatgName() + "商品 >");
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Cate3 cate3 = adapter.getResults().get(viewHolder.getLayoutPosition());
        ProductActivity.startCate3(getActivity(), cate3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_pop:
                ProductActivity.start(getActivity());
                break;
        }
    }

    //请求二级三级分类列表数据
    private void netSubCategory(final String CatgId) {
        Map<String, Object> param = new NetParam()
                .put("CatgId", CatgId)
                .build();
        ((BaseAppCompatActivity) getActivity()).showLoadingDialog();
        NetApi.NI().netSubCategory(param).enqueue(new STCallback<Cate2Wrap>(Cate2Wrap.class) {
            @Override
            public void onSuccess(int status, Cate2Wrap bean, String msg) {
                ((BaseAppCompatActivity) getActivity()).hideLoadingDialog();
                setData(bean);
                //添加运行时缓存
                CateRuntimeCache.getInstance().putCache(CatgId, bean);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                ((BaseAppCompatActivity) getActivity()).hideLoadingDialog();
            }
        });
    }
}
