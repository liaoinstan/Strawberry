package com.shelwee.update.utils;

import android.annotation.SuppressLint;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

import com.shelwee.updater.R;

public class MoreTextUtil {
	
	public static boolean hasMesure = false;	//是否已经执行过一次
	
	public static void setMore(TextView textV,String content){
		textV.setText(content);
		setMore(textV,"...","查看更多");
	}
	
	public static void setMore(TextView textV){
		setMore(textV,"...","查看更多");
	}

	@SuppressLint("NewApi")
	public static void setMore(final TextView textV, final String ellipsis, final String strmore) {
		if (textV == null) {
			return;
		}
		if (2147483647 == textV.getMaxLines()) textV.setMaxLines(5);
		textV.setEllipsize(TruncateAt.END);
		textV.setVerticalScrollBarEnabled(true);
		
		hasMesure = false;
		
		//添加布局变化监听器,view 布局完成时调用，每次view改变时都会调用
		ViewTreeObserver vto = textV.getViewTreeObserver();
	    vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	        @Override
	        public void onGlobalLayout() {
	        	if (!hasMesure) {
	        		int maxLines = textV.getMaxLines();
	        		int lines = textV.getLineCount();
	        		
	        		//如果文字的行数超过最大行数，展示缩略的textview
	        		if (lines >= maxLines) {
	        			Layout layout=textV.getLayout();
	        			String str=layout.getText().toString();
	        			int end = layout.getLineEnd(maxLines-2);
	        			str = str.substring(0, end);					//缩略的文字
	        			String strall = textV.getText().toString();		//完整的文字
		        		hasMesure = true;
	        			
		        		SpannableString spanstr;
		        		//如果以换行符结尾，则不再换行
		        		if (str.endsWith("\n")) {
		        			spanstr = new SpannableString(str + ellipsis + strmore);
						}else {
							spanstr = new SpannableString(str + "\n" + ellipsis + strmore);
						}
		        		
		        		//设置“查看更多”的点击事件
	        			spanstr.setSpan(new MyClickableSpan(strall,textV.getResources().getColor(R.color.cc_orage)), spanstr.length() - strmore.length(),
	        					spanstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

	        			textV.setText(spanstr);
	        			//移除默认背景色
	        			textV.setHighlightColor(textV.getResources().getColor(android.R.color.transparent));
	        			textV.setMovementMethod(LinkMovementMethod.getInstance());
	        		}
				}
	        }
	    });
		
	}
	
	static class MyClickableSpan extends ClickableSpan{
		private String str;
		private int color;
		public MyClickableSpan(String str,int color) {
			this.str = str;
			this.color = color;
		}
		
		@Override
		public void onClick(View view) {
			((TextView)view).setMovementMethod(new ScrollingMovementMethod());
			((TextView)view).setText(str);
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			super.updateDrawState(ds);
			ds.setColor(color);			//设置“查看更多”字体颜色
			ds.setUnderlineText(false);	//设置“查看更多”无下划线，默认有
			ds.clearShadowLayer();
		}
	}
}
