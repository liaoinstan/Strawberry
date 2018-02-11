package com.shelwee.update.dialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.shelwee.update.utils.MoreTextUtil;
import com.shelwee.updater.R;
/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class VersionDialog extends Dialog{
	
	public static final int STYLE_UPDATE_NOMAL = 0;
	public static final int STYLE_UPDATE_FORCE = 1;
	public static final int STYLE_WIFI_NOMAL   = 2;
	public static final int STYLE_WIFI_FORCEL  = 3;
	public static final int STYLE_INSTALL      = 4;
	
	private Context context;
	private EditText editText;
	private TextView text_size;
	private TextView text_log;
    private TextView positiveButton, negativeButton;
    private String msg;
    private String size;
    private String version;
    private String positiveStr;
    private String negativeStr;
    private int dialogType;
 
    public VersionDialog(Context context){
    	super(context,R.style.VersionDialog);
    	this.context = context;
    	setMsgDialog();
    }
    
    public VersionDialog(Context context,String msg) {
        super(context,R.style.VersionDialog);
        this.context = context;
        this.msg = msg;
        setMsgDialog();
    }
    
    public VersionDialog(Context context,String positiveStr,String negativeStr,int dialogType) {
        super(context,R.style.VersionDialog);
        this.context = context;
        this.positiveStr = positiveStr;
        this.negativeStr = negativeStr;
        this.dialogType = dialogType;
        setMsgDialog();
    }
    
    public VersionDialog(Context context,String msg,String size,String version,String positiveStr,String negativeStr,int dialogType) {
        super(context,R.style.VersionDialog);
        this.context = context;
        this.msg = msg;
        this.size = size;
        this.version = version;
        this.positiveStr = positiveStr;
        this.negativeStr = negativeStr;
        this.dialogType = dialogType;
        setMsgDialog();
    }
 
    private void setMsgDialog() {
    	View mView = null;
    	switch (dialogType) {
		case STYLE_UPDATE_NOMAL:
		{
			//普通更新，更新弹窗
			mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_version, null);
    		text_log = (TextView) mView.findViewById(R.id.text_version_log);
            text_size = (TextView) mView.findViewById(R.id.text_version_size);
            positiveButton = (TextView) mView.findViewById(R.id.positiveButton);
            negativeButton = (TextView) mView.findViewById(R.id.negativeButton);
            
            text_log.setText(msg==null||"".equals(msg)?"这一秒不放弃，下一秒有奇迹！":msg);
            MoreTextUtil.setMore(text_log);
            SpannableString strSpan = new SpannableString(size+"\n"+version);
    		strSpan.setSpan(new RelativeSizeSpan(0.7f), size.length(),strSpan.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//放大用户名字体
            text_size.setText(strSpan);
            
            positiveButton.setText(positiveStr==null||"".equals(positiveStr)?"立刻更新":positiveStr);
            negativeButton.setText(negativeStr==null||"".equals(negativeStr)?"我再想想":negativeStr);
            positiveButton.setOnClickListener(listener);
            negativeButton.setOnClickListener(listener);
            this.setCanceledOnTouchOutside(false);	//点击外部不会取消对话框
			break;
		}
		case STYLE_UPDATE_FORCE:
		{
			//强制更新，更新弹窗
			mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_force, null);
    		text_log = (TextView) mView.findViewById(R.id.text_version_log);
    		text_size = (TextView) mView.findViewById(R.id.text_version_size);
            positiveButton = (TextView) mView.findViewById(R.id.positiveButton);
            negativeButton = (TextView) mView.findViewById(R.id.negativeButton);
            text_log.setText(msg==null||"".equals(msg)?"这一秒不放弃，下一秒有奇迹！":msg);
            MoreTextUtil.setMore(text_log);
            SpannableString strSpan = new SpannableString(size+"\n"+version);
    		strSpan.setSpan(new RelativeSizeSpan(0.7f), size.length(),strSpan.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//放大用户名字体
            text_size.setText(strSpan);
            
            positiveButton.setText(positiveStr==null||"".equals(positiveStr)?"立刻更新":positiveStr);
            negativeButton.setText(negativeStr==null||"".equals(negativeStr)?"退出应用":negativeStr);
            positiveButton.setOnClickListener(listener);
            negativeButton.setOnClickListener(exitListener);
            this.setCancelable(false);				//强制更新，你无法拒绝
            this.setCanceledOnTouchOutside(false);	//点击外部不会取消对话框
			break;
		}
		case STYLE_WIFI_NOMAL:
			//普通更新,wifi弹窗
			mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_wifi, null);
			positiveButton = (TextView) mView.findViewById(R.id.positiveButton);
            negativeButton = (TextView) mView.findViewById(R.id.negativeButton);
            
            positiveButton.setText(positiveStr==null||"".equals(positiveStr)?"继续下载":positiveStr);
            negativeButton.setText(negativeStr==null||"".equals(negativeStr)?"取消下载":negativeStr);
            positiveButton.setOnClickListener(listener);
            negativeButton.setOnClickListener(listener);
            this.setCanceledOnTouchOutside(false);	//点击外部不会取消对话框
			break;
		case STYLE_WIFI_FORCEL:
			//强制更新,wifi弹窗
			mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_wifi, null);
			positiveButton = (TextView) mView.findViewById(R.id.positiveButton);
            negativeButton = (TextView) mView.findViewById(R.id.negativeButton);
            
            positiveButton.setText(positiveStr==null||"".equals(positiveStr)?"退出应用":positiveStr);
            negativeButton.setText(negativeStr==null||"".equals(negativeStr)?"继续下载":negativeStr);
            positiveButton.setOnClickListener(listener);
            negativeButton.setOnClickListener(exitListener);
            this.setCancelable(false);				//强制更新，你无法拒绝
            this.setCanceledOnTouchOutside(false);	//点击外部不会取消对话框
			break;
		case STYLE_INSTALL:
			//安装弹窗
			mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_install, null);
			positiveButton = (TextView) mView.findViewById(R.id.positiveButton);
            negativeButton = (TextView) mView.findViewById(R.id.negativeButton);
            
            positiveButton.setText(positiveStr==null||"".equals(positiveStr)?"退出应用":positiveStr);
            negativeButton.setText(negativeStr==null||"".equals(negativeStr)?"重新安装":negativeStr);
            positiveButton.setOnClickListener(listener);
            negativeButton.setOnClickListener(exitListener);
            this.setCancelable(false);				//强制安装，你无法拒绝
            this.setCanceledOnTouchOutside(false);	//点击外部不会取消对话框
			break;
		}
        
        super.setContentView(mView);
    }
    
    @Override
    public void show() {
    	super.show();
    	Window dialogWindow = this.getWindow();  
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); 
        /////////获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);;
		wm.getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		/////////设置高宽
        lp.width = (int) (screenWidth * 0.85); // 宽度  
//        lp.height = (int) (lp.width*0.65); // 高度  
        dialogWindow.setAttributes(lp);  
    }
     
    public View getEditText(){
        return editText;
    }
     
     @Override
    public void setContentView(int layoutResID) {
    }
 
    @Override
    public void setContentView(View view, LayoutParams params) {
    }
 
    @Override
    public void setContentView(View view) {
    }
 
    /**
     * 确定键监听器
     * @param listener
     */ 
    public void setOnPositiveListener(View.OnClickListener listener){ 
        positiveButton.setOnClickListener(listener); 
    } 
    /**
     * 取消键监听器
     * @param listener
     */ 
    public void setOnNegativeListener(View.OnClickListener listener){ 
        negativeButton.setOnClickListener(listener); 
    }
    
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	VersionDialog.this.dismiss();
        }
    };
    
    private View.OnClickListener exitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	VersionDialog.this.dismiss();
        	((Activity)context).finish();
        }
    };
}
