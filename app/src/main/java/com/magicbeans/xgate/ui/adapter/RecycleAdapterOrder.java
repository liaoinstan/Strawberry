package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.ListViewLinearLayout;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Order;

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
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
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

        ListAdapterOrderGoods adapter = new ListAdapterOrderGoods(context);
        adapter.getResults().addAll(order.getGoodsList());
        holder.list_order_goods.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ListViewLinearLayout list_order_goods;

        public Holder(View itemView) {
            super(itemView);
            list_order_goods = (ListViewLinearLayout) itemView.findViewById(R.id.list_order_goods);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
