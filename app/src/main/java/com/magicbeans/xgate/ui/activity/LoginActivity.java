package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseAppCompatActivity {

    private ActivityLoginBinding binding;

    private ValiHelper valiHelper;

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
    }

    private void initView() {
        valiHelper = new ValiHelper(binding.textVali);
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
            case R.id.btn_toregist:
                SignUpActivity.start(this);
                finish();
                break;
            case R.id.text_vali:
                valiHelper.start();
                break;
            case R.id.btn_go:
                binding.textValinote.setText("验证码错误，请重新输入");
                break;
            case R.id.btn_qq:
                getUserInfo(QQ.NAME);
                break;
            case R.id.btn_weixin:
                getUserInfo(Wechat.NAME);
                break;
            case R.id.btn_weibo:
                getUserInfo(SinaWeibo.NAME);
                break;
            case R.id.btn_test_checkemail:
                String email = binding.editEmail.getText().toString();
                String openId = ShareSDK.getPlatform(platName).getDb().getUserId();
                netCheckEmailExist(platName, openId, email);
                break;
        }
    }


    private String platName;

    private void getUserInfo(final String platName) {
        this.platName = platName;
        //启调三方平台进行登录获取用户信息，如未授权会自动完成授权后登录，如果已经授权会读取本地数据库记录不会真正发起授权
        //只有在没有授权记录的情况下才会发起授权获取用户信息，该方法在后台线程执行并在UI线程回调，不需关心授权状态和本地数据状态，直接发起login等待回调即可
        LoginApi.with(this).setOnLoginListener(new LoginApi.OnLoginCallback() {
            @Override
            public void onLogin(String platform, UserInfo userInfo) {
                binding.textLog.append("\n获取openId成功:" + userInfo.getUserName() + ":" + userInfo.getUserId());
                netCheckOpenidExist(platform, userInfo.getUserId());
            }
        }).login(platName);
    }

    //检查服务器openId是否已经存在
    private void netCheckOpenidExist(final String platform, final String openId) {
        Map<String, Object> param = new NetParam()
                .put("openId", openId)
                .put("openIDType", ParamHelper.getOpenIDType(platform))
                .put("deviceId", "a12d5d9993241b00cf9a2070484f57ca")
                .put(ParamHelper.getDeviceType())
                .build();
        NetApi.NI().checkOpenidExist(param).enqueue(new STFormatCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                //用户已存在，直接进入登录流程
                binding.textLog.append("\nopenId已存在，直接进入登录流程");
                netGetUserProfile(user.getAccountID(), user.getToken());
            }

            @Override
            public void onError(int status, String msg) {
                if (status == 201) {
                    //用户不存在（新用户）
                    binding.textLog.append("\nopenId不存在，请输入email");
                }
            }
        });
    }

    //检查服务器email是否已经存在
    private void netCheckEmailExist(String platform, String openId, String email) {
        Map<String, Object> param = new NetParam()
                .put("openId", openId)
                .put("openIDType", ParamHelper.getOpenIDType(platform))
                .put("deviceId", "a12d5d9993241b00cf9a2070484f57ca")
                .put(ParamHelper.getDeviceType())
                .put("email", email)
                .build();
        NetApi.NI().checkOpenidExist(param).enqueue(new STFormatCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                //Email已存在，输入密码进入合并账户流程
                binding.textLog.append("\nEmail已存在，输入密码进入合并账户流程");
            }

            @Override
            public void onError(int status, String msg) {
                if (status == 201) {
                    //用户不存在（新用户）
                    binding.textLog.append("\nEmail不存在，请输入手机号进入注册流程");
                }
            }
        });
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
