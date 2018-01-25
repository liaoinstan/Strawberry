package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class SafeActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, SafeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        findViewById(R.id.text_safe_psw).setOnClickListener(this);
        findViewById(R.id.text_safe_phone).setOnClickListener(this);
        findViewById(R.id.text_safe_email).setOnClickListener(this);
        findViewById(R.id.text_safe_account).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_safe_psw:
                break;
            case R.id.text_safe_phone:
                break;
            case R.id.text_safe_email:
                break;
            case R.id.text_safe_account:
                break;
        }
    }
}
