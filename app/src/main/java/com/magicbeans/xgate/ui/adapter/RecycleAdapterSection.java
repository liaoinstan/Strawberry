package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSection extends RecyclerView.Adapter<RecycleAdapterSection.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterSection(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterSection.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterSection.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        GlideUtil.loadImgTest(holder.img_1);
        GlideUtil.loadImgTest(holder.img_2);
        GlideUtil.loadImgTest(holder.img_3);
        GlideUtil.loadImgTest(holder.img_4);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_1;
        private ImageView img_2;
        private ImageView img_3;
        private ImageView img_4;

        public Holder(View itemView) {
            super(itemView);
            img_1 = (ImageView) itemView.findViewById(R.id.img_1);
            img_2 = (ImageView) itemView.findViewById(R.id.img_2);
            img_3 = (ImageView) itemView.findViewById(R.id.img_3);
            img_4 = (ImageView) itemView.findViewById(R.id.img_4);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
