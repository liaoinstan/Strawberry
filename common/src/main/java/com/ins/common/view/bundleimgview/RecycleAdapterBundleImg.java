package com.ins.common.view.bundleimgview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.common.R;
import com.ins.common.common.DragItemTouchCallback;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.Collections;
import java.util.List;


public class RecycleAdapterBundleImg extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DragItemTouchCallback.ItemTouchAdapter {

    private Context context;
    private List<BundleImgEntity> results;
    private boolean enable = true;
    private BundleImgView.OnBundleLoadImgListener onBundleLoadImgListener;

    public static final int TYPE_ITEM = 0xff01;
    public static final int TYPE_ADD = 0xff02;

    public void setDelEnable(boolean enable) {
        this.enable = enable;
    }

    public List<BundleImgEntity> getResults() {
        return results;
    }

    public RecycleAdapterBundleImg(Context context, List<BundleImgEntity> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ADD:
                return new HolderAdd(LayoutInflater.from(parent.getContext()).inflate(R.layout.bundle_item_recycle_add, parent, false));
            case TYPE_ITEM:
                return new HolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.bundle_item_recycle, parent, false));
            default:
                Log.d("error","viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });

        if (holder instanceof HolderAdd){
            bindTypeAdd((HolderAdd) holder, position);
        }else if (holder instanceof HolderItem){
            bindTypeItem((HolderItem) holder, position);
        }
    }

    private void bindTypeAdd(HolderAdd holder, int position) {
//        if (enable) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bundleClickListener != null) {
                        bundleClickListener.onPhotoAddClick(v);
                    }
                }
            });
//        }else {
//            holder.itemView.setVisibility(View.GONE);
//        }
    }

    private void bindTypeItem(final HolderItem holder, int position) {
        final BundleImgEntity bundle = results.get(position);
        holder.img_bundle_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundleClickListener != null)
                    bundleClickListener.onPhotoShowClick(bundle.getPath());
            }
        });
        //Glide.with(context).load(bundle.getPath()).placeholder(R.drawable.default_bk).crossFade().into(holder.img_bundle_show);
        if (onBundleLoadImgListener != null) {
            onBundleLoadImgListener.onloadImg(holder.img_bundle_show, bundle.getPath(), R.drawable.default_bk);
        }
        holder.img_bundle_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                if (bundleClickListener != null) {
                    bundleClickListener.onPhotoDelClick(v);
                }
            }
        });
//        if (enable) {
//            int padding = DensityUtil.dp2px(context, 5);
//            holder.card_bundle.setContentPadding(padding, padding, padding, padding);
//            holder.card_bundle.setRadius(DensityUtil.dp2px(context, 4));
//        } else {
//            holder.card_bundle.setRadius(0);
//            holder.card_bundle.setContentPadding(0, 0, 0, 0);
//        }
    }

    @Override
    public int getItemCount() {
        if (enable) {
            return results.size() + 1;
        }else {
            return results.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == results.size()) {
            return TYPE_ADD;
        } else {
            return TYPE_ITEM;
        }
    }

    public class HolderItem extends RecyclerView.ViewHolder {
        private ImageView img_bundle_show;
        private ImageView img_bundle_delete;

        public HolderItem(View itemView) {
            super(itemView);
            img_bundle_show = (ImageView) itemView.findViewById(R.id.img_bundle_show);
            img_bundle_delete = (ImageView) itemView.findViewById(R.id.img_bundle_delete);
            if (enable) {
                img_bundle_delete.setVisibility(View.VISIBLE);
            } else {
                img_bundle_delete.setVisibility(View.INVISIBLE);
            }
        }
    }

    public class HolderAdd extends RecyclerView.ViewHolder {
        public HolderAdd(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition == getItemCount() - 1 || toPosition == getItemCount() - 1) {
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnBundleLoadImgListener(BundleImgView.OnBundleLoadImgListener onBundleLoadImgListener) {
        this.onBundleLoadImgListener = onBundleLoadImgListener;
    }

    private BundleImgView.OnBundleClickListener bundleClickListener;

    public void setBundleClickListener(BundleImgView.OnBundleClickListener bundleClickListener) {
        this.bundleClickListener = bundleClickListener;
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
