package com.magicbeans.xgate.net.nethelper;

import com.ins.common.utils.L;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.HomeActivity;
import com.magicbeans.xgate.ui.activity.LoginActivity;

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
    //FIXME:这个接口非常古怪，token参数必须拼接在url上，不能以参数的形式提交否则后台无法获取正确的token，应该是后台接口取参的BUG，需要和后台进行debug调试
    public void netGetUserProfile(String accountID, String token, final UserProfileCallback callback) {
        final String url = NetApi.getBaseUrl() + "app/apiUserProfile.aspx?token=" + token;
        Map<String, Object> param = new NetParam()
                .put("accountID", accountID)
                .put("action", "get")
                .build();
        L.e(url);
        NetApi.NI().getUserProfile(url, param).enqueue(new STCallback<User>(User.class) {
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
