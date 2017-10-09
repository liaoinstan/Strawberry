package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ClearCacheUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.shelwee.update.utils.VersionUtil;

public class SettingActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private TextView text_setting_catchsize;
    private TextView text_setting_version_name;
    private View lay_setting_logout;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_LANGUAGE_CHANGE) {
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        registEventBus();
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        text_setting_catchsize = (TextView) findViewById(R.id.text_setting_catchsize);
        text_setting_version_name = (TextView) findViewById(R.id.text_setting_version_name);
        lay_setting_logout = findViewById(R.id.lay_setting_logout);
        findViewById(R.id.text_setting_safe).setOnClickListener(this);
        findViewById(R.id.text_setting_msgsetting).setOnClickListener(this);
        findViewById(R.id.text_setting_language).setOnClickListener(this);
        findViewById(R.id.text_setting_suggest).setOnClickListener(this);
        findViewById(R.id.text_setting_version).setOnClickListener(this);
        findViewById(R.id.text_setting_about).setOnClickListener(this);
        findViewById(R.id.text_setting_clear).setOnClickListener(this);
        findViewById(R.id.text_setting_logout).setOnClickListener(this);
    }

    private void initCtrl() {
        text_setting_catchsize.setText(ClearCacheUtil.getAppCacheSize(this));
        text_setting_version_name.setText(VersionUtil.getVersion(this));
        if (AppData.App.getUser() == null) {
            lay_setting_logout.setVisibility(View.GONE);
        } else {
            lay_setting_logout.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_setting_safe:
                SafeActivity.start(this);
                break;
            case R.id.text_setting_msgsetting:
                MsgSettingActivity.start(this );
                break;
            case R.id.text_setting_language:
                LanguageActivity.start(this );
                break;
            case R.id.text_setting_suggest:
                SuggestActivity.start(this );
                break;
            case R.id.text_setting_version:
                VersionActivity.start(this);
                break;
            case R.id.text_setting_about:
                WebActivity.start(this, "关于我们", "https://www.bing.com");
                break;
            case R.id.text_setting_clear:
                if (ClearCacheUtil.getAppCacheSizeValue(this) == 0) {
                    ToastUtil.showToastShort("没有需要清除的缓存");
                    return;
                }
                DialogSure.showDialog(this, "清除缓存将会删除您应用内的本地图片及数据且无法恢复，确认继续？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        ClearCacheUtil.clearAPPCache(SettingActivity.this);
                        text_setting_catchsize.setText(ClearCacheUtil.getAppCacheSize(SettingActivity.this));
                    }
                });
                break;
            case R.id.text_setting_logout:
                DialogSure.showDialog(this, "确定要退出登录？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        LoginActivity.start(SettingActivity.this);
                    }
                });
                break;
        }
    }
}
