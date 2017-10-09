package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import com.ins.common.utils.LanguageUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class LanguageActivity extends BaseAppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radiogroup;

    public static void start(Context context) {
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, LanguageActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
    }

    private void initCtrl() {
        String language = AppData.App.getLanguage();
        switch (language) {
            case "zh":
                radiogroup.check(R.id.radio_language_zh);
                break;
            case "en":
                radiogroup.check(R.id.radio_language_en);
                break;
        }
        radiogroup.setOnCheckedChangeListener(this);
    }

    private void initData() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_language_zh:
                LanguageUtil.setLanguage(this, "zh");
                AppData.App.saveLanguage("zh");
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_LANGUAGE_CHANGE));
                break;
            case R.id.radio_language_en:
                LanguageUtil.setLanguage(this, "en");
                AppData.App.saveLanguage("en");
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_LANGUAGE_CHANGE));
                break;
        }
    }


}
