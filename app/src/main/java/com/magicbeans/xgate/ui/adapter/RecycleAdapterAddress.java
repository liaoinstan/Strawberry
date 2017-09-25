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
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Address;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterAddress extends RecyclerView.Adapter<RecycleAdapterAddress.Holder> {

    private Context context;
    private List<Address> results = new ArrayList<>();

    public List<Address> getResults() {
        return results;
    }

    public RecycleAdapterAddress(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterAddress.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterAddress.Holder holder, final int position) {
        final Address address = results.get(position);
        holder.img_item_address_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onEditClick(holder);
            }
        });
        holder.text_item_address_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onDelClick(holder);
            }
        });
        holder.check_item_address_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectHelper.selectAllSelectBeans(results,false);
                address.setSelect(true);
                notifyDataSetChanged();
            }
        });

        holder.check_item_address_default.setSelected(address.isSelect());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_item_address_edit;
        private TextView check_item_address_default;
        private TextView text_item_address_del;

        public Holder(View itemView) {
            super(itemView);
            img_item_address_edit = (ImageView) itemView.findViewById(R.id.img_item_address_edit);
            check_item_address_default = (TextView) itemView.findViewById(R.id.check_item_address_default);
            text_item_address_del = (TextView) itemView.findViewById(R.id.text_item_address_del);
        }
    }

    private OnAddressBtnClickListener listener;

    public void setOnAddressBtnClickListener(OnAddressBtnClickListener listener) {
        this.listener = listener;
    }

    public interface OnAddressBtnClickListener {
        void onDelClick(Holder holder);
        void onEditClick(Holder holder);
    }
}
