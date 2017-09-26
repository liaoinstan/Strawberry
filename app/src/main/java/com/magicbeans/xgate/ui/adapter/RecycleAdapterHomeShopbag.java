package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.ui.view.CountView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeShopbag extends RecyclerView.Adapter<RecycleAdapterHomeShopbag.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();
    private boolean isEdit = false;

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterHomeShopbag(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterHomeShopbag.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopbag, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterHomeShopbag.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.img_shopbag_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setSelect(!bean.isSelect());
                notifyItemChanged(position);
            }
        });
        holder.btn_item_shopbag_favo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastShort("收藏");
            }
        });
        holder.btn_item_shopbag_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSure.showDialog(context, "确定要删除该商品？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                    }
                });
            }
        });
        holder.img_shopbag_check.setSelected(bean.isSelect());
        GlideUtil.loadImgTestByPosition(holder.img_header, position);
        holder.countview.setCount(1);
        holder.countview.setEdit(isEdit);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_header;
        private ImageView img_shopbag_check;
        private CountView countview;
        private TextView btn_item_shopbag_favo;
        private TextView btn_item_shopbag_del;

        public Holder(View itemView) {
            super(itemView);
            img_header = (ImageView) itemView.findViewById(R.id.img_header);
            img_shopbag_check = (ImageView) itemView.findViewById(R.id.img_shopbag_check);
            countview = (CountView) itemView.findViewById(R.id.countview);
            btn_item_shopbag_favo = (TextView) itemView.findViewById(R.id.btn_item_shopbag_favo);
            btn_item_shopbag_del = (TextView) itemView.findViewById(R.id.btn_item_shopbag_del);
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
