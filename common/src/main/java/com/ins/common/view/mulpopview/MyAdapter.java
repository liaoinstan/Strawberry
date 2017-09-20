package com.ins.common.view.mulpopview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.R;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<String> results = new ArrayList<>();
    private int level;
    private int selectedPosition = -1;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results.clear();
        this.results.addAll(results);
    }

    public MyAdapter(Context context, int level) {
        this.context = context;
        this.level = level;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pop_mul_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置三角形小脚
        if (level == 1) {
            holder.imageView.setImageResource(R.drawable.pop_first);
        } else if (level == 2) {
            holder.imageView.setImageResource(R.drawable.pop_sub);
        }

        // 设置选中效果
        if (selectedPosition == position) {
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.com_blue));
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.com_text_dark_blank));
            holder.imageView.setVisibility(View.GONE);
        }


        holder.textView.setText(results.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public String getSelectedValue() {
        if (results != null && results.size() - 1 >= selectedPosition && selectedPosition >= 0) {
            return results.get(selectedPosition);
        } else {
            return "";
        }
    }

}
