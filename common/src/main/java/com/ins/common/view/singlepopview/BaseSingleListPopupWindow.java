package com.ins.common.view.singlepopview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ins.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public abstract class BaseSingleListPopupWindow<T, H> extends BaseSinglePopupWindow {

    private ListView listView;
    private BaseSingleAdapter adapter;
    List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        adapter.getResults().clear();
        adapter.getResults().addAll(results);
        setResultsRemote(this.results);
        adapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public BaseSingleListPopupWindow(Context context) {
        super(context);
    }

    @Override
    public final int getLayout() {
        return R.layout.pop_single;
    }

    @Override
    public final void initBase() {
        listView = (ListView) getContentView().findViewById(R.id.list_pop_single);

        results = new ArrayList<>();
        adapter = new BaseSingleAdapter(context, results);
        listView.setAdapter(adapter);
    }

    protected void setResultsRemote(List<T> results) {
    }

    ////////////////////////
    //////////对外接口
    ////////////////////////

    private class BaseSingleAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        List<T> results;

        public List<T> getResults() {
            return results;
        }

        public BaseSingleAdapter(Context context, List<T> results) {
            this.context = context;
            this.results = results;
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
            H holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(getItemLayout(), parent, false);
                holder = getViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (H) convertView.getTag();
            }

            setData(holder, position, results.get(position));

            return convertView;
        }
    }

    public abstract int getItemLayout();

    public abstract H getViewHolder(View convertView);

    public abstract void setData(H holder, int position, T t);
}
