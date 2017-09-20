package com.ins.domain.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ins.domain.R;
import com.ins.domain.bean.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class DomainListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private Context context;
    private List<Domain> results;

    public DomainListAdapter(Context context, List<Domain> results) {
        this.context = context;
        this.results = results;
        if (this.results == null) {
            this.results = new ArrayList<>();
        }
    }

    public List<Domain> getResults() {
        return results;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        DomainListAdapter.ViewHolder hoder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.domain_item, parent, false);
            hoder = new DomainListAdapter.ViewHolder();
            hoder.text_ip = (TextView) convertView.findViewById(R.id.text_domain_ip);
            hoder.text_name = (TextView) convertView.findViewById(R.id.text_domain_name);
            convertView.setTag(hoder);
        } else {
            hoder = (DomainListAdapter.ViewHolder) convertView.getTag();
        }
        Domain domain = results.get(position);

        hoder.text_ip.setText(domain.getIp());
        hoder.text_name.setText(domain.getNote());

        return convertView;
    }

    public class ViewHolder {
        public TextView text_ip;
        public TextView text_name;
    }
}
