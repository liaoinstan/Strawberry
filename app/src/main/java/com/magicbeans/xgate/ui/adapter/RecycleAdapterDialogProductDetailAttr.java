package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.common.KeyValue;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.databinding.ItemDialogProductdetailAttrBinding;
import com.magicbeans.xgate.databinding.ItemHomeBrandSelectBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterDialogProductDetailAttr extends RecyclerView.Adapter<RecycleAdapterDialogProductDetailAttr.Holder> {

    private Context context;
    private List<Product2> results = new ArrayList<>();

    public List<Product2> getResults() {
        return results;
    }

    public RecycleAdapterDialogProductDetailAttr(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterDialogProductDetailAttr.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_dialog_productdetail_attr, parent, false));

    }

    @Override
    public void onBindViewHolder(final RecycleAdapterDialogProductDetailAttr.Holder holder, final int position) {
        final Product2 product2 = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已经选中的项目不再响应点击事件
                if (product2.isSelect()) return;
                //电击回调
                if (listener != null) listener.onItemClick(holder, position);
                //设置已选中状态
                SelectHelper.selectAllSelectBeans(results, false);
                product2.setSelect(true);
                notifyDataSetChanged();
            }
        });

        String sizeText = product2.getSizeText();
        sizeText = StrUtil.subFirstChart(sizeText, "容量：").trim();
        holder.binding.textAttr.setText(sizeText);
        holder.binding.textAttr.setSelected(product2.isSelect());
    }

    public void selectItem(String prodId) {
        Product2 product2 = Product2.findProduct2ById(results, prodId);
        if (product2 != null) {
            SelectHelper.selectAllSelectBeans(results, false);
            product2.setSelect(true);
        }
    }

    public Product2 getSelectProduct(){
        return SelectHelper.getSelectBean(results);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemDialogProductdetailAttrBinding binding;

        public Holder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = (ItemDialogProductdetailAttrBinding) binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
