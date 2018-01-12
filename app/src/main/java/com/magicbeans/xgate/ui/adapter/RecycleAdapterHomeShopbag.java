package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.common.TestBean;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.data.db.AppDatabaseManager;
import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.databinding.ItemShopbagBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.view.CountView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeShopbag extends RecyclerView.Adapter<RecycleAdapterHomeShopbag.Holder> {

    private Context context;
    private List<Product2> results = new ArrayList<>();
    private boolean isEdit = false;

    public List<Product2> getResults() {
        return results;
    }

    public RecycleAdapterHomeShopbag(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterHomeShopbag.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemShopbagBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_shopbag, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterHomeShopbag.Holder holder, final int position) {
        final Product2 bean = results.get(position);
        holder.binding.includeCoutent.layContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.binding.includeCoutent.imgShopbagCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setSelect(!bean.isSelect());
                notifyItemChanged(position);
            }
        });
        holder.binding.btnItemShopbagFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastShort("收藏");
            }
        });
        holder.binding.btnItemShopbagDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSure.showDialog(context, "确定要删除该商品？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
//                        AppDatabaseManager.getInstance().deleteShopCart(ShopCart.convertProduct2ToShopcart(bean));
                        AppDatabaseManager.getInstance().deleteShopCartTable(bean);
                        results.remove(bean);
                        notifyItemRemoved(position);
                    }
                });
            }
        });
        holder.binding.includeCoutent.imgShopbagCheck.setSelected(bean.isSelect());
        GlideUtil.loadImg(holder.binding.includeCoutent.imgHeader, R.drawable.default_bk_img, bean.getHeaderImg());
        holder.binding.includeCoutent.textName.setText(bean.getProdName());
        holder.binding.includeCoutent.textAttr.setText(bean.getSizeText());
        holder.binding.includeCoutent.textPrice.setText(AppHelper.getPriceSymbol("") + bean.getShopPrice());
        holder.binding.includeCoutent.textPriceOld.setText(AppHelper.getPriceSymbol("") + bean.getWasPrice());
        holder.binding.includeCoutent.textPriceOld.setVisibility(!TextUtils.isEmpty(bean.getWasPrice()) ? View.VISIBLE : View.INVISIBLE);
        holder.binding.includeCoutent.countview.setCount(bean.getCount());
        holder.binding.includeCoutent.countview.setEdit(isEdit);
        holder.binding.slidmenu.close();

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemShopbagBinding binding;

        public Holder(ItemShopbagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

    public void selectAll(boolean isSelect) {
        SelectHelper.selectAllSelectBeans(results, isSelect);
        notifyDataSetChanged();
    }

    public boolean isSelectAll() {
        return SelectHelper.isSelectAll(results);
    }

    //##############  get & set ###############

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }
}
