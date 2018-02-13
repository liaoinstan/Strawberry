package com.ins.version.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.ins.version.R;


/**
 * 统一dialog,根据不同模式展示不同UI
 */
public class ProgressDialog extends Dialog {

    private Context context;
    private ProgressBar progressBar;

    public ProgressDialog(Context context) {
        super(context, R.style.VersionDialog);
        this.context = context;
        setMsgDialog();
    }

    private void setMsgDialog() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.dialog_progress, null);
        progressBar = root.findViewById(R.id.progress);
        this.setCancelable(false);                //强制更新，你无法拒绝
        this.setCanceledOnTouchOutside(false);    //点击外部不会取消对话框
        super.setContentView(root);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /////////获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        /////////设置高宽
        lp.width = (int) (screenWidth * 0.85);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    public void setProgress(int nowBytes, int totalBytes) {
        if (progressBar != null) {
            progressBar.setMax(totalBytes);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar.setProgress(nowBytes, true);
            } else {
                progressBar.setProgress(nowBytes);
            }
        }
    }
}
