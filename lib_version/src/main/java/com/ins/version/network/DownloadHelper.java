package com.ins.version.network;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.ins.version.utils.VersionUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    //广播监听下载的各个状态
    private BroadcastReceiver receiver;
    //计时器
    private ScheduledExecutorService executorService;

    public DownloadHelper isOnlyWifi(boolean isOnlyWifi) {
        this.isOnlyWifi = isOnlyWifi;
        return this;
    }

    private DownloadHelper(Context context) {
        this.context = context;
    }

    //下载apk
    public void downloadAPK(String url, OnDownloadCallback downloadCallback) {
        this.callback = downloadCallback;
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

    //注册下载完成广播监听，收到广播就移除该监听
    private void registerReceiver() {
        //启动计时器，每1秒钟查询一次进度
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int[] downloadProgress = getDownloadProgress();
                if (callback != null)
                    callback.onDownloading(downloadProgress[0], downloadProgress[1]);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        //监听下载完成广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    //下载完成，移除广播
                    context.unregisterReceiver(receiver);
                    //关闭计时器
                    executorService.shutdown();
                    //回调下载uri
                    if (callback != null) {
                        Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
                        callback.onDownloadFinish(uri);
                    }
                }
            }
        };
        context.registerReceiver(receiver, intentFilter);
    }

    //查询进度
    private int[] getDownloadProgress() {
        int[] bytesAndStatus = new int[]{-1, -1};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }

    //################# 接口回调 ###################

    public interface OnDownloadCallback {
        void onDownloadStart();

        void onDownloading(int nowBytes, int totalBytes);

        void onDownloadFinish(Uri uri);
    }
}
