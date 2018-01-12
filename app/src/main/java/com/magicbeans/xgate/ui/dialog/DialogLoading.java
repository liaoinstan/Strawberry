package com.magicbeans.xgate.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.magicbeans.xgate.R;


/**
 * 加载弹窗
 */
public class DialogLoading extends Dialog {
    private String msg;

    public DialogLoading(Context context) {
        this(context, "正在加载");
    }

    public DialogLoading(Context context, String msg) {
        super(context, R.style.LoadingDialog);
        this.msg = msg;
        setLoadingDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
    }

    private void setLoadingDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_loading, null);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
        tipTextView.setText(msg);

        super.setContentView(v);
    }


}
