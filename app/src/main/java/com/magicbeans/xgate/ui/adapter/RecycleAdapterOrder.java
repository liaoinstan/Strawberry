package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.ListViewLinearLayout;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.databinding.ItemOrderBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.activity.PayTestPaypalActivity;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterOrder extends RecyclerView.Adapter<RecycleAdapterOrder.Holder> {

    private Context context;
    private List<Order> results = new ArrayList<>();

    public List<Order> getResults() {
        return results;
    }

    public RecycleAdapterOrder(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterOrder.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemOrderBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_order, parent, false));

    }

    @Override
    public void onBindViewHolder(final RecycleAdapterOrder.Holder holder, final int position) {
        final Order order = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayTestPaypalActivity.start(context);
            }
        });

        holder.binding.textCount.setText("共" + order.getItemList().size() + "件商品 实付款");
        holder.binding.textPrice.setText(AppHelper.getPriceSymbol("") + order.getNetAmount());
        holder.binding.textStatus.setText(order.getOrderStatus());

        holder.adapter.getResults().clear();
        holder.adapter.getResults().addAll(order.getItemList());
        holder.adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;
        RecycleAdapterOrderGoods adapter;

        public Holder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            adapter = new RecycleAdapterOrderGoods(context);
            binding.recycle.setNestedScrollingEnabled(false);
            binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            binding.recycle.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtil.dp2px(7), GridLayoutManager.HORIZONTAL, true));
            binding.recycle.setAdapter(adapter);
        }
    }


    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
