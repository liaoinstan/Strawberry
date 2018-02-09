package com.ins.version;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ins.version.bean.UpdateInfo;
import com.ins.version.dialog.VersionDialog;
import com.ins.version.listener.OnUpdateListener;
import com.ins.version.network.HttpRequest;
import com.ins.version.utils.DownloadUtils;
import com.ins.version.utils.JsonUtil;
import com.ins.version.utils.NetWorkHelper;
import com.ins.version.utils.URLUtils;
import com.ins.version.utils.VersionUtil;

/**
 * Created by Marie on 2018/2/9.
 */

public class VersionHelper {

    public static final String TAG = "VersionHelper";

    private Context context;

    private VersionHelper(Context context) {
        this.context = context;
    }

    public static synchronized VersionHelper with(Context context) {
        return new VersionHelper(context);
    }

    /////////////////////////////////////

    private String checkUrl;
    private OnUpdateListener updateListener;

    public VersionHelper checkUrl(String checkUrl) {
        this.checkUrl = checkUrl;
        return this;
    }

    public void check() {
        AsyncCheck asyncCheck = new AsyncCheck();
        asyncCheck.execute(checkUrl);
    }

    /**
     * 检查更新任务
     */
    private class AsyncCheck extends AsyncTask<String, Integer, UpdateInfo> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (VersionHelper.this.updateListener != null) {
                VersionHelper.this.updateListener.onStartCheck();
            }
        }

        @Override
        protected UpdateInfo doInBackground(String... params) {
            UpdateInfo updateInfo = null;
            if (params.length == 0) {
                Log.e(TAG, "checkUrl不能为null");
                return null;
            }
            String url = params[0];
            if (!URLUtils.isNetworkUrl(url)) {
                Log.e(TAG, "checkUrl格式不正确");
                return null;
            }
            updateInfo = JsonUtil.toUpdateInfo(HttpRequest.get(url));
            if (updateInfo != null) {
                //如果有返回appName则使用它，否则获取当前APP应用名
                String appName = !TextUtils.isEmpty(updateInfo.getAppName()) ? updateInfo.getAppName() : VersionUtil.getAppName(context);
                updateInfo.setAppName(appName);
            }
            return updateInfo;
        }

        @Override
        protected void onPostExecute(UpdateInfo updateInfo) {
            super.onPostExecute(updateInfo);
            if (updateInfo != null) {
                if (updateInfo.getVersionCodeInt() > VersionUtil.getAppVersionCode(context)) {
                    //服务器版本号大于当前版本号，需要提示下载更新
                    showUpdateUI(updateInfo);
                } else {
                    showToast("当前已是最新版");
                }
            } else {
                showToast("检查更新失败");
            }
            if (VersionHelper.this.updateListener != null) {
                VersionHelper.this.updateListener.onFinishCheck(updateInfo);
            }
        }
    }


    /**
     * 弹出提示更新窗口
     */
    private void showUpdateUI(final UpdateInfo updateInfo) {
        if (updateInfo.getIsForce() == 0) {
            //普通更新
            final VersionDialog dialog = new VersionDialog(context, updateInfo.getChangeLog(), updateInfo.getSize(), "V" + updateInfo.getVersionName(), "立刻更新", "我再想想", VersionDialog.STYLE_UPDATE_NOMAL);
            dialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (!NetWorkHelper.newInstance(context).isWifi()) {
                        showNetDialog(updateInfo);
                    } else {
                        //开启下载任务
                        startDownload(updateInfo);
                    }
                }
            });
            dialog.show();
        } else {
            //强制更新
            final VersionDialog dialog = new VersionDialog(context, updateInfo.getChangeLog(), updateInfo.getSize(), "V" + updateInfo.getVersionName(), "立刻更新", "退出应用", VersionDialog.STYLE_UPDATE_FORCE);
            dialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (!NetWorkHelper.newInstance(context).isWifi()) {
                        showNetDialog(updateInfo);
                    } else {
                        //开启下载任务
                        startDownload(updateInfo);
                    }
                }
            });
            dialog.show();
        }
    }

    /**
     * 流量提示框，当网络为数据流量方式时，下载就会弹出此对话框提示
     */
    private void showNetDialog(final UpdateInfo updateInfo) {
        final VersionDialog dialog;
        if (updateInfo.getIsForce() == 0) {
            dialog = new VersionDialog(context, "继续下载", "取消下载", VersionDialog.STYLE_WIFI_NOMAL);
        } else {
            dialog = new VersionDialog(context, "继续下载", "退出应用", VersionDialog.STYLE_WIFI_FORCEL);
        }
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //开启下载任务
                startDownload(updateInfo);
            }
        });
        dialog.show();
    }


    private void startDownload(UpdateInfo updateInfo) {
        Toast.makeText(context, "开始下载", Toast.LENGTH_LONG).show();
        new DownloadUtils(context).downloadAPK(updateInfo.getApkUrl(), "test.apk");
    }

    //显示提示统一调用这个方法，有些场景下使用并不希望提示
    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
