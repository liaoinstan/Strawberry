package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ins.common.helper.ValiHelper;
import com.ins.common.utils.App;
import com.ins.common.utils.L;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.databinding.ActivityLoginBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.ParamHelper;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.sharesdk.LoginApi;
import com.magicbeans.xgate.sharesdk.UserInfo;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.SignupContentController;
import com.magicbeans.xgate.ui.controller.SignupPlatformController;

import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
        signupPlatformController.setSignupPlatCallback(new SignupPlatformController.SignupPlatCallback() {
            @Override
            public void onOpenIdExist(String openId, String accountID, String token) {
                netGetUserProfile(accountID, token);
            }

            @Override
            public void onOpenIdInExist(String openId, String platform) {
                signupContentController.setPlatform(platform);
                signupContentController.setVisibleEmail();
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
                finish();
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
