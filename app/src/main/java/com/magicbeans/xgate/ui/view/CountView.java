package com.magicbeans.xgate.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.magicbeans.xgate.R;

/**
 * Created by liaoinstan on 2017/7/19.
 */

public class CountView extends FrameLayout implements View.OnClickListener {

    private Context context;
    private View lay_count_edit;
    private TextView text_count_show;
    private TextView text_count;
    private ImageView btn_sub;
    private ImageView btn_add;

    private boolean editble;
    private int count;
    private int MIN_COUNT = 1;//最小为1

    public CountView(Context context) {
        this(context, null);
    }

    public CountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // 初始化各项组件
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountView);
        editble = a.getBoolean(R.styleable.CountView_editble, false);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(context).inflate(R.layout.view_count, this, true);
        initView();
        initCtrl();
        initData();
    }

    private void initView() {
        lay_count_edit = findViewById(R.id.lay_count_edit);
        text_count_show = (TextView) findViewById(R.id.text_count_show);
        text_count = (TextView) findViewById(R.id.text_count);
        btn_sub = (ImageView) findViewById(R.id.btn_sub);
        btn_add = (ImageView) findViewById(R.id.btn_add);
        btn_sub.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        //默认初始化状态
        setEdit(editble);
        //初始化数量0
        setCount(0);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sub:
                sub();
                break;
            case R.id.btn_add:
                add();
                break;
        }
    }

    //############ 对外方法 ###########


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        //数量不能小于0
        if (count < MIN_COUNT) count = MIN_COUNT;
        if (onCountChangeListenner != null && count != this.count)
            onCountChangeListenner.onCountChange(count, this.count);
        this.count = count;
        text_count.setText(count + "");
        text_count_show.setText("x" + count);
        if (count <= 1) {
            btn_sub.setEnabled(false);
        } else {
            btn_sub.setEnabled(true);
        }
    }

    public void setEdit(boolean isEdit) {
        lay_count_edit.setVisibility(isEdit ? VISIBLE : INVISIBLE);
        text_count_show.setVisibility(isEdit ? INVISIBLE : VISIBLE);
    }

    public void add() {
        setCount(count + 1);
    }

    public void sub() {
        setCount(count - 1);
    }

    //############# 对外监听 ##############

    private OnCountChangeListenner onCountChangeListenner;

    public void setOnCountChangeListenner(OnCountChangeListenner onCountChangeListenner) {
        this.onCountChangeListenner = onCountChangeListenner;
    }

    public interface OnCountChangeListenner {
        void onCountChange(int count, int lastCount);
    }
}
