package com.ins.domain.ui.dialog;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ins.domain.R;
import com.ins.domain.bean.Domain;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class DomainPopup extends PopupWindow implements View.OnClickListener {

    protected Context context;
    private EditText domain_edit_ip;
    private EditText domain_edit_note;
    private TextView btn_go;
    private TextView btn_del;

    //是否需要背景变暗的动画
    protected boolean needanim = true;

    public DomainPopup(final Context context) {
        this.context = context;

        //加载布局
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(inflater.inflate(getLayout(), null));

        //获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int w = outMetrics.widthPixels;
        int h = outMetrics.heightPixels;

        // 设置SelectPicPopupWindow的View
        this.setContentView(getContentView());
        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(w / 3);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);

        initBase();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (needanim) resetBackground();
        domain = null;
    }
    ////////////////////////
    //////////对外接口
    ////////////////////////

    public void setNeedanim(boolean needanim) {
        this.needanim = needanim;
    }

    //////////////////////////
    ////////背景变暗
    //////////////////////////
    private void turnBackgroundDark() {
        final Activity activity = (Activity) context;
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.7f);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                activity.getWindow().setAttributes(params);
            }
        });
    }

    private void resetBackground() {
        final Activity activity = (Activity) context;
        ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1f);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                activity.getWindow().setAttributes(params);
            }
        });
    }

    //////////////////自定义部分//////////////

    private Domain domain;

    public void showPopupWindow(View parent) {
        showPopupWindow(parent, null);
    }

    public void showPopupWindow(View parent, Domain domain) {
        if (!this.isShowing()) {
            int x = (parent.getWidth() - this.getWidth()) / 2;
            this.showAsDropDown(parent, x, 0);
            if (needanim) turnBackgroundDark();
        } else {
            this.dismiss();
        }
        this.domain = domain;
        if (domain != null) {
            domain_edit_ip.setText(domain.getIp());
            domain_edit_note.setText(domain.getNote());
            btn_del.setVisibility(View.VISIBLE);
        } else {
            btn_del.setVisibility(View.GONE);
        }
    }

    private int getLayout() {
        return R.layout.domain_pop;
    }

    private void initBase() {
        domain_edit_ip = (EditText) getContentView().findViewById(R.id.domain_edit_ip);
        domain_edit_note = (EditText) getContentView().findViewById(R.id.domain_edit_note);
        btn_go = (TextView) getContentView().findViewById(R.id.btn_go);
        btn_del = (TextView) getContentView().findViewById(R.id.btn_del);
        btn_go.setOnClickListener(this);
        btn_del.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_go) {
            String ip = domain_edit_ip.getText().toString();
            String note = domain_edit_note.getText().toString();
            if (!TextUtils.isEmpty(ip)) {
                if (onDomainCallback != null) {
                    if (domain == null) domain = new Domain();
                    domain.setIp(ip);
                    domain.setNote(note);
                    onDomainCallback.onContent(domain);
                }
                dismiss();
            }
        } else if (i == R.id.btn_del) {
            if (onDomainCallback != null) {
                onDomainCallback.onDel(domain);
                dismiss();
            }
        }
    }

    //////////////////// 对外接口回调

    private OnDomainCallback onDomainCallback;

    public void setOnDomainCallback(OnDomainCallback onDomainCallback) {
        this.onDomainCallback = onDomainCallback;
    }

    public interface OnDomainCallback {
        void onContent(Domain domain);

        void onDel(Domain domain);
    }
}
