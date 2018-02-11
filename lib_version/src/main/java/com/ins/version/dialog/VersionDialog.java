package com.ins.version.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ins.version.R;
import com.ins.version.bean.UpdateInfo;
import com.ins.version.utils.MoreTextUtil;
import com.ins.version.utils.StorageHelper;


/**
 * 统一dialog,根据不同模式展示不同UI
 */
public class VersionDialog extends Dialog {

    private Context context;
    private TextView text_size;
    private TextView text_log;
    private TextView positiveButton, negativeButton;
    private CheckBox check_ignore;
    private String msg;
    private String size;
    private String version;
    private int versionCode;
    private boolean isForce;
    private boolean ignoreEnable;

    public VersionDialog(Context context, UpdateInfo updateInfo, boolean ignoreEnable) {
        super(context, R.style.VersionDialog);
        this.context = context;
        this.msg = updateInfo.getChangeLog();
        this.size = updateInfo.getSize();
        this.version = "V" + updateInfo.getVersionName();
        this.versionCode = updateInfo.getVersionCodeInt();
        this.isForce = updateInfo.isForce();
        this.ignoreEnable = ignoreEnable;
        setMsgDialog();
    }

    private void setMsgDialog() {
        View root = LayoutInflater.from(getContext()).inflate(isForce ? R.layout.dialog_force : R.layout.dialog_version, null);
        text_log = (TextView) root.findViewById(R.id.text_version_log);
        text_size = (TextView) root.findViewById(R.id.text_version_size);
        positiveButton = (TextView) root.findViewById(R.id.positiveButton);
        negativeButton = (TextView) root.findViewById(R.id.negativeButton);

        text_log.setText(TextUtils.isEmpty(msg) ? "这一秒不放弃，下一秒有奇迹！" : msg);
        MoreTextUtil.setMore(text_log);
        SpannableString strSpan = new SpannableString(size + "\n" + version);
        strSpan.setSpan(new RelativeSizeSpan(0.7f), size.length(), strSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//放大用户名字体
        text_size.setText(strSpan);
        positiveButton.setOnClickListener(listener);

        //是否是强制更新
        if (isForce) {
            negativeButton.setOnClickListener(exitListener);
            this.setCancelable(false);                //强制更新，你无法拒绝
            this.setCanceledOnTouchOutside(false);    //点击外部不会取消对话框
        } else {
            check_ignore = (CheckBox) root.findViewById(R.id.check_ignore);
            check_ignore.setOnCheckedChangeListener(ignoreListener);
            check_ignore.setVisibility(ignoreEnable ? View.VISIBLE : View.GONE);
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
            VersionDialog.this.dismiss();
        }
    };

    //忽略本次版本check弹窗
    private CompoundButton.OnCheckedChangeListener ignoreListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            if (checked) {
                StorageHelper.with(context).putIgnoreVersionCode(versionCode);
            } else {
                StorageHelper.with(context).removeIgnoreVersionCode();
            }
        }
    };

    private View.OnClickListener exitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            VersionDialog.this.dismiss();
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
    };
}
