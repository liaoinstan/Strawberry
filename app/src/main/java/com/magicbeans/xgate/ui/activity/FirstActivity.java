package com.magicbeans.xgate.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ins.domain.launcher.DomainLauncher;
import com.magicbeans.xgate.BuildConfig;

//这个Activity是APP启动的第一个activity，没有任何业务逻辑，只根据APP打包类型进行不同页面跳转
//如果是debug类型跳转Domain页面进行ip等debug功能设置
//如果是release类型跳转LoadUp页面，初始化APP并启动
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            DomainLauncher.getInstance().startDomainActivity(this, LoadUpActivity.class);
        }else {
            LoadUpActivity.start(this);
        }
        finish();
    }
}
