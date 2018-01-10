package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.entity.Image;
import com.ins.common.helper.ValiHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.banner.BannerWrap;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.databinding.LayHomeBannerboardBinding;
import com.magicbeans.xgate.databinding.LaySignupContentBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.ParamHelper;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.ui.activity.SaleActivity;
import com.magicbeans.xgate.ui.activity.WebActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;

/**
 * Created by Administrator on 2017/10/11.
 */

public class SignupContentController implements View.OnClickListener {

    private Context context;
    private LaySignupContentBinding binding;

    private ValiHelper valiHelper;

    private String platform;

    public SignupContentController(LaySignupContentBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        valiHelper = new ValiHelper(binding.textVali);
        binding.textVali.setOnClickListener(this);
        binding.btnGo.setOnClickListener(this);
        setVisibleAllHide();
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
                if (!TextUtils.isEmpty(platform)) {
                    String email = binding.editEmail.getText().toString();
                    String openId = ShareSDK.getPlatform(platform).getDb().getUserId();
                    netCheckEmailExist(platform, openId, email);
                }
                break;
        }
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
                textLog.append("\nEmail已存在，输入密码进入合并账户流程");
                setVisibleEmailPsw();
            }

            @Override
            public void onError(int status, String msg) {
                if (status == 201) {
                    //用户不存在（新用户）
                    textLog.append("\nEmail不存在，请输入手机号进入注册流程");
                    setVisiblePhone();
                }
            }
        });
    }

    //########## 对外接口 ###########

    public void setVisibleAllHide() {
        binding.getRoot().setVisibility(View.GONE);
    }

    public void setVisibleEmail(){
        binding.getRoot().setVisibility(View.VISIBLE);
        binding.editEmail.setVisibility(View.VISIBLE);
        binding.editPsw.setVisibility(View.GONE);
        binding.layPhone.setVisibility(View.GONE);
        binding.editVali.setVisibility(View.GONE);
    }

    public void setVisibleEmailPsw(){
        binding.getRoot().setVisibility(View.VISIBLE);
        binding.editEmail.setVisibility(View.VISIBLE);
        binding.editPsw.setVisibility(View.VISIBLE);
        binding.layPhone.setVisibility(View.GONE);
        binding.editVali.setVisibility(View.GONE);
    }

    public void setVisiblePhone(){
        binding.getRoot().setVisibility(View.VISIBLE);
        binding.editEmail.setVisibility(View.VISIBLE);
        binding.editPsw.setVisibility(View.GONE);
        binding.layPhone.setVisibility(View.VISIBLE);
        binding.editVali.setVisibility(View.VISIBLE);
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
