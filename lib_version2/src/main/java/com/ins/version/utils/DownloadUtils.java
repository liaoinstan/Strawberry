package com.ins.version.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ins.version.VersionHelper;

/**
 * Created by Marie on 2018/2/9.
 */

public class DownloadUtils {
    //下载器
    private DownloadManager downloadManager;
    //上下文
    private Context context;
    //下载的ID
    private long downloadId;

    public DownloadUtils(Context context) {
        this.context = context;
    }

    //下载apk
    public void downloadAPK(String url, String name) {

        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("新版本Apk");
        request.setDescription("Apk Downloading");
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径
        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath(), name);

        //获取DownloadManager
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };


    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    Log.e(VersionHelper.TAG, "下载暂停");
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    Log.e(VersionHelper.TAG, "下载延迟");
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    int bytesDownload = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    float lv = (float) bytesDownload / totalSize;
                    Log.e(VersionHelper.TAG, "正在下载" + lv);
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.e(VersionHelper.TAG, "下载完成");
                    //下载完成安装APK
                    installAPK();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Log.e(VersionHelper.TAG, "下载失败");
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        cursor.close();
    }

    //下载到本地后执行安装
    private void installAPK() {
        Toast.makeText(context, "开始安装", Toast.LENGTH_SHORT).show();
        Log.e(VersionHelper.TAG, "开始安装");
//        Uri downloadFileUri = downloadManager.getUriForDownloadedFile(downloadId);
//        if (downloadFileUri != null) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//            context.unregisterReceiver(receiver);
//        }
    }
}
