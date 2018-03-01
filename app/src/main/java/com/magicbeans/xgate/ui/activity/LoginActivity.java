package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.ins.common.utils.App;
import com.ins.common.utils.StatusBarUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.AppUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.ActivityLoginBinding;
import com.magicbeans.xgate.net.nethelper.NetTokenHelper;
import com.magicbeans.xgate.sharesdk.UserInfo;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.SignupContentController;
import com.magicbeans.xgate.ui.controller.SignupPlatformController;

import org.greenrobot.eventbus.EventBus;

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
//        StatusBarTextUtil.StatusBarLightMode(this);
        StatusBarUtil.setTextDark(this);
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
                netGetUserProfile(accountID, token, userInfo.getOpenId());
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
            public void onAccountCreated(String accountID, String token, String openId) {
                netGetUserProfile(accountID, token, openId);
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
    private void netGetUserProfile(final String accountID, final String token, final String openId) {
        showLoadingDialog();
        NetTokenHelper.getInstance().netGetUserProfile(accountID, token, new NetTokenHelper.UserProfileCallback() {
            @Override
            public void onSuccess(int status, User user, String msg) {
                dismissLoadingDialog();
                //持久化Token和User到本地
                user.setToken(token);
                AppData.App.saveToken(new Token(user.getAccountID(), token));
                AppData.App.saveUser(user);
                //存储OpenId
                AppData.App.saveOpenId(openId);
                //post登录消息
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_LOGIN));
                //跳转首页
                HomeActivity.start(LoginActivity.this);
                ToastUtil.showToastShort("登录成功");
                //FIXME:测试内容，把token复制到剪切板
                AppUtil.copyText(LoginActivity.this, "accountID:" + accountID + "\ntoken:" + token);
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
