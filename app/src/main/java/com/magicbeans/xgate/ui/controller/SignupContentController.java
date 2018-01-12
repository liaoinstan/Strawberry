package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ins.common.common.SinpleShowInAnimatorListener;
import com.ins.common.common.SinpleShowOutAnimatorListener;
import com.ins.common.helper.ValiHelper;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.KeyBoardUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.others.AnimUtil;
import com.ins.common.utils.viewutils.EditTextUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppVali;
import com.magicbeans.xgate.databinding.LaySignupContentBinding;
import com.magicbeans.xgate.helper.ProgressButtonHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.ParamHelper;
import com.magicbeans.xgate.net.STFormatCallback;

import java.util.Map;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/10/11.
 */

public class SignupContentController extends BaseController<LaySignupContentBinding> implements View.OnClickListener {

    private ValiHelper valiHelper;

    private String platform;

    public SignupContentController(LaySignupContentBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    public void initCtrl() {
        valiHelper = new ValiHelper(binding.textVali);
        binding.textVali.setOnClickListener(this);
        binding.btnGo.setOnClickListener(this);
        binding.btnGo.setIndeterminateProgressMode(true);
    }

    public void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_vali:
                valiHelper.start();
                break;
            case R.id.btn_go:
                KeyBoardUtil.hideKeybord(context);
                if (TextUtils.isEmpty(platform)) return;
                if (isStatusEmailCheck()) {
                    String email = binding.editEmail.getText().toString();
                    String openId = ShareSDK.getPlatform(platform).getDb().getUserId();
                    String msg = AppVali.email(email);
                    if (TextUtils.isEmpty(msg)) {
                        netCheckEmailExist(platform, openId, email);
                    } else {
                        binding.textValinote.setText(msg);
                    }
                } else if (isStatusMerge()) {
                    ToastUtil.showToastShort("建设中");
                } else if (isStatusCreateNew()) {
                    ToastUtil.showToastShort("建设中");
                }
                break;
        }
    }

    //检查服务器email是否已经存在
    private void netCheckEmailExist(String platform, String openId, String email) {
        binding.btnGo.setProgress(50);
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
                ProgressButtonHelper.progOk2dle(binding.btnGo);
                textLog.append("\nEmail已存在，输入密码进入合并账户流程");
                binding.textValinote.setText("该邮箱已被注册，请输入密码登录");
                setStatusMerge();
            }

            @Override
            public void onError(int status, String msg) {
                if (status == 201) {
                    //用户不存在（新用户）
                    ProgressButtonHelper.progOk2dle(binding.btnGo);
                    textLog.append("\nEmail不存在，请输入手机号进入注册流程");
                    binding.textValinote.setText("请验证手机号并完成注册");
                    setStatusCreateNew();
                } else {
                    ProgressButtonHelper.progError2dle(binding.btnGo);
                    ToastUtil.showToastShort(msg);
                }
            }
        });
    }

    //########## 对外接口 ###########

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

    //检查邮箱状态
    public void setStatusEmailCheck() {
        binding.editEmail.setVisibility(View.VISIBLE);
        binding.editPsw.setVisibility(View.GONE);
        binding.layPhone.setVisibility(View.GONE);
        setEmailEditble(true);
        binding.textValinote.setText("");
    }

    //合并已有账户状态
    public void setStatusMerge() {
        binding.editEmail.setVisibility(View.VISIBLE);
        binding.editPsw.setVisibility(View.VISIBLE);
        binding.layPhone.setVisibility(View.GONE);
        AnimUtil.show(binding.editPsw);
        setEmailEditble(false);
    }

    //创建新用户状态
    public void setStatusCreateNew() {
        binding.editEmail.setVisibility(View.VISIBLE);
        binding.editPsw.setVisibility(View.GONE);
        binding.layPhone.setVisibility(View.VISIBLE);
        AnimUtil.show(binding.layPhone);
        setEmailEditble(false);
    }

    public boolean isStatusEmailCheck() {
        if (binding.editEmail.getVisibility() == View.VISIBLE
                && binding.editPsw.getVisibility() != View.VISIBLE
                && binding.layPhone.getVisibility() != View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStatusMerge() {
        return binding.editPsw.getVisibility() == View.VISIBLE;
    }

    public boolean isStatusCreateNew() {
        return binding.layPhone.getVisibility() == View.VISIBLE;
    }

    private void setEmailEditble(boolean editble) {
        if (editble) {
            binding.editEmail.setBackgroundResource(R.drawable.shape_rect_cornerfull_none_line_red);
            binding.editEmail.setTextColor(ContextCompat.getColor(context, R.color.st_red_login));
            EditTextUtil.enableEditText(binding.editEmail);
        } else {
            binding.editEmail.setBackgroundResource(R.drawable.none);
            binding.editEmail.setTextColor(ContextCompat.getColor(context, R.color.com_text_blank));
            EditTextUtil.disableEditText(binding.editEmail);
        }
    }

    //########## get & set ###########

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    //########## 测试 ###########
    private TextView textLog;

    public void setLogTextView(TextView textLog) {
        this.textLog = textLog;
    }
}
