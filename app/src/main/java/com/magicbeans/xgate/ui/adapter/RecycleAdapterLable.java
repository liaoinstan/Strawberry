package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.common.interfaces.OnRecycleItemClickListenerEx;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.data.cache.DataCache;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLable extends RecyclerView.Adapter<RecycleAdapterLable.Holder> {

    private Context context;
    private List<Cate1> results = new ArrayList<>();

    public List<Cate1> getResults() {
        return results;
    }

    public RecycleAdapterLable(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLable.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lable, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLable.Holder holder, final int position) {
        final Cate1 bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(RecycleAdapterLable.this, holder, position);
            }
        });
        holder.text_lable.setText(bean.getTitle());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_lable;

        public Holder(View itemView) {
            super(itemView);
            text_lable = (TextView) itemView.findViewById(R.id.text_lable);
        }
    }

    private OnRecycleItemClickListenerEx listener;

    public void setOnItemClickListener(OnRecycleItemClickListenerEx listener) {
        this.listener = listener;
    }

    public void queryData() {
        results.clear();
        results.addAll(DataCache.getInstance().getCate1Cache());
        notifyDataSetChanged();
    }
}
