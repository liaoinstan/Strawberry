package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.databinding.ItemEvaBinding;
import com.magicbeans.xgate.databinding.ItemHomeSaleBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterEva extends RecyclerView.Adapter<RecycleAdapterEva.Holder> {

    private Context context;
    private List<Eva> results = new ArrayList<>();
    //是否需要展示推荐数据
    private boolean needRecomment = true;

    public void setNeedRecomment(boolean needRecomment) {
        this.needRecomment = needRecomment;
    }

    public List<Eva> getResults() {
        return results;
    }

    public RecycleAdapterEva(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterEva.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemEvaBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_eva, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterEva.Holder holder, final int position) {
        final Eva eva = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.binding.textName.setText(eva.getAccName());
        holder.binding.textAttr.setText(eva.getSubject());
        holder.binding.textContent.setText(eva.getContent());
        holder.binding.bundle.setPhotos(eva.getImgs());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemEvaBinding binding;

        public Holder(ItemEvaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            //决定是否展示推荐数据
            binding.layEvaRecomment.setVisibility(needRecomment ? View.VISIBLE : View.GONE);
            binding.bundle.setOnBundleLoadImgListener(new BundleImgView.OnBundleLoadImgListener() {
                @Override
                public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                    GlideUtil.loadImgTest(imageView);
                }
            });
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
