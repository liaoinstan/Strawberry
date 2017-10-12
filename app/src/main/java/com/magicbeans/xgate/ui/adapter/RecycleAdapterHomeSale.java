package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.viewutils.TextViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.bean.home.Product;
import com.magicbeans.xgate.databinding.ItemHomeSaleBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeSale extends RecyclerView.Adapter<RecycleAdapterHomeSale.Holder> {

    private Context context;
    private List<Product> results = new ArrayList<>();

    public List<Product> getResults() {
        return results;
    }

    public RecycleAdapterHomeSale(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterHomeSale.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemHomeSaleBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home_sale, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterHomeSale.Holder holder, final int position) {
        final Product product = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });

        GlideUtil.loadImg(holder.binding.imgHeader, R.drawable.default_bk_img, product.getProductImages().getImg350Src());
        holder.binding.textName.setText(product.getProdLangName());
        holder.binding.textPrice.setText("¥" + product.getShopprice());
        holder.binding.textPriceOld.setText("¥" + product.getRefPrice());
        TextViewUtil.addDelLine(holder.binding.textPriceOld);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemHomeSaleBinding binding;

        public Holder(ItemHomeSaleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
