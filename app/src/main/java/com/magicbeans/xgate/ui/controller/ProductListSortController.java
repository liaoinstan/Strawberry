package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.ins.common.view.singlepopview.BaseRecyclePopupWindow;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.PopBean;
import com.magicbeans.xgate.data.cache.RuntimeCache;
import com.magicbeans.xgate.databinding.LayProductlistSortBinding;
import com.magicbeans.xgate.ui.dialog.MyGridPopupWindow;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductListSortController implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, MyGridPopupWindow.OnGridPopItemClick {

    private Context context;
    private LayProductlistSortBinding binding;

    private MyGridPopupWindow pop_brand;
    private MyGridPopupWindow pop_cate;

    public ProductListSortController(LayProductlistSortBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        pop_brand = new MyGridPopupWindow(context);
        pop_cate = new MyGridPopupWindow(context);
        pop_brand.setOnGridPopItemClick(this);
        pop_cate.setOnGridPopItemClick(this);

        binding.radiogroupSort.setOnCheckedChangeListener(this);
        binding.textSelectBrand.setOnClickListener(this);
        binding.textSelectCate.setOnClickListener(this);
    }

    public void initData() {
        //获取缓存数据
        List<PopBean> popBrands = PopBean.transFromBrands(RuntimeCache.getInstance().getHotBrandsCache());
        popBrands.add(0, new PopBean(null, "全部", true));
        pop_brand.setResults(popBrands);
        List<PopBean> popCates = PopBean.transFromCate1s(RuntimeCache.getInstance().getCate1Cache());
        popCates.add(0, new PopBean(null, "全部", true));
        pop_cate.setResults(popCates);
    }

    public void setShadowView(View shadow) {
        pop_brand.setShadowView(shadow);
        pop_cate.setShadowView(shadow);
    }

    @Override
    public void onItemClick(BaseRecyclePopupWindow contain, PopBean popBean, int position) {
        if (contain == pop_brand) {
            if (!TextUtils.isEmpty(popBean.getId())) {
                binding.textSelectBrand.setText(popBean.getName());
            } else {
                binding.textSelectBrand.setText("按品牌搜索");
            }
            if (onSortSelectListenner != null) onSortSelectListenner.onSelectBrand(popBean.getId());
        } else {
            if (!TextUtils.isEmpty(popBean.getId())) {
                binding.textSelectCate.setText(popBean.getName());
            } else {
                binding.textSelectCate.setText("分类");
            }
            if (onSortSelectListenner != null) onSortSelectListenner.onSelectCate(popBean.getId());
        }
        contain.dismiss();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_select_brand:
                pop_brand.showPopupWindow(v);
                break;
            case R.id.text_select_cate:
                pop_cate.showPopupWindow(v);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        String sort = "";
        switch (i) {
            case R.id.radio_all:
                sort = "producttype";
                break;
            case R.id.radio_sale:
                sort = "popularity";
                break;
            case R.id.radio_price:
                sort = "lowerprice";
                break;
            case R.id.radio_save:
                sort = "save";
                break;
        }
        if (onSortSelectListenner != null) onSortSelectListenner.onSort(sort);
    }

    public void checkFirst() {
        //FIXME:radiogroup.check方法会回调多次
        //binding.radiogroupSort.checkFirst(R.id.radio_all);
        binding.radioAll.setChecked(true);
    }

    public void setSelectBrand(String brandId) {
        List<PopBean> results = pop_brand.getResults();
        PopBean popBean = PopBean.findById(results, brandId);
        if (popBean != null) {
            pop_brand.selectItem(popBean);
            binding.textSelectBrand.setText(popBean.getName());
        }
    }

    public void setSelectCate(String cateId) {
        List<PopBean> results = pop_cate.getResults();
        PopBean popBean = PopBean.findById(results, cateId);
        if (popBean != null) {
            pop_cate.selectItem(popBean);
            binding.textSelectCate.setText(popBean.getName());
        }
    }

    private OnSortSelectListenner onSortSelectListenner;

    public void setOnSortSelectListenner(OnSortSelectListenner onSortSelectListenner) {
        this.onSortSelectListenner = onSortSelectListenner;
    }

    public interface OnSortSelectListenner {
        void onSort(String sort);

        void onSelectCate(String catgId);

        void onSelectBrand(String brandID);
    }
}
