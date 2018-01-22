package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.ins.common.utils.L;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.net.nethelper.NetTokenHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.Map;

//应用启动页面，这个页面可以展示广告，同时检查用户是否已留下token，如果有则进行自动登录
public class LoadUpActivity extends BaseAppCompatActivity {

    private long lasttime;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoadUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadup);

        lasttime = System.currentTimeMillis();

        Token token = AppData.App.getToken();
        if (Token.isEmpty(token)) {
            //token为空，等待2秒跳转到首页
            checkTimeGoHomeActivity();
        } else {
            //发起获取用户信息请求
            netGetUserProfile(token);
        }
    }

    private void goHomeActivity() {
        HomeActivity.start(this);
        finish();
    }


    private void checkTimeGoHomeActivity() {
        long time = System.currentTimeMillis() - lasttime;
        if (time < 2000) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goHomeActivity();
                }
            }, 2000 - time);
        } else {
            goHomeActivity();
        }
    }


    //使用token获取用户信息
    private void netGetUserProfile(Token token) {
        NetTokenHelper.getInstance().netGetUserProfile(token.getAccountID(), token.getToken(), new NetTokenHelper.UserProfileCallback() {
            @Override
            public void onSuccess(int status, User user, String msg) {
                //持久化Token和User到本地
                AppData.App.saveToken(new Token(user.getAccountID(), user.getToken()));
                AppData.App.saveUser(user);
                //跳转首页
                checkTimeGoHomeActivity();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                AppData.App.removeUser();
                checkTimeGoHomeActivity();
            }
        });
    }

}
