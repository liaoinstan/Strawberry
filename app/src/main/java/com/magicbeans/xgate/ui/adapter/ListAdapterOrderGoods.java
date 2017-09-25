package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TimeUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Goods;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterOrderGoods extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Goods> results = new ArrayList<>();

    public List<Goods> getResults() {
        return results;
    }

    public ListAdapterOrderGoods(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_order_goods, parent, false);
            holder = new ViewHolder();
            holder.img_header = (ImageView) convertView.findViewById(R.id.img_header);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Goods goods = results.get(position);

        GlideUtil.loadImgTest(holder.img_header);

        return convertView;
    }

    public class ViewHolder {
        ImageView img_header;
    }
}
