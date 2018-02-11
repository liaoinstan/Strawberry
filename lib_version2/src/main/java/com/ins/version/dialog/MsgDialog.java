package com.ins.version.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ins.version.R;


/**
 * 统一dialog,根据不同模式展示不同UI
 */
public class MsgDialog extends Dialog {

    private Context context;
    private TextView text_title;
    private TextView text_log;
    private TextView positiveButton, negativeButton;
    private String title;
    private String msg;
    private String positiveStr;
    private String negativeStr;
    private boolean isForce;

    public static MsgDialog createWifiDialog(Context context, boolean isForce) {
        return new MsgDialog(context, "更新提示", "您目前的网络环境下继续下载将可能会消耗手机流量，请确认是否继续下载", "继续下载", isForce ? "退出应用" : "取消下载", isForce);
    }

    public static MsgDialog createInstallDialog(Context context) {
        return new MsgDialog(context, "安装提示", "您已经下载了最新的安装包，正在进行安装...", "继续安装", "退出应用", true);
    }

    private MsgDialog(Context context, String title, String msg, String positiveStr, String negativeStr, boolean isForce) {
        super(context, R.style.VersionDialog);
        this.context = context;
        this.title = title;
        this.msg = msg;
        this.positiveStr = positiveStr;
        this.negativeStr = negativeStr;
        this.isForce = isForce;
        setMsgDialog();
    }

    private void setMsgDialog() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.dialog_msg, null);
        text_title = (TextView) root.findViewById(R.id.text_version_title);
        text_log = (TextView) root.findViewById(R.id.text_version_log);
        positiveButton = (TextView) root.findViewById(R.id.positiveButton);
        negativeButton = (TextView) root.findViewById(R.id.negativeButton);

        text_title.setText(title);
        text_log.setText(msg);
        positiveButton.setText(positiveStr);
        negativeButton.setText(negativeStr);
        positiveButton.setOnClickListener(listener);

        //是否是强制更新
        if (isForce) {
            negativeButton.setOnClickListener(exitListener);
            this.setCancelable(false);                //强制更新，你无法拒绝
            this.setCanceledOnTouchOutside(false);    //点击外部不会取消对话框
        } else {
            negativeButton.setOnClickListener(listener);
            this.setCancelable(true);
            this.setCanceledOnTouchOutside(false);    //点击外部不会取消对话框
        }
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

    /**
     * 确定键监听器
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        positiveButton.setOnClickListener(listener);
    }

    /**
     * 取消键监听器
     */
    public void setOnNegativeListener(View.OnClickListener listener) {
        negativeButton.setOnClickListener(listener);
    }

    //默认点击监听
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MsgDialog.this.dismiss();
        }
    };

    private View.OnClickListener exitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MsgDialog.this.dismiss();
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
    };
}
