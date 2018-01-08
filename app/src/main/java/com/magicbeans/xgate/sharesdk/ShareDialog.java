package com.magicbeans.xgate.sharesdk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.magicbeans.xgate.R;


/**
 * liaoinstan 分享弹出窗口
 */
public class ShareDialog extends Dialog implements View.OnClickListener {
    private TextView text_wechat;
    private TextView text_wechatmoments;
    private TextView text_qq;
    private TextView text_qqzone;
    private TextView text_sinaweibo;
    private Context context;

    public ShareDialog(Context context) {
        super(context, R.style.PopupDialog);
        this.context = context;
        setDialog();
    }

    private void setDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sharesdk, null);
        text_wechat = (TextView) mView.findViewById(R.id.text_share_wechat);
        text_wechatmoments = (TextView) mView.findViewById(R.id.text_share_wechatmoments);
        text_qq = (TextView) mView.findViewById(R.id.text_share_qq);
        text_qqzone = (TextView) mView.findViewById(R.id.text_share_qqzone);
        text_sinaweibo = (TextView) mView.findViewById(R.id.text_share_sinaweibo);
        text_wechat.setOnClickListener(this);
        text_wechatmoments.setOnClickListener(this);
        text_qq.setOnClickListener(this);
        text_qqzone.setOnClickListener(this);
        text_sinaweibo.setOnClickListener(this);

        this.setCanceledOnTouchOutside(true);    //点击外部关闭

        super.setContentView(mView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = this.getWindow();
        win.setGravity(Gravity.BOTTOM);    //从下方弹出
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.text_share_wechat) {
            ShareHelper.showShareWeixin(context, title, url, content, img);
        } else if (i == R.id.text_share_wechatmoments) {
            ShareHelper.showSharePengyouquan(context, title, url, content, img);
        } else if (i == R.id.text_share_qq) {
            ShareHelper.showShareQQ(context, title, url, content, img);
        } else if (i == R.id.text_share_qqzone) {
            ShareHelper.showShareQQZone(context, title, url, content, img);
        } else if (i == R.id.text_share_sinaweibo) {
            ShareHelper.showShareSinaWeibo(context, title, url, content, img);
        }
        dismiss();
    }


    private String title = "测试标题";                      //微信、qq
    private String content = "草莓网，我是分享文本，我是分享文本，我是分享文本";                     //all
    private String url = "http://sharesdk.cn";
    private String img = "http://pic.jj20.com/up/allimg/811/0Q314221532/140Q3221532-9.jpg";
//    private String img = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3956038193,2397454070&fm=58&s=0614EE22C7E05D030C5498D40000C0B3";


    public ShareDialog setShareData(String title, String content, String url, String img) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.img = img;
        return this;
    }
}
