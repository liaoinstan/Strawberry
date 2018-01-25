package com.magicbeans.xgate.helper;

import android.os.Handler;

import com.dd.CircularProgressButton;

/**
 * Created by Marie on 2018/1/12.
 */

public class ProgressButtonHelper {

    public static void progError2dle(final CircularProgressButton btn_go) {
        btn_go.setProgress(-1);
        handlProgressButton(btn_go, null, 0);
    }

    public static void progOk2dle(final CircularProgressButton btn_go) {
        btn_go.setProgress(100);
        handlProgressButton(btn_go, null, 0);
    }


    public static void progError2dle(final CircularProgressButton btn_go, final ProgressCallback callback) {
        btn_go.setProgress(-1);
        handlProgressButton(btn_go, callback, 0);
    }

    public static void progOk2dle(final CircularProgressButton btn_go, final ProgressCallback callback) {
        btn_go.setProgress(100);
        handlProgressButton(btn_go, callback, 0);
    }

    public static void handlProgressButton(final CircularProgressButton btn_go, final ProgressCallback callback, final int value) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (btn_go != null) {
                    btn_go.setClickable(true);
                    btn_go.setProgress(value);
                }
                if (callback != null) {
                    callback.callback();
                }
            }
        }, 1000);
    }


    public interface ProgressCallback {
        void callback();
    }
}
