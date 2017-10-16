package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ins.common.helper.ValiHelper;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.App;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.ActivityLoginBinding;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

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
        }
    }
}
