package com.ins.common.view.singlepopview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.R;
import com.ins.common.entity.SinglePopBean;
import com.ins.common.helper.SelectHelper;

import java.util.List;


/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MySingleListPopupWindow extends BaseSingleListPopupWindow<SinglePopBean, MySingleListPopupWindow.ViewHolder> {

    private String firstItemTitle;

    public MySingleListPopupWindow(Context context) {
        this(context,"全部");
    }

    public MySingleListPopupWindow(Context context, String firstItemTitle) {
        super(context);
        this.firstItemTitle = firstItemTitle;
    }

    @Override
    public int getItemLayout() {
        return R.layout.pop_single_item;
    }

    @Override
    protected void setResultsRemote(List<SinglePopBean> results) {
        //添加数据头部
        SinglePopBean firstItem = new SinglePopBean(0, firstItemTitle);
        firstItem.setSelect(true);
        results.add(0, firstItem);
    }

    @Override
    public ViewHolder getViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.lay_item_pop = convertView.findViewById(R.id.lay_item_pop);
        holder.text_item_singpop_name = (TextView) convertView.findViewById(R.id.text_item_singpop_name);
        holder.check_item_singpop = (ImageView) convertView.findViewById(R.id.check_item_singpop);
        return holder;
    }

    @Override
    public void setData(ViewHolder holder, final int position, final SinglePopBean bean) {
        holder.text_item_singpop_name.setText(bean.getName());
        holder.check_item_singpop.setSelected(bean.isSelect());
        holder.lay_item_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectHelper.selectAllSelectBeans(results, false);
                bean.setSelect(!bean.isSelect());
                notifyDataSetChanged();
                dismiss();
                if (onPopSelectListener != null)
                    onPopSelectListener.onPopSelect(MySingleListPopupWindow.this, position, bean);
            }
        });
    }

    public static class ViewHolder {
        public View lay_item_pop;
        public TextView text_item_singpop_name;
        public ImageView check_item_singpop;
    }

    @Override
    public void showPopupWindow(View parent) {
        super.showPopupWindow(parent);
        if (selectTopView != null) selectTopView.setSelected(true);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (selectTopView != null) selectTopView.setSelected(false);
    }

    ////////////////////////接口

    private OnPopSelectListener onPopSelectListener;

    public void setOnPopSelectListener(OnPopSelectListener onPopSelectListener) {
        this.onPopSelectListener = onPopSelectListener;
    }

    public interface OnPopSelectListener {
        void onPopSelect(MySingleListPopupWindow pop, int position, SinglePopBean bean);
    }

    private View selectTopView;

    public void setSelectTopView(View selectTopView) {
        this.selectTopView = selectTopView;
    }
}
