package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.category.Cate3;
import com.magicbeans.xgate.utils.CategoryLoadImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecycleAdapterCateIn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int SRC_HEADER = R.layout.item_home_catein_header;
    private final int SRC_CONTENT = R.layout.item_home_catein;

    private Context context;
    private List<Cate3> results = new ArrayList<>();

    public List<Cate3> getResults() {
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
        final Cate3 bean = results.get(position);
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
        Cate3 bean = results.get(position);
        holder.text_title.setText(bean.getProdTypeName());

        //TODO:接口一直没有提供分类的图片，但是UI设计上有图片，直到今天香港那边发了一百多张图过来，要把图片放在本地，自行去写if else筛选图片。心累…
        //TODO:图片的问题先暂时放一放，这里先随机加载一张产品图片
        GlideUtil.loadImgTest(holder.img_header);
//        Map<String, Integer> map = CategoryLoadImage.loadImage();
//        Integer res = map.get(bean.getProdCatgId());
//        if (res != null){
//            holder.img_header.setImageResource(res);
//        }else {
//            holder.img_header.setImageResource(R.drawable.s_1);
//        }

    }

    private void bindHeader(HolderHeader holder, int position) {
        Cate3 bean = results.get(position);
        holder.text_header_title.setText(bean.getHeaderName());
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

    public class HolderContent extends RecyclerView.ViewHolder {

        private ImageView img_header;
        private TextView text_title;

        public HolderContent(View itemView) {
            super(itemView);
            img_header = itemView.findViewById(R.id.img_header);
            text_title = itemView.findViewById(R.id.text_title);
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
