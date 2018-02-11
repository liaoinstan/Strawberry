package com.ins.version.listener;

import android.net.Uri;

import com.ins.version.bean.UpdateInfo;

public interface OnUpdateListener {
    void onStartCheck();

    void onFinishCheck(UpdateInfo info);

    void onStartDownload();

    void onDownloading(int progress);

    void onFinshDownload(Uri uri);

    void onInstallApk();
}
