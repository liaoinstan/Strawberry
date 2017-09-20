package com.ins.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ins.common.R;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class DotView extends LinearLayout {

    private Context context;
    private ViewPager pager;
    private int tabCount;

    private int dot_size = 13;
    private GradientDrawable mUnSelectedGradientDrawable;
    private GradientDrawable mSelectedGradientDrawable;
    private int mUnSelectedSrc;
    private int mSelectedSrc;
    private int selectedColor;
    private int unSelectedColor;

    private int DEFAULT_BANNER_SIZE = -1;

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0);

            selectedColor = attributes.getColor(R.styleable.DotView_myselected_color, Color.rgb(255, 255, 255));
            unSelectedColor = attributes.getColor(R.styleable.DotView_myunselected_color, Color.argb(33, 255, 255, 255));

            mSelectedSrc = attributes.getResourceId(R.styleable.DotView_myselected_drawable, 0);
            mUnSelectedSrc = attributes.getResourceId(R.styleable.DotView_myunselected_drawable, 0);

            dot_size = attributes.getDimensionPixelSize(R.styleable.DotView_mydoy_size, 13);
        }

        mSelectedGradientDrawable = new GradientDrawable();
        mUnSelectedGradientDrawable = new GradientDrawable();
        mSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
        mUnSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
        mSelectedGradientDrawable.setSize(dot_size, dot_size);
        mUnSelectedGradientDrawable.setSize(dot_size, dot_size);
        if (selectedColor != 0) {
            mSelectedGradientDrawable.setColor(selectedColor);
        } else {
            mSelectedGradientDrawable.setColor(Color.rgb(255, 255, 255));
        }
        if (unSelectedColor != 0) {
            mUnSelectedGradientDrawable.setColor(unSelectedColor);
        } else {
            mUnSelectedGradientDrawable.setColor(Color.argb(33, 255, 255, 255));
        }


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setOrientation(HORIZONTAL);
        if (isInEditMode()) {
            addDots(5);
        }
    }

    private void addDots(int connt) {
        for (int i = 0; i < connt; i++) {
            ImageView imageView = new ImageView(context);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.rightMargin = 10;
            imageView.setLayoutParams(lp);
            if (i == 0) {
                if (mSelectedSrc != 0) {
                    imageView.setImageResource(mSelectedSrc);
                } else {
                    imageView.setImageDrawable(mSelectedGradientDrawable);
                }
            } else {
                if (mUnSelectedSrc != 0) {
                    imageView.setImageResource(mUnSelectedSrc);
                } else {
                    imageView.setImageDrawable(mUnSelectedGradientDrawable);
                }
            }

            addView(imageView);
        }
    }

    public void setIndicator(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            ImageView child = (ImageView) getChildAt(i);
            if (position == i) {
                if (mSelectedSrc != 0) {
                    child.setImageResource(mSelectedSrc);
                } else {
                    child.setImageDrawable(mSelectedGradientDrawable);
                }
            } else {
                if (mUnSelectedSrc != 0) {
                    child.setImageResource(mUnSelectedSrc);
                } else {
                    child.setImageDrawable(mUnSelectedGradientDrawable);
                }
            }
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (DEFAULT_BANNER_SIZE != -1) {
                position %= DEFAULT_BANNER_SIZE;
            }
            setIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //###########################################
    //###对外暴露方法
    //###########################################
    public void setViewPager(ViewPager pager) {
        this.recyclerView = null;
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        pager.addOnPageChangeListener(new MyOnPageChangeListener());
        notifyDataSetChanged();
    }

    public void setViewPager(ViewPager pager, int DEFAULT_BANNER_SIZE) {
        this.DEFAULT_BANNER_SIZE = DEFAULT_BANNER_SIZE;
        setViewPager(pager);
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        if (DEFAULT_BANNER_SIZE != -1) {
            tabCount = DEFAULT_BANNER_SIZE;
        } else {
            if (pager != null) tabCount = pager.getAdapter().getCount();
            if (recyclerView != null) tabCount = recyclerView.getChildCount();
        }
        addDots(tabCount);
    }

    public void setSelectedDotResource(int mSelectedSrc) {
        this.mSelectedSrc = mSelectedSrc;
    }

    public void setUnSelectedDotResource(int mUnSelectedSrc) {
        this.mUnSelectedSrc = mUnSelectedSrc;
    }

    private RecyclerView recyclerView;

    public void setRecyclerView(RecyclerView recyclerView) {
        this.pager = null;
        this.recyclerView = recyclerView;
        notifyDataSetChanged();
    }

    public void setDots(int count){
        removeAllViews();
        addDots(count);
    }

    public static class TextDotViewUtil{
        //给viewpager设置滚动监听 关联textView
        public static void setDotTextViewPager(final TextView dotView, final ViewPager viewPager) {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    dotView.setText(getDotText(viewPager, position));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            dotView.setText(getDotText(viewPager, 0));
        }

        private static String getDotText(ViewPager viewPager, int position) {
            int count = 0;
            String dottext = "";
            if (viewPager != null) count = viewPager.getAdapter().getCount();
            if (count != 0) {
                dottext = (position + 1) + "/" + count;
            } else {
                dottext = "";
            }
            return dottext;
        }
    }
}
