package com.ins.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class FullyListView extends ListView {
	public FullyListView(Context context) {
		super(context);
		this.setVerticalScrollBarEnabled(false);
	}
	public FullyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setVerticalScrollBarEnabled(false);
	}
    public FullyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setVerticalScrollBarEnabled(false);
	}
    
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}