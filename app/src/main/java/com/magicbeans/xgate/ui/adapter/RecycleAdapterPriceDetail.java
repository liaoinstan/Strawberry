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
import com.magicbeans.xgate.bean.order.OrderPriceDetail;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.databinding.ItemHistoryBinding;
import com.magicbeans.xgate.databinding.ItemOrderaddPricedetailBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterPriceDetail extends RecyclerView.Adapter<RecycleAdapterPriceDetail.Holder> {

    private Context context;
    private List<OrderPriceDetail> results = new ArrayList<>();

    public List<OrderPriceDetail> getResults() {
        return results;
    }

    public RecycleAdapterPriceDetail(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterPriceDetail.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemOrderaddPricedetailBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_orderadd_pricedetail, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterPriceDetail.Holder holder, final int position) {
        final OrderPriceDetail bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });

        holder.binding.textName.setText(bean.getName());
        holder.binding.textPrice.setText(bean.getPrice());
        holder.binding.line.setVisibility(position == getItemCount() - 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemOrderaddPricedetailBinding binding;

        public Holder(ItemOrderaddPricedetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
