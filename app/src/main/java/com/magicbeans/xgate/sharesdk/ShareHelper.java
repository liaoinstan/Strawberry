package com.magicbeans.xgate.sharesdk;

import android.content.Context;

import com.ins.common.utils.L;
import com.ins.common.utils.ToastUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/1/4.
 */

public class ShareHelper {

    public static void showShareWeixin(Context context, String title, String url, String content, String img) {
        showShare(context, ShareSDK.getPlatform(Wechat.NAME).getName(), title, url, content, img);
    }

    public static void showShareQQ(Context context, String title, String url, String content, String img) {
        showShare(context, ShareSDK.getPlatform(QQ.NAME).getName(), title, url, content, img);
    }

    public static void showSharePengyouquan(Context context, String title, String url, String content, String img) {
        showShare(context, ShareSDK.getPlatform(WechatMoments.NAME).getName(), title, url, content, img);
    }

    public static void showShareQQZone(Context context, String title, String url, String content, String img) {
        showShare(context, ShareSDK.getPlatform(QZone.NAME).getName(), title, url, content, img);
    }

    public static void showShareSinaWeibo(Context context, String title, String url, String content, String img) {
        showShare(context, ShareSDK.getPlatform(SinaWeibo.NAME).getName(), title, url, content, img);
    }

    public static void showShare(Context context, String platformToShare, String title, String url, String content, String img) {
        OnekeyShare oks = new OnekeyShare();
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }

        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(img);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                String text = platform.getName() + " completed at " + i;
//                ToastUtil.showToastShort(text);
//                L.e(text);
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                String text = platform.getName() + "caught error at " + i;
//                ToastUtil.showToastShort(text);
//                L.e(text);
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                String text = platform.getName() + " canceled at " + i;
//                ToastUtil.showToastShort(text);
//                L.e(text);
//            }
//        });

        // 启动分享GUI
        oks.show(context);
    }
}
