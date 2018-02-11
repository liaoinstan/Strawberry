package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ClearCacheUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.version.utils.VersionUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.ActivitySettingBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivitySettingBinding binding;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
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
    }

    private void initCtrl() {
        binding.textSettingCatchsize.setText(ClearCacheUtil.getAppCacheSize(this));
        binding.textSettingVersionName.setText(VersionUtil.getVersion(this));
        binding.laySettingLogout.setVisibility(AppHelper.User.isLogin() ? View.VISIBLE : View.GONE);
    }

    private void initData() {
        User user = AppData.App.getUser();
        if (user != null) {
            binding.textName.setText("你好");
            binding.textSign.setText(user.getNickname());
            GlideUtil.loadCircleImg(binding.imgMeHeader, R.drawable.default_header, user.getHeadImageURL());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_setting_header:
                MeDetailActivity.start(this);
                break;
            case R.id.text_setting_safe:
                SafeActivity.start(this);
                break;
            case R.id.text_setting_msgsetting:
                MsgSettingActivity.start(this);
                break;
            case R.id.text_setting_language:
                LanguageActivity.start(this);
                break;
            case R.id.text_setting_address:
                AddressActivity.start(this);
                break;
            case R.id.text_setting_email:
                break;
            case R.id.text_setting_suggest:
                SuggestActivity.start(this);
                break;
            case R.id.text_setting_version:
                VersionActivity.start(this);
                break;
            case R.id.text_setting_about:
                WebActivity.start(this, "关于我们", "https://cn.strawberrynet.com/m/mcompanydetail.aspx");
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
                        binding.textSettingCatchsize.setText(ClearCacheUtil.getAppCacheSize(SettingActivity.this));
                    }
                });
                break;
            case R.id.text_setting_logout:
                DialogSure.showDialog(this, "确定要退出登录？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        AppData.App.removeToken();
                        AppData.App.removeUser();
                        EventBus.getDefault().post(new EventBean(EventBean.EVENT_LOGOUT));
                        HomeActivity.start(SettingActivity.this);
                    }
                });
                break;
            case R.id.text_test_adyen:
                PayTestAdyenActivity.start(this);
                break;
        }
    }
}
