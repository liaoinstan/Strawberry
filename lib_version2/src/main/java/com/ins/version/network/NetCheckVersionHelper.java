package com.ins.version.network;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.ins.version.VersionHelper;
import com.ins.version.bean.UpdateInfo;
import com.ins.version.listener.OnUpdateListener;
import com.ins.version.utils.JsonUtil;
import com.ins.version.utils.URLUtils;
import com.ins.version.utils.VersionUtil;

import static com.ins.version.VersionHelper.TAG;

/**
 * 检查更新信息网络请求帮助类，由于只有一个请求为了精简类库不再引入三方请求框架，直接使用AsyncTask + HttpsURLConnection方式发起请求
 */

public class NetCheckVersionHelper {

    public static NetCheckVersionHelper newInstance() {
        return new NetCheckVersionHelper();
    }

    private NetCheckVersionHelper() {
    }

    public void onCheck(String checkUrl, OnCheckVersionCallback onCheckVersionCallback) {
        AsyncCheck asyncCheck = new AsyncCheck(onCheckVersionCallback);
        asyncCheck.execute(checkUrl);
    }

    /**
     * 检查更新任务
     */
    private static class AsyncCheck extends AsyncTask<String, Integer, UpdateInfo> {

        private OnCheckVersionCallback onCheckVersionCallback;

        public AsyncCheck(OnCheckVersionCallback onCheckVersionCallback) {
            this.onCheckVersionCallback = onCheckVersionCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (onCheckVersionCallback != null) {
                onCheckVersionCallback.onStartCheck();
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
            return updateInfo;
        }

        @Override
        protected void onPostExecute(UpdateInfo updateInfo) {
            super.onPostExecute(updateInfo);
            if (updateInfo != null) {
                if (onCheckVersionCallback != null) onCheckVersionCallback.onSuccess(updateInfo);
            } else {
                if (onCheckVersionCallback != null) onCheckVersionCallback.onError("检查更新失败");
            }
        }
    }

    public interface OnCheckVersionCallback {

        void onStartCheck();

        void onSuccess(UpdateInfo updateInfo);

        void onError(String msg);
    }
}
