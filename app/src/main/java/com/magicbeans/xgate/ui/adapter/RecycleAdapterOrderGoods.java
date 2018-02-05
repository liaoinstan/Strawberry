package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.TypeFaceHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.viewutils.TextViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Goods;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.databinding.ItemOrderBinding;
import com.magicbeans.xgate.databinding.ItemOrderGoodsBinding;
import com.magicbeans.xgate.databinding.ItemSaleProductListBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterOrderGoods extends RecyclerView.Adapter<RecycleAdapterOrderGoods.Holder> {

    private Context context;
    private List<Product> results = new ArrayList<>();

    public List<Product> getResults() {
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
        final Product product = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        GlideUtil.loadImg(holder.binding.imgHeader, product.getProductImages().getImg350Src());

        if (getItemCount() == 1) {
            holder.binding.getRoot().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.binding.textName.setVisibility(View.VISIBLE);
            holder.binding.textName.setText("商品名称非常长非常长非常长非常长非常长非常长非常长非常长非常长非常长非常长非常长非常长");
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

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

}
