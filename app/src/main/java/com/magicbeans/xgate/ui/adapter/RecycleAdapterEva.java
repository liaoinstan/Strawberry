package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.ItemEvaBinding;
import com.magicbeans.xgate.databinding.ItemHomeSaleBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecycleAdapterEva extends RecyclerView.Adapter<RecycleAdapterEva.Holder> {

    private Context context;
    private List<Eva> results = new ArrayList<>();
    //是否需要展示推荐数据
    private boolean needRecomment = true;

    public void setNeedRecomment(boolean needRecomment) {
        this.needRecomment = needRecomment;
    }

    public List<Eva> getResults() {
        return results;
    }

    public RecycleAdapterEva(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterEva.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemEvaBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_eva, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterEva.Holder holder, final int position) {
        final Eva eva = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.binding.textZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.textZan.isSelected()) {
                    ToastUtil.showToastShort("您已经点过赞了");
                } else {
                    netZanRecomment(holder, eva.getCommentId(), 1);
                }
            }
        });
        holder.binding.textNozan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.textNozan.isSelected()) {
                    ToastUtil.showToastShort("您已经点过踩了");
                } else {
                    netZanRecomment(holder, eva.getCommentId(), 0);
                }
            }
        });
        holder.binding.textName.setText(eva.getAccName());
        holder.binding.textAttr.setText(eva.getSubject());
        holder.binding.textContent.setText(eva.getContent());
        holder.binding.textZan.setText(eva.getYesCount());
        holder.binding.textNozan.setText(eva.getNoCount());
//        holder.binding.textZan.setText("1");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemEvaBinding binding;

        public Holder(ItemEvaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            //决定是否展示推荐数据
            binding.layEvaRecomment.setVisibility(needRecomment ? View.VISIBLE : View.GONE);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

    //##############  业务方法 ################

    //ic_eva_zan_hot
    public void netZanRecomment(final Holder holder, String CommentID, final int type) {
        Map<String, Object> param = new NetParam()
                .put("CommentID", CommentID)
                .put("type", type)
                .build();
        NetApi.NI().netZanRecomment(param).enqueue(new STCallback<Integer>(Integer.class) {
            @Override
            public void onSuccess(int status, Integer count, String msg) {
//                ToastUtil.showToastShort("点赞成功");
                if (type == 1) {
                    if (holder.binding.textZan != null) holder.binding.textZan.setText(count + "");
                    if (holder.binding.textZan != null) holder.binding.textZan.setSelected(true);
                } else {
                    if (holder.binding.textNozan != null)
                        holder.binding.textNozan.setText(count + "");
                    if (holder.binding.textNozan != null)
                        holder.binding.textNozan.setSelected(true);
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
