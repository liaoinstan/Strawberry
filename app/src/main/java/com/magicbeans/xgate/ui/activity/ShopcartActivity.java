package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.fragment.ShopBagFragment;

public class ShopcartActivity extends BaseAppCompatActivity {

    public static void start(Context context) {
//        if (!AppHelper.User.isLogin()) {
//            LoginActivity.start(context);
//        } else {
            Intent intent = new Intent(context, ShopcartActivity.class);
            context.startActivity(intent);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcart);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        Fragment shopbagFragment = ShopBagFragment.newInstance(0);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!shopbagFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, shopbagFragment, "shopbagFragment");
        }
        fragmentTransaction.commit();
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private void initData() {
    }
}
