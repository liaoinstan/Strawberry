package com.magicbeans.xgate.ui.base;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class BaseFragment extends Fragment {

    protected Toolbar toolbar;

    public void setToolbar() {
        setToolbar(null, true);
    }

    public void setToolbar(boolean needback) {
        setToolbar(null, needback);
    }

    public void setToolbar(String title) {
        setToolbar(title, false);
    }

    public void setToolbar(String title, boolean needback) {
        View root = getView();
        if (root == null) {
            return;
        }
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        if (toolbar != null) {
            if (toolbar.getNavigationIcon() == null && needback) {
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }
            toolbar.setTitle("");
            //设置toobar居中文字
            TextView text_title = (TextView) root.findViewById(R.id.text_toolbar_title);
            if (text_title != null) {
                if (!TextUtils.isEmpty(title)) {
                    text_title.setText(title);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommonEvent(EventBean event) {
    }

    private boolean eventBusSurppot = false;

    public void registEventBus() {
        EventBus.getDefault().register(this);
        eventBusSurppot = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (eventBusSurppot) EventBus.getDefault().unregister(this);
    }
}
