package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.TypeFaceHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.viewutils.TextViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.databinding.ItemSaleProductListBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSale extends RecyclerView.Adapter<RecycleAdapterSale.Holder> {

    private Context context;
    private List<Product> results = new ArrayList<>();

    public List<Product> getResults() {
        return results;
    }

    public RecycleAdapterSale(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterSale.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemSaleProductListBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sale_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterSale.Holder holder, final int position) {
        final Product product = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        GlideUtil.loadImg(holder.binding.imgHeader, R.drawable.default_bk_img, product.getProductImages().getImg350Src());
        holder.binding.textName.setText(product.getProdLangName());
        holder.binding.textIntro.setText(product.getProdLangSize());
        holder.binding.textPrice.setText("¥" + product.getShopprice());
        holder.binding.textPriceOld.setText("¥" + product.getWasPrice());
        holder.binding.textPriceOld.setVisibility(!TextUtils.isEmpty(product.getWasPrice()) ? View.VISIBLE : View.INVISIBLE);
        TextViewUtil.addDelLine(holder.binding.textPriceOld);
        FontUtils.boldText(holder.binding.textPrice);

        //如果折扣字段为空或者为0，则隐藏
        if (TextUtils.isEmpty(product.getSave()) || Integer.parseInt(product.getSave()) == 0) {
            holder.binding.textOffFlag.setVisibility(View.GONE);
        } else {
            holder.binding.textOffFlag.setVisibility(View.VISIBLE);
//            holder.binding.textOffFlag.setText(product.getSave() + "%\nOFF");

            Typeface typeface1 = TypeFaceHelper.with(context).getTypeFace("fonts/brlnsr.ttf");
            Typeface typeface2 = TypeFaceHelper.with(context).getTypeFace("fonts/brlnsb.ttf");
            SpannableString span = SpannableStringUtil.createTypeFaceStr(new String[]{product.getSave() + "%", "\nOFF"}, new Typeface[]{typeface1, typeface2});
            holder.binding.textOffFlag.setText(span);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemSaleProductListBinding binding;

        public Holder(ItemSaleProductListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

}
