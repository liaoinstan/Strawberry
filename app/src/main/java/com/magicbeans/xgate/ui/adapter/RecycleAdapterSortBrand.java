package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ins.common.helper.SelectHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Brand;
import com.magicbeans.xgate.bean.User;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSortBrand extends RecyclerView.Adapter<RecycleAdapterSortBrand.Holder> {

    private List<Brand> results;
    private Context context;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();

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
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_brand, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (results == null || results.size() == 0 || results.size() <= position)
            return;
        final Brand brand = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brand.setSelect(!brand.isSelect());
                notifyItemChanged(position);
            }
        });
        if (brand != null) {
            //TextDrawable drawable = mDrawableBuilder.build(String.valueOf(user.getSortName().charAt(0)), mColorGenerator.getColor(user.getSortName()));
            //holder.iv_img.setImageDrawable(drawable);
//            GlideUtil.loadCircleImg(holder.iv_img, R.drawable.default_header_edit, brand.getAvatar());
//            holder.tv_name.setText(brand.getSortNameSmart());
//            holder.tv_phone.setText(brand.getPhone());
//            holder.img_check.setSelected(brand.isSelect());
        }
    }

    public void setSelectAll(boolean selectAll) {
        SelectHelper.selectAllSelectBeans(results, selectAll);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public String getSelectedIds() {
        String ids = "";
        for (Brand brand : results) {
            if (brand.isSelect()) {
                ids += brand.getId() + ",";
            }
        }
        ids = StrUtil.subLastChart(ids, ",");
        return ids;
    }

    public boolean isAllSelect() {
        for (Brand brand : results) {
            if (!brand.isSelect()) {
                return false;
            }
        }
        return true;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public final ImageView img_item_brand;

        public Holder(View itemView) {
            super(itemView);
            img_item_brand = (ImageView) itemView.findViewById(R.id.img_item_brand);
        }
    }
}
