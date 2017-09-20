package com.ins.common.view.singlepopview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ins.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MySingleListPopupWindowTest extends BaseSinglePopupWindow {

    private ListView listView;
    private MySingleAdapter adapter;
    List<String> results;

    public void setResults(List<String> results) {
        adapter.getResults().clear();
        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }

    public MySingleListPopupWindowTest(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.pop_single;
    }

    @Override
    public void initBase() {
        listView = (ListView) getContentView().findViewById(R.id.list_pop_single);

        results = new ArrayList<>();
        adapter = new MySingleAdapter(context, results);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (onPopSingleClickListenner != null)
                    onPopSingleClickListenner.OnPopClick(adapter.getResults().get(position));
            }
        });
    }

    ////////////////////////
    //////////对外接口
    ////////////////////////


    private OnPopSingleClickListenner onPopSingleClickListenner;

    public void setOnPopSingleClickListenner(OnPopSingleClickListenner onPopSingleClickListenner) {
        this.onPopSingleClickListenner = onPopSingleClickListenner;
    }

    public interface OnPopSingleClickListenner {
        void OnPopClick(String value);
    }

    public class MySingleAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        List<String> results;

        public List<String> getResults() {
            return results;
        }

        public MySingleAdapter(Context context, List<String> results) {
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
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.pop_single_item, parent, false);
                holder = new MySingleAdapter.ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.text_item_singpop_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(results.get(position));

            return convertView;
        }

        public class ViewHolder {
            public TextView textView;
            public LinearLayout layout;
        }
    }
}
