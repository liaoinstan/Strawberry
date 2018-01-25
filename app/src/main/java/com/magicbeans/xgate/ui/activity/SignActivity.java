package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class SignActivity extends BaseAppCompatActivity {


    public static void start(Context context) {
        Intent intent = new Intent(context, SignActivity.class);
        context.startActivity(intent);
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, SuggestActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
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
    }

    private void initData() {
    }
}
