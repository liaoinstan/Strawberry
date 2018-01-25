package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.category.Cate1;
import com.magicbeans.xgate.bean.category.Cate1Wrap;
import com.magicbeans.xgate.data.cache.RuntimeCache;
import com.magicbeans.xgate.databinding.ItemCateTabBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterCateTab extends RecyclerView.Adapter<RecycleAdapterCateTab.Holder> {

    private Context context;
    private List<Cate1> results = new ArrayList<>();

    public List<Cate1> getResults() {
        return results;
    }

    public RecycleAdapterCateTab(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterCateTab.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemCateTabBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cate_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterCateTab.Holder holder, final int position) {
        final Cate1 bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder, position);
                    selectItem(position);
                }
            }
        });
        holder.binding.textTitle.setText(bean.getTitle());
        holder.binding.textTitle.setSelected(bean.isSelect());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void selectItem(int position) {
        SelectHelper.selectAllSelectBeans(results, false);
        Cate1 bean = ListUtil.get(results, position);
        if (bean != null) bean.setSelect(true);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemCateTabBinding binding;

        public Holder(ItemCateTabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }


    //请求一级分类列表数据
    public void netMainCategory() {
        NetApi.NI().netMainCategory(new NetParam().build()).enqueue(new STCallback<Cate1Wrap>(Cate1Wrap.class) {
            @Override
            public void onSuccess(int status, Cate1Wrap bean, String msg) {
                List<Cate1> cate1List = bean.getMenuList();
                //加入运行时缓存
                RuntimeCache.getInstance().putCate1Cache(cate1List);
                //添加一个品牌分类
                cate1List.add(0, new Cate1(true, "品牌"));
                getResults().clear();
                getResults().addAll(cate1List);
                //默认选中第二项
                selectItem(1);
                notifyDataSetChanged();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
