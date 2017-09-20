package com.ins.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ins.common.common.ActivityCollector;

import org.greenrobot.eventbus.EventBus;

/**
 * 现在创建的Activity都继承自AppCompatActivity了，这里列出一些基础的公共方法作为基类
 * 不要在BaseXXX里封装过多的东西，但是可以封装一些非功能性的"无关紧要"的东西
 * 一定要可以“向下兼容”！否则一旦变更得不偿失
 */
@SuppressWarnings("unused")
public class CommonBaseAppCompatActivity extends AppCompatActivity{
    //双击退出
    private boolean needDoubleClickExit = false;
    private long exitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        //双击退出
        if (needDoubleClickExit) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.finish();
            }
        }else {
            super.onBackPressed();
        }
    }

    public void setNeedDoubleClickExit(boolean needDoubleClickExit) {
        this.needDoubleClickExit = needDoubleClickExit;
    }
}
