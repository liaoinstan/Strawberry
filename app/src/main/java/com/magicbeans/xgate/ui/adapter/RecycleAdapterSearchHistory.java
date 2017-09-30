package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSearchHistory extends RecyclerView.Adapter<RecycleAdapterSearchHistory.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();
    private RecycleAdapterLable adapterSearchHot;

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterSearchHistory(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterSearchHistory.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterSearchHistory.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.text_title.setText(bean.getName());

        adapterSearchHot.getResults().add(new TestBean("美妆"));
        adapterSearchHot.getResults().add(new TestBean("热销"));
        adapterSearchHot.getResults().add(new TestBean("草莓自营"));
        adapterSearchHot.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_title;
        private RecyclerView recycle_lable;

        public Holder(View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.text_title);
            recycle_lable = (RecyclerView) itemView.findViewById(R.id.recycle_lable);

            adapterSearchHot = new RecycleAdapterLable(context);
            recycle_lable.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
            recycle_lable.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtil.dp2px(8), GridLayoutManager.HORIZONTAL, false, true));
            recycle_lable.setAdapter(adapterSearchHot);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
