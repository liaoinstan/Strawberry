package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.databinding.LaySignupPlatformBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.ParamHelper;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.sharesdk.LoginApi;
import com.magicbeans.xgate.sharesdk.UserInfo;

import java.util.Map;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Administrator on 2017/10/11.
 */

public class SignupPlatformController implements View.OnClickListener {

    private Context context;
    private LaySignupPlatformBinding binding;

    public SignupPlatformController(LaySignupPlatformBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        binding.btnQq.setOnClickListener(this);
        binding.btnWeixin.setOnClickListener(this);
        binding.btnWeibo.setOnClickListener(this);
    }

    public void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qq:
                getUserInfo(QQ.NAME);
                break;
            case R.id.btn_weixin:
                getUserInfo(Wechat.NAME);
                break;
            case R.id.btn_weibo:
                getUserInfo(SinaWeibo.NAME);
                break;
        }
    }

    private String platName;

    private void getUserInfo(final String platName) {
        this.platName = platName;
        //启调三方平台进行登录获取用户信息，如未授权会自动完成授权后登录，如果已经授权会读取本地数据库记录不会真正发起授权
        //只有在没有授权记录的情况下才会发起授权获取用户信息，该方法在后台线程执行并在UI线程回调，不需关心授权状态和本地数据状态，直接发起login等待回调即可
        LoginApi.with(context).setOnLoginListener(new LoginApi.OnLoginCallback() {
            @Override
            public void onLogin(String platform, UserInfo userInfo) {
                textLog.append("\n获取openId成功:" + userInfo.getUserName() + ":" + userInfo.getUserId() + "\nheaderImg:" + userInfo.getUserIcon() + "\ngender:" + userInfo.getUserGender());
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
                textLog.append("\nopenId已存在，直接进入登录流程");
                if (callback != null)
                    callback.onOpenIdExist(openId, user.getAccountID(), user.getToken());
            }

            @Override
            public void onError(int status, String msg) {
                if (status == 201) {
                    //用户不存在（新用户）
                    textLog.append("\nopenId不存在，请输入email");
                    if (callback != null)
                        callback.onOpenIdInExist(openId, platform);
                }
            }
        });
    }


    //########## 接口 ###########
    private SignupPlatCallback callback;

    public void setSignupPlatCallback(SignupPlatCallback signupPlatCallback) {
        this.callback = signupPlatCallback;
    }

    public interface SignupPlatCallback {
        void onOpenIdExist(String openId, String accountID, String token);

        void onOpenIdInExist(String openId, String platform);
    }


    //########## 测试 ###########
    private TextView textLog;

    public void setLogTextView(TextView textLog) {
        this.textLog = textLog;
    }
}
