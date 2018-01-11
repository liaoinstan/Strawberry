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
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.databinding.ActivityLoginBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.SignupContentController;
import com.magicbeans.xgate.ui.controller.SignupPlatformController;

import java.util.Map;

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
        signupContentController.setLogTextView(binding.textLog);
        signupPlatformController.setLogTextView(binding.textLog);
        signupContentController.hide(false);
        signupPlatformController.show(false);
        signupPlatformController.setSignupPlatCallback(new SignupPlatformController.SignupPlatCallback() {
            @Override
            public void onOpenIdExist(String openId, String accountID, String token) {
                netGetUserProfile(accountID, token);
            }

            @Override
            public void onOpenIdInExist(String openId, String platform) {
                signupContentController.setPlatform(platform);
                signupContentController.setStatusEmailCheck();

                signupPlatformController.hide(true);
                signupContentController.show(true);
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
                signupPlatformController.show(true);
                signupContentController.hide(true);
                break;
        }
    }

    //TODO:该接口服务器500异常，等待反馈
    //获取用户信息
    private void netGetUserProfile(String accountID, String token) {
        Map<String, Object> param = new NetParam()
                .put("accountID", accountID)
                .put("action", "get")
                .put("token", token)
                .build();
        NetApi.NI().getUserProfile(param).enqueue(new STCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                ToastUtil.showToastShort("XXXXXXX" + user.toString());
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
