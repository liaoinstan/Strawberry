package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.LoadingLayout;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.data.db.manager.ShopCartTableManager;
import com.magicbeans.xgate.databinding.ItemShopbagBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.helper.DataShopCartHelper;
import com.magicbeans.xgate.helper.DelayHelper;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.ui.view.CountView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeShopbag extends RecyclerView.Adapter<RecycleAdapterHomeShopbag.Holder> {

    private Context context;
    private List<ShopCart> results = new ArrayList<>();
    private LoadingLayout loadingLayout;
    private View textSelectAll;

    public List<ShopCart> getResults() {
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
        ShopCart bean = results.get(position);
        //由于给整个item的父view设置点击事件会导致在点击+ - 的时候容易点到外部区域，所以这里不对父view设置点击事件，而是给其中的子view设置点击事件
        setOnLayItemClickListenner(holder.binding.includeCoutent.imgHeader, holder, position);
        setOnLayItemClickListenner(holder.binding.includeCoutent.textName, holder, position);
        setOnLayItemClickListenner(holder.binding.includeCoutent.textAttr, holder, position);
        setOnLayItemClickListenner(holder.binding.includeCoutent.textPrice, holder, position);
        holder.binding.includeCoutent.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.binding.includeCoutent.imgShopbagCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(holder.getLayoutPosition());
                if (textSelectAll != null) {
                    textSelectAll.setSelected(SelectHelper.isSelectAll(results) ? true : false);
                }
            }
        });
        holder.binding.includeCoutent.countview.setOnCountChangeListenner(new CountView.OnCountChangeListenner() {
            @Override
            public boolean onCountChange(boolean byUser, final int count, int lastCount) {
                if (byUser) {
                    //TODO:如果用户连续点击+ -按钮，做一个延迟，在连续点击停止后再发起请求，而不是每一次点击都请求服务器修改数量
                    DelayHelper.getInstance().click(new DelayHelper.OnDelayCallback() {
                        @Override
                        public void onDo() {
                            ShopCart bean = ListUtil.get(results, holder.getLayoutPosition());
                            if (bean != null) {
                                bean.setQty(count);
                                DataShopCartHelper.getInstance().updateShopCart(bean);
                                if (onSelectChangeListenner != null)
                                    onSelectChangeListenner.onSelectChange(false);
                            }
                        }
                    });
                    return true;
                } else {
                    //拦截数据变化，在服务器成功更新后再改变数量
                    return true;
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
                        ShopCart bean = results.get(holder.getLayoutPosition());
                        //######### 从服务器及本地数据库移除 ############
//                        NetShopCartHelper.getInstance().netRemoveShopCart(context, bean.getProdID());
                        DataShopCartHelper.getInstance().removeShopCart(context, bean);
                    }
                });
            }
        });
        holder.binding.includeCoutent.imgShopbagCheck.setSelected(bean.isSelect());
        GlideUtil.loadImg(holder.binding.includeCoutent.imgHeader, bean.getHeaderImg());
        holder.binding.includeCoutent.textName.setText(bean.getTitleName());
        holder.binding.includeCoutent.textAttr.setText(bean.getSize());
        holder.binding.includeCoutent.textPrice.setText(AppHelper.getPriceSymbol("") + bean.getPriceFloat());
//        holder.binding.includeCoutent.textPriceOld.setText(AppHelper.getPriceSymbol("") + bean.getBagAddedPriceDifference());
        holder.binding.includeCoutent.textPriceOld.setText("比加入时降" + bean.getBagAddedPriceDifference() + "元");
        holder.binding.includeCoutent.textPriceOld.setVisibility(bean.getBagAddedPriceDifference() != 0 ? View.VISIBLE : View.INVISIBLE);
        holder.binding.includeCoutent.countview.setCount(bean.getQty());
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

    private void setOnLayItemClickListenner(View v, final RecycleAdapterHomeShopbag.Holder holder, final int position) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
    }

    //############## 对外方法 ################

    //这个方法代替notifyDataSetChanged()方法，主要区别在于item的添加和移除动画，该方法使用v7.DiffUtil计算数据集差异动态更新UI
    public void notifyDataSetChanged(List<ShopCart> product2List) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(getResults(), product2List), true);
        diffResult.dispatchUpdatesTo(this);
        getResults().clear();
        getResults().addAll(product2List);
        //数据集为空时展示空页面
        if (loadingLayout != null) {
            if (!StrUtil.isEmpty(results)) {
                loadingLayout.showOut();
            } else {
                loadingLayout.showLackView();
                loadingLayout.setClickbleBack(R.id.btn_go, new LoadingLayout.OnClickCallback() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new EventBean(EventBean.EVENT_JUMP_HOME));
                    }
                });
            }
        }
    }

    //选中某个item，会触发选择事件
    public void selectItem(int position) {
        ShopCart bean = results.get(position);
        bean.setSelect(!bean.isSelect());
        ShopCartTableManager.getInstance().update(bean);
        notifyItemChanged(position);
        if (onSelectChangeListenner != null) onSelectChangeListenner.onSelectChange(true);
    }

    public List<ShopCart> getSelectBeans() {
        return SelectHelper.getSelectBeans(results);
    }

    public List<ShopCart> getUnSelectBeans() {
        return SelectHelper.getUnSelectBeans(results);
    }

    public void selectAll(boolean isSelect) {
        SelectHelper.selectAllSelectBeans(results, isSelect);
        notifyDataSetChanged();
        if (onSelectChangeListenner != null) onSelectChangeListenner.onSelectChange(true);
    }

    public boolean isSelectAll() {
        return SelectHelper.isSelectAll(results);
    }

    public void setTextSelectAll(View textSelectAll) {
        this.textSelectAll = textSelectAll;
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
        void onSelectChange(boolean changePrice);
    }

    //############## get & set ################

    public void setLoadingLayout(LoadingLayout loadingLayout) {
        this.loadingLayout = loadingLayout;
    }
}
