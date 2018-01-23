package com.magicbeans.xgate.ui.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ins.common.common.SinpleShowInAnimatorListener;
import com.ins.common.utils.App;
import com.ins.common.utils.L;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.AppUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.ActivityLoginBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.nethelper.NetTokenHelper;
import com.magicbeans.xgate.sharesdk.UserInfo;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.SignupContentController;
import com.magicbeans.xgate.ui.controller.SignupPlatformController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseAppCompatActivity {

    private ActivityLoginBinding binding;

    private SignupContentController signupContentController;
    private SignupPlatformController signupPlatformController;

    public static void start() {
        Intent intent = new Intent(App.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        StatusBarTextUtil.StatusBarLightMode(this);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        signupContentController = new SignupContentController(binding.includeContent);
        signupPlatformController = new SignupPlatformController(binding.includePlatform);
        signupContentController.hide(false);
        signupPlatformController.show(false);
        signupPlatformController.setSignupPlatCallback(new SignupPlatformController.SignupPlatCallback() {
            @Override
            public void onOpenIdExist(UserInfo userInfo, final String accountID, final String token) {
                netGetUserProfile(accountID, token);
            }

            @Override
            public void onOpenIdInExist(UserInfo userInfo) {
                signupContentController.setUserInfo(userInfo);
                signupContentController.setStatusEmailCheck();

                signupPlatformController.hide(true);
                signupContentController.show(true);
            }
        });
        signupContentController.setSignupContentCallback(new SignupContentController.SignupContentCallback() {
            @Override
            public void onAccountCreated(String accountID, String token) {
                netGetUserProfile(accountID, token);
            }
        });
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!signupPlatformController.isShow()) {
            signupPlatformController.show(true);
            signupContentController.hide(true);
        } else {
            super.onBackPressed();
        }
    }

    //使用token获取用户信息
    private void netGetUserProfile(String accountID, final String token) {
        showLoadingDialog();
        NetTokenHelper.getInstance().netGetUserProfile(accountID, token, new NetTokenHelper.UserProfileCallback() {
            @Override
            public void onSuccess(int status, User user, String msg) {
                dismissLoadingDialog();
                //持久化Token和User到本地
                user.setToken(token);
                AppData.App.saveToken(new Token(user.getAccountID(), token));
                AppData.App.saveUser(user);
                //跳转首页
                HomeActivity.start(LoginActivity.this);
                //FIXME:测试内容，把token复制到剪切板
                AppUtil.copyText(LoginActivity.this, token);
            }

            @Override
            public void onError(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showToastShort(msg);
            }
        });
    }

    //########## 测试 ###########
    public String getTestOpenId() {
        return binding.editTestOpenid.getText().toString();
    }
}
