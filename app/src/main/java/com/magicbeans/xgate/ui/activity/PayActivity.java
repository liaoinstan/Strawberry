package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.ActivityPayBinding;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class PayActivity extends BaseAppCompatActivity {

    private ActivityPayBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, PayActivity.class);
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                PayResultActivity.start(this);
                finish();
                break;
        }
    }
}
