package com.magicbeans.xgate.helper;

import android.os.Handler;

import com.magicbeans.xgate.net.nethelper.NetAddressHelper;

/**
 * Created by Administrator on 2018/2/8.
 * 调用click方法后将会在500ms后调用onDo()方法，如果连续点击，将刷新时间，只调用一次onDo方法
 */

public class DelayHelper {
    private static DelayHelper instance;

    private DelayHelper() {
        handler = new Handler();
    }

    public static synchronized DelayHelper getInstance() {
        if (instance == null) instance = new DelayHelper();
        return instance;
    }

    /////////////////////////////////////

    private Handler handler;
    private Runnable runnable;

    public void click(final OnDelayCallback callback) {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                if (callback != null) callback.onDo();
            }
        };
        handler.postDelayed(runnable, 500);
    }

    public interface OnDelayCallback {
        void onDo();
    }
}
