package com.magicbeans.xgate.sharesdk;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.mob.MobApplication;
import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class LoginApi implements Callback {
    private static final int MSG_AUTH_CANCEL = 0xff01;
    private static final int MSG_AUTH_ERROR = 0xff02;
    private static final int MSG_AUTH_COMPLETE = 0xff03;

    private OnLoginCallback loginListener;
    private Context context;
    private Handler handler;

    private LoginApi() {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    public static LoginApi with(Context context) {
        LoginApi loginApi = new LoginApi();
        loginApi.context = context.getApplicationContext();
        return loginApi;
    }

    public LoginApi setOnLoginListener(OnLoginCallback login) {
        this.loginListener = login;
        return this;
    }

    //撤回授权
    public void logout(String platform) {
        Platform plat = ShareSDK.getPlatform(platform);
        if (plat != null && plat.isAuthValid()) {
            plat.removeAccount(true);
        }
    }

    //授权并获取用户数据
    public void login(String platform) {
        //初始化SDK
//        if (!(context instanceof MobApplication)) {
//            MobSDK.init(context.getApplicationContext());
//        }
        Platform plat = ShareSDK.getPlatform(platform);
        if (plat == null) {
            return;
        }
        //FIXME:获取授权之前先移除
        //plat.removeAccount(true);
        //使用SSO授权，通过客户单授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_COMPLETE;
                    msg.obj = new Object[]{plat.getName(), res};
                    handler.sendMessage(msg);
                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_ERROR;
                    msg.obj = t;
                    handler.sendMessage(msg);
                }
                t.printStackTrace();
            }

            public void onCancel(Platform plat, int action) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_CANCEL;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }
            }
        });
        plat.showUser(null);
    }

    /**
     * 处理操作结果
     */
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                // 取消
                Toast.makeText(context, "用户取消授权", Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                // 失败
                Throwable t = (Throwable) msg.obj;
                String text = "授权失败: " + t.getMessage();
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                // 成功
                Object[] objs = (Object[]) msg.obj;
                String platName = (String) objs[0];
                Platform plat = ShareSDK.getPlatform(platName);
                if (loginListener != null) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserId(plat.getDb().getUserId());
                    userInfo.setUserIcon(plat.getDb().getUserIcon());
                    userInfo.setUserName(plat.getDb().getUserName());
                    userInfo.setUserGender(plat.getDb().getUserGender());
                    loginListener.onLogin(platName, userInfo);
                }
            }
            break;
        }
        return false;
    }

    //########### 接口 ############

    public interface OnLoginCallback {
        void onLogin(String platform, UserInfo userInfo);
    }
}
