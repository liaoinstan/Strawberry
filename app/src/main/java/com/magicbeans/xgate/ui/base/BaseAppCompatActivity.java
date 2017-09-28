package com.magicbeans.xgate.ui.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.ins.common.base.CommonBaseAppCompatActivity;
import com.ins.common.utils.StatusBarTextUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.ui.dialog.DialogLoading;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by liaoinstan on 2016/7/1 0001.
 */
public class BaseAppCompatActivity extends CommonBaseAppCompatActivity {

    protected Toolbar toolbar;
    private DialogLoading dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //禁止横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogLoading != null) dialogLoading.dismiss();
        if (eventBusSurppot) EventBus.getDefault().unregister(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setToolbar() {
        setToolbar(null, true);
    }

    public void setToolbar(boolean needback) {
        setToolbar(null, needback);
    }

    public void setToolbar(String title) {
        setToolbar(title, true);
    }

    /**
     * 把toolbar设置为""，把自定义居中文字设置为title，设置是否需要返回键
     */
    public void setToolbar(String title, boolean needback) {
        //设置toobar文字图标和返回事件
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            if (toolbar.getNavigationIcon() == null) {
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(needback);
        }
        //设置toobar居中文字
        TextView text_title = (TextView) findViewById(R.id.text_toolbar_title);
        if (text_title != null) {
            if (!TextUtils.isEmpty(title)) {
                text_title.setText(title);
            }
        }
    }

    public String getToolbarText() {
        TextView text_title = (TextView) findViewById(R.id.text_toolbar_title);
        if (text_title != null) {
            return text_title.getText().toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public final void showLoadingDialog() {
        if (dialogLoading == null) dialogLoading = new DialogLoading(this);
        dialogLoading.show();
    }

    public final void hideLoadingDialog() {
        if (dialogLoading != null) dialogLoading.hide();
    }

    ///////// event

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommonEvent(EventBean event) {
    }

    private boolean eventBusSurppot = false;

    public void registEventBus() {
        EventBus.getDefault().register(this);
        eventBusSurppot = true;
    }
}
