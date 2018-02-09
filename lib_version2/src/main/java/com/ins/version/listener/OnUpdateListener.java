package com.ins.version.listener;

import com.ins.version.bean.UpdateInfo;

public interface OnUpdateListener {
    public void onStartCheck();

    public void onFinishCheck(UpdateInfo info);

    public void onStartDownload();
    
    public void onDownloading(int progress);
    
    public void onFinshDownload();

    public void onInstallApk();
}
