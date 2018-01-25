package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.LoadingLayout;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.data.db.AppDatabaseManager;
import com.magicbeans.xgate.databinding.ItemShopbagBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.view.CountView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeShopbag extends RecyclerView.Adapter<RecycleAdapterHomeShopbag.Holder> {

    private Context context;
    private List<Product2> results = new ArrayList<>();
    private LoadingLayout loadingLayout;

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
        Product2 bean = results.get(position);
        holder.binding.includeCoutent.layContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.binding.includeCoutent.imgShopbagCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product2 bean = results.get(holder.getLayoutPosition());
                bean.setSelect(!bean.isSelect());
                AppDatabaseManager.getInstance().updateShopCartTable(bean);
                notifyItemChanged(holder.getLayoutPosition());
                if (onSelectChangeListenner != null) onSelectChangeListenner.onSelectChange();
            }
        });
        holder.binding.includeCoutent.countview.setOnCountChangeListenner(new CountView.OnCountChangeListenner() {
            @Override
            public void onCountChange(int count, int lastCount) {
                Product2 bean = results.get(holder.getLayoutPosition());
                bean.setCount(count);
                AppDatabaseManager.getInstance().updateShopCartTable(bean);
                //TODO:这里请求服务器更新数量
                if (bean.isSelect() && onSelectChangeListenner != null) {
                    onSelectChangeListenner.onSelectChange();
                }
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
                        Product2 bean = results.get(holder.getLayoutPosition());
                        AppDatabaseManager.getInstance().deleteShopCartTable(bean);
                        EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART));
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
        holder.binding.includeCoutent.countview.setEdit(true);
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

    //############## 对外方法 ################

    //这个方法代替notifyDataSetChanged()方法，主要区别在于item的添加和移除动画，该方法使用v7.DiffUtil计算数据集差异动态更新UI
    public void notifyDataSetChanged(List<Product2> product2List) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(getResults(), product2List), true);
        diffResult.dispatchUpdatesTo(this);
        getResults().clear();
        getResults().addAll(product2List);
        //数据集为空时展示空页面
        if (loadingLayout != null) {
            if (!StrUtil.isEmpty(results)){
                loadingLayout.showOut();
            }else {
                loadingLayout.showLackView();
            }
        }
    }

    public List<Product2> getSelectBeans() {
        return SelectHelper.getSelectBeans(results);
    }

    public void selectAll(boolean isSelect) {
        SelectHelper.selectAllSelectBeans(results, isSelect);
        notifyDataSetChanged();
        if (onSelectChangeListenner != null) onSelectChangeListenner.onSelectChange();
    }

    public boolean isSelectAll() {
        return SelectHelper.isSelectAll(results);
    }

    //############## 对外接口 ################

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

    private OnSelectChangeListenner onSelectChangeListenner;

    public void setOnSelectChangeListenner(OnSelectChangeListenner onSelectChangeListenner) {
        this.onSelectChangeListenner = onSelectChangeListenner;
    }

    public interface OnSelectChangeListenner {
        void onSelectChange();
    }

    //############## get & set ################

    public void setLoadingLayout(LoadingLayout loadingLayout) {
        this.loadingLayout = loadingLayout;
    }
}
