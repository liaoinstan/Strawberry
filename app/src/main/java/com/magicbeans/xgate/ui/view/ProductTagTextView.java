package com.magicbeans.xgate.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.magicbeans.xgate.R;

/**
 * Created by Marie on 2017/12/22.
 */

public class ProductTagTextView extends android.support.v7.widget.AppCompatTextView {

    public ProductTagTextView(Context context) {
        this(context, null);
    }

    public ProductTagTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductTagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // 初始化各项组件
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CountView);
//        editble = a.getBoolean(R.styleable.CountView_editble, false);
        a.recycle();

//        setTextAppearance(R.style.Appt);
    }
}
