package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ins.common.helper.ValiHelper;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.App;
import com.ins.common.utils.ClearCacheUtil;
import com.ins.common.utils.MD5Util;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ValiHelper valiHelper;

    private TextView text_login_vali;
    private TextView text_login_valinote;

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
        setContentView(R.layout.activity_login);
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
        text_login_valinote = (TextView) findViewById(R.id.text_login_valinote);
        text_login_vali = (TextView) findViewById(R.id.text_login_vali);
        valiHelper = new ValiHelper(text_login_vali);
        text_login_vali.setOnClickListener(this);
        findViewById(R.id.btn_go).setOnClickListener(this);
        findViewById(R.id.btn_login_qq).setOnClickListener(this);
        findViewById(R.id.btn_login_weixin).setOnClickListener(this);
        findViewById(R.id.btn_login_weibo).setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_login_vali:
                valiHelper.start();
                break;
            case R.id.btn_go:
                text_login_valinote.setText("验证码错误，请重新输入");
                break;
            case R.id.btn_login_qq:
                DialogSure.showDialog(this, "\"草莓网\"想要打开QQ并登陆？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        ToastUtil.showToastShort("建设中...");
                    }
                });
                break;
            case R.id.btn_login_weixin:
                DialogSure.showDialog(this, "\"草莓网\"想要打开微信并登陆？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        ToastUtil.showToastShort("建设中...");
                    }
                });
                break;
            case R.id.btn_login_weibo:
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
