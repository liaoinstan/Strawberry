package com.magicbeans.xgate.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.magicbeans.xgate.R;

/**
 * liaoinstan
 * 选择照片或拍照弹窗
 */
public class DialogBottomProductAttr extends Dialog {
    private TextView text_cancel, text_photo, text_camera;
    private Context context;

    public DialogBottomProductAttr(Context context) {
        super(context, R.style.PopupDialog);
        this.context = context;
        setMsgDialog();
    }

    private void setMsgDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_productattr, null);
        text_cancel = (TextView) mView.findViewById(R.id.text_pop_identy_cancel);
        text_photo = (TextView) mView.findViewById(R.id.text_pop_identy_photo);
        text_camera = (TextView) mView.findViewById(R.id.text_pop_identy_camera);

        this.setCanceledOnTouchOutside(true);    //点击外部关闭

        super.setContentView(mView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = this.getWindow();
        win.setGravity(Gravity.BOTTOM);    //从下方弹出
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }
}
