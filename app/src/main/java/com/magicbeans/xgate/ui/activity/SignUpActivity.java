package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.ins.common.helper.ValiHelper;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.SignUpWrap;
import com.magicbeans.xgate.databinding.ActivitySignupBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.Map;

public class SignUpActivity extends BaseAppCompatActivity {

    private ActivitySignupBinding binding;

    private ValiHelper valiHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
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
            case R.id.btn_tologin:
                LoginActivity.start(this);
                finish();
                break;
            case R.id.text_vali:
                valiHelper.start();
                break;
            case R.id.btn_qq:
                DialogSure.showDialog(this, "\"草莓网\"想要打开QQ并登陆？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        ToastUtil.showToastShort("建设中...");
                    }
                });
                break;
            case R.id.btn_weixin:
                DialogSure.showDialog(this, "\"草莓网\"想要打开微信并登陆？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        ToastUtil.showToastShort("建设中...");
                    }
                });
                break;
            case R.id.btn_weibo:
                DialogSure.showDialog(this, "\"草莓网\"想要打开微博并登陆？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        ToastUtil.showToastShort("建设中...");
                    }
                });
                break;
            case R.id.btn_go:
                String account = binding.editAccount.getText().toString();
                String psw = binding.editPsw.getText().toString();
                String psw_re = binding.editPswRe.getText().toString();
                netSignUp(account, psw);
                break;
        }
    }

    private void netSignUp(String account, String psw) {
        Map<String, Object> param = new NetParam()
                .put("signupemail", account)
                .put("password", psw)
                .put("repassword", psw)
                .put("signupFirstname", "")
                .put("signupLastname", "")
                .put("isSubscribe", "1")
                .build();
        NetApi.NI().netSignUp(param).enqueue(new STCallback<SignUpWrap>(SignUpWrap.class) {
            @Override
            public void onSuccess(int status, SignUpWrap bean, String msg) {
                switch (bean.getResponseCode()) {
                    case 0:
                        binding.textValinote.setText(bean.getResponseMsg());
                        break;
                    case 1:
                        ToastUtil.showToastShort(bean.getResponseMsg());
                        break;
                    case 2:
                        binding.textValinote.setText(bean.getResponseMsg());
                        break;
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
