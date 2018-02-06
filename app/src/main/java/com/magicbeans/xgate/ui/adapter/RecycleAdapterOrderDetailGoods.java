package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.viewutils.TextViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.order.OrderProduct;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.databinding.ItemHistoryBinding;
import com.magicbeans.xgate.databinding.ItemOrderdetailGoodsBinding;
import com.magicbeans.xgate.helper.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterOrderDetailGoods extends RecyclerView.Adapter<RecycleAdapterOrderDetailGoods.Holder> {

    private Context context;
    private List<OrderProduct> results = new ArrayList<>();

    public List<OrderProduct> getResults() {
        return results;
    }

    public RecycleAdapterOrderDetailGoods(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterOrderDetailGoods.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemOrderdetailGoodsBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_orderdetail_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterOrderDetailGoods.Holder holder, final int position) {
        final OrderProduct product = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        GlideUtil.loadImg(holder.binding.imgHeader, product.getImg());
        holder.binding.textName.setText(product.getBrandName() + product.getProdName());
        holder.binding.textIntro.setText("规格：" + product.getProdSize() + "  数量：" + product.getQty());
        holder.binding.textPrice.setText(AppHelper.getPriceSymbol(null) + product.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemOrderdetailGoodsBinding binding;

        public Holder(ItemOrderdetailGoodsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
