package com.magicbeans.xgate.ui.controller;

import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ins.common.common.SinpleShowInAnimatorListener;
import com.ins.common.common.SinpleShowOutAnimatorListener;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.databinding.LaySignupPlatformBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.ParamHelper;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.sharesdk.LoginApi;
import com.magicbeans.xgate.sharesdk.UserInfo;
import com.magicbeans.xgate.ui.activity.LoginActivity;

import java.util.Map;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Administrator on 2017/10/11.
 */

public class SignupPlatformController extends BaseController<LaySignupPlatformBinding> implements View.OnClickListener {

    public SignupPlatformController(LaySignupPlatformBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    public void initCtrl() {
        binding.btnQq.setOnClickListener(this);
        binding.btnWeixin.setOnClickListener(this);
        binding.btnWeibo.setOnClickListener(this);
        binding.progress.setVisibility(View.INVISIBLE);
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

    private void getUserInfo(final String platName) {
        //启调三方平台进行登录获取用户信息，如未授权会自动完成授权后登录，如果已经授权会读取本地数据库记录不会真正发起授权
        //只有在没有授权记录的情况下才会发起授权获取用户信息，该方法在后台线程执行并在UI线程回调，不需关心授权状态和本地数据状态，直接发起login等待回调即可
        LoginApi.with(context).setOnLoginListener(new LoginApi.OnLoginCallback() {
            @Override
            public void onLogin(String platform, UserInfo userInfo) {
                //FIXME:这里生成一个假OpenId
//                userInfo.setOpenId(((LoginActivity) context).getTestOpenId());
                userInfo.setPlatform(platform);
                netCheckOpenidExist(userInfo);
            }

            @Override
            public void onFailed(String msg) {
                hideProgress();
            }
        }).login(platName);
        showProgress();
    }

    //检查服务器openId是否已经存在
    private void netCheckOpenidExist(final UserInfo userInfo) {
        Map<String, Object> param = new NetParam()
                .put("openId", userInfo.getOpenId())
                .put("openIDType", userInfo.getOpenIDType())
                .put("deviceId", "a12d5d9993241b00cf9a2070484f57ca")
                .put(ParamHelper.getDeviceTypeMap())
                .build();
        NetApi.NI().checkOpenidExist(param).enqueue(new STFormatCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User user, String msg) {
                //用户已存在，直接进入登录流程
                hideProgress();
                if (callback != null)
                    callback.onOpenIdExist(userInfo, user.getAccountID(), user.getToken());
            }

            @Override
            public void onError(int status, String msg) {
                hideProgress();
                if (status == 201) {
                    //用户不存在（新用户）
                    if (callback != null)
                        callback.onOpenIdInExist(userInfo);
                } else {
                    ToastUtil.showToastShort(msg);
                }
            }
        });
    }


    //########## 对外方法 ###########

    public boolean isShow() {
        return binding.getRoot().getVisibility() == View.VISIBLE;
    }

    public void hide(boolean needAnim) {
        if (needAnim) {
            YoYo.with(Techniques.FadeOutDown)
                    .duration(300)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .withListener(new SinpleShowOutAnimatorListener(binding.getRoot()))
                    .playOn(getRoot());
        } else {
            binding.getRoot().setVisibility(View.INVISIBLE);
        }
    }

    public void show(boolean needAnim) {
        if (needAnim) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    YoYo.with(Techniques.FadeInUp)
                            .duration(300)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .withListener(new SinpleShowInAnimatorListener(binding.getRoot()))
                            .playOn(binding.getRoot());
                }
            }, 100);
        } else {
            binding.getRoot().setVisibility(View.VISIBLE);
        }
    }

    public void showProgress() {
        binding.progress.setVisibility(View.VISIBLE);
        binding.btnQq.setClickable(false);
        binding.btnWeixin.setClickable(false);
        binding.btnWeibo.setClickable(false);
    }

    public void hideProgress() {
        binding.progress.setVisibility(View.INVISIBLE);
        binding.btnQq.setClickable(true);
        binding.btnWeixin.setClickable(true);
        binding.btnWeibo.setClickable(true);
    }

    //########## 对外接口 ###########

    private SignupPlatCallback callback;

    public void setSignupPlatCallback(SignupPlatCallback signupPlatCallback) {
        this.callback = signupPlatCallback;
    }

    public interface SignupPlatCallback {
        void onOpenIdExist(UserInfo userInfo, String accountID, String token);

        void onOpenIdInExist(UserInfo userInfo);
    }
}
