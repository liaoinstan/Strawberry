package com.magicbeans.xgate.ui.dialog;

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

import com.magicbeans.xgate.R;


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
public class DialogSureCheckout extends Dialog {

    private Context context;
    private TextView text_sure;
    private TextView text_title;
    private TextView btn_cancel;
    private TextView btn_ok;
    private View lay_bottom;

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

    DialogSureCheckout(Context context, String msg) {
        this(context, "提醒", msg, "我再想想", "确认下单");
    }

    DialogSureCheckout(Context context, String msg, String cancelStr, String sureStr) {
        this(context, "提醒", msg, cancelStr, sureStr);
    }

    public DialogSureCheckout(Context context, String title, String msg, String cancelStr, String sureStr) {
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
        View v = inflater.inflate(R.layout.dialog_sure_checkout, null);
        text_title = v.findViewById(R.id.text_title);
        text_sure = v.findViewById(R.id.text_sure);
        btn_cancel = v.findViewById(R.id.btn_cancel);
        btn_ok = v.findViewById(R.id.btn_ok);
        lay_bottom = v.findViewById(R.id.lay_bottom);

        btn_cancel.setOnClickListener(listener);
        btn_ok.setOnClickListener(listener);

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
        text_title.setText(title);
        text_sure.setText(msg);
        btn_cancel.setText(cancelStr);
        btn_ok.setText(sureStr);
        if (TextUtils.isEmpty(title)) {
            text_title.setVisibility(View.GONE);
        } else {
            text_title.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(cancelStr)) {
            btn_cancel.setVisibility(View.GONE);
        } else {
            btn_cancel.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(sureStr)) {
            btn_ok.setVisibility(View.GONE);
        } else {
            btn_ok.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(cancelStr) && TextUtils.isEmpty(sureStr)) {
            lay_bottom.setVisibility(View.GONE);
        } else {
            lay_bottom.setVisibility(View.VISIBLE);
        }
    }

    public void setOnCancleListener(View.OnClickListener listener) {
        btn_cancel.setOnClickListener(listener);
    }

    public void setOnOkListener(View.OnClickListener listener) {
        btn_ok.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    //便捷的调用方式
    public static void showDialog(Context context, String title, String msg, boolean showBtn, final CallBack callBack) {
        String cancleStr = showBtn ? "我再想想" : null;
        String sureStr = showBtn ? "确认下单" : null;
        final DialogSureCheckout dialogSure = new DialogSureCheckout(context, title, msg, cancleStr, sureStr);
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

    public static void showDialog(Context context, String title, String msg, boolean showBtn) {
        showDialog(context, title, msg, showBtn, null);
    }

    public interface CallBack {
        void onSure();
    }
}
