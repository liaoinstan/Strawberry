package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StatusBarUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.VibratorUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends BaseAppCompatActivity implements QRCodeView.Delegate {

    private ZXingView zxingview;

    public static void start(Context context) {
        Intent intent = new Intent(context, ScanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setToolbar();
        toolbar.bringToFront();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        zxingview.startCamera();
        zxingview.showScanRect();
        zxingview.startSpot();  //开始识别
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy();
        super.onDestroy();
    }

    private void initBase() {
//        StatusBarTextUtil.StatusBarDarkMode(this);
//        StatusBarTextUtil.transparencyBar(this);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setTextLight(this);
    }

    private void initView() {
        zxingview = (ZXingView) findViewById(R.id.zxingview);
        zxingview.setDelegate(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtil.showToastLong("打开相机出错，请检查是否关闭了相机权限");
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        VibratorUtil.vibrate(this, 200);
        final DialogSure dialogSure = new DialogSure(this, "扫描结果", result, "退出", "继续扫描");
        dialogSure.setOnCancleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogSure.setOnOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSure.dismiss();
                zxingview.startSpot();
            }
        });
        dialogSure.show();
    }
}
