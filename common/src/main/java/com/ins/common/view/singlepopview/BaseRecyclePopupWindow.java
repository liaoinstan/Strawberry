package com.ins.common.view.singlepopview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.R;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan on 2016/6/3 0003.
 */
public abstract class BaseRecyclePopupWindow<T, H extends RecyclerView.ViewHolder> extends BasePopupWindow {

    private RecyclerView recyclerView;
    private RecyclePopAdapter adapter;

    public void setResults(List<T> results) {
        adapter.getResults().clear();
        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }

    public BaseRecyclePopupWindow(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.pop_single_grid;
    }

    @Override
    public void initBase() {
        recyclerView = (RecyclerView) getContentView().findViewById(R.id.recycle);
        initRecyclerView(recyclerView);
        adapter = new RecyclePopAdapter(context);
        recyclerView.setAdapter(adapter);
    }

    public class RecyclePopAdapter extends RecyclerView.Adapter<H> {

        private Context context;
        private List<T> results = new ArrayList<>();

        public List<T> getResults() {
            return results;
        }

        public RecyclePopAdapter(Context context) {
            this.context = context;
        }

        @Override
        public H onCreateViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(final H holder, final int position) {
            final T t = results.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onItemClick(holder, position);
                }
            });
            setData(holder, t, position);
        }

        @Override
        public int getItemCount() {
            return results.size();
        }

        private OnRecycleItemClickListener listener;

        public void setOnItemClickListener(OnRecycleItemClickListener listener) {
            this.listener = listener;
        }
    }

    ////////////////////////
    //////////对外接口
    ////////////////////////

    protected abstract H getViewHolder(ViewGroup parent, int viewType);

    protected abstract void setData(final H holder, T t, final int position);

    protected abstract void initRecyclerView(RecyclerView recyclerView);
}
