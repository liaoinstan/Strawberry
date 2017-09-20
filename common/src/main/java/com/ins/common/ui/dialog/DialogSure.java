package com.ins.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ins.common.R;


/**
 * 确定取消弹窗
 * 可以如下这样调用，更加便捷
 * DialogSure.showDialog(this, "提示信息？", new DialogSure.CallBack() {
 *
 * @Override public void onSure() {
 * ExamActivity.super.onBackPressed();
 * }
 * });
 */
public class DialogSure extends Dialog {

    private Context context;
    private TextView text_dialog_sure;
    private TextView text_dialog_title;
    private TextView text_cancle;
    private TextView text_ok;

    private String msg;
    private String title;
    private String cancelStr;
    private String sureStr;

    //拓展字段
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public DialogSure(Context context) {
        this(context, "", "确定？", "取消", "确定");
    }

    public DialogSure(Context context, String msg) {
        this(context, "", msg, "取消", "确定");
    }

    public DialogSure(Context context, String title, String msg) {
        this(context, title, msg, "取消", "确定");
    }

    public DialogSure(Context context, String title, String msg, String cancelStr, String sureStr) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.title = title;
        this.msg = msg;
        this.cancelStr = cancelStr;
        this.sureStr = sureStr;
        setLoadingDialog();
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
        lp.width = (int) (screenWidth * 0.85); // 宽度
//        lp.height =  WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = (int) (lp.width*0.65); // 高度
        this.setCanceledOnTouchOutside(true);
    }

    private void setLoadingDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_sure, null);

        text_dialog_sure = (TextView) v.findViewById(R.id.text_dialog_sure);
        text_dialog_title = (TextView) v.findViewById(R.id.text_dialog_title);
        text_cancle = (TextView) v.findViewById(R.id.dialog_cancel);
        text_ok = (TextView) v.findViewById(R.id.dialog_ok);


        text_cancle.setOnClickListener(listener);
        text_ok.setOnClickListener(listener);

        setData();

        super.setContentView(v);
    }

    public void setTitle(String title) {
        this.title = title;
        setData();
    }

    public void setMsg(String msg) {
        this.msg = msg;
        setData();
    }

    private void setData() {
        text_dialog_title.setText(title);
        text_dialog_sure.setText(msg);
        text_cancle.setText(cancelStr);
        text_ok.setText(sureStr);
        if (TextUtils.isEmpty(title)) {
            text_dialog_title.setVisibility(View.GONE);
        } else {
            text_dialog_title.setVisibility(View.VISIBLE);
        }
    }

    public void setOnCancleListener(View.OnClickListener listener) {
        text_cancle.setOnClickListener(listener);
    }

    public void setOnOkListener(View.OnClickListener listener) {
        text_ok.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    //便捷的调用方式
    public static void showDialog(Context context, String msg, final CallBack callBack) {
        final DialogSure dialogSure = new DialogSure(context, msg);
        if (callBack != null) {
            dialogSure.setOnOkListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSure.dismiss();
                    callBack.onSure();
                }
            });
        }
        dialogSure.show();
    }

    public interface CallBack {
        void onSure();
    }
}
