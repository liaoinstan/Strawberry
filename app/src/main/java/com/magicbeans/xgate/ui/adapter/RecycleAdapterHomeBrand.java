package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.databinding.ItemHomeBrandSelectBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeBrand extends RecyclerView.Adapter<RecycleAdapterHomeBrand.Holder> {

    private Context context;
    private List<Brand> results = new ArrayList<>();

    public List<Brand> getResults() {
        return results;
    }

    public RecycleAdapterHomeBrand(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterHomeBrand.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecycleAdapterHomeBrand.Holder((ItemHomeBrandSelectBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home_brand_select, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterHomeBrand.Holder holder, final int position) {
        final Brand brand = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });

//        if (position % 3 == 2 || position == results.size() - 1) {
//            holder.binding.viewLineV.setVisibility(View.INVISIBLE);
//        } else {
//            holder.binding.viewLineV.setVisibility(View.VISIBLE);
//        }
        //FIXME:部分品牌只有英文名，如果中文名不存在则显示英文名
        holder.binding.textBrandTitle.setText(!TextUtils.isEmpty(brand.getBrandLangName()) ? brand.getBrandLangName() : brand.getBrandName());
        holder.binding.textBrandTitleEn.setText(brand.getBrandName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemHomeBrandSelectBinding binding;

        public Holder(ItemHomeBrandSelectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
