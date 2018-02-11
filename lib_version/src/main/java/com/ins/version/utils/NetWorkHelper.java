package com.ins.version.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检查网络状态
 */
public class NetWorkHelper {

    private Context context;
    private NetworkInfo networkInfo;

    private NetWorkHelper(Context context) {
        this.context = context;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }

    public static NetWorkHelper newInstance(Context context) {
        return new NetWorkHelper(context);
    }

    public boolean isConnected() {
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * @return 0: 无网络， 1:WIFI， 2:其他（流量）
     */
    public int getNetType() {
        if (!isConnected()) {
            return 0;
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return 1;
        } else {
            return 2;
        }
    }

    //是否是wifi状态
    public boolean isWifi() {
        return getNetType() == 1;
    }
}