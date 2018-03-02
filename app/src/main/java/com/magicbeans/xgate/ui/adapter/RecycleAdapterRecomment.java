package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.ShopAnimHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.TextViewUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.ItemRecommentGridBinding;
import com.magicbeans.xgate.helper.DataShopCartHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecycleAdapterRecomment extends RecyclerView.Adapter<RecycleAdapterRecomment.Holder> {

    private Context context;
    private List<Product> results = new ArrayList<>();

    public List<Product> getResults() {
        return results;
    }

    public RecycleAdapterRecomment(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterRecomment.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecycleAdapterRecomment.Holder((ItemRecommentGridBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recomment_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterRecomment.Holder holder, final int position) {
        final Product product = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailActivity.start(context, product.getProdID());
            }
        });
        holder.binding.btnAddShopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //###### 添加到服务器及本地数据库 ######
//                NetShopCartHelper.getInstance().netAddShopCart(product.getProdID(), 1);
                DataShopCartHelper.getInstance().addShopCart(product);
                //###### 飞入动画 ######
                ShopAnimHelper.newInstance().quickStart(holder.binding.imgHeader, holder.binding.btnAddShopcart, (ViewGroup) holder.itemView, product.getProductImages().getImg350Src());
            }
        });
        GlideUtil.loadImg(holder.binding.imgHeader, R.drawable.default_bk_img, product.getProductImages().getImg350Src());
        holder.binding.textName.setText(product.getProdLangName());
        holder.binding.textIntro.setText(product.getProdLangSize());
        holder.binding.textPrice.setText("¥" + product.getShopprice());
        holder.binding.textPriceOld.setText("¥" + product.getWasPrice());
        holder.binding.textPriceOld.setVisibility(!TextUtils.isEmpty(product.getWasPrice()) ? View.VISIBLE : View.INVISIBLE);
        TextViewUtil.addDelLine(holder.binding.textPriceOld);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemRecommentGridBinding binding;

        public Holder(ItemRecommentGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //##############  业务方法 ################

    //拉取推荐数据
    public void netGetRecommend() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netRecommendList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                getResults().clear();
                getResults().addAll(bean.getProductList());
                notifyDataSetChanged();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
