package com.ins.common.view.mulpopview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ins.common.R;


public class SubAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String[] results;

    public void setResults(String[] results) {
        this.results = results;
    }

    public String[] getResults() {
        return results;
    }

    public SubAdapter(Context context, String[] results) {
        this.context = context;
        this.results = results;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return results.length;
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.pop_mul_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(results[position]);

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

}
