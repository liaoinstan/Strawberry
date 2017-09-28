package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.bean.common.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterProductAttr extends RecyclerView.Adapter<RecycleAdapterProductAttr.Holder> {

    private Context context;
    private List<KeyValue> results = new ArrayList<>();

    public List<KeyValue> getResults() {
        return results;
    }

    public RecycleAdapterProductAttr(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterProductAttr.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_attr, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterProductAttr.Holder holder, final int position) {
        final KeyValue bean = results.get(position);

        holder.text_key.setText(bean.getKey());
        holder.text_value.setText(bean.getValue());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_key;
        private TextView text_value;

        public Holder(View itemView) {
            super(itemView);
            text_key = (TextView) itemView.findViewById(R.id.text_key);
            text_value = (TextView) itemView.findViewById(R.id.text_value);
        }
    }
}
