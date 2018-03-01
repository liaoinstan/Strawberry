package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.utils.GlideUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.order.OrderProduct;
import com.magicbeans.xgate.databinding.ItemOrderGoodsBinding;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterOrderGoods extends RecyclerView.Adapter<RecycleAdapterOrderGoods.Holder> {

    private Context context;
    private List<OrderProduct> results = new ArrayList<>();

    public List<OrderProduct> getResults() {
        return results;
    }

    public RecycleAdapterOrderGoods(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterOrderGoods.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemOrderGoodsBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_order_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterOrderGoods.Holder holder, final int position) {
        final OrderProduct product = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailActivity.start(context, product.getProdId());
            }
        });
        GlideUtil.loadImg(holder.binding.imgHeader, product.getImg());

        if (getItemCount() == 1) {
            holder.binding.getRoot().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.binding.textName.setVisibility(View.VISIBLE);
            holder.binding.textName.setText(product.getProdName() + product.getProdSize());
        } else if (getItemCount() > 1) {
            holder.binding.getRoot().getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.binding.textName.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemOrderGoodsBinding binding;

        public Holder(ItemOrderGoodsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
