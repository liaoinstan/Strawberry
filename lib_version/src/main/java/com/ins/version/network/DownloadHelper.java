package com.ins.version.network;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import com.ins.version.utils.VersionUtil;

/**
 * 下载帮助类，使用DownLoadManager进行下载
 */

public class DownloadHelper {

    public static DownloadHelper newInstance(Context context) {
        return new DownloadHelper(context);
    }

    //下载器
    private DownloadManager downloadManager;
    //上下文
    private Context context;
    //下载的ID
    private long downloadId;
    //只在wifi下更新
    private boolean isOnlyWifi = true;
    //回调
    private OnDownloadCallback callback;

    public DownloadHelper isOnlyWifi(boolean isOnlyWifi) {
        this.isOnlyWifi = isOnlyWifi;
        return this;
    }

    private DownloadHelper(Context context) {
        this.context = context;
    }

    //下载apk
    public void downloadAPK(String url, OnDownloadCallback callback) {
        this.callback = callback;
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(!isOnlyWifi);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("新版本Apk");
        request.setDescription("Apk Downloading");
        //如果我们希望下载的文件可以被系统的Downloads应用扫描到并管理
        request.setVisibleInDownloadsUi(true);
        //设置下载的路径
//        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
//        request.setDestinationInExternalFilesDir(context, "phoenix", "phoenix.apk");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, VersionUtil.getAppName(context) + ".apk");
        //获取DownloadManager
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);
        if (callback != null) callback.onDownloadStart();

        //注册广播接收者，监听下载状态
        registerReceiver();
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver;

    //注册下载完成广播监听，收到广播就移除该监听
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    //下载完成，移除广播
                    context.unregisterReceiver(receiver);
                    if (callback != null) {
                        //获取URI开始安装
                        Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
                        callback.onDownloadFinish(uri);
                    }
                }
            }
        };
        context.registerReceiver(receiver, intentFilter);
    }

    public interface OnDownloadCallback {
        void onDownloadStart();

        void onDownloadFinish(Uri uri);
    }
}
