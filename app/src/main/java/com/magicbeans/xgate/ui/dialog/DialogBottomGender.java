package com.magicbeans.xgate.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.postbean.FreeGift;
import com.magicbeans.xgate.databinding.DialogGenderBinding;
import com.magicbeans.xgate.databinding.DialogGiftsBinding;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterGift;

import java.util.List;

/**
 * liaoinstan
 * 选择性别弹窗
 */
public class DialogBottomGender extends Dialog implements View.OnClickListener {

    private Context context;
    private DialogGenderBinding binding;

    public DialogBottomGender(Context context) {
        super(context, R.style.PopupDialog);
        this.context = context;
        setMsgDialog();
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

    private void setMsgDialog() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_gender, null, false);

        binding.textGenderM.setOnClickListener(this);
        binding.textGenderFm.setOnClickListener(this);

        this.setCanceledOnTouchOutside(true);    //点击外部关闭

        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_gender_m:
                if (listener != null) listener.onSelect("男");
                dismiss();
                break;
            case R.id.text_gender_fm:
                if (listener != null) listener.onSelect("女");
                dismiss();
                break;
        }
    }

    private GenderSelectListener listener;

    public void setGenderSelectListener(GenderSelectListener listener) {
        this.listener = listener;
    }

    public interface GenderSelectListener {
        void onSelect(String genderStr);
    }

    public static void showDialog(Context context,GenderSelectListener listener){
        DialogBottomGender dialog = new DialogBottomGender(context);
        dialog.setGenderSelectListener(listener);
        dialog.show();
    }
}
