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
import com.ins.common.utils.GlideUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterCateIn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int SRC_HEADER = R.layout.item_home_catein_header;
    private final int SRC_CONTENT = R.layout.item_home_catein;

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterCateIn(Context context) {
        this.context = context;
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
        final TestBean bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        if (holder instanceof HolderHeader) {
            bindHeader((HolderHeader) holder, position);
        } else if (holder instanceof HolderContent) {
            bindContent((HolderContent) holder, position);
        }
    }

    private void bindContent(HolderContent holder, int position) {
        GlideUtil.loadImgTest(holder.img_header);
    }

    private void bindHeader(HolderHeader holder, int position) {
        TestBean bean = results.get(position);
        holder.text_header_title.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(results.get(position).getName())) {
            return SRC_HEADER;
        } else {
            return SRC_CONTENT;
        }
    }

    public class HolderContent extends RecyclerView.ViewHolder {

        private ImageView img_header;

        public HolderContent(View itemView) {
            super(itemView);
            img_header = (ImageView) itemView.findViewById(R.id.img_header);
        }
    }

    public class HolderHeader extends RecyclerView.ViewHolder {

        private TextView text_header_title;

        public HolderHeader(View itemView) {
            super(itemView);
            text_header_title = (TextView) itemView.findViewById(R.id.text_header_title);
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
                            return 4;
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
