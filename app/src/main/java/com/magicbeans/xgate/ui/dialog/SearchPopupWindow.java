package com.magicbeans.xgate.ui.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.view.singlepopview.BaseRecyclePopupWindow;
import com.magicbeans.xgate.R;

/**
 * Created by Administrator on 2017/9/27.
 */

public class SearchPopupWindow extends BaseRecyclePopupWindow<String, SearchPopupWindow.Holder> {

    public SearchPopupWindow(Context context) {
        super(context);
        //不获取焦点，防止edittext丢失焦点，导致软键盘消失
        setFocusable(false);
        //设置点击外部不消失
        setOutsideTouchable(false);
        //设置软键盘弹出模式，防止popupwindow过长导致遮挡软键盘
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置popupwindow不需要显示时背景变暗
        setNeedanim(false);
    }

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ItemDecorationDivider(context, LinearLayoutManager.VERTICAL));
    }

    @Override
    public Holder getViewHolder(ViewGroup parent, int viewType) {
        return new SearchPopupWindow.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_search_item, parent, false));
    }

    @Override
    public void setData(Holder holder, String popBean, int position) {
        holder.text_item_singpop_name.setText(popBean);
    }

    @Override
    protected void onPopItemClick(String popBean, int position) {
        if (onSearchKeyClickListener != null)
            onSearchKeyClickListener.onKeyClick(popBean, position);
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_item_singpop_name;

        public Holder(View itemView) {
            super(itemView);
            text_item_singpop_name = (TextView) itemView.findViewById(R.id.text_item_singpop_name);
        }
    }

    @Override
    public void showPopupWindow(View parent) {
        if (!isShowing()) super.showPopupWindow(parent);
    }

    //################ 对外接口 ##################


    private OnSearchKeyClickListener onSearchKeyClickListener;

    public void setOnSearchKeyClickListener(OnSearchKeyClickListener onSearchKeyClickListener) {
        this.onSearchKeyClickListener = onSearchKeyClickListener;
    }

    public interface OnSearchKeyClickListener {
        void onKeyClick(String key, int position);
    }
}
