package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FontUtils;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.brand.Brand;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSortBrand extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int SRC_HEADER = R.layout.item_home_brand_header;
    private final int SRC_CONTENT = R.layout.item_home_brand;

    private List<Brand> results;
    private Context context;

    public List<Brand> getResults() {
        return results;
    }

    public RecycleAdapterSortBrand(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    public void addAll(List<Brand> results) {
        results.addAll(results);
    }

    public void add(Brand bean, int position) {
        results.add(position, bean);
        notifyItemInserted(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SRC_HEADER:
                return new HolderHeader(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
            case SRC_CONTENT:
                return new HolderContent(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (results == null || results.size() == 0 || results.size() <= position)
            return;
        if (holder instanceof HolderHeader) {
            bindHeader((HolderHeader) holder, position);
        } else if (holder instanceof HolderContent) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onItemClick(holder, position);
                }
            });
            bindContent((HolderContent) holder, position);
        }
    }

    private void bindContent(HolderContent holder, int position) {
        Brand brand = results.get(position);
        //FIXME:部分品牌只有英文名，如果中文名不存在则显示英文名
        holder.text_brand_title.setText(!TextUtils.isEmpty(brand.getBrandLangName()) ? brand.getBrandLangName() : brand.getBrandName());
        holder.text_brand_title_en.setText(brand.getBrandName());
    }

    private void bindHeader(HolderHeader holder, int position) {
        Brand brand = results.get(position);
        holder.text_header_title.setText(brand.getBrandLangName());
        FontUtils.boldText(holder.text_header_title);
        if (brand.getBrandLangName().startsWith("热门")) {
            holder.text_header_title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_brandhot_title, 0, 0, 0);
        } else {
            holder.text_header_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (results.get(position).isHeader()) {
            return SRC_HEADER;
        } else {
            return SRC_CONTENT;
        }
    }

    public class HolderHeader extends RecyclerView.ViewHolder {

        private TextView text_header_title;

        public HolderHeader(View itemView) {
            super(itemView);
            text_header_title = (TextView) itemView.findViewById(R.id.text_header_title);
        }
    }

    public static class HolderContent extends RecyclerView.ViewHolder {
        public final TextView text_brand_title_en;
        public final TextView text_brand_title;

        public HolderContent(View itemView) {
            super(itemView);
            text_brand_title = (TextView) itemView.findViewById(R.id.text_brand_title);
            text_brand_title_en = (TextView) itemView.findViewById(R.id.text_brand_title_en);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case SRC_HEADER:
                            return 3;
                        case SRC_CONTENT:
                            return 1;
                        default:
                            return 1;
                    }
                }
            });
        }
    }
}
