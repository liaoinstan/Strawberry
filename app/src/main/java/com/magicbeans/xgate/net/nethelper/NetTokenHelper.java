package com.magicbeans.xgate.net.nethelper;

import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

import java.util.Map;

/**
 * Created by Marie on 2018/1/22.
 * 管理token刷新及获取用户信息的网络请求
 */

public class NetTokenHelper {
    private static NetTokenHelper instance;

    private NetTokenHelper() {
    }

    public static synchronized NetTokenHelper getInstance() {
        if (instance == null) instance = new NetTokenHelper();
        return instance;
    }

    /////////////////////////////////////

    //使用token获取用户信息
    public void netGetUserProfile(String accountID, String token, final UserProfileCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("accountID", accountID)
                .put("action", "get")
                .put("token", token)
                .build();
        NetApi.NI().getUserProfile(param).enqueue(new STCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                if (callback != null) callback.onSuccess(status, user, msg);
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status, msg);
            }
        });
    }

    public interface UserProfileCallback {
        void onSuccess(int status, User user, String msg);

        void onError(int status, String msg);
    }
}
