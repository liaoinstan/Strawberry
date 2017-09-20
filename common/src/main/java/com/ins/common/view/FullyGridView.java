package com.ins.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

/**
 * 不推荐使用，这种重写会导致getView方法被多次调用，影响性能，虽然在UI上看不出变化
 * 这个module是测试用途，不专注UI效果，为了保持框架简洁不引入其他控件所以还是继续沿用了这种方法
 */
public class FullyGridView extends GridView {
	public FullyGridView(Context context) {
		super(context);
		this.setVerticalScrollBarEnabled(false);
	}
	public FullyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setVerticalScrollBarEnabled(false);
	}
    public FullyGridView(Context context, AttributeSet attrs, int defStyle) {
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