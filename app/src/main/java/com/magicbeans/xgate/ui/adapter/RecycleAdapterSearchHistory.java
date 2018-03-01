package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListenerEx;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.search.SearchHistory;
import com.magicbeans.xgate.data.cache.DataCache;
import com.magicbeans.xgate.databinding.ItemSearchHistoryBinding;
import com.magicbeans.xgate.ui.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSearchHistory extends RecyclerView.Adapter<BaseHolder<ItemSearchHistoryBinding>> {

    private Context context;
    private List<SearchHistory> results = new ArrayList<>();

    public List<SearchHistory> getResults() {
        return results;
    }

    public RecycleAdapterSearchHistory(Context context) {
        this.context = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_history, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseHolder<ItemSearchHistoryBinding> holder, final int position) {
        final SearchHistory bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(RecycleAdapterSearchHistory.this, holder, position);
            }
        });
        switch (bean.getHistoryType()) {
            case SearchHistory.TYPE_STRING:
                holder.binding.textTitle.setText(bean.getSearchKey());
                holder.binding.textTag.setVisibility(View.GONE);
                break;
            case SearchHistory.TYPE_CATE1:
                holder.binding.textTitle.setText(bean.getCate1().getTitle());
                holder.binding.textTag.setText(bean.getTag());
                holder.binding.textTag.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private OnRecycleItemClickListenerEx listener;

    public void setOnItemClickListener(OnRecycleItemClickListenerEx listener) {
        this.listener = listener;
    }

    //########################

    public void queryData() {
        List<SearchHistory> seachHistorys = DataCache.getInstance().getSeachHistory();
        results.clear();
        results.addAll(seachHistorys);
        notifyDataSetChanged();
    }
}
