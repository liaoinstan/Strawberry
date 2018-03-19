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
import com.magicbeans.xgate.bean.postbean.FreeGift;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.databinding.ItemGiftBinding;
import com.magicbeans.xgate.databinding.ItemHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterGift extends RecyclerView.Adapter<RecycleAdapterGift.Holder> {

    private Context context;
    private List<FreeGift> results = new ArrayList<>();

    public List<FreeGift> getResults() {
        return results;
    }

    public RecycleAdapterGift(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterGift.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemGiftBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_gift, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterGift.Holder holder, final int position) {
        final FreeGift bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });

        holder.binding.textName.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemGiftBinding binding;

        public Holder(ItemGiftBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
