package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.address.City;
import com.magicbeans.xgate.bean.address.District;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marie on 2018/3/5.
 */

public class SpinnerDistrictAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<District> results = new ArrayList<>();

    public List<District> getResults() {
        return results;
    }

    public SpinnerDistrictAdapter(Context pContext) {
        this.context = pContext;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.lay_spinner_item, parent, false);
            holder = new ViewHolder();
            holder.text_title = convertView.findViewById(R.id.text_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final District district = results.get(position);

        holder.text_title.setText(district.getName());

        return convertView;
    }

    public class ViewHolder {
        TextView text_title;
    }

    public int setPositionByName(String name) {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getName().equals(name.trim())) {
                return i;
            }
        }
        return 0;
    }
}
