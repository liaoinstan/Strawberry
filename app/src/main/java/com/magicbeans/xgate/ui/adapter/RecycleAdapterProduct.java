package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.viewutils.TextViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterProduct extends RecyclerView.Adapter<RecycleAdapterProduct.Holder> {

    private Context context;
    private List<Product> results = new ArrayList<>();
    private boolean gridMode = false;

    public List<Product> getResults() {
        return results;
    }

    public RecycleAdapterProduct(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterProduct.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (gridMode) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_grid, parent, false));
        } else {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterProduct.Holder holder, final int position) {
        final Product product = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        GlideUtil.loadImg(holder.img_header, R.drawable.default_bk_img, product.getProductImages().getImg350Src());
        holder.text_name.setText(product.getProdLangName());
        holder.text_intro.setText(product.getProdLangSize());
        holder.text_price.setText("¥" + product.getShopprice());
        holder.text_price_old.setText("¥" + product.getRefPrice());
        holder.text_price_old.setVisibility(!TextUtils.isEmpty(product.getRefPrice()) ? View.VISIBLE : View.INVISIBLE);
        holder.text_rating.setText("评论次数：" + product.getRatingCount() + "次");
        TextViewUtil.addDelLine(holder.text_price_old);
        FontUtils.boldText(holder.text_price);
    }

    @Override
    public int getItemViewType(int position) {
        if (gridMode) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_header;
        private TextView text_name;
        private TextView text_intro;
        private TextView text_price;
        private TextView text_price_old;
        private TextView text_rating;

        public Holder(View itemView) {
            super(itemView);
            img_header = (ImageView) itemView.findViewById(R.id.img_header);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_intro = (TextView) itemView.findViewById(R.id.text_intro);
            text_price = (TextView) itemView.findViewById(R.id.text_price);
            text_price_old = (TextView) itemView.findViewById(R.id.text_price_old);
            text_rating = (TextView) itemView.findViewById(R.id.text_rating);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

    //############### get & set ################

    public boolean isGridMode() {
        return gridMode;
    }

    public void setGridMode(boolean gridMode) {
        this.gridMode = gridMode;
    }
}
