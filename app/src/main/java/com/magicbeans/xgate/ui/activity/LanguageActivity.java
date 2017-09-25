package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

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
        radiogroup.setOnCheckedChangeListener(this);
    }

    private void initData() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_language_ch:
                break;
            case R.id.radio_language_en:
                break;
        }
    }
}
