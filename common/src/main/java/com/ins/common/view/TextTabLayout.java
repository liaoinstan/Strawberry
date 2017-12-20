package com.ins.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.common.R;


/**
 * TabLayout无法更改字体和字体大小
 * TabLayout提供了使用自定义布局的方式进行个性化设置，这个TextTabLayout进行简单封装，提供修改字体大小的方法
 */

public class TextTabLayout extends TabLayout {
    public TextTabLayout(Context context) {
        super(context);
    }

    public TextTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        setTab();
    }

    private TextView getTextViewFromTab(TabLayout.Tab t) {
        if (t.getCustomView() == null) {
            t.setCustomView(LayoutInflater.from(getContext()).inflate(R.layout.layout_tab, this, false));
        }
        return (TextView) ((ViewGroup) t.getCustomView()).getChildAt(0);
    }

    //设置TabLayout为自定义样式
    private void setTab() {
        for (int i = 0; i < getTabCount(); i++) {
            TabLayout.Tab t = getTabAt(i);
            TextView textView = getTextViewFromTab(t);
            textView.setText(t.getText());
        }
    }

    public void setTextSize(float textSize) {
        for (int i = 0; i < getTabCount(); i++) {
            TabLayout.Tab t = getTabAt(i);
            TextView textView = getTextViewFromTab(t);
            textView.setTextSize(textSize);
        }
    }
}
