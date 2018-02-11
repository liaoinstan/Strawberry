package com.ins.version;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ins.version.bean.UpdateInfo;
import com.ins.version.dialog.MsgDialog;
import com.ins.version.dialog.ProgressDialog;
import com.ins.version.dialog.VersionDialog;
import com.ins.version.listener.OnUpdateListener;
import com.ins.version.network.DownloadHelper;
import com.ins.version.network.NetCheckVersionHelper;
import com.ins.version.utils.NetWorkHelper;
import com.ins.version.utils.StorageHelper;
import com.ins.version.utils.VersionUtil;

/**
 * Created by liaoinstan on 2018/2/9.
 */

public class VersionHelper {

    public static final String TAG = "VersionHelper";

    private Context context;
    private DownloadHelper downloadHelper;

    private VersionHelper(Context context) {
        this.context = context;
    }

    public static synchronized VersionHelper with(Context context) {
        return new VersionHelper(context);
    }

    /////////////////////////////////////

    private String checkUrl;
    private OnUpdateListener updateListener;
    //忽略标志是否可用（用户把某版本设置为忽略后不会再该提示版本的更新，这个字段设置为false将会使忽略标志不可用，依然执行更新提示，一般首页检查更新设置为true，设置中检查更新设为false）
    private boolean ignoreEnable = true;
    private boolean isShowToast = true;

    public VersionHelper ignoreEnable(boolean ignoreEnable) {
        this.ignoreEnable = ignoreEnable;
        return this;
    }

    public VersionHelper isShowToast(boolean isShowToast) {
        this.isShowToast = isShowToast;
        return this;
    }

    public VersionHelper checkUrl(String checkUrl) {
        this.checkUrl = checkUrl;
        return this;
    }

    public VersionHelper check() {
        return check(null);
    }

    public VersionHelper check(OnUpdateListener listener) {
        this.updateListener = listener;
        NetCheckVersionHelper.newInstance().onCheck(checkUrl, new NetCheckVersionHelper.OnCheckVersionCallback() {
            @Override
            public void onStartCheck() {
                if (updateListener != null) updateListener.onStartCheck();
            }

            @Override
            public void onSuccess(UpdateInfo updateInfo) {
                //如果有返回appName则使用它，否则获取当前APP应用名
                String appName = !TextUtils.isEmpty(updateInfo.getAppName()) ? updateInfo.getAppName() : VersionUtil.getAppName(context);
                updateInfo.setAppName(appName);
//                //TODO:测试代码
//                updateInfo.setIsAutoInstall(0);
//                updateInfo.setIsForce(1);
                if ((StorageHelper.with(context).getIgnoreVersionCode() == updateInfo.getVersionCodeInt()) && ignoreEnable) {
                    //如果该版本已经被用户设置为忽略，则不进行更新提示
                    return;
                }
                if (updateInfo.getVersionCodeInt() > VersionUtil.getAppVersionCode(context)) {
                    //服务器版本号大于当前版本号，需要提示下载更新
                    showUpdateUI(updateInfo);
                } else {
                    showToast("当前已是最新版");
                }
                if (updateListener != null) updateListener.onFinishCheck(updateInfo);
            }

            @Override
            public void onError(String msg) {
                showToast("检查更新失败");
                if (updateListener != null) updateListener.onFinishCheck(null);
            }
        });
        return this;
    }

    private ProgressDialog progressDialog;

    private void startDownload(final UpdateInfo updateInfo, boolean isOnlyWifi) {
        downloadHelper = DownloadHelper.newInstance(context);
        downloadHelper.isOnlyWifi(isOnlyWifi);
        downloadHelper.downloadAPK(updateInfo.getApkUrl(), new DownloadHelper.OnDownloadCallback() {
            @Override
            public void onDownloadStart() {
                if (updateInfo.isForce()) {
                    //强制更新时在下载时显示进度弹窗，更新完后显示安装弹窗
                    progressDialog = new ProgressDialog(context);
                    progressDialog.show();
                }
                if (updateListener != null) updateListener.onStartDownload();
            }

            @Override
            public void onDownloadFinish(final Uri uri) {
                if (updateInfo.isForce() && progressDialog != null) {
                    //强制更新时在下载时显示进度弹窗，更新完后显示安装弹窗
                    progressDialog.dismiss();
                    MsgDialog installDialog = MsgDialog.createInstallDialog(context);
                    installDialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            installApk(uri);
                        }
                    });
                    installDialog.show();
                }
                if (updateListener != null) updateListener.onFinshDownload(uri);
                if (updateInfo.isAutoInstall()) {
                    installApk(uri);
                }
            }
        });
    }

    /**
     * 弹出提示更新窗口
     */
    private void showUpdateUI(final UpdateInfo updateInfo) {
        final VersionDialog dialog = new VersionDialog(context, updateInfo, ignoreEnable);
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!NetWorkHelper.newInstance(context).isWifi()) {
                    showNetDialog(updateInfo);
                } else {
                    //开启下载任务
                    startDownload(updateInfo, true);
                }
            }
        });
        dialog.show();
    }

    /**
     * 流量提示框，当网络为数据流量方式时，下载就会弹出此对话框提示
     */
    private void showNetDialog(final UpdateInfo updateInfo) {
        final MsgDialog dialog = MsgDialog.createWifiDialog(context, updateInfo.isForce());
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //开启下载任务
                startDownload(updateInfo, false);
            }
        });
        dialog.show();
    }

    //显示提示统一调用这个方法，有些场景下使用并不希望提示
    private void showToast(String msg) {
        if (isShowToast) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void installApk(Uri uri) {
        if (updateListener != null) updateListener.onInstallApk();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //由于没有在Activity环境下启动Activity,设置FLAG_ACTIVITY_NEW_TASK标签
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
