package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.GlideUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.databinding.ItemOrderProductBinding;
import com.magicbeans.xgate.helper.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterOrderProduct extends RecyclerView.Adapter<RecycleAdapterOrderProduct.Holder> {

    private Context context;
    private List<ShopCart> results = new ArrayList<>();

    public List<ShopCart> getResults() {
        return results;
    }

    public RecycleAdapterOrderProduct(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterOrderProduct.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemOrderProductBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_order_product, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterOrderProduct.Holder holder, final int position) {
        final ShopCart shopCart = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        GlideUtil.loadImg(holder.binding.imgHeader, R.drawable.default_bk_img, shopCart.getHeaderImg());
        holder.binding.textName.setText(shopCart.getTitleName());
        holder.binding.textIntro.setText(shopCart.getSize());
        holder.binding.textPrice.setText(AppHelper.getPriceSymbol("") + shopCart.getPriceFloat());
        holder.binding.textPriceOld.setText("比加入时降" + shopCart.getBagAddedPriceDifference() + "元");
        holder.binding.textPriceOld.setVisibility(shopCart.getBagAddedPriceDifference() != 0 ? View.VISIBLE : View.INVISIBLE);
        holder.binding.textCount.setText("数量：" + shopCart.getQty());
        FontUtils.boldText(holder.binding.textPrice);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemOrderProductBinding binding;

        public Holder(ItemOrderProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
