package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.Map;

public class LoadUpActivity extends BaseAppCompatActivity {

    private long lasttime;
    private String token;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoadUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadup);

        lasttime = System.currentTimeMillis();

        //TODO:这里进行自动登录和数据初始化，如果没有token的情况下2秒后跳转首页，目前没有API借口对接，所以直接跳转
        //等待2秒 去首页
        checkTimeGoHomeActivity();
    }

    private void goHomeActivity() {
        HomeActivity.start(LoadUpActivity.this);
        finish();
    }


    private void checkTimeGoHomeActivity() {
        long time = System.currentTimeMillis() - lasttime;
        if (time < 2000) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goHomeActivity();
                }
            }, 2000 - time);
        } else {
            goHomeActivity();
        }
    }

}
