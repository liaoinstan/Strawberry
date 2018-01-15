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
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.ActivityLoginBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
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

    //获取用户信息
    //FIXME:这个接口非常古怪，token参数必须拼接在url上，不能以参数的形式提交否则后台无法获取正确的token，应该是后台接口取参的BUG，需要和后台进行debug调试
    private void netGetUserProfile(String accountID, String token) {
        String url = NetApi.getBaseUrl() + "app/apiUserProfile.aspx?token=" + token;
        showLoadingDialog();
        Map<String, Object> param = new NetParam()
                .put("accountID", accountID)
                .put("action", "get")
                .build();
        L.e(url);
        NetApi.NI().getUserProfile(url, param).enqueue(new STCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                dismissLoadingDialog();
                ToastUtil.showToastShort(user.toString());
                L.e(user.toString());
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
