package com.magicbeans.xgate.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.DialogProductattrBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterDialogProductDetailAttr;

/**
 * liaoinstan
 * 选择商品规格弹窗
 */
public class DialogBottomProductAttr extends Dialog implements OnRecycleItemClickListener, View.OnClickListener {

    private Context context;
    private DialogProductattrBinding binding;
    private RecycleAdapterDialogProductDetailAttr adapter;

    public DialogBottomProductAttr(Context context) {
        super(context, R.style.PopupDialog);
        this.context = context;
        setMsgDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = this.getWindow();
        win.setGravity(Gravity.BOTTOM);    //从下方弹出
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    private void setMsgDialog() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_productattr, null, false);

        adapter = new RecycleAdapterDialogProductDetailAttr(context);
        adapter.setOnItemClickListener(this);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setAdapter(adapter);
        binding.recycle.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtil.dp2px(10), GridLayoutManager.HORIZONTAL, false));
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        binding.btnGo.setOnClickListener(this);

        this.setCanceledOnTouchOutside(true);    //点击外部关闭

        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                if (onSelectListenner != null) {
                    Product2 product2 = adapter.getSelectProduct();
                    product2.setCount(binding.countview.getCount());
                    if (product2 != null)
                        onSelectListenner.onSelect(product2);
                }
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Product2 product2 = adapter.getResults().get(viewHolder.getLayoutPosition());
        setSelectData(product2);
    }

    public void setData(ProductDetail productDetail) {
        adapter.getResults().clear();
        adapter.getResults().addAll(productDetail.getProds());
        adapter.notifyDataSetChanged();
        setSelectItem(productDetail.getProdID());
        setSelectData(adapter.getSelectProduct());
        setSelectCount(adapter.getSelectProduct());
    }

    private void setSelectItem(String prodId) {
        adapter.selectItem(prodId);
        adapter.notifyDataSetChanged();
    }

    private void setSelectCount(Product2 product2) {
        binding.countview.setCount(product2.getCount());
    }

    private void setSelectData(Product2 product2) {
        if (product2 != null) {
            GlideUtil.loadImg(binding.imgHeader, R.drawable.default_bk_img, product2.getHeaderImg());
            binding.textPrice.setText("¥" + product2.getShopPrice());
            String sizeText = product2.getSizeText();
            sizeText = StrUtil.subFirstChart(sizeText, "容量：").trim();
            binding.textAttr.setText("规格：" + sizeText);
        }
    }

    private OnSelectListenner onSelectListenner;

    public void setOnSelectListenner(OnSelectListenner onSelectListenner) {
        this.onSelectListenner = onSelectListenner;
    }

    public interface OnSelectListenner {
        void onSelect(Product2 product2);
    }
}
