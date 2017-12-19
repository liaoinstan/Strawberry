package com.magicbeans.xgate.ui.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.utils.DensityUtil;
import com.ins.common.view.singlepopview.BaseRecyclePopupWindow;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.PopBean;

/**
 * Created by Administrator on 2017/9/27.
 */

public class MyGridPopupWindow extends BaseRecyclePopupWindow<PopBean, MyGridPopupWindow.Holder> {

    public MyGridPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(5, DensityUtil.dp2px(10), GridLayoutManager.VERTICAL, true));
    }

    @Override
    public Holder getViewHolder(ViewGroup parent, int viewType) {
        return new MyGridPopupWindow.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_single_item, parent, false));
    }

    @Override
    public void setData(Holder holder, PopBean popBean, int position) {
        holder.text_item_singpop_name.setText(popBean.getName());
    }

    @Override
    protected void onPopItemClick(PopBean popBean, int position) {
        if (onGridPopItemClick != null) onGridPopItemClick.onItemClick(this, popBean, position);
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_item_singpop_name;

        public Holder(View itemView) {
            super(itemView);
            text_item_singpop_name = (TextView) itemView.findViewById(com.magicbeans.xgate.R.id.text_item_singpop_name);
        }
    }


    private OnGridPopItemClick onGridPopItemClick;

    public void setOnGridPopItemClick(OnGridPopItemClick onGridPopItemClick) {
        this.onGridPopItemClick = onGridPopItemClick;
    }

    public interface OnGridPopItemClick {
        void onItemClick(BaseRecyclePopupWindow contain, PopBean popBean, int position);
    }
}
