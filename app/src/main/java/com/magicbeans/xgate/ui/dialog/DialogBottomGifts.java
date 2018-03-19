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
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.postbean.FreeGift;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.DialogGiftsBinding;
import com.magicbeans.xgate.databinding.DialogProductattrBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterDialogProductDetailAttr;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterGift;

import java.util.List;

/**
 * liaoinstan
 * 选择赠品弹窗
 */
public class DialogBottomGifts extends Dialog implements OnRecycleItemClickListener, View.OnClickListener {

    private Context context;
    private DialogGiftsBinding binding;
    private RecycleAdapterGift adapter;

    public DialogBottomGifts(Context context) {
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_gifts, null, false);

        adapter = new RecycleAdapterGift(context);
        adapter.setOnItemClickListener(this);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setAdapter(adapter);
        binding.recycle.addItemDecoration(new ItemDecorationDivider(context));
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.btnClose.setOnClickListener(this);

        this.setCanceledOnTouchOutside(true);    //点击外部关闭

        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
        }
    }

    public void setData(List<FreeGift> freeGifts) {
        adapter.getResults().clear();
        adapter.getResults().addAll(freeGifts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        FreeGift gift = adapter.getResults().get(viewHolder.getLayoutPosition());
        if (listener != null) {
            listener.onSelect(gift);
        }
        dismiss();
    }

    private GiftSelectListener listener;

    public void setGiftSelectListener(GiftSelectListener listener) {
        this.listener = listener;
    }

    public interface GiftSelectListener {
        void onSelect(FreeGift freeGift);
    }
}
