package com.magicbeans.xgate.sharesdk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.shopcart.ShopCart;

import java.util.List;


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

    //这些是默认分享文本，分析前请重新设置内容
    private String title = "草莓网";                      //微信、qq
    private String content = "快加入草莓网今日秒杀美妆热卖活动!惊喜优惠带走顶级产品。活动即将结束,马上行动起来!";                     //all
    private String url = "https://cn.strawberrynet.com/m/mmain.aspx";
    private String img = "https://a.cdnsbn.com/m/images/common/mgift_dailyspecials.jpg";


    public ShareDialog setShareData(String title, String content, String url, String img) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.img = img;
        return this;
    }

    //分享app首页
    public ShareDialog setShareHome() {
        this.title = "草莓网";
        this.content = "快加入草莓网今日秒杀美妆热卖活动!惊喜优惠带走顶级产品。活动即将结束,马上行动起来!";
        this.url = "https://cn.strawberrynet.com/m/mmain.aspx";
        this.img = "https://a.cdnsbn.com/m/images/common/mgift_dailyspecials.jpg";
        return this;
    }

    //分享购物车内容
    public ShareDialog setShareShopCart(List<ShopCart> shopCartList) {
        if (StrUtil.isEmpty(shopCartList)) return this;
        //TODO：现在还不清楚草莓怎么分享多件商品，这里先写成分享单件（取第一件）
        ShopCart shopCart = shopCartList.get(0);
        this.title = shopCart.getBrandName();
        this.content = Html.fromHtml(shopCart.getProdName()).toString();
        //TODO:现在还不知道商品详情页的链接，分享链接暂且写成首页
        this.url = "https://cn.strawberrynet.com/m/mmain.aspx";
        this.img = shopCart.getHeaderImg();
        return this;
    }


    //分享购物车内容
    public ShareDialog setShareProduct2(Product2 product2) {
        this.title = product2.getBrandName();
        this.content = product2.getProdName();
        //TODO:现在还不知道商品详情页的链接，分享链接暂且写成首页
        this.url = "https://cn.strawberrynet.com/m/mmain.aspx";
        this.img = product2.getHeaderImg();
        return this;
    }


}
