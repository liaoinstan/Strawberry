package com.ins.common.helper;

import android.content.Context;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by liaoinstan on 2017/6/21.
 * 图片压缩工具
 * 使用Luban压缩
 */

public class ImgCompressHelper {
    //异步压缩一张图片
    public static void compress(final Context context, String fromPath, final Callback callback) {
        Luban.with(context)
                .load(new File(fromPath))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (callback != null) callback.onFinish(file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null) callback.onFinish("");
                    }
                }).launch();
    }

    public interface Callback {
        void onFinish(String path);
    }
}
